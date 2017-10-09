package view.ui_components;

import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditMapControlPanel extends JPanel {
    private static final String NEW_BUTTON_LABEL = "New Map";
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    private static final String BACK_BUTTON_LABEL = "Back";
    
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    private JButton newMapButton;
    private JButton saveMapButton;
    private JComboBox addDropdown;
    private JComboBox editDropdown;
    private JButton backButton;
    
    /* Constructors */
    public EditMapControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /* Setup map selection area */
        JPanel panel_1 = new JPanel(new FlowLayout());
        JLabel chooseMapLabel = new JLabel(Config.UI_LABEL_CHOOSE_MAP);
        chooseMapDropdown = new JComboBox<>();
        loadMapButton = new JButton(Config.UI_BUTTON_LOAD_MAP);
        panel_1.add(chooseMapLabel);
        panel_1.add(chooseMapDropdown);
        panel_1.add(loadMapButton);
        add(panel_1);
        
        /* Setup buttons */
        JPanel panel_2 = new JPanel(new FlowLayout());
        newMapButton = new JButton(NEW_BUTTON_LABEL);
        panel_2.add(newMapButton);
        add(panel_2);
        
        /* Editing buttons */
        // TODO: set their visibility (in controller) when a map is loaded
        JPanel panel_3 = new JPanel(new FlowLayout());
        addDropdown = new JComboBox<>();
        editDropdown = new JComboBox<>();
        panel_3.add(addDropdown);
        panel_3.add(editDropdown);
        //panel_3.setVisible(false);
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
    
    public void addAddDropdownListener(ActionListener listenerForAddDropdown) {
        addDropdown.addActionListener(listenerForAddDropdown);
    }
    
    public void addEditDropdownListener(ActionListener listenerForEditDropdown) {
        editDropdown.addActionListener(listenerForEditDropdown);
    }
    
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    public void addSaveMapButtonListener(ActionListener listenerSaveMapButton) {
        saveMapButton.addActionListener(listenerSaveMapButton);
    }
    
    /* Public methods */
}
