import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTest {

    @Test
    public void searchTest() {

        int[] a1 = new int[]{1, 2, 3, 4, 5};
        int[] a2 = new int[]{7, 8, 9, 10, 11};
        int[] a3 = new int[]{1, 2, 3, 4, 5};
        Assert.assertEquals(4, BinarySearch.search(a1, 5));
        Assert.assertEquals(2, BinarySearch.search(a2, 9));
        Assert.assertEquals(-1, BinarySearch.search(a3, 10));
    }
}
