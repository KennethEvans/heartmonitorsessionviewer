package net.kenevans.heartmonitorsessionviewer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ListIterator;

import net.kenevans.core.utils.Utils;
import net.kenevans.heartmonitorsessionviewer.utils.Log;

/*
 * Created on Jul 8, 2014
 * By Kenneth Evans, Jr.
 */

/**
 * HMSVFileModel is a model for session files of sports monitor data.
 * 
 * @author Kenneth Evans, Jr.
 */
public class HMSVFileModel implements IConstants
{
    private String fileName;
    private long[] hrTimeVals;
    private double[] hrVals;
    private long[] rrTimeVals;
    private double[] rrVals;
    private int nHrValues;
    private int nRrValues;
    private long startTime = Long.MAX_VALUE;
    private long endTime;
    private long startHrTime = Long.MAX_VALUE;
    private long endHrTime;
    private long mLastRrUpdateTime = INVALID_DATE;
    private long mLastRrTime = INVALID_DATE;

    public HMSVFileModel(String fileName) {
        this.fileName = fileName;
        File file = new File(fileName);
        ArrayList<Long> hrTimeValsArray = new ArrayList<Long>();
        ArrayList<Double> hrValsArray = new ArrayList<Double>();
        ArrayList<Long> rrTimeValsArray = new ArrayList<Long>();
        ArrayList<Double> rrValsArray = new ArrayList<Double>();
        int mLineNumber = 0;
        // int mErrors = 0;

        BufferedReader in = null;
        try {
            // Read the file and get the data to restore
            in = new BufferedReader(new FileReader(file));
            String rrStringVal;
            long dateNum = INVALID_DATE;
            long prevDateNum = INVALID_DATE;
            long midDateNum = INVALID_DATE;
            int hrIntVal;
            String[] tokens = null;
            String line = null;
            boolean processingBlank = false;
            while((line = in.readLine()) != null) {
                dateNum = INVALID_DATE;
                hrIntVal = INVALID_INT;
                rrStringVal = " ";
                mLineNumber++;
                tokens = line.trim().split(SAVE_SESSION_DELIM);
                if(line.trim().length() == 0) {
                    // Just set a flag to indicate it found a blank line
                    // Setting a NaN data point will be done when a non-blank
                    // line is found.
                    processingBlank = true;
                    continue;
                }
                // Skip lines starting with #
                if(tokens[0].trim().startsWith("#")) {
                    continue;
                }
                hrIntVal = 0;
                rrStringVal = "";
                if(tokens.length < 3) {
                    // Utils.errMsg(this, "Found " + tokens.length
                    // + " tokens for line " + lineNum
                    // + "\nShould be 5 or more tokens");
                    // mErrors++;
                    Log.d(TAG, "tokens.length=" + tokens.length + " @ line "
                        + mLineNumber);
                    Log.d(TAG, line);
                    continue;
                }
                try {
                    dateNum = sessionSaveFormatter.parse(tokens[0]).getTime();
                } catch(Exception ex) {
                    Log.d(TAG, "Long.parseLong failed for dateNum @ line "
                        + mLineNumber);
                }
                try {
                    hrIntVal = Integer.parseInt(tokens[1]);
                } catch(Exception ex) {
                    Log.d(TAG, "Integer.parseInt failed for hr @ line "
                        + mLineNumber);
                }
                rrStringVal = tokens[2].trim();
                if(startTime == Long.MAX_VALUE) {
                    startTime = dateNum;
                    mLastRrUpdateTime = startTime;
                    mLastRrTime = startTime - INITIAL_RR_START_TIME;
                }
                endTime = dateNum;
                nHrValues++;

                // Add a NaN number point at the midpoint of the times if it is
                // currently processing a blank line
                if(processingBlank) {
                    processingBlank = false;
                    midDateNum = (prevDateNum + dateNum) / 2;
                    if(midDateNum != prevDateNum && midDateNum != dateNum
                        && prevDateNum != INVALID_DATE) {
                        hrTimeValsArray.add(midDateNum);
                        hrValsArray.add(Double.NaN);
                        rrTimeValsArray.add(midDateNum);
                        rrValsArray.add(Double.NaN);
                    }
                }

                // Create the arrays
                hrTimeValsArray.add(dateNum);
                hrValsArray.add((double)hrIntVal);
                addRrValues(rrTimeValsArray, rrValsArray, dateNum, rrStringVal);
                prevDateNum = dateNum;
            }
        } catch(Exception ex) {
            Utils.excMsg("Got Exception restoring at line " + mLineNumber, ex);
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch(Exception ex) {
                // Do nothing
            }
        }

        // HR
        hrVals = new double[hrValsArray.size()];
        int index = 0;
        for(Double dVal : hrValsArray) {
            hrVals[index++] = dVal.doubleValue();
        }
        hrTimeVals = new long[hrTimeValsArray.size()];
        index = 0;
        for(Long lVal : hrTimeValsArray) {
            hrTimeVals[index++] = lVal.longValue();
        }
        // RR
        rrVals = new double[rrValsArray.size()];
        index = 0;
        for(Double dVal : rrValsArray) {
            rrVals[index++] = dVal.doubleValue();
        }
        rrTimeVals = new long[rrTimeValsArray.size()];
        index = 0;
        for(Long lVal : rrTimeValsArray) {
            rrTimeVals[index++] = lVal.longValue();
        }
    }

    /**
     * Parses the RR String and adds RR times and values to the given arrays.
     * 
     * @param series The series to use.
     * @param updateTime The time of this update.
     * @param strValue The RR String from the database.
     * @return If the operation was successful.
     */
    /**
     * @param rrTimeValsArray Array to which to add time values.
     * @param rrValsArray Array to which to add RR values.
     * @param updateTime
     * @param strValue
     * @return
     */
    private boolean addRrValues(ArrayList<Long> rrTimeValsArray,
        ArrayList<Double> rrValsArray, long updateTime, String strValue) {
        if(strValue == null || strValue.length() == 0) {
            return false;
        }
        if(strValue.equals(INVALID_STRING)) {
            mLastRrUpdateTime = updateTime;
            mLastRrTime = updateTime - INITIAL_RR_START_TIME;
            rrTimeValsArray.add(mLastRrTime);
            rrValsArray.add(Double.NaN);
            return true;
        }
        String[] tokens;
        tokens = strValue.trim().split("\\s+");
        int nTokens = tokens.length;
        if(nTokens == 0) {
            return false;
        }
        long[] times = new long[nTokens];
        double[] values = new double[nTokens];
        long lastRrTime = mLastRrTime;
        double val = Double.NaN;
        for(int i = 0; i < nTokens; i++) {
            try {
                val = Double.parseDouble(tokens[i]);
            } catch(NumberFormatException ex) {
                return false;
            }
            nRrValues++;
            lastRrTime += val;
            times[i] = lastRrTime;
            values[i] = .001 * val * 1024;
        }
        // Make all times be >= mLastRrUpdateTime
        long deltaTime;
        long firstTime = times[0];
        if(firstTime < mLastRrUpdateTime) {
            deltaTime = mLastRrUpdateTime - firstTime;
            for(int i = 0; i < nTokens; i++) {
                times[i] += deltaTime;
            }
        }
        // Make all times be <= updateTime. Overrides previous if necessary.
        long lastTime = times[nTokens - 1];
        if(times[nTokens - 1] > updateTime) {
            deltaTime = lastTime - updateTime;
            for(int i = 0; i < nTokens; i++) {
                times[i] -= deltaTime;
            }
        }
        // Add to the series
        for(int i = 0; i < nTokens; i++) {
            rrTimeValsArray.add(times[i]);
            rrValsArray.add(values[i]);
        }
        mLastRrUpdateTime = updateTime;
        mLastRrTime = times[nTokens - 1];
        return true;
    }

    public static String sysInfo() {
        String info = "";
        String[] properties = {"user.dir", "java.version", "java.home",
            "java.vm.version", "java.vm.vendor", "java.ext.dirs"};
        String property;
        for(int i = 0; i < properties.length; i++) {
            property = properties[i];
            info += property + ": "
                + System.getProperty(property, "<not found>") + LS;
        }
        info += getClassPath("  ");
        return info;
    }

    public static String getClassPath(String tabs) {
        String info = "";
        String classPath = System.getProperty("java.class.path", "<not found>");
        String[] paths = classPath.split(File.pathSeparator);
        for(int i = 0; i < paths.length; i++) {
            info += tabs + i + " " + paths[i] + LS;
        }
        return info;
    }

    /**
     * Gets info about this file.
     * 
     * @return
     */
    public String getInfo() {
        String info = "";
        info += getFileName() + LS + LS;
        double duration = (double)endTime - (double)startTime;
        int durationHours = (int)(duration / 3600000.);
        int durationMin = (int)(duration / 60000.) - durationHours * 60;
        int durationSec = (int)(duration / 1000.) - durationHours * 3600
            - durationMin * 60;
        info += String.format("Duration: %d hr %d min %d sec", durationHours,
            durationMin, durationSec) + LS;
        double[] stats = null;
        if(nHrValues != 0) {
            stats = getTimeAverageStats(hrVals, hrTimeVals, -Double.MIN_VALUE);
            if(stats != null) {
                info += String.format(
                    "HR Min=%.0f HR Max=%.0f HR Avg=%.1f (%d Values)",
                    stats[0], stats[1], stats[2], nHrValues)
                    + LS;
            } else {
                // Get simple average
                stats = getSimpleStats(hrVals, hrTimeVals, -Double.MIN_VALUE);
                if(stats != null) {
                    info += String.format(
                        "HR Min=%.0f HR Max=%.0f HR Avg=%.1f (%d Values)"
                            + " (Simple Average)", stats[0], stats[1],
                        stats[2], nHrValues)
                        + LS;
                }
            }
        }
        if(nRrValues != 0) {
            stats = getTimeAverageStats(rrVals, rrTimeVals, -Double.MIN_VALUE);
            if(stats != null) {
                info += String.format(
                    "RR Min=%.0f RR Max=%.0f RR Avg=%.1f (%d Values)",
                    stats[0], stats[1], stats[2], nRrValues)
                    + LS;
            } else {
                // Get simple average
                stats = getSimpleStats(rrVals, rrTimeVals, -Double.MIN_VALUE);
                if(stats != null) {
                    info += String.format(
                        "RR Min=%.0f RR Max=%.0f RR Avg=%.1f (%d Values)"
                            + " (Simple Average)", stats[0], stats[1],
                        stats[2], nRrValues)
                        + LS;
                }
            }
        }
        // HRV Info
        if(nRrValues != 0) {
            // Need to remove Double.NaN from rrVals
            ArrayList<Double> rrValsList = new ArrayList<Double>();
            for(double val : rrVals) {
                if(!Double.isNaN(val)) {
                    rrValsList.add(val);
                }
            }
            double[] rrVals1 = new double[rrValsList.size()];
            ListIterator<Double> li = rrValsList.listIterator();
            int i = 0;
            double dVal;
            while(li.hasNext()) {
                rrVals1[i++] = li.next().doubleValue();
            }
            HRVInfo hrvInfo = HRVInfo.getHrvInfo(rrVals1);
            if(hrvInfo != null) {
                info += String
                    .format(
                        "RMSSD=%.2f LnRMMSSD=%.2f SDNN=%.2f NN50=%d PNN50=%.2f HRV=%.0f",
                        hrvInfo.getRmssd(), hrvInfo.getLnRmssd(),
                        hrvInfo.getSdnn(), hrvInfo.getNn50(),
                        100 * hrvInfo.getPnn50(), hrvInfo.getHrv())
                    + LS;
            }
        }
        return info;
    }

    /**
     * Gets the statistics from the given values and time values by averaging
     * over the values, not over the actual time.
     * 
     * @param vals
     * @param timeVals
     * @param omitBelow Do not include values below this one.
     * @return {min, max, avg} or null on error.
     */
    public static double[] getSimpleStats(double[] vals, long[] timeVals,
        double omitBelow) {
        // System.out.println("vals: " + vals.length + ", timeVals: "
        // + timeVals.length);
        if(vals.length != timeVals.length) {
            Utils.errMsg("getSimpleStats: Array sizes (vals: " + vals.length
                + ", timeVals: " + timeVals.length + ") do not match");
            return null;
        }
        int len = vals.length;
        if(len == 0) {
            return new double[] {0, 0, 0};
        }
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        double sum = 0;
        double val;
        int nVals = 0;
        for(int i = 0; i < len; i++) {
            val = vals[i];
            if(Double.isNaN(val)) continue;
            if(val < omitBelow) continue;
            nVals++;
            sum += val;
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        if(nVals == 0) {
            return null;
        }
        sum /= nVals;
        return new double[] {min, max, sum};
    }

    /**
     * Gets the statistics from the given values and time values by averaging
     * over the values weighted by the time.
     * 
     * @param vals
     * @param timeVals
     * @param omitBelow Do not include values below this one.
     * @return {min, max, avg} or null on error.
     */
    public static double[] getTimeAverageStats(double[] vals, long[] timeVals,
        double omitBelow) {
        // System.out.println("vals: " + vals.length + ", timeVals: "
        // + timeVals.length);
        if(vals.length != timeVals.length) {
            Utils.errMsg("getTimeAverageStats: Array sizes (vals: "
                + vals.length + ", timeVals: " + timeVals.length
                + ") do not match");
            return null;
        }
        int len = vals.length;
        if(len == 0) {
            return new double[] {0, 0, 0};
        }
        if(len < 2) {
            return new double[] {vals[0], vals[0], vals[0]};
        }
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        double sum = 0;
        double val;
        // Check for NaN
        for(int i = 0; i < len; i++) {
            val = vals[i];
            if(Double.isNaN(val)) {
                return null;
            }
        }

        // Loop over values.
        double totalWeight = 0;
        double weight;
        for(int i = 0; i < len; i++) {
            val = vals[i];
            if(Double.isNaN(val)) continue;
            if(val < omitBelow) continue;
            if(i == 0) {
                weight = .5 * (timeVals[i + 1] - timeVals[i]);
            } else if(i == len - 1) {
                weight = .5 * (timeVals[i] - timeVals[i - 1]);
            } else {
                weight = .5 * (timeVals[i] - timeVals[i - 1]);
            }
            totalWeight += weight;
            // Shoudn't happen
            sum += val * weight;
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        if(totalWeight == 0) {
            return null;
        }
        sum /= (totalWeight);
        return new double[] {min, max, sum};
    }

    /**
     * @return The value of fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return The value of hrTimeVals.
     */
    public long[] getHrTimeVals() {
        return hrTimeVals;
    }

    /**
     * @return The value of hrVals.
     */
    public double[] getHrVals() {
        return hrVals;
    }

    /**
     * @return The value of rrTimeVals.
     */
    public long[] getRrTimeVals() {
        return rrTimeVals;
    }

    /**
     * @return The value of rrVals.
     */
    public double[] getRrVals() {
        return rrVals;
    }

    /**
     * @return The value of startTime.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return The value of endTime.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @return The value of startHrTime.
     */
    public long getStartHrTime() {
        return startHrTime;
    }

    /**
     * @return The value of endHrTime.
     */
    public long getEndHrTime() {
        return endHrTime;
    }

    /**
     * @return The value of nHrValues.
     */
    public int getnHrValues() {
        return nHrValues;
    }

    /**
     * @return The value of nRrValues.
     */
    public int getnRrValues() {
        return nRrValues;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting " + HMSVFileModel.class.getName());
        System.out.println(FILE_PATH);
        HMSVFileModel app = new HMSVFileModel(FILE_PATH);
        System.out.println(app.getInfo());
        // DEBUG
        // System.out.println();
        // System.out.println("Classpath");
        // System.out.println(getClassPath("    "));
        // System.out.println();

        System.out.println();
        System.out.println("All Done");
    }

}
