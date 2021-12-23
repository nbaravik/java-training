import org.junit.Assert;
import org.junit.Test;

public class FindNegativesTest {

    @Test
    public void testFindNegatives() {
        int[] array = FindNegatives.getNegatives(new int[]{-1, -3, 5, -8, 12, 258, 0, -2345});
        Assert.assertArrayEquals(new int[] {-1, -3, -8, -2345}, array);
    }
}
