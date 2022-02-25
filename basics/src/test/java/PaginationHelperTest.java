import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PaginationHelperTest {

    PaginationHelper ph1 = new PaginationHelper(Arrays.asList("a", "b", "c", "d", "e", "f"), 4);
    PaginationHelper ph2 = new PaginationHelper(Arrays.asList("line1", "line2", "line3", "line4"), 2);


    @Test
    public void testZeroOrNegativeItemPerPage() {

        try {
            PaginationHelper ph = new PaginationHelper(Arrays.asList("a", "b", "c", "d", "e", "f"), 0);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);
    }

    @Test
    public void testNulCollection() {
        try {
            PaginationHelper ph = new PaginationHelper(null, 3);
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);
    }

    @Test
    public void testItemCount() {
        Assert.assertEquals(6, ph1.itemCount());
        Assert.assertEquals(4, ph2.itemCount());
    }

    @Test
    public void testPageCount() {
        Assert.assertEquals(2, ph1.pageCount());
        Assert.assertEquals(2, ph2.pageCount());
    }

    @Test
    public void testPageItemCount() {
        Assert.assertEquals(4, ph1.pageItemCount(0));
        Assert.assertEquals(2, ph1.pageItemCount(1));
        Assert.assertEquals(-1, ph1.pageItemCount(3));
        Assert.assertEquals(-1, ph1.pageItemCount(-1));

        Assert.assertEquals(2, ph2.pageItemCount(0));
        Assert.assertEquals(2, ph2.pageItemCount(1));
    }

    @Test
    public void testPageIndex() {
        Assert.assertEquals(1, ph1.pageIndex(5));
        Assert.assertEquals(0, ph1.pageIndex(2));
        Assert.assertEquals(-1, ph1.pageIndex(20));
        Assert.assertEquals(-1, ph1.pageIndex(-10));

        Assert.assertEquals(0, ph2.pageIndex(1));
        Assert.assertEquals(1, ph2.pageIndex(3));
    }
}
