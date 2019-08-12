package examples.ecommerce;

import org.json.JSONObject;
import sipay.Amount;
import sipay.Ecommerce;
import sipay.paymethod.Card;
import sipay.responses.ecommerce.Authentication;
import sipay.responses.ecommerce.Confirm;

import java.util.UUID;

public class Secure3DTest {

    Ecommerce ecommerce;

    public Secure3DTest() {
        ecommerce = new Ecommerce("config.properties");
    }

    public static void main(String[] args) {
        Secure3DTest sec = new Secure3DTest();
        if (args.length < 1) {
            System.out.println("Request authentication");
            Authentication auth = sec.auth();
            System.out.println("Should redirect to: " + auth.getUrl());
            System.out.println("request_id: " + auth.getRequestId());
        } else {
            System.out.println("Request confirm (complete payment)");
            Confirm conf = sec.confirm(args[0]);
            System.out.println("Status code: " + conf.getCode());
        }
    }

    public Authentication auth() {
        Amount amount = new Amount("1000", "EUR");
        Card card = new Card("4907272011072817", 2049, 2);

        JSONObject options = new JSONObject();
        options.put("url_ok", "http://google.com");
        options.put("url_ko", "http://google.com/error");
        options.put("order", UUID.randomUUID().toString());
        options.put("custom_01", "custom-001");
        options.put("custom_02", "custom-002");
        options.put("operation", "authorization");
        options.put("reference", UUID.randomUUID().toString());

        return ecommerce.authentication(card, amount, options);
    }

    public Confirm confirm(String requestId) {
        return ecommerce.confirm(requestId);
    }
}
