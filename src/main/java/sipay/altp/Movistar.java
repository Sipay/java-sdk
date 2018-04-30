package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.body.movistar.Methods;
import sipay.body.movistar.Purchase;
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
     * Send a methods to Movistar.
     *
     * @param body:   payment with the message (it can be an object of movistar/methods)
     * @param amount: amount of the operation.
     * @return GenericMethods: object that contain response of ALTP API.
     */
    public GenericMethods methods(@Nonnull Methods body, @Nonnull Amount amount) {

        String schema = "movistarMethods";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = body.update(payload);

        return genericMethods(schema, payload);
    }

    /**
     * Send a confirm to Movistar.
     *
     * @param requestId: identifier of the request.
     * @return GenericConfirm: object that contain response of ALTP API.
     */
    public GenericConfirm confirm(@Nonnull String requestId) {

        return genericConfirm(requestId, this.app);
    }

    /**
     * Send a purchase from token to Movistar.
     *
     * @param body:   payment with the message (it can be an object of movistar/purchase)
     * @param amount: amount of the operation.
     * @return Generic: object that contain response of ALTP API.
     */
    public PurchaseFromToken purchaseFromToken(@Nonnull Purchase body, @Nonnull Amount amount) {
        String endpoint = this.app + "/purchase_from_token";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = body.update(payload);

        return new PurchaseFromToken(send(payload, getPath(endpoint)));
    }
}