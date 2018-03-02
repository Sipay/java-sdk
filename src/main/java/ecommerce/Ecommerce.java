package ecommerce;

import com.google.gson.Gson;
import config.Config;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        this.logger = getLogger();
    }

    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
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
    public JSONObject authorization(Object payMethod, Amount amount, JSONObject payload) {
        // Validar metodo de pago
        validatePayMethod(payMethod);

        payload.put("amount", Double.parseDouble(amount.amount));
        payload.put("currency", amount.currency);

//      Actualizar con los datos de la tarjeta
        payload = updatePayload(payMethod, payload);

        return send(payload, "authorization");
    }

    /**
     * Send a request of cancellation to Sipay.
     *
     * @param transactionId: identificator of transaction.
     * @return Cancellation(Response): object that contain response of MDWR API
     */
    public JSONObject cancellation(String transactionId) {

        JSONObject payload = new JSONObject();
        payload.put("transactiond", transactionId);

        return send(payload, "cancellation");
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param method:  payment method
     * @param amount:  amount of the operation.
     * @param payload: {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                 custom_02: custom field 2, token: if this argument is set, it register payMethod with this token,
     *                 transactionId: identificator of transaction        }
     * @return Refund: object that contain response of MDWR API.
     */
    public JSONObject refund(Object method, Amount amount, JSONObject payload) {

        payload.put("amount", Double.parseDouble(amount.amount));
        payload.put("currency", amount.currency);

        String transactionId = ((String) payload.opt("transactionId"));
        if (transactionId == null && method == null || transactionId != null && method != null) {
            logger.severe("Incorrect identificator.");
            throw new java.lang.RuntimeException("Incorrect identificator.");
        } else if (transactionId != null) {
            payload.put("transaction_id", transactionId);
        } else {
            payload = updatePayload(method, payload);
        }

        return send(payload, "refund");
    }

    /**
     * Send a request of register to Sipay.
     *
     * @param card:  Card that register.
     * @param token: token will be associate to card.
     * @return Register: object that contain response of MDWR API.
     */
    public JSONObject register(Object card, String token) {

        JSONObject payload = new JSONObject();
        payload.put("token", token);
        payload = updatePayload(card, payload);

        return send(payload, "register");
    }

    /**
     * Send a request of remove a registry of a token to Sipay.
     *
     * @param token: token of a card.
     * @return Unregister: object that contain response of MDWR API.
     */
    public JSONObject unregister(String token) {

        JSONObject payload = new JSONObject();
        payload.put("token", token);

        return send(payload, "unregister");
    }

    /**
     * Send a request for search a card in Sipay.
     *
     * @param token: token of card.
     * @return Card(Response): object that contain response of MDWR API.
     */
    public JSONObject card(String token) {

        JSONObject payload = new JSONObject();
        payload.put("token", token);

        return send(payload, "card");
    }

    /**
     * Send a query to Sipay.
     *
     * @param transactionId: identificator of transaction.
     * @param order:         ticket of the operation.
     * @return Query: object that contain response of MDWR API.
     */
    public JSONObject query(String transactionId, String order) {

        JSONObject payload = new JSONObject();
        payload.put("transaction_id", transactionId);
        payload.put("order", order);

        return send(payload, "query");
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

            Class<?> cls = Class.forName("ecommerce.responses." + capitalize(endpoint));
            Constructor constructor = cls.getConstructor(JSONObject.class);
            Object object = constructor.newInstance(new JSONObject(EntityUtils.toString(resp.getEntity())));
            response = new JSONObject(new Gson().toJson(object));

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

    private void validatePayMethod(Object payMethod) {
        try {
            Class.forName("paymethod." + payMethod.getClass().getSimpleName());
        } catch (ClassNotFoundException e) {
            logger.severe("PayMethod isn't a PayMethod");
            throw new java.lang.RuntimeException("PayMethod isn't a PayMethod");
        }
    }

    private JSONObject updatePayload(Object payMethod, JSONObject payload) {
        Class<?> payMethodClass = payMethod.getClass();
        for (final Method method : payMethodClass.getDeclaredMethods()) {
            try {
                if (method.getName().equals("update")) {
                    method.invoke(payMethod, payload);
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.severe(e.getMessage());
            }
        }

        return payload;
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

    private Logger getLogger() {
        Logger logger = Logger.getLogger(Ecommerce.class.getName());
        FileHandler fh;

        try {
            fh = new FileHandler(this.file, this.maxFileSize, this.backupFileRotation, true);
            logger.addHandler(fh);
            logger.setLevel(Level.parse(this.level.toUpperCase()));

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            return logger;
        } catch (SecurityException | IOException e) {
            logger.severe(e.getMessage());
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }
}