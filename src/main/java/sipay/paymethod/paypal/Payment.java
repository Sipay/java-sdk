package sipay.paymethod.paypal;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class Payment implements PayMethod {

    String order;
    JSONObject notify;
    String billingId;
    String reconciliation;
    String endpoint;

    public Payment(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.billingId = ((String) payload.opt("billingId"));
        this.reconciliation = ((String) payload.opt("reconciliation"));
        this.endpoint = "pref/payment";
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("notify", this.notify);
        payload.put("billing_id", this.billingId);
        payload.put("reconciliation", this.reconciliation);

        return payload;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public JSONObject getNotify() {
        return notify;
    }

    public void setNotify(JSONObject notify) {
        this.notify = notify;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(String reconciliation) {
        this.reconciliation = reconciliation;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
