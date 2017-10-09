package view.ui_components;

import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditMapControlPanel extends JPanel {
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    private static final String BACK_BUTTON_LABEL = "Back";
    
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    private JButton saveMapButton;
    private JButton backButton;
    
    /* Constructors */
    public EditMapControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        /* Setup top buttons */
        JPanel panel_1 = new JPanel(new FlowLayout());
        backButton = new JButton(BACK_BUTTON_LABEL);
        panel_1.add(backButton);
        saveMapButton = new JButton(SAVE_BUTTON_LABEL);
        panel_1.add(saveMapButton);
        add(panel_1);
    
        /* Setup map selection area */
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new FlowLayout());
        JLabel chooseMapLabel = new JLabel(Config.UI_LABEL_CHOOSE_MAP);
        chooseMapDropdown = new JComboBox<>();
        loadMapButton = new JButton(Config.UI_BUTTON_LOAD_MAP);
        panel_2.add(chooseMapLabel);
        panel_2.add(chooseMapDropdown);
        panel_2.add(loadMapButton);
        add(panel_2);
    }
    
    /* Getters & Setters */
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    
    /* MVC & Observer pattern methods */
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
    
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    public void addSaveMapButtonListener(ActionListener listenerSaveMapButton) {
        saveMapButton.addActionListener(listenerSaveMapButton);
    }
    
    /* Public methods */
}
