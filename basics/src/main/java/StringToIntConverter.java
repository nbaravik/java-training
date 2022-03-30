public class StringToIntConverter {

    public static int parseString(String string) {

        double result = 0;
        char[] numChars = string.toCharArray();

        int length = string.length();
        boolean isPositive = true;
        int nextDigit;
        int j = 0;

        if (length == 0) {
            throw new NumberFormatException("String must not be empty");
        }

        if (numChars[0] == '-') {
            if (length >= 12) {
                throw new NumberFormatException("String \"" + string + "\" cannot be converted. Value is less than Integer.MIN_VALUE.");
            }
            isPositive = false;
            j = 1;
        } else if (length >= 11) {
            throw new NumberFormatException("String \"" + string + "\" cannot be converted. Integer.MAX_VALUE exceeded.");
        }

        for (int i = j; i < length; i++) {
            nextDigit = numChars[i] - '0';
            if (nextDigit >= 0 && nextDigit <= 9) {
                result = result + nextDigit * Math.pow(10, length - i - 1);
            } else {
                throw new NumberFormatException("String \"" + string + "\" cannot be converted to Integer");
            }
        }
        result = (isPositive) ? result : result * (-1);

        if (result > Integer.MAX_VALUE) {
            throw new NumberFormatException("String \"" + string + "\" cannot be converted. Integer.MAX_VALUE exceeded.");
        }
        if (result < Integer.MIN_VALUE) {
            throw new NumberFormatException("String \"" + string + "\" cannot be converted. Value is less than Integer.MIN_VALUE.");
        }
        return (int) result;
    }
}
