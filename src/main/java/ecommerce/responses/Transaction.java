package ecommerce.responses;

import org.json.JSONObject;

public class Transaction {

    String channelName;
    String channel;
    String method;
    String order;
    String transactionId;
    String internalCode;
    String methodName;
    String operation;
    String authorizationId;
    String description;
    String maskedCard;
    String operationName;
    String status;

    public Transaction(JSONObject resp) {
        this.channelName = getValue(resp.opt("channel_name"));
        this.channel = getValue(resp.opt("channel"));
        this.method = getValue(resp.opt("method"));
        this.order = getValue(resp.opt("order"));
        this.transactionId = getValue(resp.opt("transaction_id"));
        this.internalCode = getValue(resp.opt("internal_code"));
        this.methodName = getValue(resp.opt("method_name"));
        this.operation = getValue(resp.opt("operation"));
        this.authorizationId = getValue(resp.opt("authorization_id"));
        this.description = getValue(resp.opt("description"));
        this.maskedCard = getValue(resp.opt("masked_card"));
        this.operationName = getValue(resp.opt("operation_name"));
        this.status = getValue(resp.opt("status"));
    }

    public String getValue(Object value) {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannel() {
        return channel;
    }

    public String getMethod() {
        return method;
    }

    public String getOrder() {
        return order;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getOperation() {
        return operation;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public String getDescription() {
        return description;
    }

    public String getMaskedCard() {
        return maskedCard;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getStatus() {
        return status;
    }
}
