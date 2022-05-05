import org.junit.Assert;
import org.junit.Test;

public class BigNumberTest {

    @Test
    public void compareToTest() {
        BigNumber bn1 = new BigNumber("948694896967773");
        BigNumber bn2 = new BigNumber("948694896967772");
        BigNumber bn3 = new BigNumber("948694896967773");
        BigNumber bn4 = new BigNumber("948694896967774");
        Assert.assertEquals(1, bn1.compareTo(bn2));
        Assert.assertEquals(0, bn1.compareTo(bn3));
        Assert.assertEquals(-1, bn1.compareTo(bn4));

        Assert.assertEquals(1, new BigNumber("385789").compareTo(new BigNumber("-579")));
        Assert.assertEquals(0, new BigNumber("-94948774").compareTo(new BigNumber("-94948774")));
        Assert.assertEquals(-1, new BigNumber("-377873749875").compareTo(new BigNumber("-3778737498")));
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