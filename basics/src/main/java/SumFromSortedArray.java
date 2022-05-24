public class SumFromSortedArray {

    public static int[] findTwoNumbers(int[] array, int sum) {

//        Set<Integer> set = new HashSet<>();
//        for (int i=0; i<array.length; i++) {
//            int num = array[i];
//            int needed = sum - num;
//
//            if (set.contains(needed)) {
//                return new int[]{num, needed};
//            }
//                set.add(num);
//        }

        int leftIndex = 0;
        int rightIndex = array.length - 1;

        int closerLeft = array[leftIndex];
        int closerRight = array[rightIndex];

        while (leftIndex != rightIndex) {

            int s = array[rightIndex] + array[leftIndex];
            if (s == sum) {
                return new int[]{array[rightIndex], array[leftIndex]};
            }
            if (Math.abs(closerLeft + closerRight - sum) > Math.abs(s - sum)) {
                closerLeft = array[leftIndex];
                closerRight = array[rightIndex];
            }
            if (s < sum) {
                leftIndex++;
            } else {
                rightIndex--;
            }
        }
        return new int[]{closerLeft, closerRight};
    }

    public static void main(String[] args) {
        int[] array = {-10, 0, 2, 7, 10, 14};
        int sum = 15;
        int[] result = findTwoNumbers(array, sum);
        if (result[0] + result[1] != sum) {
            System.out.print("There are no such values. The closest case: ");
        }
            System.out.println(result[0] + " + " + result[1] + " = " + sum);
    }
}
