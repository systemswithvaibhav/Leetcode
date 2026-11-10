class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int j = 1; j <= n; j++) {
            for (int d = 1; d <= 3 && d <= j; d++) {
                int i = j - d;
                dp[j] = Math.min(dp[j], dp[i] + costs[j - 1] + d * d);
            }
        }

        return dp[n];
    }
}