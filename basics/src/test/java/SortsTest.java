import org.junit.Assert;
import org.junit.Test;

public class SortsTest {

    @Test
    public void SelectionSortTest() {
        int[] array1 = new int[]{35, 17, 0, 456, 100};
        int[] array2 = new int[]{2, 3, 2};
        int[] array3 = new int[]{-100, 0, -56, -1};
        Sorts.selectionSort(array1);
        Assert.assertArrayEquals(new int[]{0, 17, 35, 100, 456}, array1);
        Sorts.selectionSort(array2);
        Assert.assertArrayEquals(new int[]{2, 2, 3}, array2);
        Sorts.selectionSort(array3);
        Assert.assertArrayEquals(new int[]{-100, -56, -1, 0}, array3);
    }

    @Test
    public void BubbleSortTest() {
        int[] array1 = new int[]{350, 17, 0, 456, 100};
        int[] array2 = new int[]{2, 3, 2};
        int[] array3 = new int[]{-100, -140, -56, -1};
        Sorts.bubbleSort(array1);
        Assert.assertArrayEquals(new int[]{0, 17, 100, 350, 456}, array1);
        Sorts.bubbleSort(array2);
        Assert.assertArrayEquals(new int[]{2, 2, 3}, array2);
        Sorts.bubbleSort(array3);
        Assert.assertArrayEquals(new int[]{-140, -100, -56, -1}, array3);
    }

    @Test
    public void quickSortTest() {
        int[] array1 = new int[]{3, 0, 1};
        Sorts.quickSort(array1);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, array1);
        int[] array2 = new int[]{1, 1, 6, 5, 1, 1, 0};
        Sorts.quickSort(array2);
        Assert.assertArrayEquals(new int[]{0, 1, 1, 1, 1, 5, 6}, array2);
        int[] array3 = new int[]{2, 2, 2, 1, 2};
        Sorts.quickSort(array3);
        Assert.assertArrayEquals(new int[]{1, 2, 2, 2, 2}, array3);
        int[] array4 = new int[]{-100, -140, -56, -1};
        Sorts.quickSort(array4);
        Assert.assertArrayEquals(new int[]{-140, -100, -56, -1}, array4);
    }
}
