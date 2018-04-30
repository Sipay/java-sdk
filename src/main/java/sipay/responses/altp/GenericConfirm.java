package sipay.responses.altp;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import sipay.responses.Response;

public class GenericConfirm extends Response {
    @Getter
    @Setter
    private String datetime;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String requestId;

    @Getter
    @Setter
    private String countryCode;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private String billingId;

    @Getter
    @Setter
    private JSONArray paymentInfo;

    @Getter
    @Setter
    private String authToken;

    @Getter
    @Setter
    private String responseType;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private JSONObject msisdn;

    @Getter
    @Setter
    private String status;

    public GenericConfirm(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.datetime = getValue(payload.opt("datetime"));
            this.country = getValue(payload.opt("country"));
            this.requestId = getValue(payload.opt("request_id"));
            this.countryCode = getValue(payload.opt("country_code"));
            this.paymentInfo = ((JSONArray) payload.opt("payment_info"));
            this.token = getValue(payload.opt("token"));
            this.billingId = getValue(payload.opt("auth_token"));
            this.authToken = getValue(payload.opt("response_type"));
            this.responseType = getValue(payload.opt("billing_id"));
            this.userId = getValue(payload.opt("user_id"));
            this.msisdn = ((JSONObject) payload.opt("msisdn"));
            this.status = getValue(payload.opt("status"));
        }
    }
}