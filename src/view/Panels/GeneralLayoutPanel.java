package view.Panels;

import util.Config.SCREEN_NAME;

import javax.swing.*;
import java.awt.*;

public class GeneralLayoutPanel extends JPanel {
    /* Constants used for setting up General Layout */
    private static final int ROWS = 1;
    private static final int COLUMNS = 2;
    
    /* Data members */
    private SCREEN_NAME screenName;
    private TablePanel tablePanel;
    private ControlPanel controlPanel;
    
    private MapEditControlPanel mapEditControlPanel;
    
    /* Constructors */
    public GeneralLayoutPanel(SCREEN_NAME screenName) {
        this.screenName = screenName;
        this.setLayout(new GridLayout(ROWS, COLUMNS));
        
        switch (screenName) {
            case MAP_EDITOR:
                tablePanel = new MapTablePanel();
                controlPanel = new MapEditControlPanel();
                break;
            case GAME_PLAY:
                tablePanel = new GameTablePanel();
                controlPanel = new GamePlayControlPanel();
                break;
        }
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        
        mapEditControlPanel = new MapEditControlPanel();
    
        this.add(scrollPane);
        this.add(mapEditControlPanel);
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
    
    public MapEditControlPanel getMapEditControlPanel() {
        return mapEditControlPanel;
    }
    
    public TablePanel getTablePanel() {
        return tablePanel;
    }
    
    public ControlPanel getControlPanel() {
        return controlPanel;
    }
    
    /* Public methods */
}
