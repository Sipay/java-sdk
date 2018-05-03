package sipay.body.movistar;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class Purchase {
    @Getter
    @Setter
    private String authToken;

    @Getter
    @Setter
    private String clientIpAddress;

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