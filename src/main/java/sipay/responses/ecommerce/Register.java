package sipay.responses.ecommerce;

import org.json.JSONObject;
import sipay.responses.Response;

public class Register extends Response {

    String expiredAt;
    String cardMask;
    String token;

    public Register(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload.length() > 0) {
            this.expiredAt = getValue(payload.opt("expired_at"));
            this.cardMask = getValue(payload.opt("card_mask"));
            this.token = getValue(payload.opt("token"));
        }
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public String getCardMask() {
        return cardMask;
    }

    public String getToken() {
        return token;
    }
}
