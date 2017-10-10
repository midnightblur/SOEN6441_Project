package view.ui_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GamePlayControlPanel extends JPanel {
    private static final String BACK_BUTTON_LABEL = "Back";
    
    private JButton backButton;
    
    /* Constructors */
    public GamePlayControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        /* Navigation buttons */
        JPanel panel_1 = new JPanel(new FlowLayout());
        backButton = new JButton(BACK_BUTTON_LABEL);
        panel_1.add(backButton);
        add(panel_1);
        
    }
    
    /* MVC & Observer pattern methods */
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    /* Public methods */
    
    /* Private methods */
    
}
