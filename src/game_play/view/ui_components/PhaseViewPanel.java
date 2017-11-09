/*
 * Risk Game Team 2
 * PhaseViewPanel.java
 * Version 1.0
 * Nov 7, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * PhaseViewPanel is responsible for displaying all information related to current phase of the game.
 *
 * @author Team 2
 * @version 2.0
 */
public class PhaseViewPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String TERRITORY_INFO_FORMAT = "%d/%d territories";
    private static final String ARMIES_INFO_FORMAT = "%d armies + %d";
    private static final String CARDS_INFO_FORMAT = "%d cards:%s";
    private static final String HAS_CONQUERED_FORMAT = "Has conquered: %s";
    
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 100;
    private JLabel gameStateLabel;
    private JLabel tradeCardValueLabel;
    private JLabel deckSizeLabel;
    private JPanel playerInfoArea;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate a new World domination panel
     */
    public PhaseViewPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        
        JPanel gameInfoArea = new JPanel(new GridLayout(0, 1));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        gameStateLabel = new JLabel();
        gameStateLabel.setMinimumSize(new Dimension(200, 100));
        gameStateLabel.setPreferredSize((new Dimension(200, 100)));
        gameStateLabel.setMaximumSize(new Dimension(200, 100));
        gameStateLabel.setForeground(Color.BLUE);
        gameStateLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameInfoArea.add(gameStateLabel);
    
        tradeCardValueLabel = new JLabel();
        gameInfoArea.add(tradeCardValueLabel);
    
        deckSizeLabel = new JLabel();
        gameInfoArea.add(deckSizeLabel);
        
        add(gameInfoArea);
        playerInfoArea = new JPanel();
        playerInfoArea.setLayout(new GridLayout(1, 3));
        add(playerInfoArea);
        
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
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

            /* Update the game state info */
            if (gamePlayModel.getGameState() != Config.GAME_STATES.PLAY) {
                gameStateLabel.setText(gamePlayModel.getGameState().name());
            } else {
                gameStateLabel.setText(gamePlayModel.getCurrentPlayer().getGameState().name());
            }
            
            /* Update current trade card for armies value */
            tradeCardValueLabel.setText("Trade cards value = " + gamePlayModel.getArmyValue());
            
            /* Update the deck size */
            deckSizeLabel.setText(gamePlayModel.getDeck().size() + " cards in deck");
    
            /* Update all player's info */
            updatePlayersInfo(gamePlayModel);
        }
    }
    // endregion
    
    // region Private methods
    
    /**
     * Populates the players statistical information depending of the game or player state.
     *
     * Prepares a vector of players and displays the information for each one in regards to:
     * <ul>
     * <li>If the player was eliminated, display no information
     * <li>Current player has different background color
     * <li>Update player's territory info
     * <li>Update player's armies info
     * <li>Update player's cards info
     * </ul>
     *
     * @param gamePlayModel the play model for ongoing game
     */
    private void updatePlayersInfo(GamePlayModel gamePlayModel) {
        Vector<PlayerStatsPanel> playerStatsPanels = new Vector<>();
        for (Player player : gamePlayModel.getPlayers()) {
            playerStatsPanels.add(new PlayerStatsPanel(player));
        }
        playerInfoArea.removeAll();
        for (PlayerStatsPanel playerStatsPanel : playerStatsPanels) {
            playerInfoArea.add(playerStatsPanel);
        }
        playerInfoArea.revalidate();
        playerInfoArea.repaint();
        
        for (Player player : gamePlayModel.getPlayers()) {
            if (player.getPlayerStatus() == GamePlayModel.PLAYER_STATUS.ELIMINATED) {
                /* If the player was eliminated, display no information */
                setBackground(UIManager.getColor ( "Panel.background" ));
                playerStatsPanels.get(player.getPlayerID() - 1).getPlayerNameLabel().setText(player.getPlayerName() + " WAS ELIMINATED");
                playerStatsPanels.get(player.getPlayerID() - 1).getTerritoryInfoLabel().setText("");
                playerStatsPanels.get(player.getPlayerID() - 1).getArmiesInfoLabel().setText("");
                playerStatsPanels.get(player.getPlayerID() - 1).getCardsInfoLabel().setText("");
            } else if (player.getPlayerStatus() == GamePlayModel.PLAYER_STATUS.IN_GAME) {
                /* Current player has different background color */
                if (gamePlayModel.getCurrentPlayer().getPlayerID() == player.getPlayerID()) {
                    playerStatsPanels.get(player.getPlayerID() - 1).setBackground(Color.WHITE);
                } else {
                    playerStatsPanels.get(player.getPlayerID() - 1).setBackground(UIManager.getColor ( "Panel.background" ));
                }
    
                /* Update player's territory info */
                String territoryInfo = String.format(TERRITORY_INFO_FORMAT, player.getTerritories().size(),
                        gamePlayModel.getGameMap().getTerritoriesCount());
                playerStatsPanels.get(player.getPlayerID() - 1).getTerritoryInfoLabel().setText(territoryInfo);
                
                /* Update player's armies info */
                String armiesInfo = String.format(ARMIES_INFO_FORMAT, player.getTotalArmiesCount(), player.getUnallocatedArmies());
                playerStatsPanels.get(player.getPlayerID() - 1).getArmiesInfoLabel().setText(armiesInfo);
                
                /* Update player's cards info */
                StringBuilder cardsListStr = new StringBuilder();
                for (Card card : player.getPlayersHand()) {
                    cardsListStr.append(" ").append(card.getCardType().name());
                }
                String cardsInfo = String.format(CARDS_INFO_FORMAT, player.getPlayersHand().size(), cardsListStr.toString());
                playerStatsPanels.get(player.getPlayerID() - 1).getCardsInfoLabel().setText(cardsInfo);
                
                /* Update player's has just conquered any territory info */
                String hasConquered = String.format(HAS_CONQUERED_FORMAT, player.hasConqueredTerritories()? "YES" : "NO");
                playerStatsPanels.get(player.getPlayerID() - 1).getHasConquered().setText(hasConquered);
            }
        }
    }
    // endregion
}
