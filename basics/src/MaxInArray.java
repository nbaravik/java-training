public class MaxInArray {

    public static void main(String[] args) {
        int[] array = new int[5];
        int max = array[0];

        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 101); //0...100
            System.out.print(array[i] + " ");
            if (array[i] > max) {
                max = array[i];
            }
        }
        System.out.println("\nMax value in array is " + max);
    }

}
