public class Currency {

    private String name;
    private String symbol;
    private int precision;

    public Currency(String name, String symbol, int precision) {
        this.name = name;
        this.symbol = symbol;
        this.precision = precision;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getPrecision() {
        return this.precision;
    }
}
