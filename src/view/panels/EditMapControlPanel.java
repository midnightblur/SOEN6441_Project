package view.panels;

import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditMapControlPanel extends JPanel {
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    
    /* Constructors */
    public EditMapControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
        /* Setup map selection area */
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel chooseMapLabel = new JLabel(Config.UI_LABEL_CHOOSE_MAP);
        chooseMapDropdown = new JComboBox<>();
        loadMapButton = new JButton(Config.UI_BUTTON_LOAD_MAP);
        topPanel.add(chooseMapLabel);
        topPanel.add(chooseMapDropdown);
        topPanel.add(loadMapButton);

        add(topPanel);
    }
    
    /* Getters & Setters */
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    
    /* MVC & Observer pattern methods */
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
    
    /* Public methods */
}
