package sipay.paymethod.ecommerce;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

import java.util.regex.Pattern;

public class StoredCard implements PayMethod {
    String token;
    String endpoint;

    public StoredCard(String token) {
        setToken(token);
    }

    public JSONObject update(JSONObject payload) {
        payload.put("token", this.token);

        return payload;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (!Pattern.compile("^[\\w-]{6,128}$").matcher(token).find()) {
            throw new RuntimeException("Token doesn't have a correct value.");
        }

        this.token = token;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}