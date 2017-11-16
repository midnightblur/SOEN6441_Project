package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.GAME_STATES.REINFORCEMENT;
import static shared_resources.utilities.Config.log;

/**
 * The aggressive AI strategy. Reinforces its strongest country, then always attacks with it until it
 * cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country.
 */
public class AggressiveAI implements Strategy {


    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        Player player = gamePlayModel.getCurrentPlayer();

        /* trade cards whenever the Aggressive AI can */
        if (player.getPlayersHand().size() >= Config.MIN_CARDS_TO_TRADE) {
            selectedCards.clear();
            int threeOfAKindCounter = 0;
            for (Card card : player.getPlayersHand()) {
                if (false) {

                }
                selectedCards.addElement(card.getCardType().name());
            }

            int oneOfEachCounter = 0;

            // check for three of a kind pair
        }


        switch (player.getGameState()) {
            case TRADE_CARDS:
                return player.tradeInCards(gamePlayModel, selectedCards);
            case REINFORCEMENT:
                player.distributeArmies(armiesToPlace);
                break;
        }

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
