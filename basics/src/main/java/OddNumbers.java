public class OddNumbers {

    public static void main(String[] args) {
        int[] array = new int[6];

        System.out.println("Array : ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 101); //0...100
            System.out.print(array[i] + " ");
        }
        System.out.println("\nOdd numbers of array : ");
        for (int val : array) {
            if (val % 2 == 0) {
                continue;
            }
            System.out.print(val + " ");
        }
    }
}
