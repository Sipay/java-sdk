package sipay.responses;

import sipay.Amount;
import org.json.JSONObject;

public class Refund extends Response {

    String approval;
    String authorizator;
    String cardTrade;
    String cardType;
    String maskedCard;
    String order;
    String reconciliation;
    String transactionId;
    Amount amount;

    public Refund(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload.length() > 0) {
            this.approval = getValue(payload.opt("approval"));
            this.authorizator = getValue(payload.opt("authorizator"));
            this.cardTrade = getValue(payload.opt("card_trade"));
            this.cardType = getValue(payload.opt("card_type"));
            this.maskedCard = getValue(payload.opt("masked_card"));
            this.order = getValue(payload.opt("order"));
            this.reconciliation = getValue(payload.opt("reconciliation"));
            this.transactionId = getValue(payload.opt("transaction_id"));

            if (getValue(payload.opt("amount")) != null && getValue(payload.opt("currency")) != null) {
                this.amount = new Amount(Integer.parseInt(getValue(payload.opt("amount"))), getValue(payload.opt("currency")));
            }
        }
    }
}
