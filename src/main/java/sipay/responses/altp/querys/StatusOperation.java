package sipay.responses.altp.querys;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import sipay.responses.Response;

public class StatusOperation extends Response {
    @Getter
    @Setter
    private JSONObject request;

    @Getter
    @Setter
    private JSONObject response;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String confirm;

    public StatusOperation(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.request = ((JSONObject) payload.opt("request"));
            this.response = ((JSONObject) payload.opt("response"));
            this.status = getValue(payload.opt("status"));
            this.confirm = getValue(payload.opt("confirm"));
        }
    }
}