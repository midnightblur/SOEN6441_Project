package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The human strategy
 */
class Human implements Strategy {

    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {

//            switch (gameState) {
//                case TRADE_CARDS:
//                    return tradeInCards(gamePlayModel, selectedCards);
//                case REINFORCEMENT:
//                    distributeArmies(armiesToPlace);
//                    break;
//            }

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
