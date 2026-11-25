import java.util.*;

class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE1 = 1315423911L;
    private static final long BASE2 = 2654435761L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int low = 1, high = n, ans = n;

        // Precompute prefix hashes and powers for double rolling hash
        long[] pref1 = new long[n + 1];
        long[] pref2 = new long[n + 1];
        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        pow1[0] = 1;
        pow2[0] = 1;

        for (int i = 0; i < n; i++) {
            // Offset values so negative/zero numbers hash cleanly
            long val = (nums[i] % MOD1 + MOD1) % MOD1 + 1;
            pref1[i + 1] = (pref1[i] * BASE1 + val) % MOD1;
            pref2[i + 1] = (pref2[i] * BASE2 + val) % MOD2;
            pow1[i + 1] = (pow1[i] * BASE1) % MOD1;
            pow2[i + 1] = (pow2[i] * BASE2) % MOD2;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUniqueSubarray(n, mid, pref1, pref2, pow1, pow2)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUniqueSubarray(int n, int len, long[] pref1, long[] pref2, long[] pow1, long[] pow2) {
        Map<Long, Integer> freq = new HashMap<>();

        for (int i = 0; i <= n - len; i++) {
            int j = i + len;
            long h1 = (pref1[j] - (pref1[i] * pow1[len]) % MOD1 + MOD1) % MOD1;
            long h2 = (pref2[j] - (pref2[i] * pow2[len]) % MOD2 + MOD2) % MOD2;
            long combinedHash = (h1 << 32) | h2;

            freq.put(combinedHash, freq.getOrDefault(combinedHash, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) {
                return true;
            }
        }
        return false;
    }
}