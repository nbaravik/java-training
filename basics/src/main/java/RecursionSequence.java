public class RecursionSequence {

    public static void printAscending(int n) {
        if (n > 1) {
            printAscending(n - 1);
        }
        System.out.print(n + "  ");
    }

    public static void printDescending(int n) {
        System.out.print(n + "  ");
        if (n > 1) {
            printDescending(n - 1);
        }
    }

    public static void main(String[] args) {
        printAscending(15);
        System.out.println();
        printDescending(19);
    }
}
