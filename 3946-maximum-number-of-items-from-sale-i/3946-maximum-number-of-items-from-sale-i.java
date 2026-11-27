class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;
        int[] val = new int[n];

        // 1. Precompute first-purchase value for each item & find global minPrice
        for (int i = 0; i < n; i++) {
            minPrice = Math.min(minPrice, items[i][1]);
            int count = 1; // Buying item i itself
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    count++;
                }
            }
            val[i] = count;
        }

        // 2. 0/1 Knapsack for the first purchase of each distinct item
        int[] dp = new int[budget + 1];
        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int v = val[i];
            for (int w = budget; w >= price; w--) {
                dp[w] = Math.max(dp[w], dp[w - price] + v);
            }
        }

        // 3. Fill any remaining budget with the cheapest item (1 item per minPrice)
        int maxItems = 0;
        for (int w = 0; w <= budget; w++) {
            int extraItems = (budget - w) / minPrice;
            maxItems = Math.max(maxItems, dp[w] + extraItems);
        }

        return maxItems;
    }
}