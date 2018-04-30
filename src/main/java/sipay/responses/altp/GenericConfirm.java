package sipay.responses.altp;

import org.json.JSONArray;
import org.json.JSONObject;
import sipay.responses.Response;

public class GenericConfirm extends Response {
    private String datetime;
    private String country;
    private String requestId;
    private String countryCode;
    private String token;
    private String billingId;
    private JSONArray paymentInfo;
    private String authToken;
    private String responseType;
    private String userId;
    private JSONObject msisdn;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public JSONArray getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(JSONArray paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JSONObject getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(JSONObject msisdn) {
        this.msisdn = msisdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
