package sipay.paymethod.pmt;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class Methods implements PayMethod {

    String order;
    String reconciliation;
    String title;
    String logo;
    JSONObject notify;
    JSONObject customer;
    JSONObject policyData;

    public Methods(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.reconciliation = ((String) payload.opt("reconciliation"));
        this.title = ((String) payload.opt("title"));
        this.logo = ((String) payload.opt("logo"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.customer = ((JSONObject) payload.opt("customer"));
        this.policyData = ((JSONObject) payload.opt("policyData"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("reconciliation", this.reconciliation);
        payload.put("title", this.title);
        payload.put("logo", this.logo);
        payload.put("notify", this.notify);
        payload.put("customer", this.customer);
        payload.put("policy_data", this.policyData);

        return payload;
    }
}