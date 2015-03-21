package net.kenevans.heartmonitorsessionviewer.ui;

import java.awt.Color;
import java.awt.Paint;

import net.kenevans.heartmonitorsessionviewer.preferences.Settings;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.time.TimeSeriesCollection;

/*
 * Created on Jul 17, 2014
 * By Kenneth Evans, Jr.
 */

/**
 * HrDataType manages a heart rate zone data type. It differs from DataType by
 * implementing zones in the plot.
 * 
 * @author Kenneth Evans, Jr.
 */
public class HrDataType extends DataType
{
    /**
     * HrDataType constructor that sets axisIndex the same as datasetIndex. Just
     * calls super().
     * 
     * @param plot
     * @param name
     * @param datasetIndex
     * @param paint
     * @param visible
     * @param movingAvgCount
     * @see DataType#DataType(XYPlot, String, int, Paint, boolean)
     */
    public HrDataType(XYPlot plot, String name, int datasetIndex, Paint paint,
        boolean visible, int movingAvgCount) {
        super(plot, name, datasetIndex, paint, visible, movingAvgCount);
    }

    /**
     * HrDataType constructor. Just calls super().
     * 
     * @param plot
     * @param name
     * @param datasetIndex
     * @param axisIndex
     * @param paint
     * @param visible
     * @param movingAvgCount
     * @see DataType#DataType(XYPlot, String, int, int, Paint, boolean)
     */
    public HrDataType(XYPlot plot, String name, int datasetIndex,
        int axisIndex, Paint paint, boolean visible, int movingAvgCount) {
        super(plot, name, datasetIndex, axisIndex, paint, visible,
            movingAvgCount);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.kenevans.heartmonitorsessionviewer.ui.DataType#createDataset(long[], double[])
     */
    @Override
    public TimeSeriesCollection createDataset(long[] timeVals, double[] yVals) {
        dataset = new TimeSeriesCollection();
        renderer = new XYAreaRenderer();
        String key;

        if(settings == null) {
            // This should not happen. The settings should be set before this
            settings = new Settings();
            settings.loadFromPreferences();
        }
        Paint[] zoneColors = {Color.decode(settings.getZone1Color()),
            Color.decode(settings.getZone2Color()),
            Color.decode(settings.getZone3Color()),
            Color.decode(settings.getZone4Color()),
            Color.decode(settings.getZone5Color()),
            Color.decode(settings.getZone6Color())};
        double[] zoneVals = {settings.getZone1Val(), settings.getZone2Val(),
            settings.getZone3Val(), settings.getZone4Val(),
            settings.getZone5Val(), settings.getZone6Val(),};

        // Add the zones
        int nPoints = timeVals.length;
        if(nPoints > 2 && timeVals[0] != timeVals[nPoints - 1]) {
            int nZones = zoneVals.length;
            long[] zoneTimeVals = {timeVals[0], timeVals[nPoints - 1]};
            // Only need an array of one since the value is constant
            double[] zoneVal = new double[1];
            for(int i = 0; i < nZones; i++) {
                zoneVal[0] = zoneVals[i];
                key = String.format(BOUNDARY_SERIES_NAME_PREFIX + "%.0f",
                    zoneVals[i]);
                // Use 0 to not do a moving average
                addSeries(dataset, key, zoneColors[i], zoneTimeVals, zoneVal, 0);
            }
        }

        return dataset;
    }

}
