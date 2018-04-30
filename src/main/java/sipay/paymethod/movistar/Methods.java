package sipay.paymethod.movistar;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class Methods implements PayMethod {

    String order;
    String msisdn;
    JSONObject notify;
    JSONObject policyData;

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