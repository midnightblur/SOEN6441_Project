/*
 * Risk Game Team 2
 * ConquerDialog.java
 * Version 1.0
 * Nov 7, 2017
 */
package game_play.view.screens;

import shared_resources.game_entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * ConquerDialog is responsible for the attacker choose the number of armies to place on the newly conquered territory
 *
 * @author Team 2
 * @version 2.0
 */
public class StrategyDialog extends JDialog {
    // region Attributes declaration
    private static final String SUBMIT_BUTTON_LABEL = "Set Strategies";
    private JButton submitButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate the strategy dialog
     */
    public StrategyDialog(JFrame gamePlayFrame, Vector<Player> players) {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel mainPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        for (Player player : players) {
            mainPanel.add(new BehaviourOptions(player));
        }
        
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
    
    public class BehaviourOptions extends JPanel {
        private JLabel player_label;
        private ButtonGroup group;
        private JRadioButton ai_aggressive;
        private JRadioButton ai_passive;
        private JRadioButton human;
        
        public BehaviourOptions(Player player) {
            player_label = new JLabel(player.getPlayerName());
            ai_aggressive = new JRadioButton("AI Aggressive");
            ai_passive = new JRadioButton("AI Passive");
            human = new JRadioButton("Human");
            group = new ButtonGroup();
            group.add(ai_aggressive);
            group.add(ai_passive);
            group.add(human);
            
            add(player_label);
            add(ai_aggressive);
            add(ai_passive);
            add(human);
            
        }
        
    }
}

