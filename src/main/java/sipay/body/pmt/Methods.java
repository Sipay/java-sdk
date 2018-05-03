package sipay.body.pmt;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class Methods {
    @Getter
    @Setter
    private String order;

    @Getter
    @Setter
    private String reconciliation;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String logo;

    @Getter
    @Setter
    private JSONObject notify;

    @Getter
    @Setter
    private JSONObject customer;

    @Getter
    @Setter
    private JSONObject policyData;

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