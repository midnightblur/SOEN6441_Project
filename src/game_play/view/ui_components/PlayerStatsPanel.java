package game_play.view.ui_components;

import shared_resources.game_entities.Player;

import javax.swing.*;

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
    private JLabel cardsInfoLabel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate new player statistics panel
     *
     * @param player the player represented by this status panel
     */
    public PlayerStatsPanel(Player player) {
        playerID = player.getPlayerID();
        
        playerNameLabel = new JLabel();
        playerNameLabel.setForeground(player.getColor());
        playerNameLabel.setText(player.getPlayerName());
        
        territoryInfoLabel = new JLabel();
        territoryInfoLabel.setForeground(player.getColor());
        
        armiesInfoLabel = new JLabel();
        armiesInfoLabel.setForeground(player.getColor());
        
        cardsInfoLabel = new JLabel();
        cardsInfoLabel.setForeground(player.getColor());
        
        add(playerNameLabel);
        add(territoryInfoLabel);
        add(armiesInfoLabel);
        add(cardsInfoLabel);
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
    public JLabel getCardsInfoLabel() {
        return cardsInfoLabel;
    }
    
    // endregion
}
