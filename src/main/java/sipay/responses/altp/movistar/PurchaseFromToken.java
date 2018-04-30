package sipay.responses.altp.movistar;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import sipay.responses.Response;

public class PurchaseFromToken extends Response {
    @Getter @Setter private String authToken;
    @Getter @Setter private String responseType;
    @Getter @Setter private JSONObject userId;
    @Getter @Setter private String purchaseCode;
    @Getter @Setter private String amount;
    @Getter @Setter private String order;
    @Getter @Setter private String transactionId;
    @Getter @Setter private String status;


    public PurchaseFromToken(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.authToken = getValue(payload.opt("auth_token"));
            this.responseType = getValue(payload.opt("response_type"));
            this.userId = ((JSONObject) payload.opt("user_id"));
            this.purchaseCode = getValue(payload.opt("purchase_code"));
            this.amount = getValue(payload.opt("amount"));
            this.order = getValue(payload.opt("order"));
            this.transactionId = getValue(payload.opt("transaction_id"));
            this.status = getValue(payload.opt("status"));
        }
    }
}