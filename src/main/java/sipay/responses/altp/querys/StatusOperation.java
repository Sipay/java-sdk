package sipay.responses.altp.querys;

import org.json.JSONObject;
import sipay.responses.Response;

public class StatusOperation extends Response {
    private JSONObject request;
    private JSONObject response;
    private String status;
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

    public JSONObject getRequest() {
        return request;
    }

    public void setRequest(JSONObject request) {
        this.request = request;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}

