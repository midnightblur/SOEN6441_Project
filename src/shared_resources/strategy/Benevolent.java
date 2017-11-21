package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The benevolent AI strategy. Reinforces its weakest countries, never attacks, then fortifies in order
 * to move armies to weaker countries.
 */
public class Benevolent extends Bot {
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        /* trade cards as long as the bot can */
        tradeCardsForBot(gamePlayModel, selectedCards);

        /* find the weakest territory and reinforce that territory */
        Player player = gamePlayModel.getCurrentPlayer();
        Territory weakestTerritory = null;
        for (Territory territory : player.getTerritories()) {
            if (weakestTerritory == null) {
                weakestTerritory = territory;
            } else {
                if (territory.getArmies() < weakestTerritory.getArmies()) {
                    weakestTerritory = territory;
                }
            }
        }
        log.append("    " + player.getPlayerName() + "'s weakest territory is " +
                weakestTerritory.getName() +" having " + weakestTerritory.getArmies() + " armies");
        if (armiesToPlace != null) {
            armiesToPlace.clear();
        } else {
            armiesToPlace = new HashMap<>();
        }
    
        armiesToPlace.put(weakestTerritory, player.getUnallocatedArmies());
        player.distributeArmies(armiesToPlace);
    
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
