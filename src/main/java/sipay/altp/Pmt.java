package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.body.pmt.Methods;
import sipay.responses.altp.GenericMethods;

import javax.annotation.Nonnull;

public class Pmt extends Altp {

    public Pmt(String resp) {
        super(resp);
    }

    /**
     * Send a methods to PMT.
     *
     * @param body:   payment with the message (it can be an object of PMT/methods)
     * @param amount: amount of the operation.
     * @return GenericMethods: object that contain response of ALTP API.
     */
    public GenericMethods methods(@Nonnull Methods body, @Nonnull Amount amount) {

        String schema = "pmtMethods";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = body.update(payload);

        return genericMethods(schema, payload);
    }
    
    /**
     * Get PMT method URL
     *
     * @param methods: object that contain response of ALTP API.
     * @return String: Url of the webpay request 
     */
    public String getPmtMethod(@Nonnull GenericMethods methods) {
    	return methods.getMethods().getJSONObject("pmt").getString("url");
    }
}