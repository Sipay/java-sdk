package sipay.responses.altp;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import sipay.responses.Response;

public class GenericMethods extends Response {
    @Getter
    @Setter
    private JSONObject methods;
    
    @Getter
    private String requestId;

    public GenericMethods(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.methods = ((JSONObject) payload.opt("methods"));
        }
        this.requestId = resp.optString("request_id");
    }
}