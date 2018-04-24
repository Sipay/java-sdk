package examples;

import sipay.Amount;
import sipay.Ecommerce;
import sipay.paymethod.ecommerce.Card;
import sipay.paymethod.ecommerce.FastPay;
import sipay.paymethod.ecommerce.StoredCard;
import sipay.responses.ecommerce.*;

public class Operations {

    public static void main(String[] args) {
        String path = "config.properties";
        String panExample = "4242424242424242";
        Ecommerce ecommerce = new Ecommerce(path);

        // region * Authorization with card (1€)
        Amount amount = new Amount("100", "EUR");
        Card card = new Card(panExample, 2018, 8);

        Authorization auth = ecommerce.authorization(card, amount);
        if (auth == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (auth.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + auth.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }

        // endregion

        // region * Authorization with token (4.56 €)
        Amount amount2 = new Amount(456, "EUR");
        String token = "2977e78d1e3e4c9fa6b70";
        StoredCard card2 = new StoredCard(token);

        Authorization auth2 = ecommerce.authorization(card2, amount2);
        if (auth2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (auth2.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + auth2.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        //region * Authorization with FastPay  (2.34 €)
        Amount amount3 = new Amount("234", "EUR");
        String tokenFastPay = "6d4f7cc37275417f844f2bce8fd4ac55";
        FastPay card3 = new FastPay(tokenFastPay);

        Authorization auth3 = ecommerce.authorization(card3, amount3);
        if (auth3 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (auth3.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + auth3.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Cancellation
        Cancellation cancel = ecommerce.cancellation("000097485106184538988");
        if (cancel == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (cancel.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + cancel.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Refund (28.6 €)
        Amount amount4 = new Amount("2860", "EUR");

        Refund refund = ecommerce.refund(card, amount4);
        if (refund == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (refund.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + refund.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Refund from id_transaction (8.34 €)
        Amount amount5 = new Amount("834", "EUR");

        Refund refund2 = ecommerce.refund("000097485106184538982", amount5);
        if (refund2 == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (refund2.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + refund2.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Register card from FastPay to Sipay
        FastPay card6 = new FastPay("9f6f143b76f647599560e99975692628");


        Register register = ecommerce.register(card, "newtoken");
        if (register == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (register.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + register.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion

        // region * Unregister tokenized card
        Unregister unregister = ecommerce.unregister("newtoken");
        if (unregister == null) {
            System.out.println("Failure in operation. Error connecting to the service");
        } else if (unregister.getCode() != 0) {
            System.out.println("Failure in operation. Error:" + unregister.getDescription());
        } else {
            System.out.println("Operation processed successfully");
        }
        // endregion
    }
}
