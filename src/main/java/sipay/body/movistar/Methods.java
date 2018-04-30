package sipay.body.movistar;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class Methods {

    @Getter
    @Setter
    private String order;

    @Getter
    @Setter
    private String msisdn;

    @Getter
    @Setter
    private JSONObject notify;

    @Getter
    @Setter
    private JSONObject policyData;

    public Methods(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.msisdn = ((String) payload.opt("msisdn"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.policyData = ((JSONObject) payload.opt("policyData"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("msisdn", this.msisdn);
        payload.put("notify", this.notify);
        payload.put("policy_data", this.policyData);

        return payload;
    }
}