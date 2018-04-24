package sipay.paymethod;

import org.json.JSONObject;

public interface PayMethod {
    JSONObject update(JSONObject payload);

    String getEndpoint();
}