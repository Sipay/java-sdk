package sipay.responses.altp;

import org.json.JSONObject;
import sipay.responses.Response;

public class Generic extends Response {
    private JSONObject methods;

    public Generic(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.methods = ((JSONObject) payload.opt("methods"));
        }
    }
}
