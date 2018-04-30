package examples.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.altp.Sofort;
import sipay.paymethod.sofort.Methods;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;

public class OperationsSofort {

    public static void main(String[] args) {
        String path = "config.properties";
        Sofort sofort = new Sofort(path);

        JSONObject payload = new JSONObject();
        JSONObject notify = new JSONObject();

        payload.put("order", "aaa-order-223457009877483");
        payload.put("reconciliation", "reconciliation");
        payload.put("title", "Sipay Pruebas");
        payload.put("logo", "https://url/images/prueba.png");

        notify.put("result", "url");
        payload.put("notify", notify);
        payload.put("policyData", new JSONObject());

        Amount amount = new Amount("10000", "EUR");

        Methods expressCheckout = new Methods(payload);
        GenericMethods methods = sofort.methods(expressCheckout, amount);
        if (methods == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (methods.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + methods.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        GenericConfirm confirm = sofort.confirm("6a571dffcc3ac117e5aefced");
        if (confirm == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (confirm.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + confirm.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
    }
}
