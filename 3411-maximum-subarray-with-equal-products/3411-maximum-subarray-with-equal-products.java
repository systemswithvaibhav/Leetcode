class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 1; // Every subarray of length 1 (if valid) or length 2 is checked; note for len=1: prod=x, gcd=x, lcm=x -> x == x*x only if x=1, whereas any length 2 subarray always satisfies a*b == lcm(a,b)*gcd(a,b).
        
        for (int i = 0; i < n; i++) {
            long prod = 1;
            long g = 0;
            long l = 1;
            
            for (int j = i; j < n; j++) {
                prod *= nums[j];
                g = gcd(g, nums[j]);
                l = lcm(l, nums[j]);
                
                // Since elements are <= 10, max possible lcm * gcd is bounded.
                // Breaking early prevents long overflow on long subarrays of elements > 1.
                if (prod > 100000) {
                    break;
                }
                
                if (prod == l * g) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        
        return maxLen;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}