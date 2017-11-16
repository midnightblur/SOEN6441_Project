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
        private JRadioButton aggressive;
        private JRadioButton benevolent;
        private JRadioButton random;
        private JRadioButton cheater;
        private JRadioButton human;
        
        public BehaviourOptions(Player player) {
            player_label = new JLabel(player.getPlayerName());
            aggressive = new JRadioButton("Aggressive");
            benevolent = new JRadioButton("Benevolent");
            random = new JRadioButton("Random");
            cheater = new JRadioButton("Cheater");
            human = new JRadioButton("Human");
            group = new ButtonGroup();
            group.add(aggressive);
            group.add(benevolent);
            group.add(random);
            group.add(cheater);
            group.add(human);
            
            add(player_label);
            add(aggressive);
            add(benevolent);
            add(random);
            add(cheater);
            add(human);
            
        }
        
    }
}

