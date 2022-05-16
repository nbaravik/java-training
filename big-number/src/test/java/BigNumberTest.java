import org.junit.Assert;
import org.junit.Test;

public class BigNumberTest {

    @Test
    public void numberFormatExceptionsTest() {
        try {
            new BigNumber(null);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String must not be NULL", e.getMessage());
        }

        try {
            new BigNumber("");
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String must not be empty", e.getMessage());
        }

        try {
            new BigNumber("-");
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("- is not a number", e.getMessage());
        }

        try {
            new BigNumber("-84368946hh956490");
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("-84368946hh956490 is not a number", e.getMessage());
        }

        try {
            new BigNumber("-0000");
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("-0000 is not a number", e.getMessage());
        }
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("0", new BigNumber("0000").toString());
        Assert.assertEquals("11", new BigNumber("000011").toString());
        Assert.assertEquals("-22", new BigNumber("-00000022").toString());
        Assert.assertEquals("176876248768276849480", new BigNumber("176876248768276849480").toString());
        Assert.assertEquals("-176876248494809368", new BigNumber("-176876248494809368").toString());
    }

    @Test
    public void compareToTest() {

        Assert.assertEquals(1, new BigNumber("123").compareTo(new BigNumber("-234")));
        Assert.assertEquals(-1, new BigNumber("-123").compareTo(new BigNumber("234")));
        Assert.assertEquals(1, new BigNumber("12345").compareTo(new BigNumber("1234")));
        Assert.assertEquals(-1, new BigNumber("-12345").compareTo(new BigNumber("-1234")));
        Assert.assertEquals(-1, new BigNumber("1234").compareTo(new BigNumber("12345")));
        Assert.assertEquals(1, new BigNumber("-1234").compareTo(new BigNumber("-12345")));

        // same abs length
        Assert.assertEquals(1, new BigNumber("948694896967773").compareTo( new BigNumber("948694896967772")));
        Assert.assertEquals(0, new BigNumber("999999999999999").compareTo(new BigNumber("999999999999999")));
        Assert.assertEquals(-1, new BigNumber("948694896967773").compareTo( new BigNumber("948694896967774")));

        Assert.assertEquals(1, new BigNumber("-94948774").compareTo(new BigNumber("-94948775")));
        Assert.assertEquals(0, new BigNumber("-94948774").compareTo(new BigNumber("-94948774")));
        Assert.assertEquals(-1, new BigNumber("-377873749875").compareTo(new BigNumber("-3778737498")));

        Assert.assertEquals(0, new BigNumber("0").compareTo(new BigNumber("000")));
    }

    @Test
    public void plusTest() {
        BigNumber bn1 = new BigNumber("10000068787897");
        BigNumber bn2 = new BigNumber("788584");
        Assert.assertEquals("10000069576481", bn1.plus(bn2).toString());

        BigNumber bn3 = new BigNumber("10000");
        BigNumber bn4 = new BigNumber("-10000");
        Assert.assertEquals("0", bn3.plus(bn4).toString());

        Assert.assertEquals("382", new BigNumber("57").plus(new BigNumber("325")).toString());
        Assert.assertEquals("268", new BigNumber("-57").plus(new BigNumber("325")).toString());
        Assert.assertEquals("-382", new BigNumber("-57").plus(new BigNumber("-325")).toString());
        Assert.assertEquals("-268", new BigNumber("57").plus(new BigNumber("-325")).toString());

        Assert.assertEquals("4887", new BigNumber("4562").plus(new BigNumber("325")).toString());
        Assert.assertEquals("-4237", new BigNumber("-4562").plus(new BigNumber("325")).toString());
        Assert.assertEquals("-4887", new BigNumber("-4562").plus(new BigNumber("-325")).toString());
        Assert.assertEquals("4237", new BigNumber("4562").plus(new BigNumber("-325")).toString());
    }

    @Test
    public void minusTest() {
        BigNumber bn1 = new BigNumber("10000068787897");
        BigNumber bn2 = new BigNumber("788584");
        Assert.assertEquals("10000067999313", bn1.minus(bn2).toString());

        BigNumber bn3 = new BigNumber("10000");
        BigNumber bn4 = new BigNumber("10000");
        Assert.assertEquals("0", bn3.minus(bn4).toString());

        Assert.assertEquals("-268", new BigNumber("57").minus(new BigNumber("325")).toString());
        Assert.assertEquals("-382", new BigNumber("-57").minus(new BigNumber("325")).toString());
        Assert.assertEquals("268", new BigNumber("-57").minus(new BigNumber("-325")).toString());
        Assert.assertEquals("382", new BigNumber("57").minus(new BigNumber("-325")).toString());

        Assert.assertEquals("4237", new BigNumber("4562").minus(new BigNumber("325")).toString());
        Assert.assertEquals("-4887", new BigNumber("-4562").minus(new BigNumber("325")).toString());
        Assert.assertEquals("-4237", new BigNumber("-4562").minus(new BigNumber("-325")).toString());
        Assert.assertEquals("4887", new BigNumber("4562").minus(new BigNumber("-325")).toString());
    }
}