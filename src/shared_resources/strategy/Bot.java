package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import java.util.Vector;

/**
 * This class is parent class of all AI bot's classes
 * Bot class is responsible for providing mutual functionality of all kind of bots
 */
abstract class Bot implements PlayerType {
    /**
     * Let a bot player trade his cards as soon as he can
     *
     * @param gamePlayModel the game play model
     */
    void tradeCardsForBots(GamePlayModel gamePlayModel) {
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
            Vector<String> selectedCards = new Vector<>();
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
    
    void attackForBots(GamePlayModel gamePlayModel) {
        // Let the defender choose how many dice to defend
        letDefenderChooseDefendingDice(gamePlayModel);
        
        if (!gamePlayModel.getCurrentBattle().getDefendingTerritory().getOwner().isHuman()) {
            // Perform the battle
            attackForAllPlayers(gamePlayModel);
    
            // If conquer any territory, move some armies to that territory
            moveArmiesToConqueredTerritory(gamePlayModel);
        }
    }
    
    int letDefenderChooseDefendingDice(GamePlayModel gamePlayModel) {
        int defendingDice = 1;
        Player defender = gamePlayModel.getCurrentBattle().getDefender();
        if (defender.isHuman()) {
            gamePlayModel.waitForDefenderReaction();
        } else {
            int maxDefendingDice = gamePlayModel.getCurrentBattle().getMaxDefendingRoll();
            defendingDice = defender.botChooseDefendingDice(maxDefendingDice);
        }
        return defendingDice;
    }
    
    void conquerTerritoryForBots(GamePlayModel gamePlayModel) {
        Player winner = gamePlayModel.getCurrentPlayer();
        Territory defendingTerritory = gamePlayModel.getCurrentBattle().getDefendingTerritory();
        Player loser = defendingTerritory.getOwner();
        
        defendingTerritory.setOwner(winner);
        
        loser.removeTerritory(defendingTerritory.getName());
        winner.addTerritory(defendingTerritory);
    }
    
    abstract void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel);
}
