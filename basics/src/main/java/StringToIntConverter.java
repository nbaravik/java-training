import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StringToIntConverter {

    public static final Map<Byte, Integer> digitsHashMap;

    static {
        digitsHashMap = new HashMap<>();
        digitsHashMap.put((byte) 48, 0);
        digitsHashMap.put((byte) 49, 1);
        digitsHashMap.put((byte) 50, 2);
        digitsHashMap.put((byte) 51, 3);
        digitsHashMap.put((byte) 52, 4);
        digitsHashMap.put((byte) 53, 5);
        digitsHashMap.put((byte) 54, 6);
        digitsHashMap.put((byte) 55, 7);
        digitsHashMap.put((byte) 56, 8);
        digitsHashMap.put((byte) 57, 9);
    }

    public static int parseString(String string) {

        int result = 0;
        byte[] numChars = string.getBytes(StandardCharsets.UTF_8);
        int length = string.length();
        boolean isPositive = true;
        int nextDigit;
        int j = 0;

        if (length == 0) {
            throw new NumberFormatException("String must not be empty");
        }

        if (numChars[0] == '-') {
            isPositive = false;
            j = 1;
        }

        for (int i = j; i < length; i++) {
            if (digitsHashMap.containsKey(numChars[i])) {
                nextDigit = digitsHashMap.get(numChars[i]);
                result += nextDigit * Math.pow(10, length - i - 1);
            } else {
                throw new NumberFormatException("String \"" + string + "\" cannot be converted to Integer");
            }
        }
        return (isPositive) ? result : result * (-1);
    }
}
