package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class EditTerritoryPanel extends JPanel implements Observer {
    private static final String ADD_BUTTON_LABEL = "Add Territory";
    private static final String SAVE_BUTTON_LABEL = "Save Territory";
    private static final String REMOVE_BUTTON_LABEL = "Remove Territory";
    private static final String CHOOSE_TERRITORY = "Choose Territory";
    private static final String TERRITORY_NAME_LABEL = "Territory Name";
    private static final String CHOOSE_NEIGHBOURS_LABEL = "Choose Neighbours";
    private static final String CHOOSE_CONTINENT_LABEL = "Choose Continent";
    private static final int LAYOUT_ROWS = 2;
    private static final int LAYOUT_COLS = 2;
    
    private JComboBox<String> territoriesListDropdown;
    private JTextField territoryNameText;
    private JButton saveTerritoryButton;
    private JButton removeTerritoryButton;
    private JPanel radioButtonsPanel;
    private JPanel checkBoxPanel;
    
    public EditTerritoryPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(false);
    }
    
    /* MVC & Observer pattern methods */
    public void addTerritoryListDropdownListener(ActionListener listenerForTerritoryListDropdown) {
        territoriesListDropdown.addActionListener(listenerForTerritoryListDropdown);
    }
    
    public void addSaveTerritoryButtonListener(ActionListener listenerForSaveTerritoryButton) {
        saveTerritoryButton.addActionListener(listenerForSaveTerritoryButton);
    }
    
    public void addRemoveTerritoryButtonListener(ActionListener listenerForRemoveTerritoryButton) {
        removeTerritoryButton.addActionListener(listenerForRemoveTerritoryButton);
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
    
    public JButton getRemoveTerritoryButton() {
        return removeTerritoryButton;
    }
    
    public JButton getSaveTerritoryButton() {
        return saveTerritoryButton;
    }
    
    public static String getSaveButtonLabel() {
        return SAVE_BUTTON_LABEL;
    }
    
    public static String getAddButtonLabel() {
        return ADD_BUTTON_LABEL;
    }
    
    public JComboBox<String> getTerritoriesListDropdown() {
        return territoriesListDropdown;
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
        JPanel bottomPanel = new JPanel(new FlowLayout());
        saveTerritoryButton = new JButton(SAVE_BUTTON_LABEL);
        removeTerritoryButton = new JButton(REMOVE_BUTTON_LABEL);
        removeTerritoryButton.setEnabled(false);
        removeTerritoryButton.setForeground(Color.RED);
        bottomPanel.add(removeTerritoryButton);
        bottomPanel.add(saveTerritoryButton);
        add(bottomPanel);
    }
    
    private void setAllComponentsEnable(boolean isEnable) {
        territoriesListDropdown.setEnabled(isEnable);
        territoryNameText.setEnabled(isEnable);
        saveTerritoryButton.setEnabled(isEnable);
    }
    
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
