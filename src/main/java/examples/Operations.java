package examples;

import sipay.Amount;
import sipay.Ecommerce;
import sipay.paymethod.Card;
import sipay.paymethod.FastPay;
import sipay.paymethod.StoredCard;
import sipay.responses.*;

public class Operations {

    public static void main(String[] args) {
        String path = "config.properties";
        String panExample = "4242424242424242";
        Ecommerce ecommerce = new Ecommerce(path);

        // region * Pago de un Euro con tarjeta y almacenar en Sipay con un token
        Amount amount = new Amount("100", "EUR");
        Card card = new Card(panExample, 2018, 3);

        Authorization auth = ecommerce.authorization(card, amount);
        if (auth == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (auth.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + auth.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }

        // endregion

        // region * Pago de 4.56 euros con tarjeta ya almacenada en Sipay
        Amount amount2 = new Amount(456, "EUR");
        String token = "2977e78d1e3e4c9fa6b70";
        StoredCard card2 = new StoredCard(token);

        Authorization auth2 = ecommerce.authorization(card2, amount2);
        if (auth2 == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (auth2.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + auth2.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion

        //region * Pago de 2.34 euros con tarjeta ya almacenada en Sipay mediante FastPay
        Amount amount3 = new Amount("234", "EUR");
        String tokenFastPay = "6d4f7cc37275417f844f2bce8fd4ac55";
        FastPay card3 = new FastPay(tokenFastPay);

        Authorization auth3 = ecommerce.authorization(card3, amount3);
        if (auth3 == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (auth3.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + auth3.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion

        // region * Cancelar el pago con tarjeta (auth)
        Cancellation cancel = ecommerce.cancellation("000097485106184538988");
        if (cancel == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (cancel.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + cancel.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion

        // region * Hacer una devolución con tarjeta de 28.60 euros
        Amount amount4 = new Amount("2860", "EUR");
        FastPay card5 = new FastPay("9f6f143b76f647599560e99975692628");

        Refund refund = ecommerce.refund(card5, amount4);
        if (refund == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (refund.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + refund.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion

        // region * Hacer una devolución con identificador de transacción de 8.34 euros
        Amount amount5 = new Amount("834", "EUR");

        Refund refund2 = ecommerce.refund("000097485106184538982", amount5);
        if (refund2 == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (refund2.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + refund2.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion

        // region * Almacenar tarjeta en Sipay
        FastPay card6 = new FastPay("9f6f143b76f647599560e99975692628");
        // Card card = new Card(panExample, 2018, 3);

        Register register = ecommerce.register(card, "newtoken");
        if (register == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (register.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + register.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion

        // region * Borrar tarjeta de Sipay
        Unregister unregister = ecommerce.unregister("newtoken");
        if (unregister == null) {
            System.out.println("Fallo al realizar la operación, Error al conectar con el servicio");
        } else if (unregister.getCode() != 0) {
            System.out.println("Fallo al realizar la operación, Error: " + unregister.getDescription());
        } else {
            System.out.println("Operación procesada correctamente");
        }
        // endregion
    }
}