package ecommerce;

import catalogs.Currency;

public class Amount {
    String amount;
    String currency;

    public Amount(String amount, String currency) {
        Currency currencies = new Currency();

        if (Integer.parseInt(amount) <= 0) {
            throw new java.lang.RuntimeException("Value of amount is incorrect.");
        }

        Object result = currencies.find(currency);
        if (result == null) {
            throw new java.lang.RuntimeException("Value of currency is incorrect.");
        }

//        StringBuilder str = new StringBuilder(amount);
//        this.amount = str.insert(2 - 1, '.').toString();
        this.amount = amount;
        this.currency = currency;
    }

    public Amount(Double amount, String currency) {
        Currency currencies = new Currency();

        if (amount <= 0) {
            throw new java.lang.RuntimeException("Value of amount is incorrect.");
        }

        Object result = currencies.find(currency);
        if (result == null) {
            throw new java.lang.RuntimeException("Value of currency is incorrect.");
        }

        this.amount = Double.toString(amount);
        this.currency = currency;
    }
}
