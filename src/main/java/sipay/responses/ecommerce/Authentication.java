package sipay.responses.ecommerce;

import org.json.JSONObject;
import sipay.responses.Response;

public class Authentication extends Response {

    String url;

    public Authentication(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload != null && payload.length() > 0) {
            this.url = getValue(payload.opt("url"));
        }
    }

    public String getUrl() {
        return url;
    }
}