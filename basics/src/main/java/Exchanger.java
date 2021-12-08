import java.util.Scanner;

public class Exchanger {

    private static final double EURO_RATE = 0.86;
    private static final double BYN_RATE = 2.43;


    // convertUSDToEUR(usd)
    static double convertUSDToEUR(int usd) {
        return usd * Exchanger.EURO_RATE;
    }

    // convertUSDToBYN(usd)
    static double convertUSDToBYN(int usd) {
        return usd * Exchanger.BYN_RATE;
    }

    public static void main(String[] args) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter amount in USD:");
            int usd = Integer.valueOf(scanner.nextLine());
            System.out.println(usd + " USD = " + String.format("%.2f", convertUSDToEUR(usd)) + " EUR");
            System.out.println(usd + " USD = " + String.format("%.2f", convertUSDToBYN(usd)) + " BYN");
            // continue case
            System.out.print("Do you want to continue? If NO, press 'e' to exit the Exchanger : ");
            String cont = scanner.nextLine();
            if (cont.equalsIgnoreCase("e")) {
                return;
            }
        }
    }
}
