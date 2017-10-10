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
    private JButton saveTerritoryButton;
    private JPanel radioButtonsPanel;
    private JPanel checkBoxPanel;
    
    public EditTerritoryPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
    }
    
    /* MVC & Observer pattern methods */
    public void addTerritoryListDropdownListener(ActionListener listenerForTerritoryListDropdown) {
        territoriesListDropdown.addActionListener(listenerForTerritoryListDropdown);
    }
    
    public void addSaveTerritoryButtonListener(ActionListener listenerForSaveTerritoryButton) {
        saveTerritoryButton.addActionListener(listenerForSaveTerritoryButton);
    }
    
    /* Getters & Setters */
    public JTextField getTerritoryNameText() {
        return territoryNameText;
    }
    
    public JPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }
    
    public JPanel getRadioButtonsPanel() {
        return radioButtonsPanel;
    }
    
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
        JLabel chooseNeighboursLabel = new JLabel(CHOOSE_NEIGHBOURS_LABEL);
        chooseNeighboursLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(chooseNeighboursLabel);
        checkBoxPanel = new JPanel(new GridLayout(0, 3));
        add(new JScrollPane(checkBoxPanel));
        
        /* Setup Save button */
        saveTerritoryButton = new JButton(SAVE_BUTTON_LABEL);
        saveTerritoryButton.setAlignmentX(CENTER_ALIGNMENT);
        add(saveTerritoryButton);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        /* When the GameMap object is updated */
        if (o instanceof MapEditorModel) {
            territoriesListDropdown.setModel(((MapEditorModel) o).getTerritoriesDropdownModel());
            territoriesListDropdown.setSelectedIndex(0);
        }
    }
}
