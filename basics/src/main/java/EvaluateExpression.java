import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EvaluateExpression {

    /**
     * @params double a, x
     * <p>
     * 3a^2+2ax-x^2
     * ------------  - 2
     * (3x+a)(a+x)
     */

    public static String inputA;
    public static String inputХ;

    public static double a;
    public static double x;


    // квадрат числа
    public static double squareNumber(double value) {

        return value * value;

    }

    public static void main(String[] args) {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // ввод переменной а
        try {
            System.out.print("Введите переменную а : ");
            inputA = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка ввода " + e);
        }
        // ввод переменной х
        try {
            System.out.print("Введите переменную x : ");
            inputХ = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка ввода " + e);
        }


        try {
            a = Double.parseDouble(inputA);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат числа a!");
            System.exit(1);
        }

        try {
            x = Double.parseDouble(inputХ);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат числа x!");
            System.exit(1);
        }

        double numerator = 3 * squareNumber(a) + 2 * a * x - squareNumber(x);  //числитель
        double denominator = (3 * x + a) * (a + x);                            //знаменатель
        double result = numerator / denominator - 2;
        System.out.println("Результат вычилений : " + result);
    }
}
