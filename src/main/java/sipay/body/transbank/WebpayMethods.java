package sipay.body.transbank;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class WebpayMethods {
    @Getter
    @Setter
    private String order;

    @Getter
    @Setter
    private JSONObject notify;
    
    @Getter
    @Setter
    private JSONObject policyData;

    public WebpayMethods(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.policyData = ((JSONObject) payload.opt("policyData"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("notify", this.notify);
        payload.put("policy_data", this.policyData);

        return payload;
    }
}
