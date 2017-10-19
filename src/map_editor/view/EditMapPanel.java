/* 
 * Risk Game Team 2
 * EditMapPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.view;

import game_play.view.ui_components.MapLoadPanel;
import map_editor.model.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class EditMapPanel provides the user with an interface to load or create a new map.
 *
 * @author Team 2
 * @version 1.0
 */
public class EditMapPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    private static final String BACK_BUTTON_LABEL = "Back";
    private static final String TAB_CONTINENT_LABEL = "Continent";
    private static final String TAB_TERRITORY_LABEL = "Territory";
    private JButton saveMapButton;
    private JButton backButton;
    private MapLoadPanel mapLoadPanel;
    private EditContinentPanel editContinentPanel;
    private EditTerritoryPanel editTerritoryPanel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new edit map panel.
     */
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
    // endregion
    
    // region Getters & Setters
    
    /**
     * Setup UI map selection panel.
     */
    /* Private methods */
    private void setupMapSelectionArea() {
        mapLoadPanel = new MapLoadPanel();
        add(mapLoadPanel);
    }
    
    /**
     * Setup UI editing area.
     */
    private void setupEditingArea() {
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JTabbedPane editTabbedPane = new JTabbedPane();
        editContinentPanel = new EditContinentPanel();
        editTabbedPane.addTab(TAB_CONTINENT_LABEL, editContinentPanel);
        editTabbedPane.setMnemonicAt(0, KeyEvent.VK_0);
        editTerritoryPanel = new EditTerritoryPanel();
        editTabbedPane.addTab(TAB_TERRITORY_LABEL, editTerritoryPanel);
        editTabbedPane.setMnemonicAt(1, KeyEvent.VK_1);
        add(editTabbedPane);
    }
    
    /**
     * Gets the edits the continent panel.
     *
     * @return the edits the continent panel
     */
    public EditContinentPanel getEditContinentPanel() {
        return editContinentPanel;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
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
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    // endregion
    
    // region Private methods
    
    /**
     * Adds the save map button listener.
     *
     * @param listenerForSaveMapButton the listener for save map button
     */
    public void addSaveMapButtonListener(ActionListener listenerForSaveMapButton) {
        saveMapButton.addActionListener(listenerForSaveMapButton);
    }
    
    /**
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
