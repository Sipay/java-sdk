package sipay.responses;

import org.json.JSONObject;

public class PaypalMethods extends Response {
    JSONObject methods;

    public PaypalMethods(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.methods = ((JSONObject) payload.opt("methods"));
        }
    }
}
