package sipay.paymethod.ecommerce;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

import java.util.regex.Pattern;

public class StoredCard implements PayMethod {
    String token;

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
}