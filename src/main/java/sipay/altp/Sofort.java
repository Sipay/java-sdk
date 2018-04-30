package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.paymethod.PayMethod;
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
     * @param payMethod: payment with the message (it can be an object of sofort/methods)
     * @param amount:    amount of the operation.
     * @return GenericMethods: object that contain response of ALTP API.
     */
    public GenericMethods methods(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return genericMethods("s", payload);
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
