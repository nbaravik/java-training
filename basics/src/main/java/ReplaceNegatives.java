public class ReplaceNegatives {

    public static void main(String[] args) {

        int[] array = new int[6];

        // initialize array with number [-10, 10]
        System.out.println("Initial array : ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) ((Math.random() * 21) - 10);
            System.out.print(array[i] + " ");
        }
        System.out.println("\nPositive array : ");
        for (int i = 0; i < array.length; i++) {
            // abs for every number (positive, negative and 0)
            // array[i] = Math.abs(array[i]);
            //or
            if (array[i] < 0) {
                array[i] = array[i]*(-1);
            }
            System.out.print(array[i] + " ");
        }
    }
}
