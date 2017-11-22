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
 * The random AI strategy. Reinforces a random country, attacks random countries a random number of times, and
 * fortifies a random country.
 */
public class RandomBot extends Bot {
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
    
    @Override
    public void attack(GamePlayModel gamePlayModel) {
        Player player = gamePlayModel.getCurrentPlayer();
        
        // The player attacks until he decides to stop or simply cannot attack anymore
        while (true) {
            // Make the choice whether to attack or not randomly
            Random rand = new Random();
            boolean doAttack = rand.nextBoolean();
            
            if (doAttack) {
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
                            Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
                            if (!neighbor.isOwnedBy(player)) {
                                // Declare an attack using a random choice of number of dice
                                int maxAttackerDice = Math.min(3, randomTerritory.getArmies());
                                int attackerDice = 1 + rand.nextInt(maxAttackerDice);
                                int maxDefenderDice = Math.min(2, neighbor.getArmies());
                                int defenderDice = 1 + rand.nextInt(maxDefenderDice);
                                gamePlayModel.setCurrentBattle(new Battle(player, randomTerritory, attackerDice,
                                        neighbor.getOwner(), neighbor, defenderDice));
                                attackForBots(gamePlayModel);
                                break;
                            }
                        }
                        break;
                    }
                    
                    // If this territory choice cannot attack any territory, prevent it from being chosen again
                    territories.remove(randomTerritory);
                }
                
                if (territories.size() == 0) {
                    log.append("        " + player.getPlayerName() + " cannot attack anymore");
                    return;
                }
            } else {
                log.append("        " + player.getPlayerName() + " quits attacking phase");
                return;
            }
        }
    }
    
    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        Player player = gamePlayModel.getCurrentPlayer();
        
        // Randomly decide whether or not to fortify
        Random rand = new Random();
        boolean doForitification = rand.nextBoolean();
        
        if (doForitification) {
            Vector<Territory> territories = new Vector<>(player.getTerritories());
            while (territories.size() > 0) {
                // Randomly choose a territory that is valid to move armies to another territory
                int randIndex = 0;
                if (territories.size() > 1) {
                    randIndex = rand.nextInt(territories.size() - 1);
                }
                Territory fromTerritory = territories.elementAt(randIndex);
                
                if (fromTerritory.getArmies() >= 2) {
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
        } else {
            log.append("        " + player.getPlayerName() + " doesn't want to fortify any of his territory");
        }
        
        return "";
    }
    
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
            int noOfArmies = 1 + rand.nextInt(fromTerritory.getArmies() - 1);
            fromTerritory.reduceArmies(noOfArmies);
            toTerritory.addArmies(noOfArmies);
            log.append("            " + fromTerritory.getOwner().getPlayerName() + " moves " + noOfArmies + " armies from " +
                    fromTerritory.getName() + " to " + toTerritory.getName());
        }
    }
}
