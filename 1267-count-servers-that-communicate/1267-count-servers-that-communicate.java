class Solution {
    public int countServers(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[] rowCount = new int[rows];
        int[] colCount = new int[cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    rowCount[r]++;
                    colCount[c]++;
                }
            }
        }

        int communicating = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1 &&
                    (rowCount[r] > 1 || colCount[c] > 1)) {
                    communicating++;
                }
            }
        }

        return communicating;
    }
}