/*
 * Risk Game Team 2
 * ConquerDialog.java
 * Version 1.0
 * Nov 7, 2017
 */
package game_play.view.screens;

import org.reflections.Reflections;
import shared_resources.game_entities.Player;
import shared_resources.strategy.Human;
import shared_resources.strategy.PlayerType;
import shared_resources.utilities.ClassNameComparator;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * ConquerDialog is responsible for the attacker choose the number of armies to place on the newly conquered territory
 *
 * @author Team 2
 * @version 2.0
 */
public class StrategyDialog extends JDialog {
    // region Attributes declaration
    private static final String SUBMIT_BUTTON_LABEL = "Set Strategies";
    private JPanel mainPanel;
    private JButton submitButton;
    private BehaviourOptions[] playersOptions;
    private Set<Class<? extends PlayerType>> strategyClasses;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate the strategy dialog
     *
     * @param gamePlayFrame the parent frame calling this dialog
     */
    public StrategyDialog(JFrame gamePlayFrame) {
        super(gamePlayFrame, ModalityType.TOOLKIT_MODAL);
        
        Reflections reflections = new Reflections(Config.STRATEGY_PATH);
        strategyClasses = reflections.getSubTypesOf(PlayerType.class);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        mainPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        submitButton = new JButton(SUBMIT_BUTTON_LABEL);
        
        setContentPane(mainPanel);
        setTitle("Set players' strategy");
        pack();
        setResizable(false);
        setLocationRelativeTo(gamePlayFrame);
        setVisible(false);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the strategy path
     *
     * @return the strategy path
     */
    public static String getSTRATEGY_PATH() {
        return Config.STRATEGY_PATH;
    }
    
    /**
     * Populates the strategy dialog from a players vector once available
     *
     * @param players the vector of players to populate the dialog
     */
    public void populateOptions(Vector<Player> players, boolean isTournament) {
        mainPanel.removeAll();
        playersOptions = new BehaviourOptions[players.size()];  // add the options in array for easier access
        for (int i = 0; i < players.size(); i++) {
            BehaviourOptions opts = new BehaviourOptions(players.elementAt(i), isTournament);
            playersOptions[i] = opts;
            mainPanel.add(opts);
        }
        mainPanel.add(submitButton);
        checkRadioButtons(players);
        pack();
    }
    
    /**
     * Check the radio buttons corresponding to players' type
     *
     * @param players the list of players
     */
    private void checkRadioButtons(Vector<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (players.elementAt(i).getPlayerName().equals(playersOptions[i].getPlayer_label().getText())) {
                playersOptions[i].setSelection(players.elementAt(i).getPlayerType().getClass().getSimpleName());
            }
        }
    }
    
    /**
     * Disable "Human" option
     */
    public void selectHumanAsDefault() {
        for (int i = 0; i < playersOptions.length; i++) {
            Enumeration<AbstractButton> b = playersOptions[i].getGroup().getElements();
            while (b.hasMoreElements()) {
                AbstractButton r = b.nextElement();
                if (r.getText().contains("Human")) {
                    r.setEnabled(false);
                }
            }
        }
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
     * Gets the players entries
     *
     * @return player options array
     */
    public BehaviourOptions[] getPlayersOptions() {
        return playersOptions;
    }
    // endregion
    
    /**
     * Gets the available strategy classes in a sorted set
     * It uses a custom class name comparator
     *
     * @return a sorted set of strategy classes
     */
    private SortedSet<Class<? extends PlayerType>> getStrategies() {
        SortedSet<Class<? extends PlayerType>> sortedStrategySet = new TreeSet<>(new ClassNameComparator());
        strategyClasses.removeIf(strategy -> Modifier.isAbstract(strategy.getModifiers()));
        sortedStrategySet.addAll(strategyClasses);
        return sortedStrategySet;
    }
    
    // region Private methods
    
    /**
     * Behaviour Options class to dynamically provide options for players' strategy
     */
    public class BehaviourOptions extends JPanel {
        JRadioButton radioButton;
        ButtonModel radioButtonModel;
        private JLabel player_label;
        private ButtonGroup group;
        
        /**
         * Constructor that creates a set of option and a name label for each player
         *
         * @param player the player object
         */
        BehaviourOptions(Player player, boolean isTournament) {
            player_label = new JLabel(player.getPlayerName());
            add(player_label);
            
            group = new ButtonGroup();
            for (Class<? extends PlayerType> strategyClass : getStrategies()) {
                if (isTournament && strategyClass.getSimpleName().compareTo(Human.class.getSimpleName()) == 0) {
                    continue;
                }
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
    // endregion
}

