package sipay.body.paypal;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class ReferenceTransactionPayment {
    @Getter
    @Setter
    private String order;

    @Getter
    @Setter
    private JSONObject notify;

    @Getter
    @Setter
    private String billingId;

    @Getter
    @Setter
    private String reconciliation;

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