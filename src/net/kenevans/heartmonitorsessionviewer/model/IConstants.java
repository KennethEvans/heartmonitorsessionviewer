package net.kenevans.heartmonitorsessionviewer.model;

import java.text.SimpleDateFormat;
import java.util.Locale;

/*
 * Created on Jul 9, 2012
 * By Kenneth Evans, Jr.
 */

/**
 * Provides constants for classes related to Heart Monitor session files.
 * 
 * @author Kenneth Evans, Jr.
 */
/**
 * IConstants
 * 
 * @author Kenneth Evans, Jr.
 */
public interface IConstants
{
    public static final String LS = System.getProperty("line.separator");

    /** Tag to associate with log messages. */
    public static final String TAG = "HMSViewer";

    // public static final String FILE_NAME =
    // "track2014-06-30-Workout-Rehab-1475016.gpx";
    // public static final String FILE_NAME = "../CM2013.gpx";
    public static final String FILE_NAME = "HxM-2014-12-15-10-03-03.csv";

    /** The title for the viewer. */
    public static final String TITLE = "Heart Monitor Session Viewer";
    /** The version */
    public static final String VERSION = "1.0.0.0";
    /** The title for the plot. */
    public static final String PLOT_TITLE = "Heart Monitor Data";
    /** The frame width for the viewer. */
    public static final int FRAME_WIDTH = 1200;
    /** The frame height for the viewer. */
    public static final int FRAME_HEIGHT = 750;
    /** The divider location for the main split pane. */
    public static final int MAIN_PANE_DIVIDER_LOCATION = 55 * FRAME_HEIGHT / 100;
    /** The divider location for the lower split pane. */
    public static final int LOWER_PANE_DIVIDER_LOCATION = FRAME_WIDTH / 2;

    /** Value for a database date value indicating invalid. */
    public static final long INVALID_DATE = Long.MIN_VALUE;
    /** Value for a database int indicating invalid. */
    public static final int INVALID_INT = -1;
    /** Value for a database String indicating invalid. */
    public static final String INVALID_STRING = "Invalid";
    public static final String SAVE_SESSION_DELIM = ",";
    /** Initial start time for RR values. */
    public static final long INITIAL_RR_START_TIME = 0;
    /** The static formatter to use for formatting dates to ms level. */
    public static final SimpleDateFormat sessionSaveFormatter = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

    /***
     * The name of the preference node for accessing preferences for this
     * application. On Windows these are found in the registry under
     * HKCU/JavaSoft/Prefs.
     */
    public static final String P_PREFERENCE_NODE = "net/kenevans/heartmonitorsessionviewer/preferences";

    /*** The preference name for the default directory for finding session files. */
    public static final String P_DEFAULT_DIR = "defaultDir";
    /*** The default value for the default directory for finding session files. */
    public static final String D_DEFAULT_DIR = "C:/Scratch/ECG/Android/SCH-I545/Current/HxM Monitor";
    /*** The preference name for the default directory 2 for finding session files. */
    public static final String P_DEFAULT_DIR2 = "defaultDir2";
    /*** The default value for the default directory 2 for finding session files. */
    public static final String D_DEFAULT_DIR2 = "C:/Scratch/ECG/Android/SCH-I545/Current/BLE Cardiac Monitor";

    /** The number of data types. */
    public static int N_DATA_TYPES = 1;

    // HR
    /** Index for HR */
    public static int HR_INDEX = 0;
    /*** The preference name for the HR name. */
    public static final String P_HR_NAME = "hrName";
    /*** The default value for the HR name. */
    public static final String D_HR_NAME = "HR";
    /*** The preference name for the HR color. */
    public static final String P_HR_COLOR = "hrColor";
    /*** The default value for the HR color. */
    public static final String D_HR_COLOR = "0xC00000";
    /*** The preference name for the HR visibility. */
    public static final String P_HR_VISIBILITY = "hrVisibility";
    /*** The default value for the HR visibility. */
    public static final boolean D_HR_VISIBILITY = true;
    /*** The preference name for the HR range axis. */
    public static final String P_HR_RANGE_AXIS = "hrRangeAxis";
    /*** The default value for the HR range axis. */
    public static final String D_HR_RANGE_AXIS = Integer.toString(HR_INDEX);
    /*** The preference name for the HR rolling average count. */
    public static final String P_HR_ROLLING_AVG_COUNT = "hrRollingAvgCount";
    /*** The default value for the HR rolling average count. */
    public static final int D_HR_ROLLING_AVG_COUNT = 0;

    // RR
    /** Index for RR */
    public static int RR_INDEX = 1;
    /*** The preference name for the RR name. */
    public static final String P_RR_NAME = "rrName";
    /*** The default value for the RR name. */
    public static final String D_RR_NAME = "RR";
    /*** The preference name for the RR color. */
    public static final String P_RR_COLOR = "rrColor";
    /*** The default value for the RR color. */
    public static final String D_RR_COLOR = "0x0040FF";
    /*** The preference name for the RR visibility. */
    public static final String P_RR_VISIBILITY = "rrVisibility";
    /*** The default value for the RR visibility. */
    public static final boolean D_RR_VISIBILITY = true;
    /*** The preference name for the RR range axis. */
    public static final String P_RR_RANGE_AXIS = "rrRangeAxis";
    /*** The default value for the RR range axis. */
    public static final String D_RR_RANGE_AXIS = Integer.toString(RR_INDEX);
    /*** The preference name for the speed rolling average count. */
    public static final String P_RR_ROLLING_AVG_COUNT = "rrRollingAvgCount";
    /*** The default value for the speed rolling average count. */
    public static final int D_RR_ROLLING_AVG_COUNT = 0;

    // HR Zones
    /** Index for HR */
    public static int HR_ZONES_INDEX = 2;
    /*** The preference name for the HR zones name. */
    public static final String P_HR_ZONES_NAME = "hrZonesName";
    /*** The default value for the HR zones name. */
    public static final String D_HR_ZONES_NAME = "HR Zones";
    /*** The preference name for the HR color. */
    public static final String P_HR_ZONES_COLOR = "hrZonesColor";
    /*** The default value for the HR zones color. */
    public static final String D_HR_ZONES_COLOR = "0x000000";
    /*** The preference name for the HR zones visibility. */
    public static final String P_HR_ZONES_VISIBILITY = "hrZonesVisibility";
    /*** The default value for the HR zones visibility. */
    public static final boolean D_HR_ZONES_VISIBILITY = true;
    /*** The preference name for the HR zones range axis. */
    public static final String P_HR_RANGE_ZONES_AXIS = "hrZonesRangeAxis";
    /*** The default value for the HR zones range axis. */
    public static final String D_HR_RANGE_ZONES_AXIS = Integer
        .toString(HR_INDEX);

    // Zones
    /*** The preference name for zone 1 value. */
    public static final String P_ZONE_1_VAL = "Zone1Value";
    /*** The default value for zone 1 value. */
    public static final int D_ZONE_1_VAL = 79;
    /*** The preference name for the zone 1 color. */
    public static final String P_ZONE_1_COLOR = "Zone1Color";
    /*** The default value for zone 1 color. */
    public static final String D_ZONE_1_COLOR = "0x99CCFF";
    /*** The preference name for zone 1 value. */
    public static final String P_ZONE_2_VAL = "Zone2Value";
    /*** The default value for zone 1 value. */
    public static final int D_ZONE_2_VAL = 94;
    /*** The preference name for the zone 1 color. */
    public static final String P_ZONE_2_COLOR = "Zone2Color";
    /*** The default value for zone 1 color. */
    public static final String D_ZONE_2_COLOR = "0x66FF00";
    /*** The preference name for zone 1 value. */
    public static final String P_ZONE_3_VAL = "Zone3Value";
    /*** The default value for zone 1 value. */
    public static final int D_ZONE_3_VAL = 110;
    /*** The preference name for the zone 1 color. */
    public static final String P_ZONE_3_COLOR = "Zone3Color";
    /*** The default value for zone 1 color. */
    public static final String D_ZONE_3_COLOR = "0xFFFF00";
    /*** The preference name for zone 1 value. */
    public static final String P_ZONE_4_VAL = "Zone4Value";
    /*** The default value for zone 1 value. */
    public static final int D_ZONE_4_VAL = 126;
    /*** The preference name for the zone 1 color. */
    public static final String P_ZONE_4_COLOR = "Zone4Color";
    /*** The default value for zone 1 color. */
    public static final String D_ZONE_4_COLOR = "0xFFC800";
    /*** The preference name for zone 1 value. */
    public static final String P_ZONE_5_VAL = "Zone5Value";
    /*** The default value for zone 1 value. */
    public static final int D_ZONE_5_VAL = 141;
    /*** The preference name for the zone 1 color. */
    public static final String P_ZONE_5_COLOR = "Zone5Color";
    /*** The default value for zone 1 color. */
    public static final String D_ZONE_5_COLOR = "0xFf9000";
    /*** The preference name for zone 1 value. */
    public static final String P_ZONE_6_VAL = "Zone6Value";
    /*** The default value for zone 1 value. */
    public static final int D_ZONE_6_VAL = 157;
    /*** The preference name for the zone 1 color. */
    public static final String P_ZONE_6_COLOR = "Zone6Color";
    /*** The default value for zone 1 color. */
    public static final String D_ZONE_6_COLOR = "0xFF0000";

    /*** The preference name for the max HR. */
    public static final String P_MAX_HR = "MaxHeartRate";
    /*** The default value for the max HR. */
    public static final int D_MAX_HR = 157;
    /*** The preference name for the resting HR. */
    public static final String P_REST_HR = "RestingHeartRate";
    /*** The default value for the resting HR. */
    public static final int D_REST_HR = 60;
    /*** The preference name for the age. */
    public static final String P_AGE = "Age";
    /*** The default value for the resting HR. */
    public static final int D_AGE = 73;
    /*** The preference name for using Korvonen. */
    public static final String P_USE_KORVONEN = "UseKorvonen";
    /*** The default value for using Korvonen. */
    public static final boolean D_USE_KORVONEN = false;

    /** The prefix used to represent a boundary in the series name. */
    public static final String BOUNDARY_SERIES_NAME_PREFIX = "HR=";

    public static final String FILE_PATH = D_DEFAULT_DIR + "/" + FILE_NAME;

    /** The min value for the RR axis if auto-range. */
    public static final double RR_FIXED_RANGE_MIN = 0.;
    /** The max value for the RR axis if auto-range. */
    public static final double RR_FIXED_RANGE_MAX = 2500.;

}
