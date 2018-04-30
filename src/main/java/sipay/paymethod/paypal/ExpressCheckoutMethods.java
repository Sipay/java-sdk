package sipay.paymethod.paypal;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class ExpressCheckoutMethods implements PayMethod {
    String order;
    String reconciliation;
    String title;
    String logo;
    JSONObject notify;
    JSONObject policyData;

    public ExpressCheckoutMethods(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.reconciliation = ((String) payload.opt("reconciliation"));
        this.title = ((String) payload.opt("title"));
        this.logo = ((String) payload.opt("logo"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.policyData = ((JSONObject) payload.opt("policyData"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("reconciliation", this.reconciliation);
        payload.put("title", this.title);
        payload.put("logo", this.logo);
        payload.put("notify", this.notify);
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

    public JSONObject getNotify() {
        return notify;
    }

    public void setNotify(JSONObject notify) {
        this.notify = notify;
    }

    public JSONObject getPolicyData() {
        return policyData;
    }

    public void setPolicyData(JSONObject policyData) {
        this.policyData = policyData;
    }
}
