/* 
 * Risk Game Team 2
 * EditMapPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class EditMapPanel.
 */
public class EditMapPanel extends JPanel implements Observer{
    
    /** The Constant SAVE_BUTTON_LABEL. */
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    
    /** The Constant BACK_BUTTON_LABEL. */
    private static final String BACK_BUTTON_LABEL = "Back";
    
    /** The Constant TAB_CONTINENT_LABEL. */
    private static final String TAB_CONTINENT_LABEL = "Continent";
    
    /** The Constant TAB_TERRITORY_LABEL. */
    private static final String TAB_TERRITORY_LABEL = "Territory";
    
    /** The save map button. */
    private JButton saveMapButton;
    
    /** The back button. */
    private JButton backButton;
    
    /** The map load panel. */
    private MapLoadPanel mapLoadPanel;
    
    /** The edit continent panel. */
    private EditContinentPanel editContinentPanel;
    
    /** The edit territory panel. */
    private EditTerritoryPanel editTerritoryPanel;
    
    /**
     * Instantiates a new edits the map panel.
     */
    /* Constructors */
    public EditMapPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        setupMapSelectionArea();
        
        setupEditingArea();
        
        /* Navigation buttons */
        JPanel panel_4 = new JPanel(new FlowLayout());
        backButton = new JButton(BACK_BUTTON_LABEL);
        saveMapButton = new JButton(SAVE_BUTTON_LABEL);
        saveMapButton.setEnabled(false);
        panel_4.add(backButton);
        panel_4.add(saveMapButton);
        add(panel_4);
    }
    
    /**
     * Gets the edits the continent panel.
     *
     * @return the edits the continent panel
     */
    /* Getters & Setters */
    public EditContinentPanel getEditContinentPanel() {
        return editContinentPanel;
    }
    
    /**
     * Gets the edits the territory panel.
     *
     * @return the edits the territory panel
     */
    public EditTerritoryPanel getEditTerritoryPanel() {
        return editTerritoryPanel;
    }
    
    /**
     * Gets the map load panel.
     *
     * @return the map load panel
     */
    public MapLoadPanel getMapLoadPanel() {
        return mapLoadPanel;
    }
    
    /**
     * Adds the back button listener.
     *
     * @param listenerForBackButton the listener for back button
     */
    /* MVC & Observer pattern methods */
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    /**
     * Adds the save map button listener.
     *
     * @param listenerForSaveMapButton the listener for save map button
     */
    public void addSaveMapButtonListener(ActionListener listenerForSaveMapButton) {
        saveMapButton.addActionListener(listenerForSaveMapButton);
    }
    
    /* Public methods */
    
    /**
     * Setup map selection area.
     */
    /* Private methods */
    private void setupMapSelectionArea() {
        mapLoadPanel = new MapLoadPanel();
        add(mapLoadPanel);
    }
    
    /**
     * Setup editing area.
     */
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
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            if (((MapEditorModel) o).getGameMap() != null) {
                saveMapButton.setEnabled(true);
            }
        }
    }
}
