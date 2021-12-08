public class BuyAndSellStock {

    // brute force
    /*
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int temp = prices[j] - prices[i];
                if (maxProfit < temp) {
                    maxProfit = temp;
                }
            }
        }
        return maxProfit;
    }
    */

    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = prices[0];
        for (int i=1; i< prices.length; i++) {
            if ( prices[i] < minPrice ) {
                minPrice = prices[i];
            }
            if ( prices[i] - minPrice > maxProfit ) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }


    public static void main(String[] args) {

        int[] prices = {10,2,1,3,4,5,1,2,7,8,1,9,3,1,2,4};

        System.out.print("prices = [");
        for (int i = 0; i < prices.length - 1; i++) {
            System.out.print(prices[i] + ",  ");
        }
        System.out.println(prices[prices.length - 1] + "]");
        System.out.println("The maximum profit is " + maxProfit(prices));
    }
}
