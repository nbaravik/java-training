public class MathLibrary {

    public static void main(String[] args) {

        // сгенерируем целое число в диапазоне [min; max]
        // (int)(Math.random()*(max - min + 1)) + min;
        int val = (int) (Math.random() * 201 - 100); //[-100; 100]
        int abs = Math.abs(val);
        int pow3 = (int) (Math.pow(val, 3));

        System.out.println("Variable: " + val);
        System.out.println("Absolute value: " + abs);
        System.out.println(val + " raised to the power of 3 : " + pow3);
    }

}

