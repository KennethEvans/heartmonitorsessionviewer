package net.kenevans.heartmonitorsessionviewer.utils;

import java.util.Arrays;

public class MathUtils
{
    /**
     * Quicksort filter. Use low = 0 and high = length - 1 to sort the whole
     * array. From <a href=
     * "http://www.vogella.com/articles/JavaAlgorithmsQuicksort/article.html"
     * >http://www.vogella.com/articles/JavaAlgorithmsQuicksort/article.html<a>
     * 
     * @param array The array to sort.
     * @param low Starting datasetIndex of the part of the array to sort.
     * @param low Ending datasetIndex of the part of the array to sort.
     */
    public static void quickSort(double[] array, int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        double pivot = array[low + (high - low) / 2];

        // Divide into two lists
        double exchange;
        while(i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while(array[i] < pivot) {
                i++;
            }
            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while(array[j] > pivot) {
                j--;
            }

            // If we have found a values in the left list which is larger then
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if(i <= j) {
                exchange = array[i];
                array[i] = array[j];
                array[j] = exchange;
                i++;
                j--;
            }
        }
        // Recursion
        if(low < j) quickSort(array, low, j);
        if(i < high) quickSort(array, i, high);
    }

    /**
     * Quicksort from RoseIndia. Fails if two elements are equal. Don't use.
     * From <a href=
     * "http://www.roseindia.net/java/beginners/arrayexamples/QuickSort.shtml"
     * >http://www.roseindia.net/java/beginners/arrayexamples/QuickSort.shtml<a>
     * 
     * @param array
     * @param low
     * @param n
     */
    @Deprecated
    public static void quickSort1(double array[], int low, int n) {
        int lo = low;
        int hi = n;
        if(lo >= n) {
            return;
        }
        double mid = array[(lo + hi) / 2];
        while(lo < hi) {
            while(lo < hi && array[lo] < mid) {
                lo++;
            }
            while(lo < hi && array[hi] > mid) {
                hi--;
            }
            if(lo < hi) {
                double T = array[lo];
                array[lo] = array[hi];
                array[hi] = T;
            }
        }
        if(hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        quickSort1(array, low, lo);
        quickSort1(array, lo == low ? lo + 1 : lo, n);
    }

    public static double[] medianFilter(double[] array, int window) {
        int len = array.length;
        if(window > len) {
            return null;
        }
        int mid = window / 2;
        double[] result = new double[len];
        double[] subarray;
        for(int i = 0; i < len; i++) {
            // Use the part of the window centered on i that fits into the array
            if(i <= mid) {
                subarray = Arrays.copyOfRange(array, 0, i + mid + 1);
            } else if(i + mid >= len) {
                subarray = Arrays.copyOfRange(array, i - mid, len);
            } else {
                subarray = Arrays.copyOfRange(array, i - mid, i + mid + 1);
            }
            result[i] = median(subarray);
        }
        return result;
    }

    /**
     * Calculates the median of an array by sorting using QuickSort and taking
     * the middle element if the length of the array is odd and the average of
     * the two middle elements otherwise. Does not check for valid inputs.
     * 
     * @param array
     * @return
     */
    public static double median(double[] array) {
        int len = array.length;
        int mid = len / 2;
        quickSort(array, 0, len - 1);
        // Median is the midpoint of the sorted list
        if(len % 2 == 0) {
            return .5 * (array[mid - 1] + array[mid]);
        } else {
            return array[mid];
        }

    }

    /**
     * Calculates the median of an array of three numbers.
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double meadianOf3(double a, double b, double c) {
        double median;
        if((a <= b) && (a <= c)) {
            median = (b <= c) ? b : c;
        } else if((b <= a) && (b <= c)) {
            median = (a <= c) ? a : c;
        } else {
            median = (a <= b) ? a : b;
        }
        return median;
    }

    /**
     * Butterworth 6th order bandpass filter from 0.5 to 7.5 Hz. Based on code
     * obtained from Amperor.
     * 
     * @param array
     * @param y
     * @param n
     */
    public static double[] butterworth_6_05_75(double array[]) {
        int i;
        int NZEROS = 6;
        int NPOLES = 6;
        double GAIN = 8.271238682e+02;

        double result[] = new double[array.length];
        double xv[] = new double[NZEROS + 1];
        double yv[] = new double[NPOLES + 1];
        for(i = 0; i < NZEROS; i++) {
            xv[i] = array[0] / GAIN;
        }
        for(i = 0; i < NPOLES; i++) {
            yv[i] = 0;
        }
        for(i = 0; i < array.length; i++) {
            xv[0] = xv[1];
            xv[1] = xv[2];
            xv[2] = xv[3];
            xv[3] = xv[4];
            xv[4] = xv[5];
            xv[5] = xv[6];
            xv[6] = array[i] / GAIN;
            yv[0] = yv[1];
            yv[1] = yv[2];
            yv[2] = yv[3];
            yv[3] = yv[4];
            yv[4] = yv[5];
            yv[5] = yv[6];
            yv[6] = (xv[6] - xv[0]) + 3 * (xv[2] - xv[4])
                + (-0.6322662469 * yv[0]) + (4.0740243401 * yv[1])
                + (-10.9612316290 * yv[2]) + (15.7618442380 * yv[3])
                + (-12.7747539160 * yv[4]) + (5.5323831623 * yv[5]);
            result[i] = yv[6];
        }
        return result;
    }

    /**
     * Calculates the coefficients for a Butterworth 2-pole low pass filter.
     * 
     * @param samplerate The sample rate.
     * @param cutoff The cutoff.
     * @param ax First coefficient. Will be filled in.
     * @param by Second coefficient. Will be filled in.
     * 
     * @see #butterworthLowPass2Pole
     */
    public static void getButterworthLowPass2PoleCoefficients(int samplerate,
        double cutoff, double[] ax, double[] by) {
        double sqrt2 = Math.sqrt(2);

        double QcRaw = (2 * Math.PI * cutoff) / samplerate;
        // Find cutoff frequency in [0..PI]
        double QcWarp = Math.tan(QcRaw);
        // Warp cutoff frequency
        double gain = 1 / (1 + sqrt2 / QcWarp + 2 / (QcWarp * QcWarp));
        by[2] = (1 - sqrt2 / QcWarp + 2 / (QcWarp * QcWarp)) * gain;
        by[1] = (2 - 2 * 2 / (QcWarp * QcWarp)) * gain;
        by[0] = 1;
        ax[0] = 1 * gain;
        ax[1] = 2 * gain;
        ax[2] = 1 * gain;
    }

    /**
     * Filters the input array using a Butterworth 2-pole low pass filter. Based
     * on code from <a href=
     * "http://baumdevblog.blogspot.com/2010/11/butterworth-lowpass-filter-coefficients.html?m=1"
     * >http://baumdevblog.blogspot.com/2010/11/butterworth-lowpass-filter-
     * coefficients.html?m=1<a>
     * 
     * @param samplerate The sample rate.
     * @param cutoff The cutoff.
     * @param samples The array to filter.
     */
    public static double[] butterworthLowPass2Pole(int samplerate,
        double cutoff, double[] samples) {
        double[] xv = new double[3];
        double[] yv = new double[3];
        double[] ax = new double[3];
        double[] by = new double[3];
        double result[] = new double[samples.length];

        getButterworthLowPass2PoleCoefficients(samplerate, cutoff, ax, by);

        for(int i = 0; i < samples.length; i++) {
            xv[2] = xv[1];
            xv[1] = xv[0];
            xv[0] = samples[i];
            yv[2] = yv[1];
            yv[1] = yv[0];
            yv[0] = (ax[0] * xv[0] + ax[1] * xv[1] + ax[2] * xv[2] - by[1]
                * yv[0] - by[2] * yv[1]);
            result[i] = yv[0];
        }
        return result;
    }

    public static void main(String a[]) {
        int i;
        double array[] = {12.9, 9.2, 4.3, 4.4, 120.0, 1.0, 3.2, 10.9999, 4.4};

        System.out.println("Quick Sort\n");
        System.out.println("Values Before the sort:");
        for(i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println();
        System.out.println();
        quickSort(array, 0, array.length - 1);
        // quickSort(array, 2,5);
        System.out.print("Values after the sort:\n");
        for(i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println();
        System.out.println("All done");
    }
}