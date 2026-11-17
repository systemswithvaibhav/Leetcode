import java.util.Arrays;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1_000_000;
        
        // cost[i][j] = min changes to make s[i..j] a semi-palindrome
        int[][] cost = new int[n][n];
        for (int[] row : cost) {
            Arrays.fill(row, INF);
        }

        // 1. Precompute semi-palindrome costs for all substrings of length >= 2
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                cost[i][j] = getMinChanges(s, i, j);
            }
        }

        // 2. DP to partition into k substrings
        // dp[i][p] = min changes for prefix of length i using p partitions
        int[][] dp = new int[n + 1][k + 1];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        dp[0][0] = 0;

        for (int p = 1; p <= k; p++) {
            // Each partition needs at least length 2, so i starts at 2 * p
            for (int i = 2 * p; i <= n; i++) {
                for (int j = 2 * (p - 1); j <= i - 2; j++) {
                    if (dp[j][p - 1] != INF && cost[j][i - 1] != INF) {
                        dp[i][p] = Math.min(dp[i][p], dp[j][p - 1] + cost[j][i - 1]);
                    }
                }
            }
        }

        return dp[n][k];
    }

    private int getMinChanges(String s, int start, int end) {
        int len = end - start + 1;
        int minChanges = Integer.MAX_VALUE;

        // Try all proper divisors d of len
        for (int d = 1; d * 2 <= len; d++) {
            if (len % d != 0) continue;

            int changes = 0;
            // Check each of the d interleaved groups
            for (int offset = 0; offset < d; offset++) {
                int left = start + offset;
                int right = end - d + 1 + offset;
                
                while (left < right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        changes++;
                    }
                    left += d;
                    right -= d;
                }
            }
            minChanges = Math.min(minChanges, changes);
        }

        return minChanges;
    }
}