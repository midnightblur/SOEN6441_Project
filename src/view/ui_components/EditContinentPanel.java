package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class EditContinentPanel extends JPanel implements Observer {
    private static final String SAVE_BUTTON_LABEL = "Save Continent";
    private static final String REMOVE_BUTTON_LABEL = "Remove Continent";
    private static final String CHOOSE_CONTINENT = "Choose Continent";
    private static final String CONTIENT_NAME_LABEL = "Continent Name";
    private static final String CONTINENT_CONTROL_VALUE_LABEL = "Control Value";
    private static final String CONTINENT_TERRITORIES_LABEL = "Choose Territories";
    private static final int LAYOUT_ROWS = 3;
    private static final int LAYOUT_COLS = 2;
    
    private JComboBox<String> continentsListDropdown;
    private JTextField continentNameText;
    private JTextField contientControlValueText;
    private JPanel checkBoxPanel;
    private JButton saveContinentButton;
    private JButton removeContinentButton;
    
    public EditContinentPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(false);
    }
    
    /* MVC & Observer pattern methods */
    public void addContinentsListDropdownListener(ActionListener listenerForContinentsListDropdown) {
        continentsListDropdown.addActionListener(listenerForContinentsListDropdown);
    }
    
    public void addSaveContinentButtonListener(ActionListener listenerForSaveContinentButton) {
        saveContinentButton.addActionListener(listenerForSaveContinentButton);
    }
    
    public void addRemoveContinentButtonListener(ActionListener listenerForRemoveContinentButton) {
        removeContinentButton.addActionListener(listenerForRemoveContinentButton);
    }
    
    /* Getters & Setters */
    public JTextField getContinentNameText() {
        return continentNameText;
    }
    
    public JTextField getContientControlValueText() {
        return contientControlValueText;
    }
    
    public JPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }
    
    public JButton getRemoveContinentButton() {
        return removeContinentButton;
    }
    
    /* Private methods */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel gridPanel = new JPanel(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
        JLabel chooseContientLabel = new JLabel(CHOOSE_CONTINENT);
        chooseContientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridPanel.add(chooseContientLabel);
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
    
    private void setAllComponentsEnable(boolean isEnable) {
        continentsListDropdown.setEnabled(isEnable);
        continentNameText.setEnabled(isEnable);
        contientControlValueText.setEnabled(isEnable);
        saveContinentButton.setEnabled(isEnable);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        /* When the GameMap object is updated */
        if (o instanceof MapEditorModel) {
            continentsListDropdown.setModel(((MapEditorModel) o).getContinentsDropdownModel());
            continentsListDropdown.setSelectedIndex(0);
            setAllComponentsEnable(true);
        }
    }
}
