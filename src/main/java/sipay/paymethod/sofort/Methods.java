package sipay.paymethod.sofort;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class Methods implements PayMethod {
    String order;
    String reconciliation;
    String title;
    String logo;
    String customId;
    JSONObject notify;
    JSONObject policyData;

    public Methods(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.reconciliation = ((String) payload.opt("reconciliation"));
        this.title = ((String) payload.opt("title"));
        this.logo = ((String) payload.opt("logo"));
        this.customId = ((String) payload.opt("customId"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.policyData = ((JSONObject) payload.opt("policyData"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("reconciliation", this.reconciliation);
        payload.put("title", this.title);
        payload.put("logo", this.logo);
        payload.put("custom_id", this.customId);
        payload.put("notify", this.notify);
        payload.put("policy_data", this.policyData);

        return payload;
    }
}