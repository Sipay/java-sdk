package sipay.paymethod;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Card implements PayMethod {

    String cardNumber;
    int year;
    int month;

    public Card(String cardNumber, int year, int month) {
        if (!Pattern.compile("^[\\w-]{14,19}$").matcher(cardNumber).find()) {
            throw new java.lang.RuntimeException("Card_number doesn\'t have a correct value.");
        }

        this.cardNumber = cardNumber;
        setExpirationDate(year, month);
    }

    private boolean isExpired() {
        boolean error = false;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        if (this.year < year || (this.year == year && this.month < month)) {
            error = true;
        }

        return error;
    }

    private void setExpirationDate(int year, int month) {
        if (year < 999 || year > 9999) {
            throw new java.lang.RuntimeException("Year doesn\'t have a correct value.");
        }

        if (month < 0 || month > 12) {
            throw new java.lang.RuntimeException("Month doesn\'t have a correct value.");
        }

        this.year = year;
        this.month = month;

        if (isExpired()) {
            throw new java.lang.RuntimeException("Card is expired.");
        }
    }

    public JSONObject update(JSONObject payload) {
        payload.put("pan", this.cardNumber);
        payload.put("year", this.year);
        payload.put("month", this.month);

        return payload;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}