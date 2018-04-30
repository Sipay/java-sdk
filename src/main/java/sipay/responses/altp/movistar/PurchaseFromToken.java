package sipay.responses.altp.movistar;

import org.json.JSONObject;
import sipay.responses.Response;

public class PurchaseFromToken extends Response {
    private String authToken;
    private String responseType;
    private JSONObject userId;
    private String purchaseCode;
    private String amount;
    private String order;
    private String transactionId;
    private String status;


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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public JSONObject getUserId() {
        return userId;
    }

    public void setUserId(JSONObject userId) {
        this.userId = userId;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}