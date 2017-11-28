package game_play.view.screens;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ManualInteractionDialog extends JDialog {
    private static final String NO_BUTTON = "No";
    private static final String YES_BUTTON = "Continue";
    private JButton noButton;
    private JButton yesButton;
    private JFrame owner;
    
    public ManualInteractionDialog(JFrame owner, String message) {
        super(owner);
        this.owner = owner;
        JLabel messageLabel = new JLabel(message);
        noButton = new JButton(NO_BUTTON);
        yesButton = new JButton(YES_BUTTON);
        
        JPanel mainPanel = new JPanel();
        mainPanel.add(messageLabel);
        mainPanel.add(noButton);
        mainPanel.add(yesButton);
    
        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    
    /**
     * @see JFrame#getOwner()
     */
    @Override
    public JFrame getOwner() {
        return owner;
    }
    
    public void addNoButtonListener(ActionListener listenerForNoButton) {
        noButton.addActionListener(listenerForNoButton);
    }
    
    public void addYesButtonListener(ActionListener listenerForYesButton) {
        yesButton.addActionListener(listenerForYesButton);
    }
}
