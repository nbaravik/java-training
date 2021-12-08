import java.util.HashSet;

public class FairCandySwap {

    public static int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
        int[] result = new int[2];
        int aCandies = 0;
        int bCandies = 0;
        for (int aliceBox : aliceSizes) {
            aCandies += aliceBox;
        }
        for (int bobBox : bobSizes) {
            bCandies += bobBox;
        }
        int diff = (aCandies - bCandies) / 2;
        System.out.println(diff);

        HashSet<Integer> setAlice = new HashSet();
        for (int item : aliceSizes) {
            setAlice.add(item);
        }
        for (int item : bobSizes) {
            if (setAlice.contains(item + diff)) {
                result[0] = item + diff;
                result[1] = item;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] aliceSizes = {1, 1};
        int[] bobSizes = {2, 2};


        int[] result = fairCandySwap(aliceSizes, bobSizes);


        // System.out.println(differance);
        System.out.print(result[0] + ", " + result[1]);

    }

}
