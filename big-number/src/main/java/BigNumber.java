public class BigNumber {

    public static final BigNumber ZERO = new BigNumber("0", true);

    private String absValue;
    private boolean positive;

    // public constructor with all checks
    public BigNumber(String num) {

        if (num == null) {
            throw new NumberFormatException("String must not be NULL");
        }

        num.trim();
        if (num.length() == 0) {
            throw new NumberFormatException("String must not be empty");
        }

        positive = num.charAt(0) != '-';
        String subString = (positive) ? num : num.substring(1);

        if (isStringValid(subString)) {
            absValue = trimZeros(subString);
            if (!positive && "0".equals(absValue)) {  // special case: "-0" is invalid
                throw new NumberFormatException(num + " is not a number");
            }
        } else {
            throw new NumberFormatException(num + " is not a number");
        }
    }

    // private constructor for internal use
    private BigNumber(String validAbsValue, boolean isPositive) {
        absValue = validAbsValue;
        positive = isPositive;
    }

    private int charToDigit(char ch) {
        return ch - '0';
    }

    // string consists of digits and not empty
    private boolean isStringValid(String str) {
        if (str.length() == 0) return false;

        for (int j = 0; j < str.length(); j++) {
            int digit = charToDigit(str.charAt(j));
            if (digit < 0 || digit > 9) {
                return false;
            }
        }
        return true;
    }

    // "000123" -> "123"
    private String trimZeros(String str) {
        int i = 0;
        while (str.charAt(i) == '0' && i < str.length() - 1 ) {
            i++;
        }
        return str.substring(i);
    }

    // "123" -> "000123"
    private String alignWithZeros(String str, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - str.length(); i++) {
            sb.append('0');
        }
        return sb.append(str).toString();
    }

    public int compareTo(BigNumber num) {

        // this and num have opposite signs
        if (this.positive != num.positive) {
            return this.positive ? 1 : -1;
        }
        // this and num have the same sigh, but this has more digits
        if (absValue.length() > num.absValue.length()) {
            return (this.positive) ? 1 : -1;
        }
        // this and num have the same sigh, but this has fewer digits
        if (absValue.length() < num.absValue.length()) {
            return (this.positive) ? -1 : 1;
        }
        // this and num have the same sigh and same number of digits
        for (int i = 0; i < absValue.length(); i++) {
            int digitThis = charToDigit(this.absValue.charAt(i));
            int digitNum = charToDigit(num.absValue.charAt(i));
            if (digitThis > digitNum) {
                return (this.positive) ? 1 : -1;
            }
            if (digitThis < digitNum) {
                return (this.positive) ? -1 : 1;
            }
        }
        // this and num are equal
        return 0;
    }

    public String toString() {
        return (positive) ? absValue : "-" + absValue;
    }

    // addition of two positive numbers
    private String digitsPlus(String numA, String numB) {

        StringBuilder resultSB = new StringBuilder();

        if (numA.length() > numB.length()) {
            numB = alignWithZeros(numB, numA.length());
        }
        if (numA.length() < numB.length()) {
            numA = alignWithZeros(numA, numB.length());
        }

        int addTens = 0;
        for (int i = numA.length() - 1; i >= 0; i--) {
            int next = charToDigit(numA.charAt(i)) + charToDigit(numB.charAt(i)) + addTens;
            addTens = next / 10;
            next %= 10;
            resultSB.append(next);
        }
        return resultSB.reverse().toString();
    }

    // subtraction of two positive numbers
    private String digitsMinus(String numA, String numB) {

        StringBuilder resultSB = new StringBuilder();

        if (numA.length() > numB.length()) {
            numB = alignWithZeros(numB, numA.length());
        }
        if (numA.length() < numB.length()) {
            numA = alignWithZeros(numA, numB.length());
        }

        int subTens = 0;
        for (int i = numA.length() - 1; i >= 0; i--) {
            int next = charToDigit(numA.charAt(i)) - subTens - charToDigit(numB.charAt(i));
            if (next < 0) {
                subTens = 1;
                next += 10;
            } else {
                subTens = 0;
            }
            resultSB.append(next);
        }
        return trimZeros(resultSB.reverse().toString());
    }

    public BigNumber plus(BigNumber num) {

        // this and num are both positive
        if (this.positive && num.positive) {
            String thisPlusNum = digitsPlus(this.absValue, num.absValue);
            return new BigNumber(thisPlusNum, true);
        }

        // this and num are both negative
        if (!this.positive && !num.positive) {
            String thisPlusNum = digitsPlus(this.absValue, num.absValue);
            return new BigNumber(thisPlusNum, false);
        }

        BigNumber absThis = new BigNumber(this.absValue, true);
        BigNumber absNum = new BigNumber(num.absValue, true);
        int compareAbsValuesInt = absThis.compareTo(absNum);

        // abs_this is bigger than abs_num
        if (compareAbsValuesInt > 0) {
            String thisMinusNum = digitsMinus(this.absValue, num.absValue);
            return new BigNumber(thisMinusNum, this.positive);
        }
        // abs_this smaller than abs_num
        if (absThis.compareTo(absNum) < 0) {
            String numMinusThis = digitsMinus(num.absValue, this.absValue);
            return new BigNumber(numMinusThis, !this.positive);
        }
        // |this| == |num|, but one of it is negative, the other - positive
        return ZERO;
    }

    public BigNumber minus(BigNumber num) {

        // this is positive and num is negative
        if (this.positive && !num.positive) {
            String thisPlusNum = digitsPlus(this.absValue, num.absValue);
            return new BigNumber(thisPlusNum, true);
        }

        // this is negative and num is positive
        if (!this.positive && num.positive) {
            String thisPlusNum = digitsPlus(this.absValue, num.absValue);
            return new BigNumber(thisPlusNum, false);
        }

        BigNumber absThis = new BigNumber(this.absValue, true);
        BigNumber absNum = new BigNumber(num.absValue, true);
        int compareAbsValuesInt = absThis.compareTo(absNum);
        // abs_this is bigger than abs_num
        if (compareAbsValuesInt > 0) {
            String thisMinusNum = digitsMinus(this.absValue, num.absValue);
            return new BigNumber(thisMinusNum, this.positive);
        }
        // abs_this smaller than abs_num
        if (compareAbsValuesInt < 0) {
            String numMinusThis = digitsMinus(num.absValue, this.absValue);
            return new BigNumber(numMinusThis, !this.positive);
        }
        // |this| == |num| and both have the same sign
        return ZERO;
    }
}