package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.Utils;
import sipay.paymethod.paypal.Methods;
import sipay.responses.paypal.BillingWithPayment;
import sipay.responses.paypal.BillingWithoutPayment;
import sipay.responses.paypal.Payment;

import javax.annotation.Nonnull;

public class Paypal extends Utils {

    String url;

    public Paypal(String resp) {
        super(resp);
        url = "https://" + environment + ".sipay.es/altp/" + version + "/";
    }

    /**
     * Send a request of billing agreement with payment to paypal.
     *
     * @param methods: payment method.
     * @param amount:  amount of the operation.
     * @return PaypalMethods: object that contain response of ALTP API
     */
    public BillingWithPayment billingWithPayment(@Nonnull Methods methods, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = methods.update(payload);

        return new BillingWithPayment(send(payload, url + methods.getEndpoint()));
    }

    /**
     * Send a request of billing agreement without payment to paypal.
     *
     * @param methods: payment method.
     * @param amount:  amount of the operation.
     * @return PaypalMethods: object that contain response of ALTP API
     */
    public BillingWithoutPayment billingWithoutPayment(@Nonnull Methods methods, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = methods.update(payload);

        return new BillingWithoutPayment(send(payload, url + methods.getEndpoint()));
    }

    /**
     * Send a request of payment to paypal.
     *
     * @param payment: payment method.
     * @param amount:  amount of the operation.
     * @return PaypalPayment: object that contain response of ALTP API
     */
    public Payment payment(@Nonnull sipay.paymethod.paypal.Payment payment, @Nonnull Amount amount) {

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payment.update(payload);

        return new Payment(send(payload, url + payment.getEndpoint()));
    }
}
