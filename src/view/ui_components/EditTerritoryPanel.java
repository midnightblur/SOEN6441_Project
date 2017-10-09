package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class EditTerritoryPanel extends JPanel implements Observer {
    private static final String SAVE_BUTTON_LABEL = "Save Territory";
    private static final String CHOOSE_TERRITORY = "Choose Territory";
    private static final String TERRITORY_NAME_LABEL = "Territory Name";
    private static final String CHOOSE_NEIGHBOURS_LABEL = "Choose Neighbours";
    private static final String CHOOSE_CONTINENT_LABEL = "Choose Continent";
    private static final int LAYOUT_ROWS = 2;
    private static final int LAYOUT_COLS = 2;
    
    private JComboBox<String> territoriesListDropdown;
    private JTextField territoryNameText;
    private JRadioButton continentsRadio;
    private JCheckBox neighboursCheckbox;
    private JButton saveButton;
    
    public EditTerritoryPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
    }
    
    /* MVC & Observer pattern methods */
    public void addContinentsListDropdownListener(ActionListener listenerForContinentsListDropdown) {
        territoriesListDropdown.addActionListener(listenerForContinentsListDropdown);
    }
    
    public void addSaveButtonListener(ActionListener listenerForSaveButton) {
        saveButton.addActionListener(listenerForSaveButton);
    }
    
    /* Getters & Setters */
    public JComboBox<String> getTerritoriesListDropdown() {
        return territoriesListDropdown;
    }
    
    public JTextField getTerritoryNameText() {
        return territoryNameText;
    }
    
    public JCheckBox getNeighboursCheckbox() {
        return neighboursCheckbox;
    }
    
    /* Private methods */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel gridPanel = new JPanel(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
        JLabel chooseTerritoryLabel = new JLabel(CHOOSE_TERRITORY);
        chooseTerritoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridPanel.add(chooseTerritoryLabel);
        territoriesListDropdown = new JComboBox<>();
        gridPanel.add(territoriesListDropdown);
        
        JLabel territoryNameLabel = new JLabel(TERRITORY_NAME_LABEL);
        territoryNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridPanel.add(territoryNameLabel);
        territoryNameText = new JTextField();
        gridPanel.add(territoryNameText);
        
        add(gridPanel);
        
        /* Setup Continents Radio button */
        JLabel chooseContinentLabel = new JLabel(CHOOSE_CONTINENT_LABEL);
        chooseContinentLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(chooseContinentLabel);
        continentsRadio = new JRadioButton();
        continentsRadio.setAlignmentX(CENTER_ALIGNMENT);
        add(continentsRadio);
        
        /* Setup Territories Checkbox List */
        JLabel choosingNeighboursLabel = new JLabel(CHOOSE_NEIGHBOURS_LABEL);
        choosingNeighboursLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(choosingNeighboursLabel);
        neighboursCheckbox = new JCheckBox();
        neighboursCheckbox.setAlignmentX(CENTER_ALIGNMENT);
        add(neighboursCheckbox);
        
        /* Setup Save button */
        saveButton = new JButton(SAVE_BUTTON_LABEL);
        saveButton.setAlignmentX(CENTER_ALIGNMENT);
        add(saveButton);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        /* When the GameMap object is updated */
        if (o instanceof MapEditorModel) {
            territoriesListDropdown.setModel(((MapEditorModel) o).getContinentsDropdownModel());
            territoriesListDropdown.setSelectedIndex(0);
        }
    }
}
