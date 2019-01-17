package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.body.transbank.WebpayMethods;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;

import javax.annotation.Nonnull;

public class Transbank extends Altp{

    public Transbank(String resp) {
        super(resp);
    }

    /**
     * Send a request for Transbank webpay.
     *
     * @param body:   payment with the message (it can be an object of transbank/webpayMethods)
     * @param amount: amount of the operation.
     * @return GenericMethods: object that contain response of ALTP API.
     */
    public GenericMethods webpayMethods(@Nonnull WebpayMethods body, @Nonnull Amount amount) {

        String schema = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = body.update(payload);

        return genericMethods(schema, payload);
    }
    
    /**
     * Get webpay method URL
     *
     * @param methods: object that contain response of ALTP API.
     * @return String: Url of the webpay request 
     */
    public String getWebpayMethod(@Nonnull GenericMethods methods) {
    	return methods.getMethods().getJSONObject("transbank").getString("url");
    }

    /**
     * Send a express checkout confirm to Paypal.
     *
     * @param requestId: identifier of the request.
     * @return GenericConfirm: object that contain response of ALTP API.
     */
    public GenericConfirm expressCheckoutConfirm(@Nonnull String requestId) {

        String endpoint = "pexp";
        return genericConfirm(requestId, endpoint);
    }
}