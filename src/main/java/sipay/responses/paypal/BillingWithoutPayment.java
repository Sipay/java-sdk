package sipay.responses.paypal;

import org.json.JSONObject;
import sipay.responses.Response;

public class BillingWithoutPayment extends Response {
    JSONObject methods;

    public BillingWithoutPayment(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.methods = ((JSONObject) payload.opt("methods"));
        }
    }
}
