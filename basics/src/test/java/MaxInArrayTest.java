import org.junit.Assert;
import org.junit.Test;


public class MaxInArrayTest {

    @Test
    public void testPositives() {
        int max = MaxInArray.maxValue(new int[]{1, 3, 5, 8, 12});
        // System.out.println(max);
        Assert.assertEquals(12, max);
    }

    @Test
    public void testNegatives() {
        int max = MaxInArray.maxValue(new int[]{-1, -3, -5, -8, -12});
        // System.out.println(max);
        Assert.assertEquals(-1, max);
        //Assert.assertEquals(0.2, 0.19, 0.02);
    }
}
