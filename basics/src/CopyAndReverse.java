public class CopyAndReverse {

    public static void main(String[] args) {
        int[] array = new int[7];

        // initialize array with number [-10, 10]
        System.out.println("Initial array : ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) ((Math.random() * 21) - 10);
            System.out.print(array[i] + " ");
        }

        // copy
        int[] copyArray = array.clone();
        System.out.println("\nCopied array : ");
        for (int i = 0; i < copyArray.length; i++) {
            System.out.print(copyArray[i] + " ");
        }

        // reverse
        int[] reverseArray = array.clone(); // clone before reverse to save central element
        int temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[array.length - i - 1];
            reverseArray[reverseArray.length - i - 1] = array[i];
            reverseArray[i] = temp;

        }

        System.out.println("\nReversed array: ");
        for (int i = 0; i < reverseArray.length; i++) {
            System.out.print(reverseArray[i] + " ");
        }
    }
}
