package sipay.responses.altp;

import org.json.JSONObject;
import sipay.responses.Response;

public class GenericMethods extends Response {
    private JSONObject methods;

    public GenericMethods(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.methods = ((JSONObject) payload.opt("methods"));
        }
    }

    public JSONObject getMethods() {
        return methods;
    }

    public void setMethods(JSONObject methods) {
        this.methods = methods;
    }
}
