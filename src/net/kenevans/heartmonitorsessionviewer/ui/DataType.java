package net.kenevans.heartmonitorsessionviewer.ui;

import java.awt.Paint;
import java.util.Date;

import net.kenevans.heartmonitorsessionviewer.model.IConstants;
import net.kenevans.heartmonitorsessionviewer.preferences.Settings;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/*
 * Created on Jul 17, 2014
 * By Kenneth Evans, Jr.
 */

/**
 * DataType manages a particular type of data to be plotted, such as HR, Speed,
 * etc.
 * 
 * @author Kenneth Evans, Jr.
 */
public class DataType implements IConstants
{
    protected String name;
    protected int datasetIndex;
    protected int axisIndex;
    protected Paint paint;
    protected boolean visible;
    protected int movingAvgCount;

    protected XYPlot plot;
    protected TimeSeriesCollection dataset;
    protected AbstractXYItemRenderer renderer;

    protected Settings settings;

    /**
     * DataType constructor that sets the axisIndex the same as the
     * datasetIndex.
     * 
     * @param name
     * @param datasetIndex
     * @param paint
     * @param visible
     * @param movingAvgCount
     */
    public DataType(XYPlot plot, String name, int datasetIndex, Paint paint,
        boolean visible, int movingAvgCount) {
        this(plot, name, datasetIndex, datasetIndex, paint, visible,
            movingAvgCount);
    }

    /**
     * DataType constructor.
     * 
     * @param plot
     * @param name
     * @param datasetIndex
     * @param axisIndex
     * @param paint
     * @param visible
     * @param movingAvgCount
     */
    public DataType(XYPlot plot, String name, int datasetIndex, int axisIndex,
        Paint paint, boolean visible, int movingAvgCount) {
        this.plot = plot;
        this.name = name;
        this.datasetIndex = datasetIndex;
        this.axisIndex = axisIndex;
        this.paint = paint;
        this.visible = visible;
        this.movingAvgCount = movingAvgCount;
    }

    /**
     * Creates a dataset for the given model for this data type.
     * 
     * @param model
     * @return
     */
    public TimeSeriesCollection createDataset(long[] timeVals, double[] yVals) {
        dataset = new TimeSeriesCollection();
        renderer = new XYLineAndShapeRenderer();

        addSeries(dataset, name, paint, timeVals, yVals, movingAvgCount);
        return dataset;
    }

    /**
     * Adds a series to the given dataset.
     * 
     * @param dataset
     * @param seriesName Name of the series.
     * @param paint Paint (color) to use for the series.
     * @param timeVals Array of time values.
     * @param yVals Array of data values.
     * @param movingAvgCount The movingAverageCount. Use 0 for the zone
     *            boundaries.
     */
    protected void addSeries(TimeSeriesCollection dataset, String seriesName,
        Paint paint, long[] timeVals, double[] yVals, int movingAvgCount) {
        int nPoints = timeVals.length;
        int nDataPoints = yVals.length;
        TimeSeries series = new TimeSeries(seriesName);

        for(int n = 0; n < nPoints; n++) {
            if(nDataPoints == 1) {
                series.addOrUpdate(new FixedMillisecond(new Date(timeVals[n])),
                    yVals[0]);
            } else {
                series.addOrUpdate(new FixedMillisecond(new Date(timeVals[n])),
                    yVals[n]);
            }
        }

        // Plot the unaveraged data if the movingAvgCount in negative or 0 or 1
        // (in which case the moving average is the same as the unaveraged data)
        if(movingAvgCount < 2) {
            dataset.addSeries(series);
            int seriesIndex = dataset.indexOf(series);
            renderer.setSeriesPaint(seriesIndex, paint);
            renderer.setSeriesVisible(seriesIndex, visible);
        }

        // Moving Average
        // nMaV must be 1 or greater, 1 is the same as no average
        int absMovingAvgCount = Math.abs(movingAvgCount);
        if(absMovingAvgCount > 1) {
            TimeSeries mavSeries = MovingAverage.createMovingAverage(series,
                seriesName + " (" + movingAvgCount + " pt MA)",
                absMovingAvgCount, 0);
            dataset.addSeries(mavSeries);
            int mavSeriesIndex = dataset.indexOf(mavSeries);
            renderer.setSeriesPaint(mavSeriesIndex, paint);
        }
    }

    /**
     * @return The value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The new value for name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The value of datasetIndex.
     */
    public int getDatasetIndex() {
        return datasetIndex;
    }

    /**
     * @param datasetIndex The new value for datasetIndex.
     */
    public void setDatasetIndex(int datasetIndex) {
        this.datasetIndex = datasetIndex;
    }

    /**
     * @return The value of axisIndex.
     */
    public int getAxisIndex() {
        return axisIndex;
    }

    /**
     * @param axisIndex The new value for axisIndex.
     */
    public void setAxisIndex(int axisIndex) {
        this.axisIndex = axisIndex;
    }

    /**
     * @return The value of paint.
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * @param paint The new value for paint.
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * @return The value of visible.
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * @param visible The new value for visible.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return The value of plot.
     */
    public XYPlot getPlot() {
        return plot;
    }

    /**
     * @return The value of dataset.
     */
    public TimeSeriesCollection getDataset() {
        return dataset;
    }

    /**
     * @return The value of renderer.
     */
    public AbstractXYItemRenderer getRenderer() {
        return renderer;
    }

    /**
     * @return The value of settings.
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @param settings The new value for settings.
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
