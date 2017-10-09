package view.ui_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditMapControlPanel extends JPanel {
    private static final int MAP_SELECTION_LAYOUT_ROWS = 1;
    private static final int MAP_SELECTION_LAYOUT_COLS = 2;
    private static final String NEW_MAP_BUTTON_LABEL = "Create a new Map";
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    private static final String BACK_BUTTON_LABEL = "Back";
    private static final String LOAD_BUTTON_LABEL = "Load Map";
    private static final String LABEL_OR = "or";
    private static final String LABEL_CHOOSE_MAP = "Choose a Map to edit";
    
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    private JButton newMapButton;
    private JButton saveMapButton;
    private JComboBox addDropdown;
    private JComboBox editDropdown;
    private JButton backButton;
    
    /* Constructors */
    public EditMapControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        /* Setup map selection area */
        JPanel panel_1 = new JPanel(new FlowLayout());
        JPanel panel_1_left = new JPanel();
        newMapButton = new JButton(NEW_MAP_BUTTON_LABEL);
        panel_1_left.add(newMapButton);
        panel_1.add(panel_1_left);
    
        panel_1.add(new JLabel(LABEL_OR));
        
        JPanel panel_1_right = new JPanel();
        panel_1_right.setLayout(new BoxLayout(panel_1_right, BoxLayout.Y_AXIS));
        JLabel chooseMapLabel = new JLabel(LABEL_CHOOSE_MAP);
        chooseMapDropdown = new JComboBox<>();
        loadMapButton = new JButton(LOAD_BUTTON_LABEL);
        chooseMapLabel.setAlignmentX(CENTER_ALIGNMENT);
        chooseMapDropdown.setAlignmentX(CENTER_ALIGNMENT);
        loadMapButton.setAlignmentX(CENTER_ALIGNMENT);
        panel_1_right.add(chooseMapLabel);
        panel_1_right.add(chooseMapDropdown);
        panel_1_right.add(loadMapButton);
        panel_1.add(panel_1_right);
        
        add(panel_1);
        
        /* Editing buttons */
        // TODO: set their visibility (in controller) when a map is loaded
        JPanel panel_3 = new JPanel(new FlowLayout());
        addDropdown = new JComboBox<>();
        editDropdown = new JComboBox<>();
        panel_3.add(addDropdown);
        panel_3.add(editDropdown);
        add(panel_3);
        
        /* Navigation buttons */
        JPanel panel_4 = new JPanel(new FlowLayout());
        backButton = new JButton(BACK_BUTTON_LABEL);
        saveMapButton = new JButton(SAVE_BUTTON_LABEL);
        panel_4.add(backButton);
        panel_4.add(saveMapButton);
        add(panel_4);
        
    }
    
    /* Getters & Setters */
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    
    public JComboBox<String> getAddDropdown() {
        return addDropdown;
    }
    
    public JComboBox<String> getEditDropdown() {
        return editDropdown;
    }
    
    /* MVC & Observer pattern methods */
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
    
    public void addNewMapButtonListener(ActionListener listenerForNewMapButton) {
        newMapButton.addActionListener(listenerForNewMapButton);
    }
    
    public void addAddDropdownListener(ActionListener listenerForAddDropdown) {
        addDropdown.addActionListener(listenerForAddDropdown);
    }
    
    public void addEditDropdownListener(ActionListener listenerForEditDropdown) {
        editDropdown.addActionListener(listenerForEditDropdown);
    }
    
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    public void addSaveMapButtonListener(ActionListener listenerForSaveMapButton) {
        saveMapButton.addActionListener(listenerForSaveMapButton);
    }
    
    /* Public methods */
    public void setEditElementsEnable(boolean isEnable) {
        addDropdown = new JComboBox<>();
        editDropdown = new JComboBox<>();
    }
}
