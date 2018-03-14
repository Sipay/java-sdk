package sipay.responses;

import org.json.JSONObject;

public class Response {
    Integer code;
    String detail;
    String description;
    String requestId;
    String type;
    String uuid;

    public Response(JSONObject resp) {
        this.code = Integer.parseInt(resp.opt("code").toString());
        this.detail = getValue(resp.opt("detail"));
        this.description = getValue(resp.opt("description"));
        this.requestId = getValue(resp.opt("request_id"));
        this.type = getValue(resp.opt("type"));
        this.uuid = getValue(resp.opt("uuid"));
    }

    public String getValue(Object value) {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public String getDescription() {
        return description;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getType() {
        return type;
    }

    public String getUuid() {
        return uuid;
    }
}
