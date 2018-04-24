package examples;

import org.json.JSONObject;
import sipay.Amount;
import sipay.altp.Paypal;
import sipay.paymethod.paypal.Methods;
import sipay.paymethod.paypal.Payment;
import sipay.responses.paypal.BillingWithPayment;
import sipay.responses.paypal.BillingWithoutPayment;

public class Altp {

    public static void main(String[] args) {
        String path = "config.properties";
        Paypal paypal = new Paypal(path);

        JSONObject payload = new JSONObject();
        JSONObject notify = new JSONObject();
        JSONObject billing = new JSONObject();

        payload.put("order", "aaa-order-123457009877483");
        payload.put("reconciliation", "reconciliation");
        payload.put("title", "Sipay Pruebas");
        payload.put("logo", "url");
        payload.put("customId", "90");

        notify.put("result", "url");
        payload.put("notify", notify);

        billing.put("description", "prueba subscription");
        payload.put("billing", billing);
        payload.put("policyData", new JSONObject());

        // region * Petición donde se solicite un token para una suscripción de Pay Pal (sin pago en ese momento).
        Amount amount = new Amount("0", "EUR");

        Methods methods = new Methods(payload);
        BillingWithoutPayment paypalMethods = paypal.billingWithoutPayment(methods, amount);
        if (paypalMethods == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (paypalMethods.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + paypalMethods.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Petición de un pago con Pay Pal en el que se solicite un token para una suscripción de Pay Pal (1€)
        Amount amount2 = new Amount("100", "EUR");
        Methods methods2 = new Methods(payload);
        BillingWithPayment paypalMethods2 = paypal.billingWithPayment(methods2, amount2);
        if (paypalMethods2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (paypalMethods2.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + paypalMethods2.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Cobro recurrente de una suscripción de Pay Pal usando el token.
        JSONObject payload2 = new JSONObject();
        JSONObject notify2 = new JSONObject();

        payload2.put("order", "aaa-order-833457009877483");
        notify2.put("result", "url");
        payload2.put("notify", notify2);
        payload2.put("billingId", "B-5YT73212N0953524X");
        payload2.put("reconciliation", "reconciliation");

        Payment payment = new Payment(payload2);
        sipay.responses.paypal.Payment resp = paypal.payment(payment, amount2);
        if (resp == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (resp.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + resp.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion
    }
}
