package view;

import util.Config.SCREEN_NAME;

import javax.swing.*;
import java.awt.*;

public class GeneralLayoutPanel extends JPanel {
    /* Constants used for setting up General Layout */
    private static final int ROWS = 1;
    private static final int COLUMNS = 2;
    
    /* Data members */
    private SCREEN_NAME screenName;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private TableScrollPane tableScrollPane;
    
    private MapSelectionPanel mapSelectionPanel;
    
    /* Constructors */
    public GeneralLayoutPanel(SCREEN_NAME screenName) {
        this.screenName = screenName;
        this.setLayout(new GridLayout(ROWS, COLUMNS));
    
        leftPanel = new JPanel();
        tableScrollPane = new TableScrollPane(screenName);
        leftPanel.add(tableScrollPane);
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        mapSelectionPanel = new MapSelectionPanel();
        rightPanel.add(mapSelectionPanel);
    
        this.add(leftPanel);
        this.add(rightPanel);
    }
    
    /* Getters & Setters */
    public static int getROWS() {
        return ROWS;
    }
    
    public static int getCOLUMNS() {
        return COLUMNS;
    }
    
    public SCREEN_NAME getScreenName() {
        return screenName;
    }
    
    public JPanel getLeftPanel() {
        return leftPanel;
    }
    
    public JPanel getRightPanel() {
        return rightPanel;
    }
}
