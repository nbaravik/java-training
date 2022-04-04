public class Sorts {

    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int smallestIndex = i;
            for (int j = smallestIndex + 1; j < array.length; j++) {
                if (array[j] < array[smallestIndex]) {
                    smallestIndex = j;
                }
            }
            int smallestElement = array[smallestIndex];
            array[smallestIndex] = array[i];
            array[i] = smallestElement;
        }
    }

    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void quickSort(int[] array) {

        if (array.length < 2) {
            return;
        }
        int low = 0;
        int high = array.length - 1;
        makeQuickSort(array, low, high);
    }

    private static void makeQuickSort(int[] array, int leftIndex, int rightIndex) {

        if (leftIndex >= rightIndex) {  // basic case
            return;
        }

        int keyIndex = leftIndex + (rightIndex - leftIndex) / 2;
        int keyElement = array[keyIndex];

        int i = leftIndex;    // left from keyElement
        int j = rightIndex;   // right from support Element
        while (i <= j) {
            while (array[i] < keyElement) {
                i++;
            }
            while (array[j] > keyElement) {
                j--;
            }
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        makeQuickSort(array, leftIndex, i-1);
        makeQuickSort(array, i, rightIndex);
    }
}