/*
 * Risk Game Team 2
 * ConquerDialog.java
 * Version 1.0
 * Nov 7, 2017
 */
package game_play.view.screens;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.strategy.Strategy;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * ConquerDialog is responsible for the attacker choose the number of armies to place on the newly conquered territory
 *
 * @author Team 2
 * @version 2.0
 */
public class StrategyDialog extends JDialog implements Observer {
    // region Attributes declaration
    private static final String SUBMIT_BUTTON_LABEL = "Set Strategies";
    private JButton submitButton;
    private JPanel mainPanel;
    private BehaviourOptions[] playersOptions;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate the strategy dialog
     *
     * @param gamePlayFrame the parent frame calling this dialog
     * @param players       the players vector
     */
    public StrategyDialog(JFrame gamePlayFrame, Vector<Player> players) {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        mainPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        playersOptions = new BehaviourOptions[players.size()];  // add the options in array for easier access
        for (int i = 0; i < players.size(); i++) {
            BehaviourOptions opts = new BehaviourOptions(players.elementAt(i));
            playersOptions[i] = opts;
            mainPanel.add(opts);
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
    
    /**
     * Gets the players entries
     *
     * @return player options array
     */
    public BehaviourOptions[] getPlayersOptions() {
        return playersOptions;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Add the listener for submit strategy selections button
     *
     * @param listenerForSubmitButton The listener for submit strategy selections button
     */
    public void addSubmitButtonListener(ActionListener listenerForSubmitButton) {
        submitButton.addActionListener(listenerForSubmitButton);
    }
    
    /**
     * Update based on observed model
     *
     * @param o   the model object that broadcasts changes
     * @param arg the caller object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (((GamePlayModel) o).getPlayers().size() > 0) {
            Vector<Player> players = ((GamePlayModel) o).getPlayers();
            for (int i = 0; i < players.size(); i++) {
                if (players.elementAt(i).getPlayerName().equals(playersOptions[i].getPlayer_label().getText())) {
                    playersOptions[i].setSelection(players.elementAt(i).getStrategy().getClass().getSimpleName());
                }
            }
        }
        
    }
    // endregion
    
    /**
     * Behaviour Options class to dynamically provide options for players' strategy
     */
    public class BehaviourOptions extends JPanel {
        private JLabel player_label;
        private ButtonGroup group;
        JRadioButton radioButton;
        ButtonModel radioButtonModel;
        /**
         * Constructor that creates a set of option and a name label for each player
         *
         * @param player the player object
         */
        BehaviourOptions(Player player) {
            player_label = new JLabel(player.getPlayerName());
            add(player_label);
            
            group = new ButtonGroup();
            for (Class<? extends Strategy> strategyClass : Config.getStrategies()) {
                String strategy = strategyClass.getSimpleName();
                radioButton = new JRadioButton(strategy);
                radioButton.setActionCommand(strategy);
                radioButtonModel = radioButton.getModel();
                group.add(radioButton);
                add(radioButton);
            }
        }
        
        /**
         * Gets the group for each player
         *
         * @return the group of options
         */
        public ButtonGroup getGroup() {
            return group;
        }
        
        /**
         * Gets the player name label
         *
         * @return the label for player name
         */
        public JLabel getPlayer_label() {
            return player_label;
        }
        
        /**
         * Makes the selections based on player's current strategies
         *
         * @param option the option to be selected
         */
        private void setSelection(String option) {
            Enumeration elements = group.getElements();
            while (elements.hasMoreElements()) {
                AbstractButton button = (AbstractButton) elements.nextElement();
                if (button.getActionCommand().equals(option)) {
                    button.setSelected(true);
                }
            }
        }
        
    }
}

