package examples;

import org.json.JSONObject;
import sipay.Ecommerce;
import sipay.responses.Card;
import sipay.responses.Query;
import sipay.responses.Transaction;

public class Querys {

    public static void main(String[] args) {
        String path = "config.properties";
        Ecommerce ecommerce = new Ecommerce(path);

        // region * Consultar tarjeta
        Card cardResp = ecommerce.card("2977e78d1e3e4c9fa6b70");
        if (cardResp == null) {
            System.out.println("Fallo al consultar la tarjeta, Error al conectar con el servicio");
        } else if (cardResp.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + cardResp.getDescription());
        } else {
            System.out.println("Consulta procesada correctamente.");
        }
        // endregion

        // region * Consultar operación por id
        JSONObject payload = new JSONObject();
        payload.put("transaction_id", "000097485106184565651");

        Query query = ecommerce.query(payload);
        if (query == null) {
            System.out.println("Fallo al consultar la tarjeta, Error al conectar con el servicio");
        } else if (query.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + query.getDescription());
        } else {
            System.out.println("Consulta procesada correctamente.");
        }

        for (Transaction item : query.getTransactions()) {
            System.out.println(item.get());
        }
        // endregion

        // region * Consultar operación por ticket
        JSONObject payload2 = new JSONObject();
        payload2.put("order", "435890fb684443628152fb7ba998d1d0");

        Query query2 = ecommerce.query(payload2);
        if (query2 == null) {
            System.out.println("Fallo al consultar la tarjeta, Error al conectar con el servicio");
        } else if (query2.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + query2.getDescription());
        } else {
            System.out.println("Consulta procesada correctamente.");
        }

        for (Transaction item : query2.getTransactions()) {
            System.out.println(item.get());
        }
        // endregion
    }
}