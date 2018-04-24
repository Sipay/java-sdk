package sipay;

import org.json.JSONObject;
import sipay.paymethod.PayMethod;
import sipay.responses.ecommerce.*;

import javax.annotation.Nonnull;

public class Ecommerce extends Utils {

    String url;

    public Ecommerce(String resp) {
        super(resp);
        url = "https://" + environment + ".sipay.es/mdwr/" + version + "/";
    }

    /**
     * Send a request of authorization to Sipay.
     *
     * @param payMethod: payment method of authorization (it can be an object of Card, StoredCard or FastPay).
     * @param amount:    amount of the operation.
     * @param options:   {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                   custom_02: custom field 2, token: if this argument is set, it register payMethod with this token}
     * @return Authorization: object that contain response of MDWR API
     */
    public Authorization authorization(@Nonnull PayMethod payMethod, @Nonnull Amount amount, @Nonnull JSONObject options) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        validateSchema(methodName, options);

        options.put("amount", amount.amount);
        options.put("currency", amount.currency);
        options = payMethod.update(options);

        return new Authorization(send(options, url + methodName));
    }

    /**
     * Send a request of authorization to Sipay.
     *
     * @param payMethod: payment method of authorization (it can be an object of Card, StoredCard or FastPay).
     * @param amount:    amount of the operation.
     * @return Authorization: object that contain response of MDWR API
     */
    public Authorization authorization(@Nonnull PayMethod payMethod, @Nonnull Amount amount) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = payMethod.update(payload);

        return new Authorization(send(payload, url + methodName));
    }

    /**
     * Send a request of cancellation to Sipay.
     *
     * @param transactionId: identificator of transaction.
     * @return Cancellation(Response): object that contain response of MDWR API
     */
    public Cancellation cancellation(@Nonnull String transactionId) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("transaction_id", transactionId);

        validateSchema(methodName, payload);

        return new Cancellation(send(payload, url + methodName));
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param method:  payment method
     * @param amount:  amount of the operation.
     * @param options: {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                 custom_02: custom field 2, token: if this argument is set, it register payMethod with this token }
     * @return Refund: object that contain response of MDWR API.
     */
    public Refund refund(@Nonnull PayMethod method, @Nonnull Amount amount, @Nonnull JSONObject options) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        validateSchema(methodName, options);

        options.put("amount", amount.amount);
        options.put("currency", amount.currency);
        options = method.update(options);

        return new Refund(send(options, url + methodName));
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param method: payment method
     * @param amount: amount of the operation.
     * @return Refund: object that contain response of MDWR API.
     */
    public Refund refund(@Nonnull PayMethod method, @Nonnull Amount amount) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);
        payload = method.update(payload);

        return new Refund(send(payload, url + methodName));
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param transactionId: transactionId: identificator of transaction
     * @param amount:        amount of the operation.
     * @param payload:       {reconciliation: identification for bank reconciliation, custom_01: custom field 1,
     *                       custom_02: custom field 2, token: if this argument is set, it register payMethod with this token }
     * @return Refund: object that contain response of MDWR API.
     */
    public Refund refund(@Nonnull String transactionId, @Nonnull Amount amount, @Nonnull JSONObject payload) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        payload.put("transaction_id", transactionId);
        validateSchema(methodName, payload);

        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);

        return new Refund(send(payload, url + methodName));
    }

    /**
     * Send a request of refund to Sipay.
     *
     * @param transactionId: transactionId: identificator of transaction
     * @param amount:        amount of the operation.
     * @return Refund: object that contain response of MDWR API.
     */
    public Refund refund(@Nonnull String transactionId, @Nonnull Amount amount) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("transaction_id", transactionId);
        validateSchema(methodName, payload);

        payload.put("amount", amount.amount);
        payload.put("currency", amount.currency);

        return new Refund(send(payload, url + methodName));
    }

    /**
     * Send a request of register to Sipay.
     *
     * @param card:  Card that register.
     * @param token: token will be associate to card.
     * @return Register: object that contain response of MDWR API.
     */
    public Register register(@Nonnull PayMethod card, @Nonnull String token) {

        if (card.getClass().getName().equals("paymethod.StoredCard")) {
            logger.severe("Incorrect payment method.");
            throw new RuntimeException("Incorrect payment method.");
        }

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("token", token);
        payload = card.update(payload);

        validateSchema(methodName, payload);

        return new Register(send(payload, url + methodName));
    }

    /**
     * Send a request of remove a registry of a token to Sipay.
     *
     * @param token: token of a card.
     * @return Unregister: object that contain response of MDWR API.
     */
    public Unregister unregister(@Nonnull String token) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("token", token);

        validateSchema(methodName, payload);

        return new Unregister(send(payload, url + methodName));
    }

    /**
     * Send a request for search a card in Sipay.
     *
     * @param token: token of card.
     * @return Card(Response): object that contain response of MDWR API.
     */
    public Card card(@Nonnull String token) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        JSONObject payload = new JSONObject();
        payload.put("token", token);

        validateSchema(methodName, payload);

        return new Card(send(payload, url + methodName));
    }

    /**
     * Send a query to Sipay.
     *
     * @param payload: {transactionId: identificator of transaction, order: ticket of the operation.}
     * @return Query: object that contain response of MDWR API.
     */
    public Query query(@Nonnull JSONObject payload) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        if (payload.opt("transaction_id") == null && payload.opt("order") == null || payload.opt("transaction_id") != null && payload.opt("order") != null) {
            logger.severe("Incorrect query.");
            throw new RuntimeException("Incorrect query.");
        }

        validateSchema(methodName, payload);

        return new Query(send(payload, url + methodName));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }
}