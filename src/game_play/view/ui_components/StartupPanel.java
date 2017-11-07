/* 
 * Risk Game Team 2
 * StartupPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.ui_components;

import game_play.model.DropDownModel;
import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * StartupPanel is responsible for representing the ui components letting players place 1 army into their territory in round-robin fashion
 *
 * @author Team 2
 * @version 1.0
 */
public class StartupPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String PLACE_ARMY_BUTTON = "Place Army";
    private static final String PLAY_BUTTON = "Play the Game";
    private static final String TERRITORY_LABEL = "Choose territory to place army on";
    private static final String TOTAL_ARMIES_TO_PLACE_LABEL = "Armies to be placed: ";
    private JButton placeArmyButton;
    private JButton playButton;
    private JLabel playerNameLabel;
    private JLabel totalArmiesToPlaceLabel;
    private JComboBox<String> territoryDropdown;
    // endregion
    
    // region Constructors
    /**
     * Instantiates a new startup panel.
     */
    public StartupPanel() {
        JLabel gameStateLabel = new JLabel();
        gameStateLabel.setForeground(Color.BLUE);
        gameStateLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameStateLabel.setText(Config.GAME_STATES.STARTUP.name());
        playerNameLabel = new JLabel();
        //playerNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        totalArmiesToPlaceLabel = new JLabel();
        totalArmiesToPlaceLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        JLabel territoryLabel = new JLabel(TERRITORY_LABEL);
        territoryDropdown = new JComboBox<>();
        placeArmyButton = new JButton(PLACE_ARMY_BUTTON);
        placeArmyButton.setForeground(Color.BLUE);
        playButton = new JButton(PLAY_BUTTON);
        playButton.setEnabled(false);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new GridLayout(17, 1));

        /* Add the elements to the panel */
        controlWrapper.add(gameStateLabel);
        controlWrapper.add(playerNameLabel);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(totalArmiesToPlaceLabel);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(territoryLabel);
        controlWrapper.add(territoryDropdown);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(placeArmyButton);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(playButton);
        addVerticalSpacing(controlWrapper);
        
        add(controlWrapper);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the territory dropdown.
     *
     * @return the territory dropdown
     */
    public JComboBox getTerritoryDropdown() {
        return territoryDropdown;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the place army button listener.
     *
     * @param listenerForPlaceArmiesButton the listener for place armies button
     */
    public void addPlaceArmyButtonListener(ActionListener listenerForPlaceArmiesButton) {
        placeArmyButton.addActionListener(listenerForPlaceArmiesButton);
    }
    
    /**
     * Adds the play button listener
     *
     * @param listenerForPlayButton the listener for play button
     */
    public void addPlayButtonListener(ActionListener listenerForPlayButton) {
        playButton.addActionListener(listenerForPlayButton);
    }
    // endregion
    
    // region Public methods
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.STARTUP) {
                int count = 0; // count how many players have run out of unallocated armies
                for (Player player : gamePlayModel.getPlayers()) {
                    if (player.getUnallocatedArmies() == 0) {
                        count++;
                    }
                }
                
                // Let players place armies in a round-robin-fashion until no player has any army left
                if (count != gamePlayModel.getPlayers().size()) {
                    territoryDropdown.setModel(new DropDownModel(gamePlayModel.getCurrentPlayerTerritories()));
    
                    // Randomly select an item from the list for the purpose of demo
                    territoryDropdown.setSelectedIndex((int) (Math.random() * (gamePlayModel.getCurrentPlayerTerritories().size() - 1)));
    
                    playerNameLabel.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                    playerNameLabel.setText(gamePlayModel.getCurrentPlayer().playerInfo());
                    totalArmiesToPlaceLabel.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(gamePlayModel.getCurrentPlayer().getUnallocatedArmies()));
                } else {
                    playerNameLabel.setForeground(Color.BLACK);
                    playerNameLabel.setText("All armies are allocated");
                    totalArmiesToPlaceLabel.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(gamePlayModel.getCurrentPlayer().getUnallocatedArmies()));
                    territoryDropdown.removeAllItems();
                    territoryDropdown.setEnabled(false);
                    placeArmyButton.setEnabled(false);
                    playButton.setEnabled(true);
                }
            }
        }
    }
    // endregion
}
