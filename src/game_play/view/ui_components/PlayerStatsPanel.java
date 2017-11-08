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
    
    public int getPlayerID() {
        return playerID;
    }
    
    public JLabel getPlayerNameLabel() {
        return playerNameLabel;
    }
    
    public JLabel getTerritoryInfoLabel() {
        return territoryInfoLabel;
    }
    
    public JLabel getArmiesInfoLabel() {
        return armiesInfoLabel;
    }
    
    public JLabel getCardsInfoLabel() {
        return cardsInfoLabel;
    }
    
    // endregion
}
