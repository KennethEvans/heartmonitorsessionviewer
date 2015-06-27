/*
 * Program to generate statistics from an array of doubles
 * Created on Apr 15, 2006
 * By Kenneth Evans, Jr.
 */

package net.kenevans.heartmonitorsessionviewer.utils;

/**
 * Statistics calculates the max, min, mean, and standard deviation for an array
 * of doubles. The standard deviation is sample-based and uses n-1, not n;
 * 
 * @author Kenneth Evans, Jr.
 */
public class Statistics
{
    private double max = 0.0;
    private double min = 0.0;
    private double mean = 0.0;
    private double sigma = 0.0;
    private double rms = 0.0;
    private int maxIndex = 0;
    private int minIndex = 0;
    private int nPoints;

    /**
     * Statistics constructor
     * 
     * @param array
     */
    public Statistics(double[] array) {
        int nPoints = array.length;
        if(nPoints == 0) return;

        max = -Double.MAX_VALUE;
        min = Double.MAX_VALUE;
        double sum = 0.0;
        double sumsq = 0.0;
        for(int i = 0; i < nPoints; i++) {
            double val = array[i];
            if(val > max) {
                max = val;
                maxIndex = i;
            }
            if(val < min) {
                min = val;
                minIndex = i;
            }
            sum += val;
            sumsq += val * val;
        }
        mean = sum / nPoints;
        rms = Math.sqrt(sumsq / nPoints);
        sigma = (sumsq - nPoints * mean * mean) / (nPoints - 1);
        sigma = Math.sqrt(sigma);
    }

    /**
     * @return Returns the max.
     */
    public double getMax() {
        return max;
    }

    /**
     * @return Returns the min.
     */
    public double getMin() {
        return min;
    }

    /**
     * @return Returns the mean.
     */
    public double getMean() {
        return mean;
    }

    /**
     * @return Returns the rms.
     */
    public double getRms() {
        return rms;
    }

    /**
     * @return Returns the sample standard deviation, sigma, using n - 1, not n;
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * @return Returns the maxIndex.
     */
    public int getMaxIndex() {
        return maxIndex;
    }

    /**
     * @return Returns the minIndex.
     */
    public int getMinIndex() {
        return minIndex;
    }

    /**
     * @return Returns the number of points.
     */
    public int getNPoints() {
        return nPoints;
    }

    /**
     * @return Returns a summary of the statistics.
     */
    public String getInfo() {
        String info = "";
        info += "Max:   " + max + " at i = " + maxIndex + "\n";
        info += "Min:   " + min + " at i = " + minIndex + "\n";
        info += "Mean:  " + mean + "\n";
        info += "Sigma: " + sigma + "\n";
        info += "RMS:   " + rms + "\n";
        return info;
    }

}
