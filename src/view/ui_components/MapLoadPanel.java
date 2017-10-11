package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class MapLoadPanel extends JPanel implements Observer {
    private static final String LABEL_CHOOSE_MAP = "Choose a Map to edit";
    private static final String LOAD_BUTTON_LABEL = "Load Map";
    private static final String NEW_MAP_BUTTON_LABEL = "Create a new Map";
    private static final String LABEL_OR = " or ";
    
    private JLabel chooseMapLabel;
    private JButton newMapButton;
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    
    public MapLoadPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(true);
    }
    
    
    /* MVC & Observer pattern methods */
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
    
    public void addNewMapButtonListener(ActionListener listenerForNewMapButton) {
        newMapButton.addActionListener(listenerForNewMapButton);
    }
    
    /* Getters & Setters */
    
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    
    
    /* Private methods */
    private void setupComponents() {
        /* Setup grid panel */
        JPanel load_map_panel = new JPanel(new FlowLayout());
        
        /* left panel */
        JPanel left_panel = new JPanel();
        newMapButton = new JButton(NEW_MAP_BUTTON_LABEL);
        left_panel.add(newMapButton);
    
        
        /* right panel */
        JPanel right_panel = new JPanel();
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        chooseMapLabel = new JLabel(LABEL_CHOOSE_MAP);
        chooseMapLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        chooseMapDropdown = new JComboBox<>();
        chooseMapDropdown.setAlignmentX(CENTER_ALIGNMENT);
        
        loadMapButton = new JButton(LOAD_BUTTON_LABEL);
        loadMapButton.setAlignmentX(CENTER_ALIGNMENT);
        
        right_panel.add(chooseMapLabel);
        right_panel.add(chooseMapDropdown);
        right_panel.add(loadMapButton);
        
        /* add the 3 columns */
        load_map_panel.add(left_panel);
        load_map_panel.add(new JLabel(LABEL_OR));
        load_map_panel.add(right_panel);
        
        add(load_map_panel);
    }
    
    private void setAllComponentsEnable(boolean isEnable) {
        chooseMapDropdown.setEnabled(isEnable);
        loadMapButton.setEnabled(isEnable);
        newMapButton.setEnabled(isEnable);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        /* When the dropdown for maps is update */
        if (o instanceof MapEditorModel) {
            chooseMapDropdown.setModel(((MapEditorModel) o).getMapDropdownModel());
            //chooseMapDropdown.setSelectedIndex(0);
            setAllComponentsEnable(true);
        }
    }
    
    
}
