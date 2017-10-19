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
 * Startup Panel representing the controls for Startup phase of the game.
 */
public class GameSetupPanel extends JPanel implements Observer {
    
    /** The Constant MAX_PLAYERS_LABEL. */
    private static final String MAX_PLAYERS_LABEL = "Maximum players you can enter is ";
    
    /** The Constant PLAYERS_LABEL. */
    private static final String PLAYERS_LABEL = "Enter the desired number of players:";
    
    /** The Constant PLAY_BUTTON. */
    private static final String PLAY_BUTTON = "Play";
    
    /** The total players. */
    private int totalPlayers = 0;
    
    /** The players label. */
    private JLabel playersLabel;
    
    /** The max players label. */
    private JLabel maxPlayersLabel;
    
    /** The player count. */
    private JTextField playerCount;
    
    /** The play button. */
    private JButton playButton;
    
    /**
     * Instantiates a new game setup panel.
     */
    /* Constructors */
    public GameSetupPanel() {
        playersLabel = new JLabel(PLAYERS_LABEL);
        maxPlayersLabel = new JLabel();
        playersLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
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
    
    /**
     * Sets the max players label.
     *
     * @param playerCount the new max players label
     */
    /* Getters & Setters */
    public void setMaxPlayersLabel(int playerCount) {
        this.maxPlayersLabel.setText(MAX_PLAYERS_LABEL + Integer.toString(playerCount));
    }
    
    /**
     * Sets the player count.
     *
     * @param playerCount the new player count
     */
    public void setPlayerCount(JTextField playerCount) {
        this.playerCount = playerCount;
    }
    
    /**
     * Gets the player count.
     *
     * @return the player count
     */
    public JTextField getPlayerCount() {
        return playerCount;
    }
    
    /**
     * Adds the play button listener.
     *
     * @param listenerForPlayButton the listener for play button
     */
    /* MVC & Observer pattern methods */
    public void addPlayButtonListener(ActionListener listenerForPlayButton) {
        playButton.addActionListener(listenerForPlayButton);
    }
    
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            setMaxPlayersLabel(((GamePlayModel) o).getGameMap().getMaxPlayers());
        }
    }
}
