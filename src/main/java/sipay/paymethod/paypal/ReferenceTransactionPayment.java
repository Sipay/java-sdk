package sipay.paymethod.paypal;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;

public class ReferenceTransactionPayment implements PayMethod {
    String order;
    JSONObject notify;
    String billingId;
    String reconciliation;

    public ReferenceTransactionPayment(JSONObject payload) {
        this.order = ((String) payload.opt("order"));
        this.notify = ((JSONObject) payload.opt("notify"));
        this.billingId = ((String) payload.opt("billingId"));
        this.reconciliation = ((String) payload.opt("reconciliation"));
    }

    public JSONObject update(JSONObject payload) {
        payload.put("order", this.order);
        payload.put("notify", this.notify);
        payload.put("billing_id", this.billingId);
        payload.put("reconciliation", this.reconciliation);

        return payload;
    }
}