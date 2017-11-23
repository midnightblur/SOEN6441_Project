/* 
 * Risk Game Team 2
 * MapLoadPanel.java
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
 * MapLoadPanel is a sub-panel that allows the user to load a map or make a new map.
 * It is used as part of the edit map control panel
 *
 * @author Team 2
 * @version 1.0
 * @see map_editor.view.EditMapPanel
 */
public class MapLoadPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String LABEL_CHOOSE_MAP = "Choose a Map to edit";
    private static final String LOAD_BUTTON_LABEL = "Load Map";
    private static final String NEW_MAP_BUTTON_LABEL = "Create a new Map";
    private static final String LABEL_OR = " or ";
    private JButton newMapButton;
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map load panel.
     */
    public MapLoadPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        setAllComponentsEnable(true);
    }
    // endregion
    
    // region Private methods
    
    /**
     * Setup components.
     */
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
        /* The choose map label. */
        JLabel chooseMapLabel = new JLabel(LABEL_CHOOSE_MAP);
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
    
    /**
     * Sets the all components enable.
     *
     * @param isEnable  makes all new  components enable
     */
    private void setAllComponentsEnable(boolean isEnable) {
        chooseMapDropdown.setEnabled(isEnable);
        loadMapButton.setEnabled(isEnable);
        newMapButton.setEnabled(isEnable);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the choose map dropdown.
     *
     * @return the choose map dropdown
     */
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the load map button listener.
     *
     * @param listenerForLoadMapButton the listener for load map button
     */
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
    
    /**
     * Adds the new map button listener.
     *
     * @param listenerForNewMapButton the listener for new map button
     */
    public void addNewMapButtonListener(ActionListener listenerForNewMapButton) {
        newMapButton.addActionListener(listenerForNewMapButton);
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
        /* When the dropdown for maps is updated */
        if (o instanceof MapEditorModel) {
            chooseMapDropdown.setModel(((MapEditorModel) o).getMapDropdownModel());
            setAllComponentsEnable(true);
        }
    }
    // region
    
}
