package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] results;


    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;


    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T outside of range for constructor.");
        }
        results = new double[T];

        for (int i = 0; i < T; i++) {
            int thisRun = 0;
            Percolation myPerc = pf.make(N);
            while (!myPerc.percolates()) {

                int randomNum;
                int randomNum2;
                do {
                    randomNum = StdRandom.uniform(0, N);
                    randomNum2 = StdRandom.uniform(0, N);
                } while (myPerc.isOpen(randomNum, randomNum2));

                myPerc.open(randomNum, randomNum2);
                thisRun++;
            }

            results[i] = ((double) thisRun) / (N * N);
        }

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceHi = mean + (1.96 * stddev()) / Math.sqrt(T);
        confidenceLo = mean - (1.96 * stddev()) / Math.sqrt(T);
    }   // perform T independent experiments on an N-by-N grid

    public double mean() {
        return mean;
    }                                       // sample mean of percolation threshold

    public double stddev() {
        return stddev;
    } // sample standard deviation of percolation threshold

    public double confidenceLow() {
        return confidenceLo;
    } // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return confidenceHi;
    }

}
