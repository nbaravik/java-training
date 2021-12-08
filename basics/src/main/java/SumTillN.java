import java.util.Scanner;

public class SumTillN {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number n>0 : ");
        int n = scanner.nextInt();
        int result = 0;
        for (int i=1; i <= n; i++) {
            result += i;
        }
        System.out.println("From 1 to " + n + " sum is " + result);
    }
}
