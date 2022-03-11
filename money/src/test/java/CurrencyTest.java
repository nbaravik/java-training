import org.junit.Assert;
import org.junit.Test;

public class CurrencyTest {

    @Test
    public void testCurrency() {
        Currency usd = new Currency("USD", "$", 2);
        Assert.assertEquals("USD", usd.getName());
        Assert.assertEquals("$", usd.getSymbol());
        Assert.assertEquals(2, usd.getPrecision());

        Currency byr = new Currency("BYR", "", 0);
        Assert.assertEquals("BYR", byr.getName());
        Assert.assertEquals("", byr.getSymbol());
        Assert.assertEquals(0, byr.getPrecision());

        Currency eur = new Currency("EUR", "€", 2);
        Assert.assertEquals("EUR", eur.getName());
        Assert.assertEquals("€", eur.getSymbol());
        Assert.assertEquals(2, eur.getPrecision());

        Currency btc = new Currency("BTC", "₿", 4);
        Assert.assertEquals("BTC", btc.getName());
        Assert.assertEquals("₿", btc.getSymbol());
        Assert.assertEquals(4, btc.getPrecision());

    }
}
