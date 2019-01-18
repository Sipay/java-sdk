package examples.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.altp.Pmt;
import sipay.body.pmt.Methods;
import sipay.responses.altp.GenericMethods;

public class OperationsPmt {

    public static void main(String[] args) {
        String path = "config.properties";
        Pmt pmt = new Pmt(path);

        JSONObject payload = new JSONObject();
        JSONObject notify = new JSONObject();
        JSONObject customer = new JSONObject();

        // El campo order tiene que ser único
        payload.put("order", "prueba-order-00000000204");
        payload.put("reconciliation", "reconciliation");
        payload.put("title", "Sipay Pruebas");
        payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");

        notify.put("result", "https://www.sipay.es");
        payload.put("notify", notify);

        customer.put("email", "email@example.com");
        customer.put("full_name", "John Doe");

        payload.put("customer", customer);
        payload.put("policyData", new JSONObject());

        Methods methods = new Methods(payload);
        Amount amount = new Amount("100", "EUR");

        GenericMethods resp = pmt.methods(methods, amount);
        if (resp == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (resp.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + resp.getDescription());
        } else {
            System.out.println("Success getting PMT methods");
        }
        
        // Get redirect URL
        String referenceTransactionUri = pmt.getPmtMethod(resp);
        System.out.println("Redirect client browser to this URL: " + referenceTransactionUri);
    }
}