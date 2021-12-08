import java.util.Arrays;

public class AssignCookies {

    public static int findContentChildren(int[] g, int[] s) {
        int result = 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0; //index for greed
        int j = 0; //index for cookies
        while ( i < g.length && j < s.length ) {
            if ( g[i] <= s[j]) {
                result++;
                i++;
                j++;
            } else {
                j++;
            }
        }
        return result;
    }

    public static void main (String[] args) {
        int[] childrenGreed = { 33, 3, 3, 5, 1 };
        int[] cookies = {33, 1, 2, 33};
        System.out.println("The number of content children is " + findContentChildren(childrenGreed, cookies));
    }

}
