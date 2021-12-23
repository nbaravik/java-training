import java.util.ArrayList;
import java.util.List;

public class FindNegatives {

    public static int[] getNegatives(int[] array) {

        List<Integer> listOfNegatives = new ArrayList<>();
        for (int currentItem : array) {
            if (currentItem < 0) {
                listOfNegatives.add(currentItem);
            }
        }
        int[] result = new int[listOfNegatives.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = listOfNegatives.get(i);
        }
        return result;
    }

    public static void main(String[] args) {

        int[] array = {-2, 10, 12, -34, -2, 0, -234, 69};

        for (int currentItem : getNegatives(array)) {
            System.out.print(currentItem + " ");
        }
    }
}
