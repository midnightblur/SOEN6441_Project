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
 *
 * @author Team 2
 * @version 1.0
 */
public class EditContinentPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ADD_BUTTON_LABEL = "Add Continent";
    private static final String SAVE_BUTTON_LABEL = "Save Continent";
    private static final String REMOVE_BUTTON_LABEL = "Remove Continent";
    private static final String CHOOSE_CONTINENT = "Choose Continent";
    private static final String CONTINENT_NAME_LABEL = "Continent Name";
    private static final String CONTINENT_CONTROL_VALUE_LABEL = "Control Value";
    private static final String CONTINENT_TERRITORIES_LABEL = "Choose Territories";
    private static final int LAYOUT_ROWS = 3;
    private static final int LAYOUT_COLS = 2;
    private JComboBox<String> continentsListDropdown;
    private JTextField continentNameText;
    private JTextField continentControlValueText;
    private JPanel checkBoxPanel;
    private JButton saveContinentButton;
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
     * Setup UI components for the Edit Continent Panel.
     */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel gridPanel = new JPanel(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
        JLabel chooseContinentLabel = new JLabel(CHOOSE_CONTINENT);
        chooseContinentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(chooseContinentLabel);
        continentsListDropdown = new JComboBox<>();
        gridPanel.add(continentsListDropdown);
        
        /* Setup continent name labels */
        JLabel continentNameLabel = new JLabel(CONTINENT_NAME_LABEL);
        continentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(continentNameLabel);
        continentNameText = new JTextField();
        gridPanel.add(continentNameText);
        
        /* Setup continent control value labels */
        JLabel continentControlValueLabel = new JLabel(CONTINENT_CONTROL_VALUE_LABEL);
        continentControlValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(continentControlValueLabel);
        continentControlValueText = new JTextField();
        gridPanel.add(continentControlValueText);
        
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
        continentControlValueText.setEnabled(isEnable);
        saveContinentButton.setEnabled(isEnable);
    }
    // endregion
    
    // region Getters & Setters
    
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
     * Gets the continent name text.
     *
     * @return the continent name text
     */
    public JTextField getContinentNameText() {
        return continentNameText;
    }
    
    /**
     * Gets the continent control value text.
     *
     * @return the continent control value text
     */
    public JTextField getContinentControlValueText() {
        return continentControlValueText;
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
            continentsListDropdown.setModel(((MapEditorModel) o).getContinentsDropdownModel());
            continentsListDropdown.setSelectedIndex(0);
            setAllComponentsEnable(true);
        }
    }
    // endregion
}
