import java.util.Locale;

public class StringVariale {


    public static void main(String[] args) {

        String initial = "apple orange pear pineapple banana";

        // all values separated by comma
        System.out.println(initial.replaceAll(" ", ", "));

        // every word in a separate line: each value with count of characters in it
        String[] fruits = initial.split(" ");
        for (int i = 0; i < fruits.length; i++)
            System.out.println(fruits[i] + " - " + fruits[i].length());

        // in one line: all values in upper case
        System.out.println(initial.toUpperCase(Locale.ROOT));

        // in one line: first character of each word
        for (int i = 0; i < fruits.length; i++)
            System.out.print(fruits[i].charAt(0) + " ");


    }


}
