package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidentLow;
    private double confidentHigh;

    public PercolationStats(int N, int T){
        if(N < 0 || T < 0)
            throw new IllegalArgumentException("illegal T or N");
        double[] sampleValues = new double[T];
        int totalSites = N * N;
        for(int i = 0; i < T; i++){
            Percolation p = new Percolation(N);
            while(!p.percolates()){
                openRandomSite(p, N);
            }
            mean += p.numberOfOpenSites() / totalSites;
            sampleValues[i] = p.numberOfOpenSites() / totalSites;
        }
        mean /= T;
        stddev = calculateStdDev(sampleValues, T);
        confidentLow = mean - 1.96*stddev / Math.sqrt(T);
        confidentHigh = mean + 1.96 * stddev / Math.sqrt(T);

    }

    private double calculateStdDev(double[] sampleValues, int T){
        double stdDev = 0.0;
        for(double val: sampleValues){
            stdDev += Math.pow(val - mean, 2);
        }
        stdDev /= T - 1;
        stdDev = Math.sqrt(stdDev);
        return stdDev;
    }

    private static void openRandomSite(Percolation p, int N){
        int row, col;
        do{
            row = StdRandom.uniform(N);
            col = StdRandom.uniform(N);
        }while(p.isOpen(row, col));
        p.open(row,col);
    }


    public double mean(){
        return mean;
    }
    public double stddev(){
        return stddev;
    }

    public double confidenceLow(){
        return confidentLow;
    }

    public double confidenceHigh(){
        return  confidentHigh;
    }

}                       
