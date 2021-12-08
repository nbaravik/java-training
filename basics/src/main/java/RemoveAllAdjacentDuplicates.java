public class RemoveAllAdjacentDuplicates {

    public static String removeDuplicates(String s) {
        if (s.length() < 2) {
            return s;
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < s.length(); ++i) {
            if (builder.length() > 0 && s.charAt(i) == builder.charAt(builder.length() - 1)) {
                builder.deleteCharAt(builder.length() - 1);
                continue;
            }
            builder.append(s.charAt(i));
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        //List emp = new List();

        //String s = "abbaca";
        //String s = "azxxzy";
        String s = "aaaaaaaa";
       // String s = "aababaab";

        System.out.println(s);
        System.out.println(removeDuplicates(s));
    }
}
