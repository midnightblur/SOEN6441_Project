package view.Panels;

import util.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapEditorControlPanel extends ControlPanel {
    private JPanel topPanel;
    private MapSelectionPanel mapSelectionPanel;
    private JButton loadMapButton;
    
    /* Constructors */
    public MapEditorControlPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        mapSelectionPanel = new MapSelectionPanel();
        topPanel.add(mapSelectionPanel);
        loadMapButton = new JButton(Config.UI_BUTTON_LOAD_MAP);
        topPanel.add(loadMapButton);
        this.add(topPanel, BorderLayout.PAGE_START);
    }
    
    /* Getters & Setters */
    public MapSelectionPanel getMapSelectionPanel() {
        return mapSelectionPanel;
    }
    
    public JButton getLoadMapButton() {
        return loadMapButton;
    }
    
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
}
