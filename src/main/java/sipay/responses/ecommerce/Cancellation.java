package sipay.responses.ecommerce;

import org.json.JSONObject;
import sipay.responses.Response;

public class Cancellation extends Response {

    public Cancellation(JSONObject resp) {
        super(resp);
    }
}
