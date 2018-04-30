package sipay.responses.altp.paypal;

import org.json.JSONObject;
import sipay.responses.Response;

public class Payment extends Response {
    private String paymentType;
    private String amount;
    private String transactionId;
    private String datetime;
    private String pendingReason;
    private String transactionType;
    private String currency;
    private String reasonCode;
    private String fee;
    private String tax;
    private String status;
    private String billingId;
    private String requestId;


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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPendingReason() {
        return pendingReason;
    }

    public void setPendingReason(String pendingReason) {
        this.pendingReason = pendingReason;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
