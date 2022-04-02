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
}