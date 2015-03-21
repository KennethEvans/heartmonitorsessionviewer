package net.kenevans.heartmonitorsessionviewer.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import net.kenevans.core.utils.Utils;
import net.kenevans.heartmonitorsessionviewer.model.HMSVFileModel;
import net.kenevans.heartmonitorsessionviewer.model.IConstants;
import net.kenevans.heartmonitorsessionviewer.preferences.Settings;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.Series;
import org.jfree.data.time.TimeSeriesCollection;

/*
 * Created on Jul 29, 2012
 * By Kenneth Evans, Jr.
 */

/**
 * HMSViewerPlot handles plotting for the HMSViewer.
 * 
 * @author Kenneth Evans, Jr.
 */
public class HMSViewerPlot implements IConstants
{
    // /** Default value for the range maximum. */
    // private static final double YMAX = 160;
    // /** Value for the domain maximum. */
    // private static final double XMAX = 30;

    // /** Used to retain the domain limits for resetting the plot. */
    // private double defaultXMax;
    // /** Used to retain the range limits for resetting the plot. */
    // private double defaultYMax;

    /** Whether to show markers in the plot. */
    private boolean showMarkers = false;

    /** Whether to make RR be auto-range.. */
    private boolean rrAutoRange = false;

    /** The ChartPanel for the chart. */
    private ChartPanel chartPanel;

    /** The HMSViewer that contains this plot. */
    private HMSViewer viewer;

    /** The subtitle */
    private TextTitle subTitle;

    /** The array of types */
    private DataType[] dataTypes;

    private JCheckBoxMenuItem hrVisibleItem;
    private JCheckBoxMenuItem hrZonesVisibleItem;
    private JCheckBoxMenuItem rrVisibleItem;
    private JCheckBoxMenuItem rrFixedRangeItem;

    public HMSViewerPlot(HMSViewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Creates the JFreeChart and ChartPanel. Sets the XYDataSet in it but does
     * nothing with it otherwise.
     * 
     * @return The chart created.
     */
    public JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(PLOT_TITLE,
            "Time", "HR", null, true, true, false);
        subTitle = new TextTitle("No file loaded");
        subTitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        chart.addSubtitle(subTitle);
        // chart.getPlot().setBackgroundPaint(Color.BLACK);

        // chart.getPlot().setDrawingSupplier(
        // new DefaultDrawingSupplier(zoneColors,
        // DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
        // DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
        // DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
        // DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

        // Define the panel before extending the popup menu
        chartPanel = new ChartPanel(chart);

        // Add to the popup menu
        extendPopupMenu();

        XYPlot plot = chart.getXYPlot();
        Settings settings = getSettings();

        // Makes the grid lines easier to see
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);

        dataTypes = new DataType[] {
            // Don't use Boolean.getBoolean. It gets a system value with that
            // name
            new DataType(plot, D_HR_NAME, HR_INDEX, Color.decode(D_HR_COLOR),
                settings.getHrVisible(), settings.getHrRollingAvgCount()),
            new DataType(plot, D_RR_NAME, RR_INDEX, Color.decode(D_RR_COLOR),
                settings.getRrVisible(), settings.getRrRollingAvgCount()),
            new HrDataType(plot, D_HR_ZONES_NAME, HR_ZONES_INDEX,
                Color.decode(D_HR_ZONES_COLOR), settings.getHrZonesVisible(), 0),
        // Comment to keep brace on a separate line
        };

        return chart;
    }

    /**
     * Adds to the plot pop-up menu.
     */
    private void extendPopupMenu() {
        JPopupMenu menu = chartPanel.getPopupMenu();
        if(menu == null) return;

        Settings settings = getSettings();

        JSeparator separator = new JSeparator();
        menu.add(separator);

        JMenuItem item;

        // item = new JMenuItem();
        // item.setText("Reset");
        // item.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent ae) {
        // JFreeChart chart = chartPanel.getChart();
        // chart.getXYPlot().getRangeAxis()
        // .setRange(-.5 * defaultYMax, .5 * defaultYMax);
        // chart.getXYPlot().getDomainAxis().setRange(0, defaultXMax);
        // if(showMarkers) {
        // showMarkers = false;
        // // Get the renderer
        // XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)chartPanel
        // .getChart().getXYPlot().getRenderer();
        // // Change for the first 3 series
        // for(int i = 0; i < 3; i++) {
        // renderer.setSeriesShapesVisible(i, showMarkers);
        // }
        // }
        // }
        // });
        // menu.add(item);

        // item = new JMenuItem();
        // item.setText("Reset Axes");
        // item.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent ae) {
        // JFreeChart chart = chartPanel.getChart();
        // chart.getXYPlot().getRangeAxis()
        // .setRange(-.5 * defaultYMax, .5 * defaultYMax);
        // chart.getXYPlot().getDomainAxis().setRange(0, defaultXMax);
        // }
        // });
        // menu.add(item);

        item = new JMenuItem();
        item.setText("Toggle Markers");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                toggleMarkers();
            }
        });
        menu.add(item);

        separator = new JSeparator();
        menu.add(separator);

        hrVisibleItem = new JCheckBoxMenuItem("HR", settings.getHrVisible());
        hrVisibleItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean selected = ((JCheckBoxMenuItem)ae.getSource())
                    .isSelected();
                int datasetIndex = HR_INDEX;
                DataType type = dataTypes[datasetIndex];
                int axisIndex = type.getDatasetIndex();
                type.setVisible(selected);
                XYPlot plot = type.getPlot();
                plot.getRangeAxis(axisIndex).setVisible(selected);
                plot.setDataset(datasetIndex, selected ? type.getDataset()
                    : null);
            }
        });
        menu.add(hrVisibleItem);

        hrZonesVisibleItem = new JCheckBoxMenuItem("HR Zones",
            settings.getHrZonesVisible());
        hrZonesVisibleItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean selected = ((JCheckBoxMenuItem)ae.getSource())
                    .isSelected();
                int datasetIndex = HR_ZONES_INDEX;
                DataType type = dataTypes[datasetIndex];
                int axisIndex = type.getDatasetIndex();
                type.setVisible(selected);
                XYPlot plot = type.getPlot();
                plot.getRangeAxis(axisIndex).setVisible(selected);
                plot.setDataset(datasetIndex, selected ? type.getDataset()
                    : null);
            }
        });
        menu.add(hrZonesVisibleItem);

        rrVisibleItem = new JCheckBoxMenuItem("RR", settings.getRrVisible());
        rrVisibleItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean selected = ((JCheckBoxMenuItem)ae.getSource())
                    .isSelected();
                int datasetIndex = RR_INDEX;
                DataType type = dataTypes[datasetIndex];
                int axisIndex = type.getDatasetIndex();
                type.setVisible(selected);
                XYPlot plot = type.getPlot();
                plot.getRangeAxis(axisIndex).setVisible(selected);
                plot.setDataset(datasetIndex, selected ? type.getDataset()
                    : null);
            }
        });
        menu.add(rrVisibleItem);

        separator = new JSeparator();
        menu.add(separator);

        rrFixedRangeItem = new JCheckBoxMenuItem("RR Auto Range", rrAutoRange);
        rrFixedRangeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean selected = ((JCheckBoxMenuItem)ae.getSource())
                    .isSelected();
                if(rrAutoRange == selected) {
                    return;
                }
                rrAutoRange = selected;
                DataType type = dataTypes[RR_INDEX];
                int axisIndex = type.getDatasetIndex();
                XYPlot plot = type.getPlot();
                if(rrAutoRange) {
                    plot.getRangeAxis(axisIndex).setAutoRange(true);
                } else {
                    plot.getRangeAxis(axisIndex).setRange(RR_FIXED_RANGE_MIN,
                        RR_FIXED_RANGE_MAX);
                }
            }
        });
        menu.add(rrFixedRangeItem);

        separator = new JSeparator();
        menu.add(separator);

        item = new JMenuItem();
        item.setText("Reset");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                reset();
            }
        });
        menu.add(item);
    }

    // public String info() {
    // String info = "Plot info" + LS;
    // XYPlot plot = chartPanel.getChart().getXYPlot();
    // int nDatasets = plot.getDatasetCount();
    // info += "DatasetCount=" + plot.getDatasetCount() + LS;
    // TimeSeriesCollection dataset;
    // Series series;
    // int nSeries = 0;
    // for(int i = 0; i < nDatasets; i++) {
    // dataset = (TimeSeriesCollection)plot.getDataset(i);
    // nSeries = dataset.getSeriesCount();
    // for(int j = 0; j < nSeries; j++) {
    // series = dataset.getSeries(j);
    // }
    // }
    //
    // return info;
    // }

    /**
     * Removes all series from the plot.
     */
    public void clearPlot() {
        if(dataTypes == null) {
            return;
        }
        TimeSeriesCollection dataset;
        for(DataType type : dataTypes) {
            dataset = type.getDataset();
            if(dataset != null) {
                try {
                    ((TimeSeriesCollection)dataset).removeAllSeries();
                } catch(Exception ex) {
                    Utils.excMsg("Error clearing plot", ex);
                }
            }
        }
    }

    /**
     * Toggles the markers.
     */
    public void toggleMarkers() {
        showMarkers = !showMarkers;
        setAllMarkers();
    }

    /**
     * Resets the plot.
     */
    public void reset() {
        // Clear the plot
        clearPlot();
        HMSVFileModel model = null;
        if(viewer != null) {
            model = viewer.getModel();
        }
        if(model == null) {
            return;
        }

        // Reset the context menu
        Settings settings = getSettings();
        hrVisibleItem.setSelected(settings.getHrVisible());
        hrZonesVisibleItem.setSelected(settings.getHrZonesVisible());
        rrVisibleItem.setSelected(settings.getRrVisible());
        XYPlot plot = chartPanel.getChart().getXYPlot();

        // Restore autoRange for the chartPanel
        // Seems to be nesessary to get reset to work
        // Needs to be done here
        chartPanel.restoreAutoRangeBounds();
        chartPanel.restoreAutoDomainBounds();

        // Markers
        showMarkers = false;
        setAllMarkers();

        // Recreate the data types
        dataTypes = new DataType[] {
            // Don't use Boolean.getBoolean. It gets a system value with that
            // name
            new DataType(plot, D_HR_NAME, HR_INDEX, Color.decode(D_HR_COLOR),
                settings.getHrVisible(), settings.getHrRollingAvgCount()),
            new DataType(plot, D_RR_NAME, RR_INDEX, Color.decode(D_RR_COLOR),
                settings.getRrVisible(), settings.getRrRollingAvgCount()),
            new HrDataType(plot, D_HR_ZONES_NAME, HR_ZONES_INDEX,
                Color.decode(D_HR_ZONES_COLOR), settings.getHrZonesVisible(), 0),
        // Comment to keep brace on a separate line
        };

        // Add the model to the chart which causes it to recalculate
        if(model != null) {
            addModelToChart(model);
        }

        // Restore RR autoRange
        DataType type = dataTypes[RR_INDEX];
        int axisIndex = type.getDatasetIndex();
        if(rrAutoRange) {
            plot.getRangeAxis(axisIndex).setAutoRange(true);
        } else {
            plot.getRangeAxis(axisIndex).setRange(RR_FIXED_RANGE_MIN,
                RR_FIXED_RANGE_MAX);
        }
    }

    /**
     * Sets all the markers to the current marker visibility.
     */
    public void setAllMarkers() {
        if(dataTypes == null) {
            return;
        }
        TimeSeriesCollection dataset;
        AbstractXYItemRenderer renderer;
        for(DataType type : dataTypes) {
            dataset = type.getDataset();
            renderer = type.getRenderer();
            if(dataset != null) {
                try {
                    if(renderer instanceof XYLineAndShapeRenderer) {
                        setSeriesMarkers((TimeSeriesCollection)dataset,
                            (XYLineAndShapeRenderer)renderer, showMarkers);
                    }
                } catch(Exception ex) {
                    Utils.excMsg("Error clearing plot", ex);
                }
            }
        }
    }

    /**
     * Sets the markers for all series that are not HR boundaries.
     * 
     * @param dataset
     * @param showMarkers
     */
    public void setSeriesMarkers(TimeSeriesCollection dataset,
        XYLineAndShapeRenderer renderer, boolean showMarkers) {
        int nSeries = dataset.getSeriesCount();
        if(nSeries == 0) {
            return;
        }
        // Change for the series that are not boundaries
        // String seriesName;
        for(int i = 0; i < nSeries; i++) {
            // seriesName = (String)dataset.getSeries(i).getKey();
            // if(!seriesName.startsWith(BOUNDARY_SERIES_NAME_PREFIX)) {
            renderer.setSeriesShapesVisible(i, showMarkers);
            // }
        }
    }

    /**
     * Fills in the chart with the data from the given model.
     * 
     * @param model
     */
    public void addModelToChart(HMSVFileModel model) {
        if(dataTypes == null) {
            Utils.errMsg("No data types defined");
            return;
        }
        try {
            JFreeChart chart = chartPanel.getChart();
            chart.removeSubtitle(subTitle);
            subTitle.setText(model.getFileName());
            chart.addSubtitle(subTitle);
            int datasetIndex, axisIndex;
            long[] timeVals;
            double[] yVals;
            XYPlot plot;
            for(DataType type : dataTypes) {
                type.setSettings(getSettings());
                plot = type.getPlot();
                datasetIndex = type.getDatasetIndex();
                if(datasetIndex == HR_ZONES_INDEX) {
                    // Use the HR axis
                    axisIndex = dataTypes[HR_INDEX].getAxisIndex();
                } else {
                    axisIndex = type.getAxisIndex();
                    // Axis
                    NumberAxis axis = new NumberAxis(type.getName());
                    axis.setFixedDimension(10.0);
                    axis.setAutoRangeIncludesZero(false);
                    axis.setAutoRange(true);
                    axis.setLabelPaint(type.getPaint());
                    axis.setTickLabelPaint(type.getPaint());
                    // Make the label font be the same as for the primary axis
                    // axis.setLabelFont(font);
                    plot.setRangeAxis(axisIndex, axis);
                }
                // type.getPlot().setRangeAxisLocation(datasetIndex,
                // AxisLocation.BOTTOM_OR_LEFT);
                switch(datasetIndex) {
                case HR_INDEX:
                    timeVals = model.getHrTimeVals();
                    yVals = model.getHrVals();
                    type.createDataset(timeVals, yVals);
                    plot.setDataset(datasetIndex, type.getDataset());
                    plot.mapDatasetToRangeAxis(datasetIndex, axisIndex);
                    plot.setRenderer(datasetIndex, type.getRenderer());
                    break;
                case HR_ZONES_INDEX:
                    timeVals = model.getHrTimeVals();
                    yVals = model.getHrVals();
                    type.createDataset(timeVals, yVals);
                    plot.setDataset(datasetIndex, type.getDataset());
                    plot.mapDatasetToRangeAxis(datasetIndex, axisIndex);
                    plot.setRenderer(datasetIndex, type.getRenderer());
                    break;
                case RR_INDEX:
                    timeVals = model.getRrTimeVals();
                    yVals = model.getRrVals();
                    type.createDataset(timeVals, yVals);
                    plot.setDataset(datasetIndex, type.getDataset());
                    plot.mapDatasetToRangeAxis(datasetIndex, axisIndex);
                    plot.setRenderer(datasetIndex, type.getRenderer());
                    if(rrAutoRange) {
                        plot.getRangeAxis(axisIndex).setAutoRange(true);
                    } else {
                        plot.getRangeAxis(axisIndex).setRange(
                            RR_FIXED_RANGE_MIN, RR_FIXED_RANGE_MAX);
                    }
                    break;
                default:
                    Utils.errMsg("Invalid data set datasetIndex:"
                        + datasetIndex);
                    break;
                }
                // Set the visibility
                if(!type.getVisible()) {
                    if(datasetIndex != HR_ZONES_INDEX) {
                        plot.getRangeAxis(axisIndex).setVisible(false);
                    }
                    plot.setDataset(datasetIndex, null);
                }
            }
            setAllMarkers();
        } catch(Exception ex) {
            Utils.excMsg("Error adding data to plot", ex);
            ex.printStackTrace();
        }
    }

    /**
     * Generated information about the given plot.
     * 
     * @param prefix
     * @param plot
     * @return
     */
    public static String dumpXYPlot(String prefix, XYPlot plot) {
        String info = prefix;
        int nDatasets = plot.getDatasetCount();
        info += "nDatasets=" + nDatasets + LS;
        int nSeries = 0;
        TimeSeriesCollection dataset;
        Series series;
        for(int i = 0; i < nDatasets; i++) {
            dataset = (TimeSeriesCollection)plot.getDataset(i);
            if(dataset == null) {
                info += "Dataset " + i + ":  null" + LS;
                continue;
            }
            nSeries = dataset.getSeriesCount();
            info += "Dataset " + i + ":  nSeries=" + nSeries + LS;
            for(int j = 0; j < nSeries; j++) {
                series = dataset.getSeries(j);
                info += "  Series " + j + ": \"" + series.getKey()
                    + "\" nItems=" + series.getItemCount() + LS;
            }
        }
        return info;
    }

    /**
     * Get the Settings from the viewer. Use one based on preferences if there
     * is an error.
     * 
     * @return
     */
    Settings getSettings() {
        Settings settings = null;

        if(viewer != null) {
            settings = viewer.getSettings();
        }
        if(settings == null) {
            settings = new Settings();
            settings.loadFromPreferences();
        }
        return settings;
    }

    /**
     * @return The value of chartPanel.
     */
    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    /**
     * @return The value of dataTypes.
     */
    public DataType[] getDataTypes() {
        return dataTypes;
    }

}
