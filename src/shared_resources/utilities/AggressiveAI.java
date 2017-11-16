package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The aggressive AI strategy. Reinforces its strongest country, then always attacks with it until it
 * cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country.
 */
class AggressiveAI implements Strategy {


    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        return null;
    }

    @Override
    public void attack(GamePlayModel gamePlayModel) {

    }

    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        return null;
    }
}
