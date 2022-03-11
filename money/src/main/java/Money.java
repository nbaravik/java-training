import java.util.Locale;

public class Money {

    private double amount;
    private Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public double getAmount() {
        return this.amount;
    }

    public String format() {

        String format = "%." + this.currency.getPrecision() + "f";
        return String.format(Locale.ROOT, format, this.amount) + " " + this.currency.getName();
    }

    public String formatShort() {

        String format = "%." + this.currency.getPrecision() + "f";
        if ("".equalsIgnoreCase(this.currency.getSymbol())) {
            return String.format(Locale.ROOT, format, this.amount) + " " + this.currency.getName();
        }
        return this.currency.getSymbol() + String.format(Locale.ROOT, format, this.amount);

    }

    public String toString() {
        return format();
    }
}
