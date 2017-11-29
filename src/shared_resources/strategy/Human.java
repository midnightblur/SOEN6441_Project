/*
 * Risk Game Team 2
 * Human.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The human strategy
 */
public class Human implements PlayerType {
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        Player player = gamePlayModel.getCurrentPlayer();
        switch (player.getGameState()) {
            case TRADE_CARDS:
                return player.tradeInCards(gamePlayModel, selectedCards);
            case REINFORCEMENT:
                player.distributeArmies(armiesToPlace);
                break;
        }

        return "";
    }
    
    @Override
    public void attack(GamePlayModel gamePlayModel) {
        gamePlayModel.performBattleIfPossible();
    }

    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        Player currentPlayer = gamePlayModel.getCurrentPlayer();
        Territory fromTerritory = gamePlayModel.getGameMap().getATerritory(sourceTerritory);
        Territory toTerritory = gamePlayModel.getGameMap().getATerritory(targetTerritory);
    
        // Validate if the two territories are owned by the player, are different, and are neighbors.
        if (!fromTerritory.isOwnedBy(currentPlayer.getPlayerID()) ||
                !toTerritory.isOwnedBy(currentPlayer.getPlayerID()) ||
                fromTerritory == toTerritory) {
            return "No armies moved!\nYou must pick two Territories that are neighbors.";
        }
    
        if (fromTerritory.getArmies() == 1 || fromTerritory.getArmies() <= noOfArmies) {
            return "No armies moved!\nYou must always have at least 1 army in each Territory";
        }
    
        fromTerritory.reduceArmies(noOfArmies);
        toTerritory.addArmies(noOfArmies);
    
        log.append("    " + currentPlayer.getPlayerName() + " moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory);
        return "Successfully moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory + ".";
    }
    
    @Override
    public void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel) {
        // Do nothing
    }
}
