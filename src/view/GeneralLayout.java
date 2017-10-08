package view;

import javax.swing.*;
import java.awt.*;

public class GeneralLayout extends JPanel {
    public enum SCREENS {MAP_EDITOR, GAME_PLAY}
    
    private SCREENS screenName;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private MapSelection mapSelection;
    
    public GeneralLayout(SCREENS screenName) {
        this.screenName = screenName;
        this.setLayout(new GridLayout(1, 2));
        
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        mapSelection = new MapSelection();
        
        rightPanel.add(mapSelection);
        this.add(leftPanel);
        this.add(rightPanel);
    }
}
