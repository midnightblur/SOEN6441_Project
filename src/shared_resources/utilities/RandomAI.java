package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The random AI strategy. Reinforces a random country, attacks random countries a random number of times, and
 * fortifies a random country.
 */
class RandomAI implements Strategy {


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
