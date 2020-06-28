package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF myGrid;
    private WeightedQuickUnionUF myGridNoVirtuals;
    private int numOpen;

    private int xyTo1D(int x, int y) {
        return x * grid.length + y;
    }

    private boolean[][] grid;

    public Percolation(int N) {
        numOpen = 0;
        if (N <= 0) {
            throw new IllegalArgumentException("Out of bounds for array construction.");
        }
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        myGridNoVirtuals = new WeightedQuickUnionUF(N * N + 1);

        myGrid = new WeightedQuickUnionUF(N * N + 2);
        /*for (int i = 0; i < grid.length; i++) {
            myGrid.union(i, grid.length * grid.length + 1);
        }

        for (int i = 0; i < grid.length; i++) {
            myGridNoVirtuals.union(i, grid.length * grid.length);
        }


        for (int i = grid.length * (grid.length - 1); i < grid.length * grid.length; i++) {
            myGrid.union(i, grid.length * grid.length);
        }*/

    }

    public void open(int row, int col) {
        helperForOutOfRange(row, col);

        if (!grid[row][col]) {
            grid[row][col] = true;
            numOpen++;
        }


        if (row == 0) {
            myGrid.union(xyTo1D(row, col), grid.length * grid.length + 1);
            myGridNoVirtuals.union(xyTo1D(row, col), grid.length * grid.length);
        }
        if (row == grid.length - 1) {
            myGrid.union(xyTo1D(row, col), grid.length * grid.length);
        }

        try {
            if (isOpen(row - 1, col)) {
                myGrid.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                myGridNoVirtuals.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print(e);
        }

        try {
            if (isOpen(row + 1, col)) {

                myGrid.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                myGridNoVirtuals.union(xyTo1D(row, col), xyTo1D(row + 1, col));

            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print(e);
        }

        try {
            if (isOpen(row, col - 1)) {
                myGrid.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                myGridNoVirtuals.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print(e);
        }

        try {
            if (isOpen(row, col + 1)) {
                myGrid.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                myGridNoVirtuals.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print(e);
        }

    } // open the site (row, col) if it is not open already

    private void helperForOutOfRange(int row, int col) {
        if (row > grid.length - 1 || row < 0 || col > grid.length - 1 || col < 0) {
            throw new IndexOutOfBoundsException("Row or column was out of range for grid.");
        }
    }

    public boolean isOpen(int row, int col) {
        helperForOutOfRange(row, col);
        return grid[row][col];

    }

    public boolean isFull(int row, int col) {
        helperForOutOfRange(row, col);
        //for (int i = 0; i < grid.length; i++)
        //     if (myGrid.connected(xyTo1D(row, col), xyTo1D(0, i))) {
        //       return true;
        //   }


        return myGrid.connected(xyTo1D(row, col), grid.length * grid.length + 1)
                && myGridNoVirtuals.connected(xyTo1D(row, col), grid.length * grid.length);
    } // is the site (row, col) full?

    public int numberOfOpenSites() {
        return numOpen;
    }    // number of open sites


    public boolean percolates() {
        return myGrid.connected(grid.length * grid.length + 1, grid.length * grid.length);
    }              // does the system percolate?

    public static void main(String[] args) {
        Percolation myPerc = new Percolation(5);
        myPerc.open(1, 1);
        myPerc.open(2, 1);
        myPerc.open(0, 1);
        System.out.print(myPerc.isFull(2, 1));
    } // use for unit testing (not required, but keep this here for the autograder)

}
