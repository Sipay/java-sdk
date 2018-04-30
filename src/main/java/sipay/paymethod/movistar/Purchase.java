package sipay.paymethod.movistar;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class Purchase implements PayMethod {

    String authToken;
    String clientIpAddress;

    public Purchase(JSONObject payload) {
        this.authToken = ((String) payload.opt("authToken"));
        this.clientIpAddress = ((String) payload.opt("clientIpAddress"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("auth_token", this.authToken);
        payload.put("client_ip_address", this.clientIpAddress);

        return payload;
    }
}