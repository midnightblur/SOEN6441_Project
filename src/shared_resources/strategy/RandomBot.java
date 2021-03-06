/*
 * Risk Game Team 2
 * RandomBot.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Battle;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The random AI strategy. Reinforces a random country, attacks random countries a random number of times,and
 * fortifies a random country.
 */
public class RandomBot extends Bot {
    /**
     * The reinforcement of the Random bot reinforces a random country with the unallocated armies.
     *
     * @see shared_resources.strategy.PlayerType#reinforcement(GamePlayModel, Vector, Map)
     */
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        /* trade cards as long as the bot can */
        tradeCardsForBots(gamePlayModel);

        /* find a random territory and reinforce that territory */
        Player player = gamePlayModel.getCurrentPlayer();
        Territory randomTerritory = player.getRandomTerritory();
        
        randomTerritory.addArmies(player.getUnallocatedArmies());
        log.append("        " + player.getPlayerName() + " placed " + player.getUnallocatedArmies() +
                " armies on " + randomTerritory.getName());
        player.setUnallocatedArmies(0);
        
        return "";
    }

    /**
     * The attack of Random bot attacks a random country a random amount of times until it decides to quit.
     *
     * @see shared_resources.strategy.PlayerType#attack(GamePlayModel)
     */
    @Override
    public void attack(GamePlayModel gamePlayModel) {
        Player player = gamePlayModel.getCurrentPlayer();
        
        // Make the choice whether to attack or not randomly
        Random rand = new Random();
        boolean doAttack = rand.nextBoolean();
        
        if (doAttack) {
            boolean hasAttacked = false;
            Vector<Territory> territories = new Vector<>(player.getTerritories());
            while (territories.size() > 0) {
                // Find a random territory
                int randIndex = 0;
                if (territories.size() > 1) {
                    randIndex = rand.nextInt(territories.size() - 1);
                }
                Territory randomTerritory = territories.elementAt(randIndex);
                
                if (randomTerritory.getArmies() >= 2) {
                    // Find one of its neighbor owned by another player
                    for (String neighborName : randomTerritory.getNeighbors()) {
                        if (hasAttacked) {
                            break;
                        }
                        
                        Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
                        if (!neighbor.isOwnedBy(player)) {
                            // Declare an attack using a random choice of number of dice
                            int maxAttackerDice = Math.min(3, randomTerritory.getArmies() - 1);
                            int attackerDice = 1 + rand.nextInt(maxAttackerDice);
                            gamePlayModel.setCurrentBattle(new Battle(player, randomTerritory, attackerDice,
                                    neighbor.getOwner(), neighbor));
                            
                            hasAttacked = true;
                        }
                    }
                    
                    if (hasAttacked) {
                        break;
                    }
                }
                
                // If this territory choice cannot attack any territory, prevent it from being chosen again
                territories.remove(randomTerritory);
                territories.trimToSize();
            }
            
            if (!hasAttacked) {
                log.append("        " + player.getPlayerName() + " cannot attack anymore");
                gamePlayModel.setCurrentBattle(null);
            }
        } else {
            log.append("        " + player.getPlayerName() + " quits attacking phase");
            gamePlayModel.setCurrentBattle(null);
        }
    }

    /**
     * The fortification method of Random bot fortifies a random territory with a random number of armies.
     *
     * @see shared_resources.strategy.PlayerType#fortification(GamePlayModel, String, String, int)
     */
    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        Player player = gamePlayModel.getCurrentPlayer();
        
        // Randomly decide whether or not to fortify
        Random rand = new Random();
        boolean doFortification = rand.nextBoolean();
        
        if (doFortification) {
            Vector<Territory> territories = new Vector<>(player.getTerritories());
            while (territories.size() > 0) {
                // Randomly choose a territory that is valid to move armies to another territory
                int randIndex = 0;
                if (territories.size() > 1) {
                    randIndex = rand.nextInt(territories.size() - 1);
                }
                Territory fromTerritory = territories.elementAt(randIndex);
                
                if (fromTerritory != null && fromTerritory.getArmies() >= 2) {
                    // Randomly choose a neighbor to move armies to
                    for (String neighborName : fromTerritory.getNeighbors()) {
                        Territory toTerritory = gamePlayModel.getGameMap().getATerritory(neighborName);
                        
                        if (toTerritory.isOwnedBy(player)) {
                            // Randomly choose a valid number of armies to move
                            noOfArmies = 1 + rand.nextInt(fromTerritory.getArmies() - 1);
                            fromTerritory.reduceArmies(noOfArmies);
                            toTerritory.addArmies(noOfArmies);
                            log.append("        " + fromTerritory.getOwner().getPlayerName() + " moves " + noOfArmies + " armies from " +
                                    fromTerritory.getName() + " to " + toTerritory.getName());
                            
                            return "";
                        }
                    }
                    
                    territories.remove(fromTerritory);
                } else {
                    territories.remove(fromTerritory);
                }
            }
            
            if (territories.size() == 0) {
                log.append("        " + player.getPlayerName() + " cannot fortify");
            }
        } else {
            log.append("        " + player.getPlayerName() + " doesn't want to fortify any of his territory");
        }
        
        return "";
    }

    /**
     * The moveArmiesToConqueredTerritory of Random bot moves random number of armies to the conquered territory
     * while respecting the game rules.
     *
     * @see shared_resources.strategy.PlayerType#moveArmiesToConqueredTerritory(GamePlayModel)
     */
    @Override
    public void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel) {
        Territory defendingTerritory = gamePlayModel.getCurrentBattle().getDefendingTerritory();
        if (defendingTerritory.getArmies() == 0) {
            // Set player to be new owner of the conquered territory
            conquerTerritoryForBots(gamePlayModel);
            
            // Randomly choose a (valid) number of armies to move to conquered territory
            Random rand = new Random();
            Territory fromTerritory = gamePlayModel.getCurrentBattle().getAttackingTerritory();
            Territory toTerritory = gamePlayModel.getCurrentBattle().getDefendingTerritory();
            int noOfArmies = gamePlayModel.getCurrentBattle().getAttackerDice().getRollsCount()
                    + rand.nextInt(fromTerritory.getArmies() - gamePlayModel.getCurrentBattle().getAttackerDice().getRollsCount());
            fromTerritory.reduceArmies(noOfArmies);
            toTerritory.addArmies(noOfArmies);
            log.append("            " + fromTerritory.getOwner().getPlayerName() + " moves " + noOfArmies + " armies from " +
                    fromTerritory.getName() + " to " + toTerritory.getName());
            
            gamePlayModel.eliminatePlayerIfPossible();
        }
    }
}
