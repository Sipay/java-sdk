package sipay.responses.paypal;

import org.json.JSONObject;
import sipay.responses.Response;

public class Payment extends Response {
    String transactionId;
    String amount;
    String billingId;
    String fee;
    String tax;
    String transactionType;
    String datetime;
    String paymentType;
    String pendingReason;
    String currency;
    String requestId;
    String status;

    public Payment(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.transactionId = getValue(payload.opt("transaction_id"));
            this.amount = getValue(payload.opt("amount"));


            this.billingId = getValue(payload.opt("billing_id"));
            this.fee = getValue(payload.opt("fee"));
            this.tax = getValue(payload.opt("tax"));
            this.transactionType = getValue(payload.opt("transaction_type"));
            this.datetime = getValue(payload.opt("datetime"));
            this.paymentType = getValue(payload.opt("payment_type"));
            this.pendingReason = getValue(payload.opt("pending_reason"));
            this.currency = getValue(payload.opt("currency"));
            this.requestId = getValue(payload.opt("request_id"));
            this.status = getValue(payload.opt("status"));
        }
    }
}
