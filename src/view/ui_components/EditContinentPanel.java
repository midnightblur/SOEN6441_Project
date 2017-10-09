package view.ui_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditContinentPanel extends JPanel {
    private static final String SAVE_BUTTON_LABEL = "Save Continent";
    private static final String CHOOSE_CONTINENT = "Choose Continent";
    private static final String CONTIENT_NAME_LABEL = "Continent Name";
    private static final String CONTINENT_CONTROL_VALUE_LABEL = "Control Value";
    private static final String CONTINENT_TERRITORIES_LABEL = "Choose Territories";
    private static final int LAYOUT_ROWS = 3;
    private static final int LAYOUT_COLS = 2;
    
    private JComboBox<String> continentsListDropdown;
    private JTextField continentNameText;
    private JTextField contientControlValueText;
    private JCheckBox continentTerritoriesListRadio;
    private JButton saveButton;
    
    public EditContinentPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
    }
    
    /* MVC & Observer pattern methods */
    public void addContinentsListDropdownListener(ActionListener listenerForContinentsListDropdown) {
        continentsListDropdown.addActionListener(listenerForContinentsListDropdown);
    }
    
    public void addSaveButtonListener(ActionListener listenerForSaveButton) {
        saveButton.addActionListener(listenerForSaveButton);
    }
    
    /* Private methods */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel gridPanel = new JPanel(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
        JLabel chooseContientLabel = new JLabel(CHOOSE_CONTINENT);
        chooseContientLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridPanel.add(chooseContientLabel);
        continentsListDropdown = new JComboBox<>();
        gridPanel.add(continentsListDropdown);
        
        JLabel continentNameLabel = new JLabel(CONTIENT_NAME_LABEL);
        continentNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridPanel.add(continentNameLabel);
        continentNameText = new JTextField();
        gridPanel.add(continentNameText);
        
        JLabel contientControlValueLabel = new JLabel(CONTINENT_CONTROL_VALUE_LABEL);
        contientControlValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridPanel.add(contientControlValueLabel);
        contientControlValueText = new JTextField();
        gridPanel.add(contientControlValueText);
        
        add(gridPanel);
        
        /* Setup Territories Checkbox List */
        JLabel continentTerritoriesLabel = new JLabel(CONTINENT_TERRITORIES_LABEL);
        continentTerritoriesLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(continentTerritoriesLabel);
        continentTerritoriesListRadio = new JCheckBox();
        continentTerritoriesListRadio.setAlignmentX(CENTER_ALIGNMENT);
        add(continentTerritoriesListRadio);
        
        /* Setup Save button */
        saveButton = new JButton(SAVE_BUTTON_LABEL);
        saveButton.setAlignmentX(CENTER_ALIGNMENT);
        add(saveButton);
    }
}
