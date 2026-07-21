class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int oneCount = 0;
        int maxMergedZeros = 0;
        int currZeroCount = 0;
        int lastZeroCount = 0;

        for (char c : s.toCharArray()) {
            if (c == '0') {
                currZeroCount++;
            } else {
                if (currZeroCount != 0) {
                    lastZeroCount = currZeroCount;
                }
                currZeroCount = 0;
                oneCount++;
            }
            maxMergedZeros = Math.max(maxMergedZeros, currZeroCount + lastZeroCount);
        }

        if (maxMergedZeros == currZeroCount || maxMergedZeros == lastZeroCount) {
            return oneCount;
        }

        return oneCount + maxMergedZeros;
    }
}