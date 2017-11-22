package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The aggressive AI strategy. Reinforces its strongest country, then always attacks with it until it
 * cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country.
 */
public class AggressiveBot extends Bot {
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        /* trade cards as long as the bot can */
        tradeCardsForBot(gamePlayModel, selectedCards);

        /* find the strongest territory and reinforce that territory */
        Player player = gamePlayModel.getCurrentPlayer();
        Territory strongestTerritory = findStrongestTerritory(player.getTerritories());
        log.append("    " + player.getPlayerName() + "'s strongest territory is " +
                strongestTerritory.getName() +" having " + strongestTerritory.getArmies() + " armies");
        if (armiesToPlace != null) {
            armiesToPlace.clear();
        } else {
            armiesToPlace = new HashMap<>();
        }
        armiesToPlace.put(strongestTerritory, player.getUnallocatedArmies());
        player.distributeArmies(armiesToPlace);
    
        return null;
    }


    @Override
    public void attack(GamePlayModel gamePlayModel) {
        Player player = gamePlayModel.getCurrentPlayer();
        Territory strongestTerritory = findStrongestTerritory(player.getTerritories());
        if (hasAttackableNeighbors(strongestTerritory, gamePlayModel)) {
        
        } else {
        
        }
        
        while (strongestTerritory.getArmies() >= 2) {
        
        }
    }

    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        Player player = gamePlayModel.getCurrentPlayer();
        Territory strongestTerritory = findStrongestTerritory(player.getTerritories());
        Territory toTerritory;
        Territory fromTerritory;
        
        /* if the strongest territory has attackable neighbors, fortify the strongest territory */
        if (hasAttackableNeighbors(strongestTerritory, gamePlayModel)) {
            toTerritory = strongestTerritory;
            fromTerritory = findStrongestNeighbor(gamePlayModel, strongestTerritory.getNeighbors());
        }
        /* if the strongest territory does not have any attackable neighbors, fortify a random adjacent territory owned by the player */
        else {
            toTerritory = findRandomNeighbor(gamePlayModel, strongestTerritory.getNeighbors());
            fromTerritory = strongestTerritory;
        }
    
        /* move armies */
        if (fromTerritory.getArmies() >= 2) {
            noOfArmies = fromTerritory.getArmies() - 1;
            toTerritory.addArmies(noOfArmies);
            fromTerritory.reduceArmies(noOfArmies);
            log.append("        " + player.getPlayerName() + " moves " + noOfArmies + " armies from " +
                    fromTerritory.getName() + " to " + toTerritory.getName());
        } else {
            log.append("        " + player.getPlayerName() + " doesn't want to fortify any of his territory");
            return null;
        }
        
        return null;
    }
    
    /**
     * Find a random neighbor territory (assumes that all neighbors are owned by the player)
     *
     * @param gamePlayModel the game play model
     * @param neighbors     the neighbors list
     *
     * @return random neighbor territory
     */
    private Territory findRandomNeighbor(GamePlayModel gamePlayModel, Vector<String> neighbors) {
        Random rand = new Random();
        int randIndex = rand.nextInt(neighbors.size());
        return gamePlayModel.getGameMap().getATerritory(neighbors.get(randIndex));
    }
    
    /**
     * Find the neighboring territory owned by a player having the most number of armies
     *
     * @param gamePlayModel the game play model
     * @param neighbors     the neighbors list
     *
     * @return the weakest territory
     */
    private Territory findStrongestNeighbor(GamePlayModel gamePlayModel, Vector<String> neighbors) {
        Territory strongestNeighbor = null;
        for (String neighborName : neighbors) {
            Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
            if (neighbor.getOwner() == gamePlayModel.getCurrentPlayer()) {
                if (strongestNeighbor == null) {
                    strongestNeighbor = neighbor;
                } else {
                    if (neighbor.getArmies() > strongestNeighbor.getArmies()) {
                        strongestNeighbor = neighbor;
                    }
                }
            }
        }
        return strongestNeighbor;
    }
    
    /**
     * Checks to see if a territory has any attackable neighbors (neighboring territories
     * that are not owned by the same player).
     *
     * @param territory the territory to check for any attackable neighbors
     * @param gamePlayModel the game play model
     *
     * @return boolean true if there are any attackable neighbors, false if there aren't
     */
    private boolean hasAttackableNeighbors(Territory territory, GamePlayModel gamePlayModel) {
        for (int i = 0; i < territory.getNeighbors().size(); i++) {
            if (gamePlayModel.getGameMap().getATerritory(territory.getNeighbors().get(i))
                    .getOwner() != gamePlayModel.getCurrentPlayer()) {
                return true;
            }
        }
        return false;
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
}
