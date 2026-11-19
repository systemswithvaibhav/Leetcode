import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        int n = nums.length;
        if (n <= 3) {
            return 0;
        }
        
        Arrays.sort(nums);
        
        int option1 = nums[n - 1] - nums[2];     // Remove 2 smallest
        int option2 = nums[n - 3] - nums[0];     // Remove 2 largest
        int option3 = nums[n - 2] - nums[1];     // Remove 1 smallest and 1 largest
        
        return Math.min(option1, Math.min(option2, option3));
    }
}