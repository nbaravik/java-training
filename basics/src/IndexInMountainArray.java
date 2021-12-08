public class IndexInMountainArray {

    public static int peakIndexInMountainArray(int[] arr) {

        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] arr = {0,1,0};
        //int[] arr = {24, 69, 100, 99, 79, 78, 67, 36, 26, 19};
        System.out.println(peakIndexInMountainArray(arr));
    }
}
