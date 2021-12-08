public class TruncateSentence {
    public static String truncateSentence(String s, int k) {

        String result = "";
        String[] arrayS = s.split(" ");
        for (int i = 0; i < k - 1; i++) {
            result += arrayS[i] + " ";
        }
        result += arrayS[k - 1];
        return result;

    }

    public static void main(String[] args) {
        String s = "Hello how are you Contestant";
        int k = 4;
        System.out.println(s);
        System.out.println(truncateSentence(s, k));
    }
}
