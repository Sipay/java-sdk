package sipay.responses.ecommerce;

import org.json.JSONObject;
import sipay.Amount;
import sipay.responses.Response;

public class Confirm extends Response {

    Amount amount;
    String currency;
    String order;
    String reconciliation;
    String cardTrade;
    String cardType;
    String maskedCard;
    String transactionId;
    String sequence;
    String authorizator;
    String approval;

    public Confirm(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));

        if (payload != null && payload.length() > 0) {
            this.currency = getValue(payload.opt("currency"));
            this.order = getValue(payload.opt("order"));
            this.reconciliation = getValue(payload.opt("reconciliation"));
            this.cardTrade = getValue(payload.opt("card_trade"));
            this.cardType = getValue(payload.opt("card_type"));
            this.maskedCard = getValue(payload.opt("masked_card"));
            this.transactionId = getValue(payload.opt("transaction_id"));
            this.sequence = getValue(payload.opt("sequence"));
            this.authorizator = getValue(payload.opt("authorizator"));
            this.approval = getValue(payload.opt("approval"));

            if (payload.has("amount") && payload.get("amount") != null
                    && payload.has("currency") && payload.get("amount") != null) {
                this.amount = new Amount(Integer.parseInt(getValue(payload.opt("amount"))), getValue(payload.opt("currency")));
            }
        }
    }

    public Amount getAmount() {
        return amount;
    }

    public String getOrder() {
        return order;
    }

    public String getReconciliation() {
        return reconciliation;
    }

    public String getCardTrade() {
        return cardTrade;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMaskedCard() {
        return maskedCard;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getSequence() {
        return sequence;
    }

    public String getAuthorizator() {
        return authorizator;
    }

    public String getApproval() {
        return approval;
    }
}
