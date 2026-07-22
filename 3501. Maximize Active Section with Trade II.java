import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
//date change
class Solution {

    private int[] zeroStart;
    private int[] zeroEnd;
    private int[] valleyGain;
    private int zeroBlockCount;
    private List<int[]> sparseTable;

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {

        int totalOnes = (int) s.chars().filter(ch -> ch == '1').count();

        List<Integer> startList = new ArrayList<>();
        List<Integer> endList = new ArrayList<>();

        Matcher matcher = Pattern.compile("0+").matcher(s);
        while (matcher.find()) {
            startList.add(matcher.start());
            endList.add(matcher.end() - 1);
        }

        zeroStart = startList.stream().mapToInt(Integer::intValue).toArray();
        zeroEnd = endList.stream().mapToInt(Integer::intValue).toArray();
        zeroBlockCount = zeroStart.length;

        valleyGain = IntStream.range(0, zeroBlockCount - 1)
                .map(i ->
                        (zeroEnd[i] - zeroStart[i] + 1) +
                        (zeroEnd[i + 1] - zeroStart[i + 1] + 1))
                .toArray();

        buildSparseTable();

        List<Integer> answer = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            answer.add(totalOnes + bestGain(query[0], query[1]));
        }

        return answer;
    }

    private void buildSparseTable() {
        sparseTable = new ArrayList<>();
        sparseTable.add(valleyGain);

        int size = valleyGain.length;

        for (int jump = 1; jump * 2 <= size; jump <<= 1) {
            int[] previous = sparseTable.get(sparseTable.size() - 1);
            int[] current = new int[previous.length - jump];

            for (int i = 0; i < current.length; i++) {
                current[i] = Math.max(previous[i], previous[i + jump]);
            }

            sparseTable.add(current);
        }
    }

    private int rangeMaximum(int left, int right) {
        int power = 31 - Integer.numberOfLeadingZeros(right - left + 1);
        return Math.max(
                sparseTable.get(power)[left],
                sparseTable.get(power)[right - (1 << power) + 1]
        );
    }

    private int clippedGain(int valleyIndex, int leftBound, int rightBound) {

        int leftPenalty = Math.max(0, leftBound - zeroStart[valleyIndex]);
        int rightPenalty = Math.max(0, zeroEnd[valleyIndex + 1] - rightBound);

        return valleyGain[valleyIndex] - leftPenalty - rightPenalty;
    }

    private int bestGain(int left, int right) {

        if (zeroBlockCount < 2) {
            return 0;
        }

        int firstValley = lowerBound(zeroEnd, left);
        int lastValley = upperBound(zeroStart, right) - 2;

        if (firstValley > lastValley) {
            return 0;
        }

        int answer = Math.max(
                clippedGain(firstValley, left, right),
                clippedGain(lastValley, left, right)
        );

        if (lastValley - firstValley >= 2) {
            answer = Math.max(answer, rangeMaximum(firstValley + 1, lastValley - 1));
        }

        return answer;
    }

    private static int lowerBound(int[] array, int target) {
        int low = 0;
        int high = array.length;

        while (low < high) {
            int mid = (low + high) >>> 1;

            if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    private static int upperBound(int[] array, int target) {
        int low = 0;
        int high = array.length;

        while (low < high) {
            int mid = (low + high) >>> 1;

            if (array[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }
}