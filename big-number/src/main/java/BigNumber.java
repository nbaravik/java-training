public class BigNumber {

    private static final BigNumber BIG_NUMBER_ZERO = new BigNumber("0", true);

    private String absValue;
    private boolean positive;

    // public constructor with all checks
    public BigNumber(String num) {

        if (num == null) {
            throw new NumberFormatException("String must not be NULL!");
        }

        num.trim();
        if (num.length() == 0) {
            throw new NumberFormatException("String must not be empty!");
        }

        positive = (num.charAt(0) == '-') ? false : true;

        int i = positive ? 0 : 1;
        boolean startOfLine = true;

        for (int j = i; j < num.length(); j++) {
            int digit = charToDigit(num.charAt(j));
            if (digit < 0 || digit > 9) {
                throw new NumberFormatException(num + " is not a number");
            } else if (digit == 0 && startOfLine) {
                i++;
            } else {
                startOfLine = false;
            }
        }

        // case   "00" = "0",       case   "012" = "12"
        // case  "-00" - INVALID,   case  "-012" = "-12"
        if (!positive && num.length() == i) {
            throw new NumberFormatException(num + " is not a number");
        } else {
            absValue = (i < num.length()) ? num.substring(i) : "0";
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

    // "123" -> "000123"
    private String addSomeZeros(String str, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - str.length(); i++) {
            sb.append('0');
        }
        return sb.append(str).toString();
    }

    public int compareTo(BigNumber num) {

        // this - positive, num - negative
        if (this.positive && !num.positive) {
            return 1;
        }
        // this - negative, num - positive
        if (!this.positive && num.positive) {
            return -1;
        }
        // both has the same sigh, but this has more digits
        if (absValue.length() > num.absValue.length()) {
            return (this.positive) ? 1 : -1;
        }
        // both has the same sigh, but this has fewer digits
        if (absValue.length() < num.absValue.length()) {
            return (this.positive) ? -1 : 1;
        }

        // both has the same sigh and same number of digits
        if (absValue.length() == num.absValue.length()) {
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
        }
        // this and num are equal
        return 0;
    }

    public String toString() {
        return (positive) ? absValue : "-" + absValue;
    }

    // addition of two positive numbers
    private String digitsPlus(String num1, String num2) {

        StringBuilder resultSB = new StringBuilder();

        if (num1.length() > num2.length()) {
            num2 = addSomeZeros(num2, num1.length());
        }
        if (num1.length() < num2.length()) {
            num1 = addSomeZeros(num1, num2.length());
        }

        int addTens = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int next = charToDigit(num1.charAt(i)) + charToDigit(num2.charAt(i)) + addTens;
            if (next < 10) {
                resultSB.append(next);
                addTens = 0;
            } else {
                resultSB.append(next % 10);
                addTens = 1;
            }
        }
        resultSB.trimToSize();
        return resultSB.reverse().toString();
    }

    // subtraction of two positive numbers
    private String digitsMinus(String num1, String num2) {

        StringBuilder resultSB = new StringBuilder();

        if (num1.length() > num2.length()) {
            num2 = addSomeZeros(num2, num1.length());
        }
        if (num1.length() < num2.length()) {
            num1 = addSomeZeros(num1, num2.length());
        }

        int addUnits = 0;
        int subTens = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int next = charToDigit(num1.charAt(i)) - subTens - charToDigit(num2.charAt(i));
            if (next < 0) {
                subTens = 1;
                addUnits = 10;
            } else {
                subTens = 0;
            }
            next += addUnits;
            resultSB.append(next);
            addUnits = 0;
        }

        int i = resultSB.length() - 1;
        while (resultSB.charAt(i) == '0' && resultSB.length() > 1) {
            resultSB.deleteCharAt(i);
            i--;
        }
        resultSB.trimToSize();
        return resultSB.reverse().toString();
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
            return (this.positive) ? new BigNumber(thisMinusNum, true) : new BigNumber(thisMinusNum, false);
        }
        // abs_this smaller than abs_num
        if (absThis.compareTo(absNum) < 0) {
            String numMinusThis = digitsMinus(num.absValue, this.absValue);
            return (this.positive) ? new BigNumber(numMinusThis, false) : new BigNumber(numMinusThis, true);
        }
        // |this| == |num|, but one of it is negative, the other - positive
        return BIG_NUMBER_ZERO;
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
            return (this.positive) ? new BigNumber(thisMinusNum, true) : new BigNumber(thisMinusNum, false);
        }
        // abs_this smaller than abs_num
        if (compareAbsValuesInt < 0) {
            String numMinusThis = digitsMinus(num.absValue, this.absValue);
            return (this.positive) ? new BigNumber(numMinusThis, false) : new BigNumber(numMinusThis, true);
        }
        // |this| == |num| and both have the same sign
        return BIG_NUMBER_ZERO;
    }
}