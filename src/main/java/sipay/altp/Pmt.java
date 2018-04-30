package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.paymethod.PayMethod;
import sipay.responses.altp.GenericMethods;

import javax.annotation.Nonnull;

public class Pmt extends Altp {

    private String app;

    public Pmt(String resp) {
        super(resp);
        this.app = "pmt";
    }

    /**
     * Send a methods to PMT.
     *
     * @param payMethod: payment with the message (it can be an object of PMT/methods)
     * @param amount:    amount of the operation.
     * @return GenericMethods: object that contain response of MDWR API.
     */
    public GenericMethods methods(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {

        String schema = "pmtMethods";

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return genericMethods(schema, payload);
    }
}


