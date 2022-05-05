import java.util.LinkedList;
import java.util.stream.Collectors;

public class BigNumber {

    private String absValue;
    private boolean positive;

    public BigNumber(String num) {
        if (num.length() == 0) {
            throw new NumberFormatException("String must not be empty");
        }

        int i = 0;
        char[] numChars = num.toCharArray();
        if (numChars[0] == '-') {
            if (numChars.length > 1) {
                positive = false;
                i++;
            } else {
                throw new NumberFormatException(num + " is not a number");
            }
        } else {
            positive = true;
        }

        for (int j = i; j < numChars.length; j++) {
            int digit = numChars[j] - '0';
            if (digit < 0 || digit > 9) {
                throw new NumberFormatException(num + " is not a number");
            }
        }
        absValue = num.substring(i);
    }

    public boolean isPositive() {
        return positive;
    }

    public String getAbsValue() {
        return absValue;
    }

    public int compareTo(BigNumber num) {

        // this - positive, num - negative
        if (this.positive && !num.isPositive()) {
            return 1;
        }
        // this - negative, num - positive
        if (!this.positive && num.isPositive()) {
            return -1;
        }
        // this - positive, num - positive, this has more digits than num
        if (absValue.length() > num.getAbsValue().length() && this.positive && num.isPositive()) {
            return 1;
        }
        // this - positive, num - positive, this has fewer digits than num
        if (absValue.length() < num.getAbsValue().length() && this.positive && num.isPositive()) {
            return -1;
        }
        // this - negative, num - negative, this has more digits than num
        if (absValue.length() > num.getAbsValue().length() && !this.positive && !num.isPositive()) {
            return -1;
        }
        // this - negative, num - negative, this has fewer digits than num
        if (absValue.length() < num.getAbsValue().length() && !this.positive && !num.isPositive()) {
            return 1;
        }
        // this and num match in number of digits and signs
        if (absValue.length() == num.getAbsValue().length()) {
            for (int i = 0; i < absValue.length(); i++) {
                int digitThis = this.absValue.charAt(i) - '0';
                int digitNum = num.getAbsValue().charAt(i) - '0';
                if (digitThis != digitNum) {
                    if (this.positive && num.isPositive()) {
                        return (digitThis > digitNum) ? 1 : -1;
                    }
                    if (!this.positive && !num.isPositive()) {
                        return (digitThis > digitNum) ? -1 : 1;
                    }
                }
            }
        }
        // this and num are equal
        return 0;
    }

    public String toString() {
        if (positive) {
            return absValue;
        } else {
            return "-" + absValue;
        }
    }

    private int[] stringToArrayOfDigits(String s, int arraySize) {
        int[] result = new int[arraySize];
        int delta = arraySize - s.length();

        for (int i = 0; i < arraySize; i++) {
            if (i < delta) {
                result[i] = 0;
            } else {
                result[i] = s.charAt(i - delta) - '0';
            }
        }
        return result;
    }

    // addition of two positive numbers
    private String digitsPlus(String number1, String number2) {

        int arraysLength;
        if (number1.length() >= number2.length()) {
            arraysLength = number1.length();
        } else {
            arraysLength = number2.length();
        }
        int[] array1 = stringToArrayOfDigits(number1, arraysLength);
        int[] array2 = stringToArrayOfDigits(number2, arraysLength);

        LinkedList<Integer> resultList = new LinkedList<>();
        int additional = 0;
        for (int i = array1.length - 1; i >= 0; i--) {

            int next = array1[i] + array2[i] + additional;
            if (next < 10) {
                resultList.addFirst(next);
                additional = 0;
            } else {
                resultList.addFirst(next % 10);
                additional = 1;
            }
        }
        String resultString = resultList.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(""));
        return resultString;
    }

    // subtraction of two positive numbers
    private String digitsMinus(String number1, String number2) {

        int arraysLength;
        if (number1.length() >= number2.length()) {
            arraysLength = number1.length();
        } else {
            arraysLength = number2.length();
        }
        int[] array1 = stringToArrayOfDigits(number1, arraysLength);
        int[] array2 = stringToArrayOfDigits(number2, arraysLength);

        LinkedList<Integer> resultList = new LinkedList<>();
        for (int i = array1.length - 1; i >= 0; i--) {
            if (array1[i] < array2[i]) {
                array1[i] += 10;
                int j = i - 1;
                while (array1[j] == 0) {
                    array1[j] += 9;
                    j--;
                }
                array1[j]--;
            }
            int next = array1[i] - array2[i];
            resultList.addFirst(next);
        }
        while (resultList.getFirst() == 0 && resultList.size() > 1) {
            resultList.removeFirst();
        }
        String resultString = resultList.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(""));
        return resultString;
    }

    public BigNumber plus(BigNumber num) {

        // this and num are both positive
        if (this.positive && num.isPositive()) {
            String plusThisPlusNum = digitsPlus(this.absValue, num.getAbsValue());
            return new BigNumber(plusThisPlusNum);
        }

        // this and num are both negative
        if (!this.positive && !num.isPositive()) {
            String minusThisMinusNum = "-" + digitsPlus(this.absValue, num.getAbsValue());
            return new BigNumber(minusThisMinusNum);
        }

        BigNumber absThis = new BigNumber(this.absValue);
        BigNumber absNum = new BigNumber(num.getAbsValue());
        // abs_this is bigger than abs_num
        if (absThis.compareTo(absNum) > 0) {
            return (this.positive) ? new BigNumber(digitsMinus(this.absValue, num.getAbsValue())) :
                    new BigNumber("-" + digitsMinus(this.absValue, num.getAbsValue()));
        }
        // abs_this smaller than abs_num
        if (absThis.compareTo(absNum) < 0) {
            return (this.positive) ? new BigNumber("-" + digitsMinus(num.getAbsValue(), this.absValue)) :
                    new BigNumber(digitsMinus(num.getAbsValue(), this.absValue));
        }
        // |this| == |num|, but one of it is negative, the other - positive
        return new BigNumber("0");
    }


    public BigNumber minus(BigNumber num) {

        // this is positive and num is negative
        if (this.positive && !num.isPositive()) {
            String plusThisPlusNum = digitsPlus(this.absValue, num.getAbsValue());
            return new BigNumber(plusThisPlusNum);
        }

        // this is negative and num is positive
        if (!this.positive && num.isPositive()) {
            String minusThisMinusNum = "-" + digitsPlus(this.absValue, num.getAbsValue());
            return new BigNumber(minusThisMinusNum);
        }

        BigNumber absThis = new BigNumber(this.absValue);
        BigNumber absNum = new BigNumber(num.getAbsValue());
        // abs_this is bigger than abs_num
        if (absThis.compareTo(absNum) > 0) {
            return (this.positive) ? new BigNumber(digitsMinus(this.absValue, num.getAbsValue())) :
                    new BigNumber("-" + digitsMinus(this.absValue, num.getAbsValue()));
        }
        // abs_this smaller than abs_num
        if (absThis.compareTo(absNum) < 0) {
            return (this.positive) ? new BigNumber("-" + digitsMinus(num.getAbsValue(), this.absValue)) :
                    new BigNumber(digitsMinus(num.getAbsValue(), this.absValue));
        }
        // |this| == |num| and both have the same sign
        return new BigNumber("0");
    }
}
