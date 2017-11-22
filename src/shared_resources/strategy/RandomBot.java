package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

/**
 * The random AI strategy. Reinforces a random country, attacks random countries a random number of times, and
 * fortifies a random country.
 */
public class RandomBot extends Bot {
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        /* trade cards as long as the bot can */
        tradeCardsForBot(gamePlayModel, selectedCards);

        /* find a random territory and reinforce that territory */
        Player player = gamePlayModel.getCurrentPlayer();
        Random rand = new Random();
        int randIndex = rand.nextInt(player.getTerritories().size() - 1);
    
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
