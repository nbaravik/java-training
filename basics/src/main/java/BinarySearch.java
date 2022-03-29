public class BinarySearch {

    public static int search(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {

            int middle = (low + high) / 2;
            int middleValue = array[middle];

            if (middleValue < value) {
                low = middle + 1;
            } else if (middleValue > value) {
                high = middle - 1;
            } else if (middleValue == value) {
                return middle;
            }
        }
        return -1;
    }
}
