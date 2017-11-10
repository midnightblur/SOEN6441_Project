/*
 * Risk Game Team 2
 * ConquerDialog.java
 * Version 1.0
 * Nov 7, 2017
 */
package game_play.view.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * ConquerDialog is responsible for the attacker choose the number of armies to place on the newly conquered territory
 *
 * @author Team 2
 * @version 2.0
 */
public class StrategyDialog extends JDialog {
    // region Attributes declaration
    private static final String SUBMIT_BUTTON_LABEL = "Set Strategies";
    
    private JRadioButtonMenuItem options;
    private JButton submitButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate the strategy dialog
     */
    public StrategyDialog(JFrame gamePlayFrame) {
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        JPanel mainPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        options = new JRadioButtonMenuItem();
        mainPanel.add(options);
        
        submitButton = new JButton(SUBMIT_BUTTON_LABEL);
        mainPanel.add(submitButton);
        
        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(gamePlayFrame);
        setVisible(true);
    }
    // endregion
    
    // region Getters & Setters
    
    
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Add the listener for move armies button
     *
     * @param listenerForSubmitButton The listener for move armies button
     */
    public void addSubmitButtonListener(ActionListener listenerForSubmitButton) {
        submitButton.addActionListener(listenerForSubmitButton);
    }
    // endregion
}
