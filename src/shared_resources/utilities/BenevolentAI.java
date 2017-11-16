package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The benevolent AI strategy. Reinforces its weakest countries, never attacks, then fortifies in order
 * to move armies to weaker countries.
 */
public class BenevolentAI implements Strategy {


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
