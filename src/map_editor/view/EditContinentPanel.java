/* 
 * Risk Game Team 2
 * EditContinentPanel.java
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
 * The Class EditContinentPanel provides the user with an interface to add or edit a continent.
 *
 * It is a panel that is loaded on the right of the main frame split.
 */
public class EditContinentPanel extends JPanel implements Observer {
    // region Attributes delcaration
    /** The Constant ADD_BUTTON_LABEL. */
    private static final String ADD_BUTTON_LABEL = "Add Continent";
    
    /** The Constant SAVE_BUTTON_LABEL. */
    private static final String SAVE_BUTTON_LABEL = "Save Continent";
    
    /** The Constant REMOVE_BUTTON_LABEL. */
    private static final String REMOVE_BUTTON_LABEL = "Remove Continent";
    
    /** The Constant CHOOSE_CONTINENT. */
    private static final String CHOOSE_CONTINENT = "Choose Continent";
    
    /** The Constant CONTIENT_NAME_LABEL. */
    private static final String CONTIENT_NAME_LABEL = "Continent Name";
    
    /** The Constant CONTINENT_CONTROL_VALUE_LABEL. */
    private static final String CONTINENT_CONTROL_VALUE_LABEL = "Control Value";
    
    /** The Constant CONTINENT_TERRITORIES_LABEL. */
    private static final String CONTINENT_TERRITORIES_LABEL = "Choose Territories";
    
    /** The Constant LAYOUT_ROWS. */
    private static final int LAYOUT_ROWS = 3;
    
    /** The Constant LAYOUT_COLS. */
    private static final int LAYOUT_COLS = 2;
    
    /** The continents list dropdown. */
    private JComboBox<String> continentsListDropdown;
    
    /** The continent name text. */
    private JTextField continentNameText;
    
    /** The contient control value text. */
    private JTextField contientControlValueText;
    
    /** The check box panel. */
    private JPanel checkBoxPanel;
    
    /** The save continent button. */
    private JButton saveContinentButton;
    
    /** The remove continent button. */
    private JButton removeContinentButton;
    // endregion
    
    // region Constructors
    /**
     * Instantiates a new edits the continent panel.
     */
    public EditContinentPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(false);
    }
    // endregion
    
    // region Private methods
    /**
     * Setup components.
     */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel gridPanel = new JPanel(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
        JLabel chooseContinentLabel = new JLabel(CHOOSE_CONTINENT);
        chooseContinentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(chooseContinentLabel);
        continentsListDropdown = new JComboBox<>();
        gridPanel.add(continentsListDropdown);
        
        JLabel continentNameLabel = new JLabel(CONTIENT_NAME_LABEL);
        continentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(continentNameLabel);
        continentNameText = new JTextField();
        gridPanel.add(continentNameText);
        
        JLabel contientControlValueLabel = new JLabel(CONTINENT_CONTROL_VALUE_LABEL);
        contientControlValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(contientControlValueLabel);
        contientControlValueText = new JTextField();
        gridPanel.add(contientControlValueText);
        
        add(gridPanel);
        
        /* Setup Territories Checkbox List */
        JLabel continentTerritoriesLabel = new JLabel(CONTINENT_TERRITORIES_LABEL);
        continentTerritoriesLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(continentTerritoriesLabel);
        checkBoxPanel = new JPanel(new GridLayout(0, 3));
        add(new JScrollPane(checkBoxPanel));
        
        /* Setup Save button */
        JPanel bottomPanel = new JPanel(new FlowLayout());
        saveContinentButton = new JButton(SAVE_BUTTON_LABEL);
        removeContinentButton = new JButton(REMOVE_BUTTON_LABEL);
        removeContinentButton.setEnabled(false);
        removeContinentButton.setForeground(Color.RED);
        bottomPanel.add(removeContinentButton);
        bottomPanel.add(saveContinentButton);
        add(bottomPanel);
    }
    
    /**
     * Sets the all components enable.
     *
     * @param isEnable the new all components enable
     */
    private void setAllComponentsEnable(boolean isEnable) {
        continentsListDropdown.setEnabled(isEnable);
        continentNameText.setEnabled(isEnable);
        contientControlValueText.setEnabled(isEnable);
        saveContinentButton.setEnabled(isEnable);
    }
    // endregion
    
    // region Getters & Setters
    /**
     * Gets the continent name text.
     *
     * @return the continent name text
     */
    public JTextField getContinentNameText() {
        return continentNameText;
    }
    
    /**
     * Gets the contient control value text.
     *
     * @return the contient control value text
     */
    public JTextField getContientControlValueText() {
        return contientControlValueText;
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
     * Gets the removes the continent button.
     *
     * @return the removes the continent button
     */
    public JButton getRemoveContinentButton() {
        return removeContinentButton;
    }
    
    /**
     * Gets the save continent button.
     *
     * @return the save continent button
     */
    public JButton getSaveContinentButton() {
        return saveContinentButton;
    }
    
    /**
     * Gets the continents list dropdown.
     *
     * @return the continents list dropdown
     */
    public JComboBox<String> getContinentsListDropdown() {
        return continentsListDropdown;
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
    
    // region MVC & Observer pattern
    /**
     * Adds the continents list dropdown listener.
     *
     * @param listenerForContinentsListDropdown the listener for continents list dropdown
     */
    public void addContinentsListDropdownListener(ActionListener listenerForContinentsListDropdown) {
        continentsListDropdown.addActionListener(listenerForContinentsListDropdown);
    }
    
    /**
     * Adds the save continent button listener.
     *
     * @param listenerForSaveContinentButton the listener for save continent button
     */
    public void addSaveContinentButtonListener(ActionListener listenerForSaveContinentButton) {
        saveContinentButton.addActionListener(listenerForSaveContinentButton);
    }
    
    /**
     * Adds the remove continent button listener.
     *
     * @param listenerForRemoveContinentButton the listener for remove continent button
     */
    public void addRemoveContinentButtonListener(ActionListener listenerForRemoveContinentButton) {
        removeContinentButton.addActionListener(listenerForRemoveContinentButton);
    }
    
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        /* When the GameMap object is updated */
        if (o instanceof MapEditorModel) {
            continentsListDropdown.setModel(((MapEditorModel) o).getContinentsDropdownModel());
            continentsListDropdown.setSelectedIndex(0);
            setAllComponentsEnable(true);
        }
    }
    // endregion
}
