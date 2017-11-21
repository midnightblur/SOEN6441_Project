package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The cheater AI strategy. Reinforcement doubles the number of armies on all its countries, attacks automatically
 * conquer all the neighbors of all its countries, and fortification doubles the number of armies on its countries
 * that have neighbors that belong to other players
 */
public class Cheater extends Bot {
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
