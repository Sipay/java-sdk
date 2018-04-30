package sipay.responses.altp.paypal;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import sipay.responses.Response;

public class Payment extends Response {
    @Getter @Setter private String paymentType;
    @Getter @Setter private String amount;
    @Getter @Setter private String transactionId;
    @Getter @Setter private String datetime;
    @Getter @Setter private String pendingReason;
    @Getter @Setter private String transactionType;
    @Getter @Setter private String currency;
    @Getter @Setter private String reasonCode;
    @Getter @Setter private String fee;
    @Getter @Setter private String tax;
    @Getter @Setter private String status;
    @Getter @Setter private String billingId;
    @Getter @Setter private String requestId;

    public Payment(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.paymentType = getValue(payload.opt("payment_type"));
            this.amount = getValue(payload.opt("amount"));
            this.transactionId = getValue(payload.opt("transaction_id"));
            this.datetime = getValue(payload.opt("datetime"));
            this.pendingReason = getValue(payload.opt("pending_reason"));
            this.transactionType = getValue(payload.opt("transaction_type"));
            this.currency = getValue(payload.opt("currency"));
            this.reasonCode = getValue(payload.opt("reason_code"));
            this.fee = getValue(payload.opt("fee"));
            this.tax = getValue(payload.opt("tax"));
            this.status = getValue(payload.opt("status"));
            this.billingId = getValue(payload.opt("billing_id"));
            this.requestId = getValue(payload.opt("request_id"));
        }
    }
}
