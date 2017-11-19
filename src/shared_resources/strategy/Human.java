package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The human strategy
 */
public class Human implements Strategy {
    
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        Player player = gamePlayModel.getCurrentPlayer();
        switch (player.getGameState()) {
            case TRADE_CARDS:
                return player.tradeInCards(gamePlayModel, selectedCards);
            case REINFORCEMENT:
                player.distributeArmies(armiesToPlace);
                break;
        }

        return "";
    }

    @Override
    public void attack(GamePlayModel gamePlayModel) {
    
    }

    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        return null;
    }
}
