import org.junit.Assert;
import org.junit.Test;

public class MoneyTest {

    @Test
    public void testMoney() {

        Money m = new Money(100, Currencies.USD);
        Assert.assertEquals("100.00 USD", m.format());
        Assert.assertEquals("100.00 USD", m.toString());
        Assert.assertEquals("$100.00", m.formatShort());
        Assert.assertEquals(100.0, m.getAmount(), 0.001);
        Assert.assertEquals(Currencies.USD, m.getCurrency());
        Assert.assertEquals(2, m.getCurrency().getPrecision());

        m = new Money(25, Currencies.BYR);
        Assert.assertEquals("25 BYR", m.format());
        Assert.assertEquals("25 BYR", m.toString());
        Assert.assertEquals("25 BYR", m.formatShort());
        Assert.assertEquals(25.0, m.getAmount(), 0.001);
        Assert.assertEquals(Currencies.BYR, m.getCurrency());
        Assert.assertEquals(0, m.getCurrency().getPrecision());

        m = new Money(500, Currencies.EUR);
        Assert.assertEquals("500.00 EUR", m.format());
        Assert.assertEquals("500.00 EUR", m.toString());
        Assert.assertEquals("€500.00", m.formatShort());
        Assert.assertEquals(500.0, m.getAmount(), 0.001);
        Assert.assertEquals(Currencies.EUR, m.getCurrency());
        Assert.assertEquals(2, m.getCurrency().getPrecision());

        m = new Money(5, Currencies.BTC);
        Assert.assertEquals("5.0000 BTC", m.format());
        Assert.assertEquals("5.0000 BTC", m.toString());
        Assert.assertEquals("₿5.0000", m.formatShort());
        Assert.assertEquals(5.0, m.getAmount(), 0.00001);
        Assert.assertEquals(Currencies.BTC, m.getCurrency());
        Assert.assertEquals(4, m.getCurrency().getPrecision());

    }
}

