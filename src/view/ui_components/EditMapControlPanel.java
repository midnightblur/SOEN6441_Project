package view.ui_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EditMapControlPanel extends JPanel {
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    private static final String BACK_BUTTON_LABEL = "Back";
    
    private static final String TAB_CONTINENT_LABEL = "Continent";
    private static final String TAB_TERRITORY_LABEL = "Territory";
    
    private JButton saveMapButton;
    private JButton backButton;
    private MapLoadPanel mapLoadPanel;
    private EditContinentPanel editContinentPanel;
    private EditTerritoryPanel editTerritoryPanel;
    
    /* Constructors */
    public EditMapControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        setupMapSelectionArea();
        
        setupEditingArea();
        
        /* Navigation buttons */
        JPanel panel_4 = new JPanel(new FlowLayout());
        backButton = new JButton(BACK_BUTTON_LABEL);
        saveMapButton = new JButton(SAVE_BUTTON_LABEL);
        panel_4.add(backButton);
        panel_4.add(saveMapButton);
        add(panel_4);
    }
    
    /* Getters & Setters */
    public EditContinentPanel getEditContinentPanel() {
        return editContinentPanel;
    }
    
    public EditTerritoryPanel getEditTerritoryPanel() {
        return editTerritoryPanel;
    }
    
    public MapLoadPanel getMapLoadPanel() {
        return mapLoadPanel;
    }
    
    /* MVC & Observer pattern methods */
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    public void addSaveMapButtonListener(ActionListener listenerForSaveMapButton) {
        saveMapButton.addActionListener(listenerForSaveMapButton);
    }
    
    /* Public methods */
    
    /* Private methods */
    private void setupMapSelectionArea() {
        mapLoadPanel = new MapLoadPanel();
        add(mapLoadPanel);
    }
    
    private void setupEditingArea() {
        JTabbedPane editTabbedPane = new JTabbedPane();
        editContinentPanel = new EditContinentPanel();
        editTabbedPane.addTab(TAB_CONTINENT_LABEL, editContinentPanel);
        editTabbedPane.setMnemonicAt(0, KeyEvent.VK_0);
        editTerritoryPanel = new EditTerritoryPanel();
        editTabbedPane.addTab(TAB_TERRITORY_LABEL, editTerritoryPanel);
        editTabbedPane.setMnemonicAt(1, KeyEvent.VK_1);
        add(editTabbedPane);
    }
    
}
