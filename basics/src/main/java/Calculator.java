import java.util.Scanner;

public class Calculator {

    // +/-/*/:
    static double calculate(double n1, double n2, String operation) {
        double result = 0;

        switch (operation) {
            case "a": {
                result = n1 + n2;
                break;
            }
            case "s": {
                result = n1 - n2;
                break;
            }
            case "m": {
                result = n1 * n2;
                break;
            }
            case "d": {
                result = n1 / n2;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Enter number 1 : ");
            double n1 = Double.valueOf(scanner.nextLine());
            System.out.print("Enter number 2 : ");
            double n2 = Double.valueOf(scanner.nextLine());

            System.out.print("Enter action (a - add, s - substract, m - multiply, d - divide) : ");
            String action = scanner.nextLine();

            double calculateValue = calculate(n1, n2, action);

            switch (action) {
                case "a": {
                    System.out.print(n1 + ", " + n2 + ", \"add\" => returns ");
                    System.out.printf("%.2f", calculateValue);
                    break;
                }
                case "s": {
                    System.out.print(n1 + ", " + n2 + ", \"substract\" => returns ");
                    System.out.printf("%.2f", calculateValue);
                    break;
                }
                case "m": {
                    System.out.print(n1 + ", " + n2 + ", \"multiply\" => returns ");
                    System.out.printf("%.2f", calculateValue);
                    break;
                }
                case "d": {
                    System.out.print(n1 + ", " + n2 + ", \"divide\" => returns ");
                    System.out.printf("%.2f", calculateValue);
                    break;
                }
                default: {
                    System.out.println("Unknown operation!");
                }
            }

            // continue case
            System.out.print("\nPress any key to continue calculations : ");
            String cont = scanner.nextLine();
            if (cont.equals("")) {
                return;
            }
        }
    }
}
