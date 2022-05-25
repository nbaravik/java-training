/*
Leetcode #866. Prime palindrome.
Given an integer n, return the smallest prime palindrome greater than or equal to n.
The test cases are generated so that the answer always exists and is in the range [2, 2 * 108].
 */

public class PrimePalindrome {

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        // enough to check the factors from 2 to sqrt(n)
        for (int j = 2; j * j <= n; j++) {
            if (n % j == 0) {
                return false;
            }
        }
        return true;
    }

    public static int nearestPalindromic(int n) {

        int order = (int) Math.pow(10, String.valueOf(n).length() / 2);
        int withoutChange = mirror(n);
        int larger = mirror((n / order) * order + order);
        if (withoutChange >= n) {
            larger = Math.min(withoutChange, larger);
        }
        return larger;
    }

    private static int mirror(int num) {
        char[] a = String.valueOf(num).toCharArray();
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            a[j--] = a[i++];
        }
        return Integer.valueOf(new String(a));
    }

    public static int primePalindrome(int n) {
        int next = nearestPalindromic(n);
        if (isPrime(next)) {
            return next;
        }
        return primePalindrome(next + 1);
    }

    public static void main(String[] args) {

        int[] test = new int[]{0, 1, 3, 9, 11, 21, 101, 151, 161, 181, 194, 7997, 375356933};
        for (int each : test) {
            System.out.println("The closest prime palindrome for " + each + " is " + primePalindrome(each));
        }
    }
}