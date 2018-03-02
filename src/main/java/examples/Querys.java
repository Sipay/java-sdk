package examples;

import com.google.gson.JsonArray;
import ecommerce.Ecommerce;
import org.json.JSONObject;

public class Querys {

    public static void main(String[] args) {
        String path = "config.properties";
        Ecommerce ecommerce = new Ecommerce(path);

        // region * Consultar tarjeta
        JSONObject cardResp = ecommerce.card("2977e78d1e3e4c9fa6b70");
        printResult(cardResp);
        // endregion

        // region * Consultar operación por id
        JSONObject query = ecommerce.query("000097485106184538992", null);
        printResult(query);

        JsonArray items = ((JsonArray) query.opt("transactions"));
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i));
        }
        // endregion

        // region * Consultar operación por ticket
        JSONObject query2 = ecommerce.query(null, "0fbec7a7739f4164816a96406e63389a");
        printResult(query2);

        JsonArray items2 = ((JsonArray) query2.opt("transactions"));
        for (int i = 0; i < items2.size(); i++) {
            System.out.println(items2.get(i));
        }
        // endregion
    }

    private static void printResult(JSONObject query) {
        if (query == null) {
            System.out.println("Fallo al consultar la tarjeta, Error al conectar con el servicio");
        } else if (Integer.parseInt(query.opt("code").toString()) != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + query.opt("description"));
        } else {
            System.out.println("Consulta procesada correctamente.");
        }
    }
}