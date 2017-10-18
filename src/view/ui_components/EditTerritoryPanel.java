/**  
 * @file  EditTerritoryPanel.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class EditTerritoryPanel.
 */
public class EditTerritoryPanel extends JPanel implements Observer {
    
    /** The Constant ADD_BUTTON_LABEL. */
    private static final String ADD_BUTTON_LABEL = "Add Territory";
    
    /** The Constant SAVE_BUTTON_LABEL. */
    private static final String SAVE_BUTTON_LABEL = "Save Territory";
    
    /** The Constant REMOVE_BUTTON_LABEL. */
    private static final String REMOVE_BUTTON_LABEL = "Remove Territory";
    
    /** The Constant CHOOSE_TERRITORY. */
    private static final String CHOOSE_TERRITORY = "Choose Territory";
    
    /** The Constant TERRITORY_NAME_LABEL. */
    private static final String TERRITORY_NAME_LABEL = "Territory Name";
    
    /** The Constant CHOOSE_NEIGHBORS_LABEL. */
    private static final String CHOOSE_NEIGHBORS_LABEL = "Choose Neighbors";
    
    /** The Constant CHOOSE_CONTINENT_LABEL. */
    private static final String CHOOSE_CONTINENT_LABEL = "Choose Continent";
    
    /** The Constant LAYOUT_ROWS. */
    private static final int LAYOUT_ROWS = 2;
    
    /** The Constant LAYOUT_COLS. */
    private static final int LAYOUT_COLS = 2;
    
    /** The territories list dropdown. */
    private JComboBox<String> territoriesListDropdown;
    
    /** The territory name text. */
    private JTextField territoryNameText;
    
    /** The save territory button. */
    private JButton saveTerritoryButton;
    
    /** The remove territory button. */
    private JButton removeTerritoryButton;
    
    /** The radio buttons panel. */
    private JPanel radioButtonsPanel;
    
    /** The check box panel. */
    private JPanel checkBoxPanel;
    
    /**
     * Instantiates a new edits the territory panel.
     */
    public EditTerritoryPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(false);
    }
    
    /**
     * Adds the territory list dropdown listener.
     *
     * @param listenerForTerritoryListDropdown the listener for territory list dropdown
     */
    /* MVC & Observer pattern methods */
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
    /* Getters & Setters */
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
    
    /**
     * Gets the save territory button.
     *
     * @return the save territory button
     */
    public JButton getSaveTerritoryButton() {
        return saveTerritoryButton;
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
    
    /**
     * Gets the territories list dropdown.
     *
     * @return the territories list dropdown
     */
    public JComboBox<String> getTerritoriesListDropdown() {
        return territoriesListDropdown;
    }
    
    /**
     * Setup components.
     */
    /* Private methods */
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
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
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
}
