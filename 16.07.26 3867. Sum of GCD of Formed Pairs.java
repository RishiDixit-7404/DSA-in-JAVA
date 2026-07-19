import java.util.Arrays;

class Solution {
    public long gcdSum(int[] nums) {
        int n = nums.length;
        int[] prefixGcd = new int[n];
        int maxVal = 0;
        
        // 1. Construct the prefix GCD array
        for (int i = 0; i < n; i++) {
            maxVal = Math.max(maxVal, nums[i]);
            prefixGcd[i] = gcd(nums[i], maxVal);
        }
        
        // 2. Sort in non-decreasing order
        Arrays.sort(prefixGcd);
        
        // 3. Form pairs (smallest and largest) and sum the GCDs
        long sumOfGcds = 0;
        for (int i = 0; i < n / 2; i++) {
            sumOfGcds += gcd(prefixGcd[i], prefixGcd[n - 1 - i]);
        }
        
        return sumOfGcds;
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
