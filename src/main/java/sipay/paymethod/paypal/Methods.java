package sipay.paymethod.paypal;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class Methods implements PayMethod {
    String order;
    String reconciliation;
    String title;
    String logo;
    String customId;
    JSONObject notify;
    JSONObject billing;
    JSONObject policyData;
    String endpoint;

    public Methods(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.reconciliation = ((String) payload.opt("reconciliation"));
        this.title = ((String) payload.opt("title"));
        this.logo = ((String) payload.opt("logo"));
        this.customId = ((String) payload.opt("customId"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.billing = ((JSONObject) payload.opt("billing"));
        this.policyData = ((JSONObject) payload.opt("policyData"));
        this.endpoint = "methods";
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("reconciliation", this.reconciliation);
        payload.put("title", this.title);
        payload.put("logo", this.logo);
        payload.put("custom_id", this.customId);
        payload.put("notify", this.notify);
        payload.put("billing", this.billing);
        payload.put("policy_data", this.policyData);

        return payload;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(String reconciliation) {
        this.reconciliation = reconciliation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public JSONObject getNotify() {
        return notify;
    }

    public void setNotify(JSONObject notify) {
        this.notify = notify;
    }

    public JSONObject getBilling() {
        return billing;
    }

    public void setBilling(JSONObject billing) {
        this.billing = billing;
    }

    public JSONObject getPolicyData() {
        return policyData;
    }

    public void setPolicyData(JSONObject policyData) {
        this.policyData = policyData;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}