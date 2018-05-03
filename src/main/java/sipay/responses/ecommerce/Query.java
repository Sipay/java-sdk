package sipay.responses.ecommerce;

import org.json.JSONArray;
import org.json.JSONObject;
import sipay.responses.Response;

import java.util.ArrayList;

public class Query extends Response {

    ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public Query(JSONObject resp) {
        super(resp);

        JSONObject payload = ((JSONObject) resp.opt("payload"));
        if (payload.length() > 0) {
            JSONArray items = ((JSONArray) payload.opt("items"));
            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    this.transactions.add(new Transaction(((JSONObject) items.opt(i))));
                }
            }
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
