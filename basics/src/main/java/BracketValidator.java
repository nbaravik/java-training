import java.util.*;

public class BracketValidator {

    private static final Map<Character, Character> map = new HashMap<>();

    static {
        map.put('}', '{');
        map.put(')', '(');
        map.put(']', '[');
    }

    public static boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            Character charFromMap = map.get(ch);
            if (charFromMap == null) {
                stack.push(ch);
                continue;
            }
            if (stack.isEmpty()) {
                return false;
            }
            char chFromStack = stack.pop();
            if (!charFromMap.equals(chFromStack)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {

        String s[] = { null, "", "({})[{((()))[]}]", "[]{}{}((())))[", "[](){}" };
        for (String each : s) {
            if (isValid(each)) {
                System.out.println(each + " - valid");
            } else {
                System.out.println(each + " - not valid");
            }
        }
    }
}