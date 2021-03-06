/* 
 * Risk Game Team 2
 * GameSetupPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * GameSetupPanel is responsible for display ui components, allowing users to choose the number of players in the game.
 *
 * @author Team 2
 * @version 1.0
 */
public class GameSetupPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String MAX_PLAYERS_LABEL = "Maximum players you can enter is ";
    private static final String PLAYERS_LABEL = "Enter the desired number of players:";
    private static final String PLAY_BUTTON = "Play";
    private JLabel maxPlayersLabel;
    private JTextField playerCount;
    private JButton playButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new game setup panel.
     */
    public GameSetupPanel() {
        /* The players label. */
        JLabel playersLabel = new JLabel(PLAYERS_LABEL);
        maxPlayersLabel = new JLabel();
        playersLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        /* The total players. */
        int totalPlayers = 0;
        playerCount = new JTextField(totalPlayers);
        playButton = new JButton(PLAY_BUTTON);
        playButton.setForeground(Color.BLUE);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new GridLayout(15, 1));

        /* Add the elements to the panel */
        controlWrapper.add(playersLabel);
        controlWrapper.add(maxPlayersLabel);
        controlWrapper.add(playerCount);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(playButton);
        addVerticalSpacing(controlWrapper);
        addVerticalSpacing(controlWrapper);
        
        add(controlWrapper);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Sets the max players label.
     *
     * @param playerCount the new max players label
     */
    private void setMaxPlayersLabel(int playerCount) {
        this.maxPlayersLabel.setText(MAX_PLAYERS_LABEL + Integer.toString(playerCount));
    }
    
    /**
     * Gets the player count.
     *
     * @return the player count
     */
    public JTextField getPlayerCount() {
        return playerCount;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the play button listener.
     *
     * @param listenerForPlayButton the listener for play button
     */
    public void addPlayButtonListener(ActionListener listenerForPlayButton) {
        playButton.addActionListener(listenerForPlayButton);
    }
    
    
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
            setMaxPlayersLabel(((GamePlayModel) o).getGameMap().getMaxPlayers());
        }
    }
    // endregion
}
