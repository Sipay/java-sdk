package examples;

import org.json.JSONObject;
import sipay.Ecommerce;
import sipay.responses.ecommerce.Card;
import sipay.responses.ecommerce.Query;
import sipay.responses.ecommerce.Transaction;

public class Querys {

    public static void main(String[] args) {
        String path = "config.properties";
        Ecommerce ecommerce = new Ecommerce(path);

        // region * get card
        Card cardResp = ecommerce.card("2977e78d1e3e4c9fa6b70");
        if (cardResp == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (cardResp.getCode() != 0) {
            System.out.println("Failure in operation. Error:  " + cardResp.getDescription());
        } else {
            System.out.println("Operation processed successfully.");
        }
        // endregion

        // region * get transaction from transaction_id
        JSONObject payload = new JSONObject();
        payload.put("transaction_id", "000097485106184565651");

        Query query = ecommerce.query(payload);
        if (query == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (query.getCode() != 0) {
            System.out.println("Failure in operation. Error:  " + query.getDescription());
        } else {
            System.out.println("Operation processed successfully.");
        }

        for (Transaction item : query.getTransactions()) {
            System.out.println(item.get());
        }
        // endregion

        // region * Get transaction from ticket
        JSONObject payload2 = new JSONObject();
        payload2.put("order", "435890fb684443628152fb7ba998d1d0");

        Query query2 = ecommerce.query(payload2);
        if (query2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (query2.getCode() != 0) {
            System.out.println("Failure in operation. Error:  " + query2.getDescription());
        } else {
            System.out.println("Operation processed successfully.");
        }

        for (Transaction item : query2.getTransactions()) {
            System.out.println(item.get());
        }
        // endregion
    }
}
