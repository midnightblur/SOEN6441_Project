package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import java.util.Map;
import java.util.Vector;

/**
 * The aggressive AI strategy. Reinforces its strongest country, then always attacks with it until it
 * cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country.
 */
public class Aggressive implements Strategy {
    
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        Player player = gamePlayModel.getCurrentPlayer();

        /* trade cards whenever the Aggressive AI can */
        boolean doneTrade = false;
        Vector<Card> infantryCards = new Vector<>();
        Vector<Card> cavalryCards = new Vector<>();
        Vector<Card> artilleryCards = new Vector<>();
        while (player.getPlayersHand().size() >= Config.MIN_CARDS_TO_TRADE) {
            selectedCards.clear();
            int threeOfAKindCounter = 0;
    
            for (int i = 0; i < player.getPlayersHand().size(); i++) {
                Card card = player.getPlayersHand().get(i);
                switch (card.getCardType().name()) {
                    case "INFANTRY":
                        infantryCards.addElement(card);
                        break;
                    case "CAVALRY":
                        cavalryCards.addElement(card);
                        break;
                    case "ARTILLERY":
                        artilleryCards.addElement(card);
                        break;
                }
            }
    
            if (infantryCards.size() != 0 && cavalryCards.size() != 0 && artilleryCards.size() != 0) {
                selectedCards.addElement(infantryCards.firstElement().getCardType().name());
                infantryCards.remove(0);
                infantryCards.trimToSize();
        
                selectedCards.addElement(cavalryCards.firstElement().getCardType().name());
                cavalryCards.remove(0);
                cavalryCards.trimToSize();
        
                selectedCards.addElement(artilleryCards.firstElement().getCardType().name());
                artilleryCards.remove(0);
                artilleryCards.trimToSize();
            } else if (infantryCards.size() >= 3) {
        
            } else if (cavalryCards.size() >= 3) {
        
            } else if (artilleryCards.size() >= 3) {
        
            } else {
                doneTrade = true;
                break;  // there are no valid card sets to trade, so break the loop.
            }
        
    
        if (doneTrade) {
            break;
        }
    
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
