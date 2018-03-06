package ecommerce;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import config.Config;
import ecommerce.responses.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import paymethod.PayMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Ecommerce {

    String file;
    String level;
    Integer maxFileSize;
    Integer backupFileRotation;
    String key;
    String secret;
    String resource;
    String environment;
    String version;
    String mode;
    Integer connection;
    Integer process;
    Logger logger;

    public Ecommerce(String path) {
        Config config = new Config(path);

        this.file = config.getProperty("file");
        this.level = config.getProperty("level");
        this.maxFileSize = Integer.parseInt(config.getProperty("maxFileSize"));
        this.backupFileRotation = Integer.parseInt(config.getProperty("backupFileRotation"));
        this.key = config.getProperty("key");
        this.secret = config.getProperty("secret");
        this.resource = config.getProperty("resource");
        this.environment = config.getProperty("environment");
        this.version = config.getProperty("version");
        this.mode = config.getProperty("mode");
        this.connection = Integer.parseInt(config.getProperty("connection"));
        this.process = Integer.parseInt(config.getProperty("process"));
        this.logger = getLogger(this.file, this.level, this.maxFileSize, this.backupFileRotation);
    }

    private JSONObject send(JSONObject payload, String endpoint) {

        logger.info("Start send to endpoint " + endpoint);
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(connection * 1000).
                setConnectionRequestTimeout(process * 1000).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        String path = "https://" + environment + ".sipay.es/mdwr/" + version + "/" + endpoint;
        HttpPost post = new HttpPost(path);

        JSONObject response = new JSONObject();
        JSONObject params = generateBody(payload);

        try {
            post.setEntity(new StringEntity(params.toString(), "UTF8"));
            post.setHeader("Content-type", "application/json");
            post.addHeader("Content-Signature", generateSignature(params.toString()));

            HttpResponse resp = client.execute(post);

            response = new JSONObject(EntityUtils.toString(resp.getEntity()));
        } catch (Exception E) {
            logger.severe("Exception: " + E.getMessage());
            response.put("url", path);
            response.put("body", params);
            response.put("code", -1);
            response.put("description", E.getMessage());
        }

        logger.info("End send to endpoint " + endpoint);
        return response;
    }

    private JSONObject generateBody(JSONObject payload) {
        JSONObject params = new JSONObject();

        params.put("key", key);
        params.put("resource", resource);
        params.put("nonce", String.valueOf(Instant.now().getEpochSecond()));
        params.put("mode", mode);
        params.put("payload", payload);

        return params;
    }

    private String generateSignature(String body) {
        try {
            Mac sha256_HMAC = Mac.getInstance("Hmac" + mode);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            byte[] hash = sha256_HMAC.doFinal(body.getBytes());

            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.severe(e.getMessage());
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    private Logger getLogger(String file, String level, Integer maxFileSize, Integer backupFileRotation) {
        Logger logger = Logger.getLogger(Ecommerce.class.getName());
        FileHandler fh;

        try {
            fh = new FileHandler(file, maxFileSize, backupFileRotation, true);
            logger.addHandler(fh);
            logger.setLevel(Level.parse(level.toUpperCase()));

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            return logger;
        } catch (SecurityException | IOException e) {
            logger.severe(e.getMessage());
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    private void validateSchema(String name, JSONObject payload) {

        try {
            ProcessingReport report;
            JsonNode value = JsonLoader.fromURL(this.getClass().getResource("/schema/" + name + ".json"));
            final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            final JsonSchema schema = factory.getJsonSchema(value);

            JsonNode inputJson = JsonLoader.fromString(payload.toString());

            report = schema.validate(inputJson);

            if (!report.isSuccess()) {
                String message = report.toString().replaceAll("\n", " | ");
                logger.severe("ERROR: schema: " + message);
                throw new java.lang.RuntimeException("ERROR: schema: " + message);
            }

        } catch (ProcessingException | IOException e) {
            logger.severe(e.getMessage());
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    /**
     * Send a request of authorization to Sipay.
     *
     * @param payMethod: payment method of authorization (it can be an object of Card, StoredCard or FastPay).
     * @param amount:    amount of the operation.
     * @param payload:   {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                   custom_02: custom field 2, token: if this argument is set, it register payMethod with this token}
     * @return Authorization: object that contain response of MDWR API
     */
    public Authorization authorization(PayMethod payMethod, Amount amount, JSONObject payload) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);

        validateSchema(methodName, payload);

        payload = payMethod.update(payload);

        return new Authorization(send(payload, methodName));
    }

    /**
     * Send a request of cancellation to Sipay.
     *
     * @param transactionId: identificator of transaction.
     * @return Cancellation(Response): object that contain response of MDWR API
     */
    public Cancellation cancellation(String transactionId) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("transaction_id", transactionId);

        validateSchema(methodName, payload);

        return new Cancellation(send(payload, methodName));
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param method:  payment method
     * @param amount:  amount of the operation.
     * @param payload: {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                 custom_02: custom field 2, token: if this argument is set, it register payMethod with this token }
     * @return Refund: object that contain response of MDWR API.
     */
    public Refund refund(PayMethod method, Amount amount, JSONObject payload) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);

        validateSchema(methodName, payload);

        if (payload.opt("transactionId") == null && method == null) {
            logger.severe("Incorrect identificator.");
            throw new java.lang.RuntimeException("Incorrect identificator.");
        } else {
            payload = method.update(payload);
        }

        return new Refund(send(payload, methodName));
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param transactionId: transactionId: identificator of transaction
     * @param amount:        amount of the operation.
     * @param payload:       {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                       custom_02: custom field 2, token: if this argument is set, it register payMethod with this token }
     * @return Refund: object that contain response of MDWR API.
     */
    public Refund refund(String transactionId, Amount amount, JSONObject payload) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);

        validateSchema(methodName, payload);

        if (transactionId == null) {
            logger.severe("Incorrect identificator.");
            throw new java.lang.RuntimeException("Incorrect identificator.");
        } else {
            payload.put("transaction_id", transactionId);
        }

        return new Refund(send(payload, methodName));
    }

    /**
     * Send a request of register to Sipay.
     *
     * @param card:  Card that register.
     * @param token: token will be associate to card.
     * @return Register: object that contain response of MDWR API.
     */
    public Register register(PayMethod card, String token) {

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("token", token);
        payload = card.update(payload);

        validateSchema(methodName, payload);

        return new Register(send(payload, methodName));
    }

    /**
     * Send a request of remove a registry of a token to Sipay.
     *
     * @param token: token of a card.
     * @return Unregister: object that contain response of MDWR API.
     */
    public Unregister unregister(String token) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("token", token);

        validateSchema(methodName, payload);

        return new Unregister(send(payload, methodName));
    }

    /**
     * Send a request for search a card in Sipay.
     *
     * @param token: token of card.
     * @return Card(Response): object that contain response of MDWR API.
     */
    public Card card(String token) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("token", token);

        validateSchema(methodName, payload);

        return new Card(send(payload, methodName));
    }

    /**
     * Send a query to Sipay.
     *
     * @param payload: {transactionId: identificator of transaction, order: ticket of the operation.}
     * @return Query: object that contain response of MDWR API.
     */
    public Query query(JSONObject payload) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        if (payload.opt("transaction_id") == null && payload.opt("order") == null || payload.opt("transaction_id") != null && payload.opt("order") != null) {
            logger.severe("Incorrect query.");
            throw new java.lang.RuntimeException("Incorrect query.");
        }

        validateSchema(methodName, payload);

        return new Query(send(payload, methodName));
    }
}