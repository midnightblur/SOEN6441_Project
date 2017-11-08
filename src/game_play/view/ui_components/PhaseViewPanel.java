package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;

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
    private static final String ARMIES_INFO_FORMAT = "%d armies";
    private static final String CARDS_INFO_FORMAT = "%d cards:%s";
    
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 100;
    private JLabel gameStateLabel;
    private JPanel playerInfoArea;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate a new World domination panel
     */
    public PhaseViewPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        
        setLayout(new GridLayout(1, 2));
        gameStateLabel = new JLabel();
        gameStateLabel.setForeground(Color.BLUE);
        gameStateLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        add(gameStateLabel);
        
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
            gameStateLabel.setText(gamePlayModel.getGameState().toString());
    
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
     * </ul>>
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
                setBackground(Color.WHITE);
                playerStatsPanels.get(player.getPlayerID() - 1).getPlayerNameLabel().setText(player.getPlayerName() + " WAS ELIMINATED");
                playerStatsPanels.get(player.getPlayerID() - 1).getTerritoryInfoLabel().setText("");
                playerStatsPanels.get(player.getPlayerID() - 1).getArmiesInfoLabel().setText("");
                playerStatsPanels.get(player.getPlayerID() - 1).getCardsInfoLabel().setText("");
            } else if (player.getPlayerStatus() == GamePlayModel.PLAYER_STATUS.IN_GAME) {
                /* Current player has different background color */
                if (gamePlayModel.getCurrentPlayer().getPlayerID() == player.getPlayerID()) {
                    playerStatsPanels.get(player.getPlayerID() - 1).setBackground(Color.LIGHT_GRAY);
                } else {
                    playerStatsPanels.get(player.getPlayerID() - 1).setBackground(Color.WHITE);
                }
    
                /* Update player's territory info */
                String territoryInfo = String.format(TERRITORY_INFO_FORMAT, player.getTerritories().size(),
                        gamePlayModel.getGameMap().getTerritoriesCount());
                playerStatsPanels.get(player.getPlayerID() - 1).getTerritoryInfoLabel().setText(territoryInfo);
                
                /* Update player's armies info */
                String armiesInfo = String.format(ARMIES_INFO_FORMAT, player.getTotalArmiesCount());
                playerStatsPanels.get(player.getPlayerID() - 1).getArmiesInfoLabel().setText(armiesInfo);
                
                /* Update player's cards info */
                StringBuilder cardsListStr = new StringBuilder();
                for (Card card : player.getPlayersHand()) {
                    cardsListStr.append(" ").append(card.getCardType().name());
                }
                String cardsInfo = String.format(CARDS_INFO_FORMAT, player.getPlayersHand().size(), cardsListStr.toString());
                playerStatsPanels.get(player.getPlayerID() - 1).getCardsInfoLabel().setText(cardsInfo);
            }
        }
    }
    // endregion
}
