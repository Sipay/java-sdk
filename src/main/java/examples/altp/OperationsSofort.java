package examples.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.altp.Sofort;
import sipay.body.sofort.Methods;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;

public class OperationsSofort {

    public static void main(String[] args) {
        String path = "config.properties";
        Sofort sofort = new Sofort(path);

        JSONObject payload = new JSONObject();
        JSONObject notify = new JSONObject();

        // El campo order tiene que ser único
        payload.put("order", "prueba-order-00000000205");
        payload.put("reconciliation", "reconciliation");
        payload.put("title", "Sipay Pruebas");
        payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");

        notify.put("result", "https://www.sipay.es");
        payload.put("notify", notify);
        payload.put("policyData", new JSONObject());

        Amount amount = new Amount("10000", "EUR");

        Methods methods = new Methods(payload);
        GenericMethods resp = sofort.methods(methods, amount);
        if (resp == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (resp.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + resp.getDescription());
        } else {
            System.out.println("Success getting Sofort methods");
        }
        
        // Get redirect URL
        String referenceTransactionUri = sofort.getSofortMethod(resp);
        System.out.println("Redirect client browser to this URL: " + referenceTransactionUri);
        
        String requestId = resp.getRequestId();
        GenericConfirm confirm = sofort.confirm(requestId);
        if (confirm == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (confirm.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + confirm.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
    }
}