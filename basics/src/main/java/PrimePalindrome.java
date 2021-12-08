public class PrimePalindrome {

    public static int closerPalindrom (int n) {
        int palindome = n;
        StringBuffer num = new StringBuffer(n);
        for (int i=0; i<num.capacity()/2; i++) {
           // num.s
        }
        return n;
    }
    public static int primePalindrome(int n) {
        int primeP = n;
        if (n > 1) {
            for ( int j=2; j*j <= n; j++) {
                if ( n % j == 0 ) {
                    System.out.println("Number n is not prime!");
                    return 0;
                }
            }
            System.out.println("Number n is prime!");

            return primeP;
        } else {
            System.out.println("Number n is not prime!");
            return 2;
        }

    }

    public static void main(String[] args) {
        int n = 6;
        System.out.println ( 1 + " -> " + primePalindrome(1));
        System.out.println( 21 + " -> " + primePalindrome(21));
        System.out.println ( 11 + " -> " + primePalindrome(11));
        System.out.println ( 101 + " -> " + primePalindrome(101));

    }
}
