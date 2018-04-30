package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.paymethod.PayMethod;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;
import sipay.responses.altp.paypal.Payment;

import javax.annotation.Nonnull;

public class Paypal extends Altp {

    public Paypal(String resp) {
        super(resp);
    }

    /**
     * Send a express checkout methods to Paypal.
     *
     * @param payMethod: payment with the message (it can be an object of paypal/expressCheckoutMethods)
     * @param amount:    amount of the operation.
     * @return GenericMethods: object that contain response of MDWR API.
     */
    public GenericMethods expressCheckoutMethods(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {

        String schema = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return genericMethods(schema, payload);
    }

    /**
     * Send a express checkout confirm to Paypal.
     *
     * @param requestId: identifier of the request.
     * @return GenericConfirm: object that contain response of MDWR API.
     */
    public GenericConfirm expressCheckoutConfirm(@Nonnull String requestId) {

        String endpoint = "pexp";
        return genericConfirm(requestId, endpoint);
    }

    /**
     * Send a reference transaction methods to Paypal.
     *
     * @param payMethod: payment with the message (it can be an object of paypal/referenceTransactionMethods)
     * @param amount:    amount of the operation.
     * @return GenericMethods: object that contain response of MDWR API.
     */
    public GenericMethods referenceTransactionMethods(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {

        String schema = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return genericMethods(schema, payload);
    }

    /**
     * Send a reference transaction confirm to Paypal.
     *
     * @param requestId: identifier of the request.
     * @return GenericConfirm: object that contain response of MDWR API.
     */
    public GenericConfirm referenceTransactionConfirm(@Nonnull String requestId) {

        String endpoint = "pref";
        return genericConfirm(requestId, endpoint);
    }

    /**
     * Send a reference transaction payment to Paypal.
     *
     * @param payMethod: payment with the message (it can be an object of movistar/referenceTransactionPayment)
     * @param amount:    amount of the operation.
     * @return Payment: object that contain response of MDWR API.
     */
    public Payment referenceTransactionPayment(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {

        String endpoint = "pref/payment";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return new Payment(send(payload, url + endpoint));
    }
}
