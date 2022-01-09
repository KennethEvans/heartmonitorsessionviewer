package net.kenevans.heartmonitorsessionviewer.preferences;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.kenevans.core.utils.Utils;
import net.kenevans.heartmonitorsessionviewer.model.IConstants;
import net.kenevans.heartmonitorsessionviewer.ui.HMSViewer;

/**
 * PreferencesDialog is a dialog to set the Preferences for HMSViewer. It only
 * returns after Done. It can save the values to the preference store or set
 * them in the viewer. In either case it remains visible.
 * 
 * @author Kenneth Evans, Jr.
 */
/**
 * PreferencesDialog
 * 
 * @author Kenneth Evans, Jr.
 */
public class PreferencesDialog extends JDialog implements IConstants
{
    private static final long serialVersionUID = 1L;
    private static final int DIR_COLS = 40;
    private HMSViewer viewer;
    /**
     * The return value. It is always true.
     */
    private boolean ok = true;

    JTextField defaultDirText;
    JTextField defaultDir2Text;
    JTextField defaultDir3Text;
    JCheckBox hrVisibileCheck;
    JCheckBox hrZonesVisibileCheck;
    JCheckBox rrVisibileCheck;
    JTextField hrRavCountText;
    JTextField rrRavCountText;

    JTextField zone1ValText;
    JTextField zone2ValText;
    JTextField zone3ValText;
    JTextField zone4ValText;
    JTextField zone5ValText;
    JTextField zone6ValText;

    JTextField zone1ColorText;
    JTextField zone2ColorText;
    JTextField zone3ColorText;
    JTextField zone4ColorText;
    JTextField zone5ColorText;
    JTextField zone6ColorText;

    JTextField maxHrText;
    JTextField restHrText;
    JTextField ageText;
    JCheckBox useKorvonenCheck;

    /**
     * Constructor
     */
    public PreferencesDialog(JFrame parent, HMSViewer viewer) {
        super(parent);
        this.viewer = viewer;
        if(viewer == null) {
            Utils.errMsg("Viewer is null");
            return;
        }
        init();
        Settings settings = new Settings();
        settings.loadFromPreferences();
        setValues(settings);
        // Locate it on the screen
        this.setLocationRelativeTo(parent);
    }

    /**
     * This method initializes this dialog
     * 
     * @return void
     */
    private void init() {
        this.setTitle("Preferences");
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbcDefault = new GridBagConstraints();
        gbcDefault.insets = new Insets(2, 2, 2, 2);
        gbcDefault.anchor = GridBagConstraints.WEST;
        gbcDefault.fill = GridBagConstraints.NONE;
        GridBagConstraints gbc = null;
        int gridy = -1;

        // File Group //////////////////////////////////////////////////////
        JPanel fileGroup = new JPanel();
        fileGroup.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("File"),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        gridy++;
        fileGroup.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        contentPane.add(fileGroup, gbc);

        // Default directory 1
        JLabel label = new JLabel("Default Directory:");
        label.setToolTipText("The default directory.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        fileGroup.add(label, gbc);

        // File JPanel holds the filename and browse button
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        fileGroup.add(filePanel, gbc);

        defaultDirText = new JTextField(DIR_COLS);
        defaultDirText.setToolTipText(label.getToolTipText());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        filePanel.add(defaultDirText, gbc);

        JButton button = new JButton();
        button.setText("Browse");
        button.setToolTipText("Choose the file.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                if(defaultDirText == null) {
                    return;
                }
                String initialDirName = defaultDirText.getText();
                String dirName = browse(initialDirName);
                defaultDirText.setText(dirName);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        filePanel.add(button);

        // Default directory 2
        gridy++;
        label = new JLabel("Default Directory 2:");
        label.setToolTipText("The default directory 2.  May be blank.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        fileGroup.add(label, gbc);

        // File JPanel holds the filename and browse button
        filePanel = new JPanel();
        filePanel.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        fileGroup.add(filePanel, gbc);

        defaultDir2Text = new JTextField(DIR_COLS);
        defaultDir2Text.setToolTipText(label.getToolTipText());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        filePanel.add(defaultDir2Text, gbc);

        button = new JButton();
        button.setText("Browse");
        button.setToolTipText("Choose the file.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                if(defaultDir2Text == null) {
                    return;
                }
                String initialDirName = defaultDir2Text.getText();
                String dirName = browse(initialDirName);
                defaultDir2Text.setText(dirName);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        filePanel.add(button);

        // Default directory 3
        gridy++;
        label = new JLabel("Default Directory 3:");
        label.setToolTipText("The default directory 3.  May be blank.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        fileGroup.add(label, gbc);

        // File JPanel holds the filename and browse button
        filePanel = new JPanel();
        filePanel.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        fileGroup.add(filePanel, gbc);

        defaultDir3Text = new JTextField(DIR_COLS);
        defaultDir3Text.setToolTipText(label.getToolTipText());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        filePanel.add(defaultDir3Text, gbc);

        button = new JButton();
        button.setText("Browse");
        button.setToolTipText("Choose the file.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                if(defaultDir3Text == null) {
                    return;
                }
                String initialDirName = defaultDir3Text.getText();
                String dirName = browse(initialDirName);
                defaultDir3Text.setText(dirName);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        filePanel.add(button);

        // HR Group /////////////////////////////////////////////////////////
        JPanel hrGroup = new JPanel();
        hrGroup.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Heart Rate"),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        gridy++;
        hrGroup.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.weightx = 100;
        contentPane.add(hrGroup, gbc);

        // Visible
        hrVisibileCheck = new JCheckBox("Visible");
        hrVisibileCheck.setToolTipText("Whether HR data is visible.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        hrGroup.add(hrVisibileCheck, gbc);

        // Running average
        String toolTip = "Number of data points to average over.  "
            + "0->Don't average.  " + "Negative->Omit raw values.";
        label = new JLabel("Running Average Count:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        hrGroup.add(label, gbc);

        hrRavCountText = new JTextField(5);
        hrRavCountText.setToolTipText(label.getText());
        hrRavCountText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        hrGroup.add(hrRavCountText, gbc);

        // Zones visible
        hrZonesVisibileCheck = new JCheckBox("Zones Visible");
        hrZonesVisibileCheck.setToolTipText("Whether HR zones are visible.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        hrGroup.add(hrZonesVisibileCheck, gbc);

        // RR Group //////////////////////////////////////////////////////
        JPanel speedGroup = new JPanel();
        speedGroup.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("RR"),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        gridy++;
        speedGroup.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        contentPane.add(speedGroup, gbc);

        // Visible
        rrVisibileCheck = new JCheckBox("Visible");
        rrVisibileCheck.setToolTipText("Whether RR data is visible.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        speedGroup.add(rrVisibileCheck, gbc);

        // Running average
        toolTip = "Number of data points to average over.  "
            + "0->Don't average.  " + "Negative->Omit raw values.";
        label = new JLabel("Running Average Count:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        speedGroup.add(label, gbc);

        rrRavCountText = new JTextField(5);
        rrRavCountText.setToolTipText(label.getText());
        rrRavCountText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        speedGroup.add(rrRavCountText, gbc);

        // Zone Group ///////////////////////////////////////////////////////
        JPanel zoneGroup = new JPanel();
        zoneGroup.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Zones"),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        gridy++;
        zoneGroup.setLayout(new GridBagLayout());
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;
        contentPane.add(zoneGroup, gbc);
        int zoneGridy = -1;

        // Zone 1
        zoneGridy++;
        toolTip = "Value for zone 1";
        label = new JLabel("Zone 1 Value:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone1ValText = new JTextField(5);
        zone1ValText.setToolTipText(label.getText());
        zone1ValText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridy = zoneGridy;
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone1ValText, gbc);

        toolTip = "Color for zone 1";
        label = new JLabel("Zone 1 Color:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone1ColorText = new JTextField(10);
        zone1ColorText.setToolTipText(label.getText());
        zone1ColorText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone1ColorText, gbc);

        button = new JButton();
        button.setText("Pick Color");
        button.setToolTipText("Pick the zone color.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                chooseZoneColor(ev, zone1ColorText);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Zone 2
        zoneGridy++;
        toolTip = "Value for zone 2";
        label = new JLabel("Zone 2 Value:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone2ValText = new JTextField(5);
        zone2ValText.setToolTipText(label.getText());
        zone2ValText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone2ValText, gbc);

        toolTip = "Color for zone 2";
        label = new JLabel("Zone 2 Color:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone2ColorText = new JTextField(10);
        zone2ColorText.setToolTipText(label.getText());
        zone2ColorText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone2ColorText, gbc);

        button = new JButton();
        button.setText("Pick Color");
        button.setToolTipText("Pick the zone color.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                chooseZoneColor(ev, zone2ColorText);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Zone 3
        zoneGridy++;
        toolTip = "Value for zone 3";
        label = new JLabel("Zone 3 Value:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone3ValText = new JTextField(5);
        zone3ValText.setToolTipText(label.getText());
        zone3ValText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone3ValText, gbc);

        toolTip = "Color for zone 3";
        label = new JLabel("Zone 3 Color:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone3ColorText = new JTextField(10);
        zone3ColorText.setToolTipText(label.getText());
        zone3ColorText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone3ColorText, gbc);

        button = new JButton();
        button.setText("Pick Color");
        button.setToolTipText("Pick the zone color.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                chooseZoneColor(ev, zone3ColorText);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Zone 4
        zoneGridy++;
        toolTip = "Value for zone 4";
        label = new JLabel("Zone 4 Value:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone4ValText = new JTextField(5);
        zone4ValText.setToolTipText(label.getText());
        zone4ValText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone4ValText, gbc);

        toolTip = "Color for zone 4";
        label = new JLabel("Zone 4 Color:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone4ColorText = new JTextField(10);
        zone4ColorText.setToolTipText(label.getText());
        zone4ColorText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone4ColorText, gbc);

        button = new JButton();
        button.setText("Pick Color");
        button.setToolTipText("Pick the zone color.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                chooseZoneColor(ev, zone4ColorText);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Zone 5
        zoneGridy++;
        toolTip = "Value for zone 5";
        label = new JLabel("Zone 5 Value:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone5ValText = new JTextField(5);
        zone5ValText.setToolTipText(label.getText());
        zone5ValText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone5ValText, gbc);

        toolTip = "Color for zone 5";
        label = new JLabel("Zone 5 Color:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone5ColorText = new JTextField(10);
        zone5ColorText.setToolTipText(label.getText());
        zone5ColorText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone5ColorText, gbc);

        button = new JButton();
        button.setText("Pick Color");
        button.setToolTipText("Pick the zone color.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                chooseZoneColor(ev, zone5ColorText);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Zone 6
        zoneGridy++;
        toolTip = "Value for zone 6";
        label = new JLabel("Zone 6 Value:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone6ValText = new JTextField(5);
        zone6ValText.setToolTipText(label.getText());
        zone6ValText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone6ValText, gbc);

        toolTip = "Color for zone 6";
        label = new JLabel("Zone 6 Color:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        zone6ColorText = new JTextField(10);
        zone6ColorText.setToolTipText(label.getText());
        zone6ColorText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(zone6ColorText, gbc);

        button = new JButton();
        button.setText("Pick Color");
        button.setToolTipText("Pick the zone color.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                chooseZoneColor(ev, zone6ColorText);
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Max HR
        zoneGridy++;
        toolTip = "Maximum HR";
        label = new JLabel("Max HR:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        maxHrText = new JTextField(5);
        maxHrText.setToolTipText(label.getText());
        maxHrText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(maxHrText, gbc);

        toolTip = "Age";
        label = new JLabel("Age:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        ageText = new JTextField(5);
        ageText.setToolTipText(label.getText());
        ageText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 3;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(ageText, gbc);

        button = new JButton();
        button.setText("Calculate Max HR");
        button.setToolTipText("Calculate the max HR for this age.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                calculateMaxHr();
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // Rest HR
        zoneGridy++;
        toolTip = "Resting HR";
        label = new JLabel("Resting HR:");
        label.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        gbc.gridy = zoneGridy;
        zoneGroup.add(label, gbc);

        restHrText = new JTextField(5);
        restHrText.setToolTipText(label.getText());
        restHrText.setToolTipText(toolTip);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 1;
        gbc.gridy = zoneGridy;
        gbc.weightx = 100;
        zoneGroup.add(restHrText, gbc);

        // Use Korvonen
        useKorvonenCheck = new JCheckBox("Use Korvonen");
        useKorvonenCheck.setToolTipText("Whether to use the Korvonen formula.");
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 0;
        hrGroup.add(useKorvonenCheck, gbc);
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 2;
        gbc.gridy = zoneGridy;
        zoneGroup.add(useKorvonenCheck, gbc);

        button = new JButton();
        button.setText("Calculate Zone Values");
        button.setToolTipText("Calculate the zone values for this age.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                calculateZoneValues();
            }
        });
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridx = 4;
        gbc.gridy = zoneGridy;
        zoneGroup.add(button, gbc);

        // // Dummy Group
        // JPanel dummyGroup = new JPanel();
        // dummyGroup.setBorder(BorderFactory.createCompoundBorder(
        // BorderFactory.createTitledBorder("Dummy"),
        // BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        // gridy++;
        // dummyGroup.setLayout(new GridBagLayout());
        // gbc = (GridBagConstraints)gbcDefault.clone();
        // gbc.gridx = 0;
        // gbc.gridy = gridy;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.weightx = 100;
        // contentPane.add(dummyGroup, gbc);
        //
        // // Dummy
        // label = new JLabel("Dummy:");
        // label.setToolTipText("Dummy.");
        // gbc = (GridBagConstraints)gbcDefault.clone();
        // gbc.gridx = 0;
        // dummyGroup.add(label, gbc);
        //
        // JTextField dummyText = new JTextField(DIR_COLS);
        // dummyText.setToolTipText(label.getText());
        // gbc = (GridBagConstraints)gbcDefault.clone();
        // gbc.gridx = 1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.weightx = 100;
        // dummyGroup.add(dummyText, gbc);

        // Button panel /////////////////////////////////////////////////////
        gridy++;
        JPanel buttonPanel = new JPanel();
        gbc = (GridBagConstraints)gbcDefault.clone();
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(buttonPanel, gbc);

        button = new JButton();
        button.setText("Use Current");
        button.setToolTipText("Set to the current viewer values.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                Settings settings = viewer.getSettings();
                if(settings == null) {
                    Utils.errMsg("Settings in the viewer do not exist");
                    return;
                }
                setValues(settings);
            }
        });
        buttonPanel.add(button);

        button = new JButton();
        button.setText("Use Defaults");
        button.setToolTipText("Set to the HMSViewer default values.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                Settings settings = new Settings();
                if(settings == null) {
                    Utils.errMsg("Default settings do not exist");
                    return;
                }
                setValues(settings);
            }
        });
        buttonPanel.add(button);

        button = new JButton();
        button.setText("Use Stored");
        button.setToolTipText("Reset to the current stored preferences.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                Settings settings = new Settings();
                settings.loadFromPreferences();
                if(settings == null) {
                    Utils.errMsg("Cannot load preferences");
                    return;
                }
                setValues(settings);
            }
        });
        buttonPanel.add(button);

        button = new JButton();
        button.setText("Save");
        button.setToolTipText("Save the changes as preferences.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                save();
            }
        });
        buttonPanel.add(button);

        button = new JButton();
        button.setText("Set Current");
        button.setToolTipText("Set the current values in the viewer.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                setToViewer();
            }
        });
        buttonPanel.add(button);

        button = new JButton();
        button.setText("Done");
        button.setToolTipText("Close the dialog and do nothing.");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                PreferencesDialog.this.setVisible(false);
            }
        });
        buttonPanel.add(button);

        pack();
    }

    /**
     * Brings up a JColorChooser to get a new color starting with the color in
     * the colorText and sets the new color in the colorText unless cancelled.
     * 
     * @param ev Not used
     * @param colorText
     */
    void chooseZoneColor(java.awt.event.ActionEvent ev, JTextField colorText) {
        Color initialColor = Color.BLACK;
        try {
            initialColor = Color.decode(colorText.getText());
        } catch(NumberFormatException ex) {
            Utils.excMsg("Cannot parse color", ex);
            return;
        }
        Color newColor = JColorChooser.showDialog(PreferencesDialog.this,
            "Choose Zone Color", initialColor);
        if(newColor != null) {
            int rgb = newColor.getRGB() & 0x00FFFFFF;
            String colorString = String.format("0x%06X", rgb);
            colorText.setText(colorString);
        }
    }

    /**
     * Calculates the max HR from the age using teh formula by Tanaka, Monahan,
     * & Seals and sets the max HR.
     */
    void calculateMaxHr() {
        int maxHr = 0, age = 0;
        try {
            age = Integer.parseInt(ageText.getText());
            // Tanaka, Monahan, & Seals
            maxHr = (int)Math.round(208 - .7 * age);
            maxHrText.setText(Integer.toString(maxHr));
        } catch(NumberFormatException ex) {
            Utils.excMsg("Invalid age", ex);
            return;
        }
    }

    void calculateZoneValues() {
        int maxHr = 0, restHr = 0;
        boolean useKorvonen = false;
        int[] zoneVals = new int[6];
        try {
            maxHr = Integer.parseInt(maxHrText.getText());
            restHr = Integer.parseInt(restHrText.getText());
            useKorvonen = useKorvonenCheck.isSelected();
            // Tanaka, Monahan, & Seals
            double intensity;
            for(int i = 0; i < 6; i++) {
                intensity = .1 * i + .5;
                if(useKorvonen) {
                    zoneVals[i] = (int)Math
                        .round(intensity * (maxHr - restHr) + restHr);
                } else {
                    zoneVals[i] = (int)Math.round(intensity * maxHr);
                }
            }
            zone1ValText.setText(Integer.toString(zoneVals[0]));
            zone2ValText.setText(Integer.toString(zoneVals[1]));
            zone3ValText.setText(Integer.toString(zoneVals[2]));
            zone4ValText.setText(Integer.toString(zoneVals[3]));
            zone5ValText.setText(Integer.toString(zoneVals[4]));
            zone6ValText.setText(Integer.toString(zoneVals[5]));
        } catch(NumberFormatException ex) {
            Utils.excMsg("Invalid value for max Hr or resting HR", ex);
            return;
        }
    }

    /**
     * Brings up a JFileChooser to choose a directory.
     */
    private String browse(String initialDirName) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(initialDirName != null) {
            File dir = new File(initialDirName);
            chooser.setCurrentDirectory(dir);
            chooser.setSelectedFile(dir);
        }
        int result = chooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            // Process the directory
            String dirName = chooser.getSelectedFile().getPath();
            File dir = new File(dirName);
            if(!dir.exists()) {
                Utils.errMsg("Does not exist: " + dirName);
                return null;
            }
            if(!dir.isDirectory()) {
                Utils.errMsg("Not a diretory: " + dirName);
                return null;
            }
            return dirName;
        } else {
            return null;
        }
    }

    /**
     * Set the Controls from the given Settings. Can also be used to initialize
     * the dialog.
     * 
     * @param settings
     */
    public void setValues(Settings settings) {
        if(viewer == null) {
            return;
        }
        if(defaultDirText != null) {
            defaultDirText.setText(settings.getDefaultDirectory());
        }
        if(defaultDir2Text != null) {
            defaultDir2Text.setText(settings.getDefaultDirectory2());
        }
        if(defaultDir3Text != null) {
            defaultDir3Text.setText(settings.getDefaultDirectory3());
        }

        if(hrVisibileCheck != null) {
            hrVisibileCheck.setSelected(settings.getHrVisible());
        }
        if(hrZonesVisibileCheck != null) {
            hrZonesVisibileCheck.setSelected(settings.getHrZonesVisible());
        }
        if(rrVisibileCheck != null) {
            rrVisibileCheck.setSelected(settings.getRrVisible());
        }

        if(hrRavCountText != null) {
            hrRavCountText
                .setText(Integer.toString(settings.getHrRollingAvgCount()));
        }
        if(rrRavCountText != null) {
            rrRavCountText
                .setText(Integer.toString(settings.getRrRollingAvgCount()));
        }

        if(zone1ValText != null) {
            zone1ValText.setText(Integer.toString(settings.getZone1Val()));
        }
        if(zone2ValText != null) {
            zone2ValText.setText(Integer.toString(settings.getZone2Val()));
        }
        if(zone3ValText != null) {
            zone3ValText.setText(Integer.toString(settings.getZone3Val()));
        }
        if(zone4ValText != null) {
            zone4ValText.setText(Integer.toString(settings.getZone4Val()));
        }
        if(zone5ValText != null) {
            zone5ValText.setText(Integer.toString(settings.getZone5Val()));
        }
        if(zone6ValText != null) {
            zone6ValText.setText(Integer.toString(settings.getZone6Val()));
        }

        if(zone1ColorText != null) {
            zone1ColorText.setText(settings.getZone1Color());
        }
        if(zone2ColorText != null) {
            zone2ColorText.setText(settings.getZone2Color());
        }
        if(zone3ColorText != null) {
            zone3ColorText.setText(settings.getZone3Color());
        }
        if(zone4ColorText != null) {
            zone4ColorText.setText(settings.getZone4Color());
        }
        if(zone5ColorText != null) {
            zone5ColorText.setText(settings.getZone5Color());
        }
        if(zone6ColorText != null) {
            zone6ColorText.setText(settings.getZone6Color());
        }

        if(ageText != null) {
            ageText.setText(Integer.toString(settings.getAge()));
        }
        if(maxHrText != null) {
            maxHrText.setText(Integer.toString(settings.getMaxHr()));
        }
        if(restHrText != null) {
            restHrText.setText(Integer.toString(settings.getRestHr()));
        }
        if(useKorvonenCheck != null) {
            useKorvonenCheck.setSelected(settings.getUseKorvonen());
        }
    }

    /**
     * Sets the current values in the given Settings then checks if they are
     * valid.
     * 
     * @param settings
     * @return True if they are valid, else false.
     */
    public boolean setSettingsFromValues(Settings settings) {
        if(settings == null) {
            Utils.errMsg("Input settings is null");
            return false;
        }
        try {
            settings.setDefaultDirectory(defaultDirText.getText());
            settings.setDefaultDirectory2(defaultDir2Text.getText());
            settings.setDefaultDirectory3(defaultDir3Text.getText());

            settings.setHrVisible(hrVisibileCheck.isSelected());
            settings.setHrZonesVisible(hrZonesVisibileCheck.isSelected());
            settings.setRrVisible(rrVisibileCheck.isSelected());

            settings.setHrRollingAvgCount(
                Integer.parseInt((hrRavCountText.getText())));
            settings.setRrRollingAvgCount(
                Integer.parseInt((rrRavCountText.getText())));

            settings.setZone1Val(Integer.parseInt(zone1ValText.getText()));
            settings.setZone2Val(Integer.parseInt(zone2ValText.getText()));
            settings.setZone3Val(Integer.parseInt(zone3ValText.getText()));
            settings.setZone4Val(Integer.parseInt(zone4ValText.getText()));
            settings.setZone5Val(Integer.parseInt(zone5ValText.getText()));
            settings.setZone6Val(Integer.parseInt(zone6ValText.getText()));

            settings.setZone1Color(zone1ColorText.getText());
            settings.setZone2Color(zone2ColorText.getText());
            settings.setZone3Color(zone3ColorText.getText());
            settings.setZone4Color(zone4ColorText.getText());
            settings.setZone5Color(zone5ColorText.getText());
            settings.setZone6Color(zone6ColorText.getText());

            settings.setAge(Integer.parseInt(ageText.getText()));
            settings.setMaxHr(Integer.parseInt(maxHrText.getText()));
            settings.setRestHr(Integer.parseInt(restHrText.getText()));
            settings.setUseKorvonen(useKorvonenCheck.isSelected());
        } catch(Exception ex) {
            Utils.excMsg("Error reading values", ex);
            return false;
        }

        // Check if the values are valid
        boolean res = settings.checkValues(true);
        if(!res) {
            Utils.errMsg("Some values are invalid");
        }
        return res;
    }

    /**
     * Saves the current values to the preference store if they are valid.
     */
    public void save() {
        Settings settings = new Settings();
        boolean res = setSettingsFromValues(settings);
        if(!res) {
            Utils.errMsg("Aborting");
            return;
        }
        // Save to the preference store
        try {
            res = settings.saveToPreferences(true);
        } catch(Exception ex) {
            Utils.excMsg("Error saving preferences", ex);
            return;
        }
        if(res) {
            // Utils.errMsg("Preferences saved successfully");
        } else {
            Utils.errMsg("Error saving preferences");
        }
    }

    /**
     * Sets the current values to the viewer if they are valid.
     */
    public void setToViewer() {
        Settings settings = new Settings();
        boolean res = setSettingsFromValues(settings);
        if(!res) {
            Utils.errMsg("Aborting");
            return;
        }
        // Copy to the viewer settings
        try {
            viewer.onPreferenceReset(settings);
        } catch(Exception ex) {
            Utils.excMsg("Error setting viewer settings", ex);
            ex.printStackTrace();
            return;
        }
        if(res) {
            // Utils.errMsg("Viewer settings set successfully");
        } else {
            Utils.errMsg("Error setting viewer settings");
        }
    }

    /**
     * Shows the dialog and returns whether it was successful or not. However
     * currently it is always successful and returns only on Done.
     * 
     * @return
     */
    public boolean showDialog() {
        setVisible(true);
        dispose();
        return ok;
    }

}
