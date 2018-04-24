package sipay.responses.paypal;

import org.json.JSONObject;
import sipay.responses.Response;

public class BillingWithPayment extends Response {
    JSONObject methods;

    public BillingWithPayment(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.methods = ((JSONObject) payload.opt("methods"));
        }
    }
}
