package examples.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.altp.Movistar;
import sipay.paymethod.movistar.Methods;
import sipay.paymethod.movistar.Purchase;
import sipay.responses.altp.GenericConfirm;
import sipay.responses.altp.GenericMethods;
import sipay.responses.altp.movistar.PurchaseFromToken;

public class OperationsMovistar {

    public static void main(String[] args) {
        String path = "config.properties";
        Movistar movistar = new Movistar(path);

        JSONObject payload = new JSONObject();
        JSONObject notify = new JSONObject();
        notify.put("result", "url");

        payload.put("order", "prueba-order-00000000060");
        payload.put("msisdn", "34611111111");
        payload.put("notify", notify);
        payload.put("policyData", new JSONObject());

        Methods methods = new Methods(payload);
        Amount amount = new Amount("100", "EUR");

        GenericMethods resp = movistar.methods(methods, amount);
        if (resp == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (resp.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + resp.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        GenericConfirm confirm = movistar.confirm("6a571dffcc3ac117e5aefced");
        if (confirm == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (confirm.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + confirm.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        JSONObject payload2 = new JSONObject();
        payload2.put("authToken", "34611111111");
        payload2.put("clientIpAddress", "127.0.0.1");

        Purchase purchase = new Purchase(payload2);
        Amount amount2 = new Amount("100", "EUR");

        PurchaseFromToken resp2 = movistar.purchaseFromToken(purchase, amount2);
        if (resp2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (resp2.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + resp2.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

    }
}