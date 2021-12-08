import java.util.Scanner;

public class ArrayFromEvenPositions {

    // remove extra zeros in the end of the number
    static String formatDoubleValue(double value) {

        String result = String.valueOf(value);
        while (result.endsWith("0") && result.contains(".")) {
            result = result.substring(0, result.length() - 1);
            if (result.endsWith(".")) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    // evens elements from array
    static double[] getEvens(double[] array) {
        // result.lenght = array.lengh/2 + 1, if  array has odd number of items
        // result.lenght = array.lengh/2, if it has even number of items
        int len = array.length / 2 + array.length % 2;
        double[] result = new double[len];
        for (int i = 0; i < array.length; i++) {
            if (i % 2 != 0) {
                continue;
            }
            result[i / 2] = array[i];
        }
        return result;
    }

    public static void main(String[] args) {

        double[] initialArray = new double[6];

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < initialArray.length; i++) {
            int index = i + 1;
            System.out.print("Enter array element number " + index + " : ");
            initialArray[i] = scanner.nextDouble();
        }

        System.out.print("The result of getEvens({");
        for (int i = 0; i < initialArray.length - 1; i++) {
            System.out.print(formatDoubleValue(initialArray[i]) + ", ");
        }
        System.out.print(formatDoubleValue(initialArray[initialArray.length - 1]) + "}) is {");

        double[] resultArray = new double[initialArray.length / 2];
        resultArray = getEvens(initialArray);
        for (int i = 0; i < resultArray.length - 1; i++) {
            System.out.print(formatDoubleValue(resultArray[i]) + ", ");
        }
        System.out.print(formatDoubleValue(resultArray[resultArray.length - 1]) + "}");
    }
}
