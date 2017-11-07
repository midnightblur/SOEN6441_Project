/* 
 * Risk Game Team 2
 * EditTerritoryPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.view;

import map_editor.model.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class EditTerritoryPanel represents a sub-panel for map editor allowing user to add or edit a territory.
 *
 * It is used as part of the edit map control panel
 *
 * @author Team 2
 * @version 1.0
 */
public class EditTerritoryPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ADD_BUTTON_LABEL = "Add Territory";
    private static final String SAVE_BUTTON_LABEL = "Save Territory";
    private static final String REMOVE_BUTTON_LABEL = "Remove Territory";
    private static final String CHOOSE_TERRITORY = "Choose Territory";
    private static final String TERRITORY_NAME_LABEL = "Territory Name";
    private static final String CHOOSE_NEIGHBORS_LABEL = "Choose Neighbors";
    private static final String CHOOSE_CONTINENT_LABEL = "Choose Continent";
    private static final int LAYOUT_ROWS = 2;
    private static final int LAYOUT_COLS = 2;
    private JComboBox<String> territoriesListDropdown;
    private JTextField territoryNameText;
    private JButton saveTerritoryButton;
    private JButton removeTerritoryButton;
    private JPanel radioButtonsPanel;
    private JPanel checkBoxPanel;
    // endregion
    
    // region Public methods
    
    /**
     * Instantiates a new edits the territory panel.
     */
    public EditTerritoryPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(false);
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Setup components.
     */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel gridPanel = new JPanel(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
        JLabel chooseTerritoryLabel = new JLabel(CHOOSE_TERRITORY);
        chooseTerritoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(chooseTerritoryLabel);
        territoriesListDropdown = new JComboBox<>();
        gridPanel.add(territoriesListDropdown);
        
        JLabel territoryNameLabel = new JLabel(TERRITORY_NAME_LABEL);
        territoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(territoryNameLabel);
        territoryNameText = new JTextField();
        gridPanel.add(territoryNameText);
        
        add(gridPanel);
        
        /* Setup Continents Radio button */
        JLabel chooseContinentLabel = new JLabel(CHOOSE_CONTINENT_LABEL);
        chooseContinentLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(chooseContinentLabel);
        radioButtonsPanel = new JPanel(new GridLayout(0, 3));
        add(new JScrollPane(radioButtonsPanel));
        
        /* Setup Territories Checkbox List */
        JLabel chooseNeighborsLabel = new JLabel(CHOOSE_NEIGHBORS_LABEL);
        chooseNeighborsLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(chooseNeighborsLabel);
        checkBoxPanel = new JPanel(new GridLayout(0, 3));
        add(new JScrollPane(checkBoxPanel));
        
        /* Setup Save button */
        JPanel bottomPanel = new JPanel(new FlowLayout());
        saveTerritoryButton = new JButton(SAVE_BUTTON_LABEL);
        removeTerritoryButton = new JButton(REMOVE_BUTTON_LABEL);
        removeTerritoryButton.setEnabled(false);
        removeTerritoryButton.setForeground(Color.RED);
        bottomPanel.add(removeTerritoryButton);
        bottomPanel.add(saveTerritoryButton);
        add(bottomPanel);
    }
    
    /**
     * Sets the all components enable.
     *
     * @param isEnable the new all components enable
     */
    private void setAllComponentsEnable(boolean isEnable) {
        territoriesListDropdown.setEnabled(isEnable);
        territoryNameText.setEnabled(isEnable);
        saveTerritoryButton.setEnabled(isEnable);
    }
    
    /**
     * Gets the save button label.
     *
     * @return the save button label
     */
    public static String getSaveButtonLabel() {
        return SAVE_BUTTON_LABEL;
    }
    
    /**
     * Gets the adds the button label.
     *
     * @return the adds the button label
     */
    public static String getAddButtonLabel() {
        return ADD_BUTTON_LABEL;
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        /* When the GameMap object is updated */
        if (o instanceof MapEditorModel) {
            territoriesListDropdown.setModel(((MapEditorModel) o).getTerritoriesDropdownModel());
            territoriesListDropdown.setSelectedIndex(0);
            setAllComponentsEnable(true);
        }
    }
    
    /**
     * Adds the territory list dropdown listener.
     *
     * @param listenerForTerritoryListDropdown the listener for territory list dropdown
     */
    public void addTerritoryListDropdownListener(ActionListener listenerForTerritoryListDropdown) {
        territoriesListDropdown.addActionListener(listenerForTerritoryListDropdown);
    }
    
    /**
     * Adds the save territory button listener.
     *
     * @param listenerForSaveTerritoryButton the listener for save territory button
     */
    public void addSaveTerritoryButtonListener(ActionListener listenerForSaveTerritoryButton) {
        saveTerritoryButton.addActionListener(listenerForSaveTerritoryButton);
    }
    
    /**
     * Adds the remove territory button listener.
     *
     * @param listenerForRemoveTerritoryButton the listener for remove territory button
     */
    public void addRemoveTerritoryButtonListener(ActionListener listenerForRemoveTerritoryButton) {
        removeTerritoryButton.addActionListener(listenerForRemoveTerritoryButton);
    }
    
    /**
     * Gets the territory name text.
     *
     * @return the territory name text
     */
    public JTextField getTerritoryNameText() {
        return territoryNameText;
    }
    
    /**
     * Gets the check box panel.
     *
     * @return the check box panel
     */
    public JPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }
    
    /**
     * Gets the radio buttons panel.
     *
     * @return the radio buttons panel
     */
    public JPanel getRadioButtonsPanel() {
        return radioButtonsPanel;
    }
    
    /**
     * Gets the removes the territory button.
     *
     * @return the removes the territory button
     */
    public JButton getRemoveTerritoryButton() {
        return removeTerritoryButton;
    }
    // endregion
    
    // region Private methods
    
    /**
     * Gets the save territory button.
     *
     * @return the save territory button
     */
    public JButton getSaveTerritoryButton() {
        return saveTerritoryButton;
    }
    
    /**
     * Gets the territories list dropdown.
     *
     * @return the territories list dropdown
     */
    public JComboBox<String> getTerritoriesListDropdown() {
        return territoriesListDropdown;
    }
    // endregion
}
