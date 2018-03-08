package ecommerce.responses;

import org.json.JSONObject;

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
}
