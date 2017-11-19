package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * The aggressive AI strategy. Reinforces its strongest country, then always attacks with it until it
 * cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country.
 */
public class Aggressive implements Strategy {
    
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        /* trade cards as long as the bot can */
        tradeCardsForBot(gamePlayModel, selectedCards);

        /* find the strongest territory and reinforce that territory */
        armiesToPlace.clear();
        Player player = gamePlayModel.getCurrentPlayer();
        Territory strongestTerritory = null;
        for (Territory territory : player.getTerritories()) {
            if (strongestTerritory == null) {
                strongestTerritory = territory;
            } else {
                if (territory.getArmies() > strongestTerritory.getArmies()) {
                    strongestTerritory = territory;
                }
            }
        }
        
        
        
        player.distributeArmies(armiesToPlace);
    
    
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
