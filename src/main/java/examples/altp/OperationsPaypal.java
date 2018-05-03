package examples.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.altp.Paypal;
import sipay.body.paypal.ExpressCheckoutMethods;
import sipay.body.paypal.ReferenceTransactionMethods;
import sipay.body.paypal.ReferenceTransactionPayment;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;
import sipay.responses.altp.paypal.Payment;

public class OperationsPaypal {

    public static void main(String[] args) {
        String path = "config.properties";
        Paypal paypal = new Paypal(path);

        JSONObject payload = new JSONObject();
        JSONObject notify = new JSONObject();

        // El campo order tiene que ser único
        payload.put("order", "prueba-order-00000000201");
        payload.put("reconciliation", "reconciliation");
        payload.put("title", "Sipay Pruebas");
        payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");

        notify.put("result", "https://www.sipay.es");
        payload.put("notify", notify);
        payload.put("policyData", new JSONObject());

        Amount amount = new Amount("10000", "EUR");

        ExpressCheckoutMethods expressCheckout = new ExpressCheckoutMethods(payload);
        GenericMethods methods = paypal.expressCheckoutMethods(expressCheckout, amount);
        if (methods == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (methods.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + methods.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        JSONObject payload2 = new JSONObject();
        JSONObject billing = new JSONObject();

        payload2.put("order", "prueba-order-00000000202");
        payload2.put("reconciliation", "reconciliation");
        payload2.put("title", "Sipay Pruebas");
        payload2.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");
        payload2.put("customId", "90");
        payload2.put("notify", notify);

        billing.put("description", "prueba subscription");
        payload2.put("billing", billing);
        payload2.put("policyData", new JSONObject());

        Amount amount2 = new Amount("0", "EUR");
        ReferenceTransactionMethods referenceTransactionMethods = new ReferenceTransactionMethods(payload2);
        GenericMethods methods2 = paypal.referenceTransactionMethods(referenceTransactionMethods, amount2);
        if (methods2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (methods2.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + methods2.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        GenericConfirm confirm = paypal.expressCheckoutConfirm("5ae726d81d65fb000196dad4");
        if (confirm == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (confirm.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + confirm.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        GenericConfirm confirm2 = paypal.referenceTransactionConfirm("5ae728121d65fb000196dad6");
        if (confirm2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (confirm2.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + confirm2.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        JSONObject payload3 = new JSONObject();
        payload3.put("order", "prueba-order-00000000203");
        payload3.put("notify", notify);
        payload3.put("billingId", "B-1YF41782NT322241B");
        payload3.put("reconciliation", "reconciliation");

        ReferenceTransactionPayment referenceTransactionPayment = new ReferenceTransactionPayment(payload3);
        Payment payment = paypal.referenceTransactionPayment(referenceTransactionPayment, amount);
        if (payment == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (payment.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + payment.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
    }
}