package sipay.altp;

import org.json.JSONObject;
import sipay.Amount;
import sipay.body.transbank.WebpayMethods;
import sipay.responses.Response;
import sipay.responses.altp.GenericMethods;

import javax.annotation.Nonnull;

public class Transbank extends Altp{

    public Transbank(String resp) {
        super(resp);
    }

    /**
     * Send a request for Transbank webpay.
     *
     * @param body:   payment with the message (it can be an object of transbank/webpayMethods)
     * @param amount: amount of the operation.
     * @return GenericMethods: object that contain response of ALTP API.
     */
    public GenericMethods webpayMethods(@Nonnull WebpayMethods body, @Nonnull Amount amount) {

        String schema = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = body.update(payload);

        return genericMethods(schema, payload);
    }

    /**
     * Get webpay method URL
     *
     * @param methods: object that contain response of ALTP API.
     * @return String: Url of the webpay request
     */
    public String getWebpayMethod(@Nonnull GenericMethods methods) {
        return methods.getMethods().getJSONObject("transbank").getString("url");
    }

    /**
     * Check webpay transaction from Status
     *
     * @param status: status of the Altp transaction.
     * @return boolean: Transaction succesful
     */
    public Boolean getWebpayResult(@Nonnull JSONObject status) {
        // Check status is completed and the result code was 200
        return status.getJSONObject("payload").getString("status").equals("completed") &&
                status.getJSONObject("payload").getJSONObject("response").getInt("code") == 200;
    }

    /**
     * Perform refund to webpay
     * @param authorizationCode: The authorizationCode of the transaction
     * @param buyOrder: The order of the transaction
     * @param originalAmount: Original amount of the transaction
     * @param nullifyAmount: Refund amount of the transaction
     * @return Response: Response of the refund
     */
    public Response refundWebpay(@Nonnull String authorizationCode, @Nonnull String buyOrder,
            @Nonnull Amount originalAmount, @Nonnull Amount nullifyAmount) {

        JSONObject payload = new JSONObject();
        payload.put("authorizationCode", authorizationCode);
        payload.put("buyOrder", buyOrder);
        payload.put("authorizedAmount", originalAmount.amount);
        payload.put("nullifyAmount", nullifyAmount.amount);

        Response response = new Response(this.send(payload, this.getPath("trans/refund")));
        return response;
    }
}