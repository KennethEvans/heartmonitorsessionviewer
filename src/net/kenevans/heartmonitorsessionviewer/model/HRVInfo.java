package net.kenevans.heartmonitorsessionviewer.model;

import net.kenevans.heartmonitorsessionviewer.utils.Statistics;

/**
 * HRVInfo is a class to manage HRV parameters.
 * 
 * @author Kenneth Evans, Jr.
 */
public class HRVInfo
{
    /** AHRV in Elite HRV formula for HRV: HRV = AHRV + BHRV*lnRmssd. */
    private static final double AHRV = -.65;
    /** BHRV in Elite HRV formula for HRV: HRV = AHRV + BHRV*lnRmssd. */
    private static final double BHRV = 15.6;
    /*** The standard deviation of NN intervals. */
    private double sdnn;
    /***
     * The square root of the mean of the squares of the successive differences
     * between adjacent NNs.
     */
    private double rmssd;
    /*** Natural log of rmssd. */
    private double lnRmssd;
    /*** The proportion of NN50 divided by total number of NNs. */
    private double pnn50;
    /***
     * Elite HRV value of HRV. This is given by AHRV + BHRV*lnRmssd.
     * 
     * @see #AHRV
     * @see #BHRV
     */
    private double hrv;
    /*** The number of pairs of successive NNs that differ by more than 50 ms. */
    private int nn50;

    /**
     * HRVInfo constructor.
     * 
     * @param sdnn
     * @param rmssd
     * @param lnRmssd
     * @param pnn50
     * @param hrv
     * @param nn50
     */
    HRVInfo(double sdnn, double rmssd, double lnRmssd, double pnn50,
        double hrv, int nn50) {
        this.sdnn = sdnn;
        this.rmssd = rmssd;
        this.lnRmssd = lnRmssd;
        this.pnn50 = pnn50;
        this.hrv = hrv;
        this.nn50 = nn50;
    }

    public static HRVInfo getHrvInfo(double[] rrVals) {
        if(rrVals == null) return null;
        int nRrVals = rrVals.length;

        // Calculate the successive differences
        double[] sdVals = new double[nRrVals - 1];
        int nn50 = 0;
        for(int i = 1; i < nRrVals; i++) {
            sdVals[i - 1] = rrVals[i] - rrVals[i - 1];
            if(Math.abs(sdVals[i - 1]) > 50) {
                nn50++;
            }
        }
        double pnn50 = (double)nn50 / (nRrVals - 1);

        Statistics rrStats = new Statistics(rrVals);
        Statistics sdStats = new Statistics(sdVals);
        double sdnn = rrStats.getSigma();
        double rmssd = sdStats.getRms();
        double lnRmssd = Math.log(rmssd);
        double hrv = Math.round(AHRV + BHRV * lnRmssd);

        return new HRVInfo(sdnn, rmssd, lnRmssd, pnn50, hrv, nn50);
    }

    /**
     * @return The value of sdnn.
     */
    public double getSdnn() {
        return sdnn;
    }

    /**
     * @param sdnn The new value for sdnn.
     */
    public void setSdnn(double sdnn) {
        this.sdnn = sdnn;
    }

    /**
     * @return The value of rmssd.
     */
    public double getRmssd() {
        return rmssd;
    }

    /**
     * @param rmssd The new value for rmssd.
     */
    public void setRmssd(double rmssd) {
        this.rmssd = rmssd;
    }

    /**
     * @return The value of lnRmssd.
     */
    public double getLnRmssd() {
        return lnRmssd;
    }

    /**
     * @param lnRmssd The new value for lnRmssd.
     */
    public void setLnRmssd(double lnRmssd) {
        this.lnRmssd = lnRmssd;
    }

    /**
     * @return The value of pnn50.
     */
    public double getPnn50() {
        return pnn50;
    }

    /**
     * @param pnn50 The new value for pnn50.
     */
    public void setPnn50(double pnn50) {
        this.pnn50 = pnn50;
    }

    /**
     * @return The value of hrv.
     */
    public double getHrv() {
        return hrv;
    }

    /**
     * @param hrv The new value for hrv.
     */
    public void setHrv(double hrv) {
        this.hrv = hrv;
    }

    /**
     * @return The value of nn50.
     */
    public int getNn50() {
        return nn50;
    }

    /**
     * @param nn50 The new value for nn50.
     */
    public void setNn50(int nn50) {
        this.nn50 = nn50;
    }

}
