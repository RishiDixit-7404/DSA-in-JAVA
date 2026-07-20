class Solution {

    public void rev(int[] arr, int i, int j) {
        while (i < j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            i++;
            j--;
        }
    }

    public List<List<Integer>> shiftGrid(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;
        int size = m * n;

        int[] flat = new int[size];

        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                flat[idx++] = grid[i][j];
            }
        }

        k %= size;

        // Right rotate by k
        rev(flat, 0, size - 1);
        rev(flat, 0, k - 1);
        rev(flat, k, size - 1);

        List<List<Integer>> ans = new ArrayList<>();

        idx = 0;
        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                row.add(flat[idx++]);
            }

            ans.add(row);
        }

        return ans;
    }
}