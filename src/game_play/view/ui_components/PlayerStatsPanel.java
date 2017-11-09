/*
 * Risk Game Team 2
 * PlayerStatsPanel.java
 * Version 1.0
 * Nov 7, 2017
 */
package game_play.view.ui_components;

import shared_resources.game_entities.Player;

import javax.swing.*;
import java.awt.*;

/**
 * PlayerStatsPanel is responsible for displaying a player's info in the PhaseViewPanel
 *
 * @author Team 2
 * @version 2.0
 */
public class PlayerStatsPanel extends JPanel {
    // region Attributes declaration
    private int playerID;
    private JLabel playerNameLabel;
    private JLabel territoryInfoLabel;
    private JLabel armiesInfoLabel;
    private JTextArea cardsInfoLabel;
    private JLabel hasConquered;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate new player statistics panel
     *
     * @param player the player represented by this status panel
     */
    public PlayerStatsPanel(Player player) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        playerID = player.getPlayerID();
        
        playerNameLabel = new JLabel();
        playerNameLabel.setAlignmentX(CENTER_ALIGNMENT);
        playerNameLabel.setForeground(player.getColor());
        playerNameLabel.setText(player.getPlayerName());
        
        territoryInfoLabel = new JLabel();
        territoryInfoLabel.setForeground(player.getColor());
        territoryInfoLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        armiesInfoLabel = new JLabel();
        armiesInfoLabel.setForeground(player.getColor());
        armiesInfoLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        cardsInfoLabel = new JTextArea();
        cardsInfoLabel.setForeground(player.getColor());
        cardsInfoLabel.setBackground(null);
        cardsInfoLabel.setLineWrap(true);
        cardsInfoLabel.setMargin(new Insets(0, 5, 0, 5));
        cardsInfoLabel.setAlignmentX(CENTER_ALIGNMENT);
    
        hasConquered = new JLabel();
        hasConquered.setForeground(player.getColor());
        hasConquered.setAlignmentX(CENTER_ALIGNMENT);
        
        add(playerNameLabel);
        add(territoryInfoLabel);
        add(armiesInfoLabel);
        add(cardsInfoLabel);
        add(hasConquered);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the player ID
     *
     * @return the player ID
     */
    public int getPlayerID() {
        return playerID;
    }
    
    /**
     * Gets the label for the player name
     *
     * @return the label for player name
     */
    public JLabel getPlayerNameLabel() {
        return playerNameLabel;
    }
    
    /**
     * Gets the label for the territory owned
     *
     * @return the territory information label
     */
    public JLabel getTerritoryInfoLabel() {
        return territoryInfoLabel;
    }
    
    /**
     * Gets the label for the armies owned
     *
     * @return the armies information label
     */
    public JLabel getArmiesInfoLabel() {
        return armiesInfoLabel;
    }
    
    /**
     * Gets the label for the cards owned
     *
     * @return the cards information label
     */
    public JTextArea getCardsInfoLabel() {
        return cardsInfoLabel;
    }
    
    /**
     * Gets the has conquered label
     *
     * @return the has conquered label
     */
    public JLabel getHasConquered() {
        return hasConquered;
    }
    
    // endregion
}
