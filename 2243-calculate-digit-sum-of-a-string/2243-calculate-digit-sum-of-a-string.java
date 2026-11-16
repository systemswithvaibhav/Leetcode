class Solution {
    public String digitSum(String s, int k) {
        while (s.length() > k) {
            StringBuilder nextRound = new StringBuilder();
            
            // Process the string in chunks of size k
            for (int i = 0; i < s.length(); i += k) {
                int groupSum = 0;
                int end = Math.min(i + k, s.length());
                
                // Sum the digits in the current chunk
                for (int j = i; j < end; j++) {
                    groupSum += s.charAt(j) - '0';
                }
                
                nextRound.append(groupSum);
            }
            
            s = nextRound.toString();
        }
        
        return s;
    }
}