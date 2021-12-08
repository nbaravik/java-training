public class IsPalindrome {

    public static boolean isPalindrome(int num) {
        boolean result = true;
        String s = String.valueOf(num);
        for (int i = 0; i < s.length() / 2; i++) {
            if (!(s.charAt(i) == s.charAt(s.length() - i - 1))) {
                result = false;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 121;
        int m = -121;
        int p = 110;
        System.out.println(n + " - " + isPalindrome(n));
        System.out.println(m + " - " + isPalindrome(m));
        System.out.println(p + " - " + isPalindrome(p));
    }
}
