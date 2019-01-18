package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.body.sofort.Methods;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;

import javax.annotation.Nonnull;

public class Sofort extends Altp {

    public Sofort(String resp) {
        super(resp);
    }

    /**
     * Send a methods to Sofort.
     *
     * @param body:   payment with the message (it can be an object of sofort/methods)
     * @param amount: amount of the operation.
     * @return GenericMethods: object that contain response of ALTP API.
     */
    public GenericMethods methods(@Nonnull Methods body, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = body.update(payload);

        return genericMethods("sofortMethods", payload);
    }
    
    /**
     * Get sofort method URL
     *
     * @param methods: object that contain response of ALTP API.
     * @return String: Url of the webpay request 
     */
    public String getSofortMethod(@Nonnull GenericMethods methods) {
    	return methods.getMethods().getJSONObject("sofort").getString("url");
    }

    /**
     * Send a express checkout methods to Paypal.
     *
     * @param requestId: identifier of the request.
     * @return GenericConfirm: object that contain response of ALTP API.
     */
    public GenericConfirm confirm(@Nonnull String requestId) {

        String method = "pref";
        return genericConfirm(requestId, method);
    }
}