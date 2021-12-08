import java.util.ArrayList;
import java.util.Scanner;

public class SplitNumberToDigits {

    // вариант 1
    /*
    static int[] digits(int number) {

        String numStr = Integer.toString(number);
        int[] result = new int[numStr.length()];
        for (int i = 0; i < numStr.length(); i++) {
            String tmp = numStr.substring(i, i + 1);
            result[i] = Integer.parseInt(tmp);
        }
        return result;
    }
    */

    // вариант 2
    //создаю список, т.к. длина массива фиксированная, а мы не знаем сколько элементов будет в итоге
    // как вариант, можно каждый раз создававать копию массива с длинной+1
    // и при необходимости добавления нового элемента вызыввать Array.copyOf(), но так некрасиво:)

    static int[] digits(int number) {

        ArrayList<Integer> digitsList = new ArrayList<Integer>();

        do {
            int digit = number % 10; // остаток от деления числа на 10 - последняя цифра
            digitsList.add(0, digit); //записываем цифру в список сразу в нужном порядке
            number /= 10; //получаем следующий разряд

        } while (number != 0);

        int[] result = new int[digitsList.size()];
        for (int i=0; i<result.length; i++) {
            result[i] = digitsList.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Please enter your number>0 :");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int[] digits = digits(num);
        System.out.println("Digits are:");
        for (int i = 0; i < digits.length - 1; i++) {
            System.out.print(digits[i] + ", ");
        }
        System.out.print(digits[digits.length - 1]);
    }
}
