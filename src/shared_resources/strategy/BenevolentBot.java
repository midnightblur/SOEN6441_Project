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
public class BenevolentBot extends Bot {
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        /* trade cards as long as the bot can */
        tradeCardsForBots(gamePlayModel);

        /* find the weakest territory and reinforce that territory */
        Player player = gamePlayModel.getCurrentPlayer();
        Territory weakestTerritory = findWeakestNeighbor(player);
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
        Player currentPlayer = gamePlayModel.getCurrentPlayer();
        log.append("        " + currentPlayer.getPlayerName() + " quits attacking phase");
    }

    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        Player player = gamePlayModel.getCurrentPlayer();
        
        /* find the strongest territory then try to move armies into weaker territory so they have roughly same no of armies */
        /* if strongest territory doesn't have weaker neighbor, try to find second strongest one */
        /* repeat until he has no choice -> quit fortification */
        Vector<Territory> playersTerritories = new Vector<>(player.getTerritories());
        while (playersTerritories.size() > 0) {
            Territory strongestTerritory = findStrongestTerritory(playersTerritories);
            Territory weakestNeighbor = findWeakestNeighbor(gamePlayModel, strongestTerritory.getNeighbors());
            if (weakestNeighbor != null && strongestTerritory.getArmies() - weakestNeighbor.getArmies() >= 2) {
                noOfArmies = (strongestTerritory.getArmies() - weakestNeighbor.getArmies()) / 2;
                strongestTerritory.reduceArmies(noOfArmies);
                weakestNeighbor.addArmies(noOfArmies);
                log.append("        " + player.getPlayerName() + " moves " + noOfArmies +" armies from " +
                        strongestTerritory.getName() + " to " + weakestNeighbor.getName());
                return "";
            } else {
                playersTerritories.remove(strongestTerritory);
            }
        }
        
        log.append("        " + player.getPlayerName() + " doesn't want to fortify any of his territory");
        return "";
    }
    
    /**
     * Find the territory owned by a player having the least number of armies
     *
     * @param player the player
     *
     * @return the weakest territory
     */
    private Territory findWeakestNeighbor(Player player) {
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
        return weakestTerritory;
    }
    
    /**
     * Find the territory owned by a player having the least number of armies
     *
     * @param gamePlayModel the game play model
     * @param neighbors     the neighbors list
     *
     * @return the weakest territory
     */
    private Territory findWeakestNeighbor(GamePlayModel gamePlayModel, Vector<String> neighbors) {
        Territory weakestNeighbor = null;
        for (String neighborName : neighbors) {
            Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
            if (neighbor.getOwner() == gamePlayModel.getCurrentPlayer()) {
                if (weakestNeighbor == null) {
                    weakestNeighbor = neighbor;
                } else {
                    if (neighbor.getArmies() < weakestNeighbor.getArmies()) {
                        weakestNeighbor = neighbor;
                    }
                }
            }
        }
        return weakestNeighbor;
    }
    
    /**
     * Find the territory owned by a player having the most number of armies
     *
     * @param territories the territories list
     *
     * @return the weakest territory
     */
    private Territory findStrongestTerritory(Vector<Territory> territories) {
        Territory strongestTerritory = null;
        for (Territory territory : territories) {
            if (strongestTerritory == null) {
                strongestTerritory = territory;
            } else {
                if (territory.getArmies() > strongestTerritory.getArmies()) {
                    strongestTerritory = territory;
                }
            }
        }
        return strongestTerritory;
    }
    
    @Override
    void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel) {
        // Do nothing
    }
}
