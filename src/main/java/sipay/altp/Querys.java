package sipay.altp;

import org.json.JSONObject;
import sipay.responses.altp.querys.StatusOperation;

import javax.annotation.Nonnull;

public class Querys extends Altp {

    public Querys(String resp) {
        super(resp);
    }

    /**
     * Send a get status operation to Altp.
     *
     * @param requestId: identifier of the request.
     * @return StatusOperation: object that contain response of ALTP API.
     */
    public StatusOperation getStatusOperation(@Nonnull String requestId) {
        String uri = "status/operation";

        JSONObject payload = new JSONObject();
        payload.put("request_id", requestId);

        return new StatusOperation(send(payload, getPath(uri)));
    }
}
