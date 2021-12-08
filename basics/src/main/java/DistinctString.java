import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DistinctString {

    public static String kthDistinct(String[] arr, int k) {

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arr));
        ArrayList<String> distinctStrings = new ArrayList<>();
        for (String arrayItem : arrayList) {
            if (Collections.frequency(arrayList, arrayItem) == 1) {
                distinctStrings.add(arrayItem);
            }
        }

        if (distinctStrings.isEmpty() || distinctStrings.size() < k - 1) {
            return "";
        } else {
            return distinctStrings.get(k - 1);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"d", "b", "c", "b", "c", "a"};
        // String[] arr = {"aaa", "aa", "a"};
        int k = 2;
        System.out.println("K-th distinct string is \"" + kthDistinct(arr, k) + "\"");
    }
}
