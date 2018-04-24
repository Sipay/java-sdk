package sipay;

import sipay.catalogs.Currency;

public class Amount {
    public int amount;
    public String currency;

    public Amount(String amount, String currency) {
        Currency currencies = new Currency();

        String[] result = ((String[]) currencies.find(currency));
        if (result == null) {
            throw new RuntimeException("Value of currency is incorrect.");
        }

        if (amount.matches("^([0-9]+\\.[0-9]{" + (Integer.parseInt(result[2])) + "})")) {
            this.amount = Integer.parseInt(amount.replace(".", ""));
        } else if (amount.matches("^([0-9]+)$")) {
            this.amount = Integer.parseInt(amount);
        } else {
            throw new RuntimeException("Value of " + amount + " is incorrect.");
        }

        if (this.amount < 0) {
            throw new RuntimeException("Value of amount is incorrect.");
        }

        this.currency = currency;
    }

    public Amount(int amount, String currency) {
        Currency currencies = new Currency();

        if (amount < 0) {
            throw new RuntimeException("Value of amount is incorrect.");
        }

        Object result = currencies.find(currency);
        if (result == null) {
            throw new RuntimeException("Value of currency is incorrect.");
        }

        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String get() {
        String[] result = ((String[]) new Currency().find(this.currency));

        StringBuilder str = new StringBuilder(Integer.toString(this.amount));
        str.insert(Integer.parseInt(result[2]) - 1, '.');

        return str.toString() + " " + this.currency;
    }
}
