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

        /* check for valid card sets to trade if the AI has 3 or more cards */
        Vector<Card> infantryCards = new Vector<>();
        Vector<Card> cavalryCards = new Vector<>();
        Vector<Card> artilleryCards = new Vector<>();
        if (player.getPlayersHand().size() >= Config.MIN_CARDS_TO_TRADE) {
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
        }
        
        /* trade cards as long as the Aggressive AI can */
        while (true) {
            selectedCards.clear();
            
            /* if there is a set of 3 cards with each different type */
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
            }
            /* if there are 3 infantry cards */
            else if (infantryCards.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    selectedCards.addElement(infantryCards.firstElement().getCardType().name());
                    infantryCards.remove(0);
                    infantryCards.trimToSize();
                }
            }
            /* if there are 3 cavalry cards */
            else if (cavalryCards.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    selectedCards.addElement(cavalryCards.firstElement().getCardType().name());
                    cavalryCards.remove(0);
                    cavalryCards.trimToSize();
                }
            }
            /* if there are 3 artillery cards */
            else if (artilleryCards.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    selectedCards.addElement(artilleryCards.firstElement().getCardType().name());
                    artilleryCards.remove(0);
                    artilleryCards.trimToSize();
                }
            }
            /* if cannot trade anymore */
            else {
                break;
            }
        
            player.tradeInCards(gamePlayModel, selectedCards);
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
