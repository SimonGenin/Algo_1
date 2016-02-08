import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Random;

public class Percolation {

    private WeightedQuickUnionUF quickUnionAlgo;
    private boolean[] grid;
    private int sideLength;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)  {
        if (N <= 0) throw new IllegalArgumentException("Percolation.Percolation(int)");
        sideLength = N;
        quickUnionAlgo = new WeightedQuickUnionUF(N * N);
        grid = new boolean[N * N];

        for (int i = 0 ; i < N * N ; i++) {
            grid[i] = false;
        }

    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (i <= 0 || i > sideLength || j <= 0 || j > sideLength) throw new IndexOutOfBoundsException("Percolation.open(int, int)");
        grid[getIndex(i, j)] = true;

        // Connect les 4 cas
        if (i - 1 > 1)
            if (isOpen(i - 1, j)) quickUnionAlgo.union(getIndex(i - 1, j), getIndex(i, j));
        if (i + 1 < sideLength)
            if (grid[getIndex(i + 1, j)]) quickUnionAlgo.union(getIndex(i + 1, j), getIndex(i, j));
        if (j - 1 > 1)
            if (grid[getIndex(i, j - 1)]) quickUnionAlgo.union(getIndex(i, j - 1), getIndex(i, j));
        if (j + 1 < sideLength)
            if (grid[getIndex(i, j + 1)]) quickUnionAlgo.union(getIndex(i, j + 1), getIndex(i, j));
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > sideLength || j <= 0 || j > sideLength) throw new IndexOutOfBoundsException("Percolation.isOpen(int, int)");
        return grid[getIndex(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > sideLength || j <= 0 || j > sideLength) throw new IndexOutOfBoundsException("Percolation.isFull(int, int) : " + i + ", " + j);

        for (int k = 0 ; k < sideLength ; k++) {

            if (grid[k] == true && quickUnionAlgo.connected(k, getIndex(i, j))) {
                System.out.print("Site full");
                return true;
            }

        }

        return  false;
    }

    // does the system percolate?
    public boolean percolates()     {

        for (int i = 1 ; i <= sideLength ; i++) {
            for (int j = 1 ; j <= sideLength ; j++) {
                if ( isFull(sideLength, i) && isFull(1, j) && quickUnionAlgo.connected(getIndex(sideLength, i), getIndex(1, j)))
                    return true;
            }

        }

        for (int i = 0; i < grid.length; i++) {
            System.out.print(grid[i] + " ");
        }
        System.out.println();

        return false;
    }

    private int getIndex(int i, int j) {
        return (i - 1) * sideLength + (j - 1);
    }

    public static void main(String[] args){

        Percolation p = new Percolation(20);

        int siteIndexI = 0;
        int siteIndexJ = 0;

        while (true) {

            siteIndexI = StdRandom.uniform(1, 21);
            siteIndexJ = StdRandom.uniform(1, 21);

            if (!p.isOpen(siteIndexI, siteIndexJ)) p.open(siteIndexI, siteIndexJ);

            if (p.percolates()) break;

        }

        System.out.println("Yep");

    }
}