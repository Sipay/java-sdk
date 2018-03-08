package sipay.responses;

import org.json.JSONObject;

public class Card extends Response {

    String expiredAt;
    String card;
    String cardMask;
    String token;

    public Card(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload.length() > 0) {
            this.expiredAt = getValue(payload.opt("expired_at"));
            this.card = getValue(payload.opt("card"));
            this.cardMask = getValue(payload.opt("card_mask"));
            this.token = getValue(payload.opt("token"));
        }

    }
}
