package examples;

import ecommerce.Amount;
import ecommerce.Ecommerce;
import org.json.JSONObject;
import paymethod.Card;
import paymethod.FastPay;
import paymethod.StoredCard;

public class Operations {

    public static void main(String[] args) {
        String path = "config.properties";
        Ecommerce ecommerce = new Ecommerce(path);

        String panExample = "4242424242424242";
        JSONObject payload = new JSONObject();
        payload.put("custom_01", "custom_01");
        payload.put("token", "2977e78d1e3e4c9fa6b70");


        // region * Pago de un Euro con tarjeta y almacenar en Sipay con un token
        Amount amount = new Amount("100", "EUR");
        Card card = new Card(panExample, 2018, 3);

        JSONObject auth = ecommerce.authorization(card, amount, payload);
        printResult(auth);
        // endregion

        // region * Pago de 4.56 euros con tarjeta ya almacenada en Sipay
        Amount amount2 = new Amount("456", "EUR");
        String token = "2977e78d1e3e4c9fa6b70";
        StoredCard card2 = new StoredCard(token);

        JSONObject auth2 = ecommerce.authorization(card2, amount2, payload);
        printResult(auth2);
        // endregion

        //region * Pago de 2.34 euros con tarjeta ya almacenada en Sipay mediante FastPay
        Amount amount3 = new Amount("234", "EUR");
        String tokenFastPay = "6d4f7cc37275417f844f2bce8fd4ac55";
        FastPay card3 = new FastPay(tokenFastPay);

        JSONObject auth3 = ecommerce.authorization(card3, amount3, payload);
        printResult(auth3);
        // endregion

        // region * Cancelar el pago con tarjeta (auth)
        JSONObject cancel = ecommerce.cancellation("000097485106184538988");
        printResult(cancel);
        // endregion

        // region * Hacer una devolución con tarjeta de 28.60 euros
        Amount amount4 = new Amount("2860", "EUR");
        FastPay card5 = new FastPay("9f6f143b76f647599560e99975692628");

        JSONObject refund2 = ecommerce.refund(card5, amount4, payload);
        printResult(refund2);
        // endregion

        // region * Hacer una devolución con identificador de transacción de 8.34 euros
        Amount amount5 = new Amount("834", "EUR");

        payload.put("transactionId", "000097485106184538982");
        JSONObject refund = ecommerce.refund(null, amount5, payload);
        printResult(refund);
        // endregion

        // region * Almacenar tarjeta en Sipay
        FastPay card6 = new FastPay("9f6f143b76f647599560e99975692628");
        // Card card = new Card(panExample, 2018, 3);

        JSONObject register = ecommerce.register(card, "newtoken");
        printResult(register);
        // endregion

        // region * Borrar tarjeta de Sipay
        JSONObject unregister = ecommerce.unregister("newtoken");
        printResult(unregister);
        // endregion
    }

    private static void printResult(JSONObject auth) {
        if (auth == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (Integer.parseInt(auth.optString("code")) != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + auth.opt("description").toString());
        } else {
            System.out.println("Operación procesada correctamente");
        }
    }
}