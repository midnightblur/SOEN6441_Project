/*
 * Risk Game Team 2
 * Strategy.java
 * Version 3.0
 * Nov 10, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

/**
 * The classes implementing a concrete strategy should implement this.
 * The players use this to adopt a concrete strategy.
 */
public interface Strategy extends Serializable {
    
    /**
     *
     * @param gamePlayModel
     * @param selectedCards
     */
    default void tradeCardsForBot(GamePlayModel gamePlayModel, Vector<String> selectedCards) {
        Player player = gamePlayModel.getCurrentPlayer();
        
        /* check for valid card sets to trade if the AI has 3 or more cards */
        Vector<Card> infantryCards = new Vector<>();
        Vector<Card> cavalryCards = new Vector<>();
        Vector<Card> artilleryCards = new Vector<>();
        if (player.getPlayersHand().size() >= Config.MIN_CARDS_TO_TRADE) {
            for (int i = 0; i < player.getPlayersHand().size(); i++) {
                Card card = player.getPlayersHand().get(i);
                switch (card.getCardType()) {
                    case INFANTRY:
                        infantryCards.addElement(card);
                        break;
                    case CAVALRY:
                        cavalryCards.addElement(card);
                        break;
                    case ARTILLERY:
                        artilleryCards.addElement(card);
                        break;
                }
            }
        }
        
        /* trade cards as long as the AI can */
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
    }
    
    String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace);

    void attack(GamePlayModel gamePlayModel);

    String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies);
}

