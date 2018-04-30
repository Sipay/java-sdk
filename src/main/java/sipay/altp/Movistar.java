package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.paymethod.PayMethod;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;
import sipay.responses.altp.movistar.PurchaseFromToken;

import javax.annotation.Nonnull;

public class Movistar extends Altp {

    private String app;

    public Movistar(String resp) {
        super(resp);
        this.app = "movi";
    }

    /**
     * Send a methods to Paypal.
     *
     * @param payMethod: payment with the message (it can be an object of movistar/methods)
     * @param amount:    amount of the operation.
     * @return GenericMethods: object that contain response of MDWR API.
     */
    public GenericMethods methods(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {

        String schema = "movistarMethods";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return genericMethods(schema, payload);
    }

    /**
     * Send a confirm to Paypal.
     *
     * @param requestId: identifier of the request.
     * @return GenericConfirm: object that contain response of MDWR API.
     */
    public GenericConfirm confirm(@Nonnull String requestId) {

        return genericConfirm(requestId, this.app);
    }

    /**
     * Send a purchase from token to Paypal.
     *
     * @param payMethod: payment with the message (it can be an object of movistar/purchase)
     * @param amount:    amount of the operation.
     * @return Generic: object that contain response of MDWR API.
     */
    public PurchaseFromToken purchaseFromToken(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {
        String endpoint = this.app + "/purchase_from_token";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return new PurchaseFromToken(send(payload, getPath(endpoint)));
    }
}
