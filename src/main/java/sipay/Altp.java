package sipay;

import org.json.JSONObject;
import sipay.paymethod.paypal.Methods;
import sipay.paymethod.paypal.Payment;
import sipay.responses.PaypalMethods;
import sipay.responses.PaypalPayment;

import javax.annotation.Nonnull;

public class Altp extends Utils {

    String url;

    public Altp(String resp) {
        super(resp);
        url = "https://" + environment + ".sipay.es/altp/" + version + "/";
    }

    /**
     * Send a request of methods to paypal.
     *
     * @param methods: payment method.
     * @param amount:  amount of the operation.
     * @return PaypalMethods: object that contain response of ALTP API
     */
    public PaypalMethods paypalMethods(@Nonnull Methods methods, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = methods.update(payload);

        return new PaypalMethods(send(payload, url + methods.getEndpoint()));
    }

    /**
     * Send a request of payment to paypal.
     *
     * @param payment: payment method.
     * @param amount:  amount of the operation.
     * @return PaypalPayment: object that contain response of ALTP API
     */
    public PaypalPayment paypalPayment(@Nonnull Payment payment, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payment.update(payload);

        return new PaypalPayment(send(payload, url + payment.getEndpoint()));
    }
}
