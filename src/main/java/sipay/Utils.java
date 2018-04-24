package sipay;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
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
import sipay.altp.Paypal;

import javax.annotation.Nonnull;
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

public class Utils {
    public String key;
    public String secret;
    public String resource;
    public String environment;
    public String version;
    public String mode;
    public Integer connection;
    public Integer process;
    public Logger logger;

    public Utils(@Nonnull String path) {
        Config config = new Config(path);

        this.key = config.getProperty("key");
        this.secret = config.getProperty("secret");
        this.resource = config.getProperty("resource");
        this.environment = config.getProperty("environment");
        this.version = config.getProperty("version");
        this.mode = config.getProperty("mode");
        this.connection = Integer.parseInt(config.getProperty("connection"));
        this.process = Integer.parseInt(config.getProperty("process"));

        this.logger = getLogger(
                config.getProperty("file"),
                config.getProperty("level"),
                Integer.parseInt(config.getProperty("maxFileSize")),
                Integer.parseInt(config.getProperty("backupFileRotation")));
    }

    public JSONObject send(@Nonnull JSONObject payload, @Nonnull String path) {

        logger.info("Start send to endpoint ");
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(connection * 1000).
                setConnectionRequestTimeout(process * 1000).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

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

        logger.info("End send to endpoint ");
        return response;
    }

    public JSONObject generateBody(@Nonnull JSONObject payload) {
        JSONObject params = new JSONObject();

        params.put("key", key);
        params.put("resource", resource);
        params.put("nonce", String.valueOf(Instant.now().getEpochSecond()));
        params.put("mode", mode);
        params.put("payload", payload);

        return params;
    }

    public String generateSignature(@Nonnull String body) {
        try {
            Mac sha256_HMAC = Mac.getInstance("Hmac" + mode);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            byte[] hash = sha256_HMAC.doFinal(body.getBytes());

            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Logger getLogger(@Nonnull String file, @Nonnull String level, @Nonnull Integer maxFileSize, @Nonnull Integer backupFileRotation) {
        Logger logger = Logger.getLogger(Paypal.class.getName());
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
            throw new RuntimeException(e.getMessage());
        }
    }

    public void validateSchema(@Nonnull String name, @Nonnull JSONObject payload) {

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
                throw new RuntimeException("ERROR: schema: " + message);
            }

        } catch (ProcessingException | IOException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
