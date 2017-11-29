/*
 * Risk Game Team 2
 * CheaterBot.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Battle;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The cheater AI strategy. Reinforcement doubles the number of armies on all its countries, attacks automatically
 * conquer all the neighbors of all its countries, and fortification doubles the number of armies on its countries
 * that have neighbors that belong to other players.
 */
public class CheaterBot extends Bot {
    /**
     * @see PlayerType#reinforcement(GamePlayModel, Vector, Map)
     */
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        // Double armies in all territories - fantastic @__@
        Player player = gamePlayModel.getCurrentPlayer();
        for (Territory territory : player.getTerritories()) {
            if (territory.getArmies() <= Integer.MAX_VALUE / 2) {
                territory.setArmies(territory.getArmies() * 2);
            } else {
                territory.setArmies(Integer.MAX_VALUE);
            }
            log.append("        " + player.getPlayerName() +
                    " doubles armies in " + territory.getName() + " to " + territory.getArmies());
        }
        return "";
    }
    
    /**
     * @see PlayerType#attack(GamePlayModel)
     */
    @Override
    public void attack(GamePlayModel gamePlayModel) {
        // Conquer all neighbors of all its territories without losing an army - awesome :))
        Player player = gamePlayModel.getCurrentPlayer();
        Vector<Territory> conqueredTerritories = new Vector<>();
        for (Territory territory : player.getTerritories()) {
            for (String neighborName : territory.getNeighbors()) {
                Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
                if (neighbor.getOwner() != player && !conqueredTerritories.contains(neighbor)) {
                    gamePlayModel.setCurrentBattle(new Battle(player, territory, 1,
                            neighbor.getOwner(), neighbor, 1));
                    conqueredTerritories.add(neighbor);
                    log.append("        " + neighbor.getName() + " of " +
                            neighbor.getOwner().getPlayerName() + " has been conquered by " + player.getPlayerName() +
                            " from " + territory.getName());
                    neighbor.getOwner().removeTerritory(neighborName);
                    gamePlayModel.eliminatePlayerIfPossible();
                    if (gamePlayModel.getGameState() == Config.GAME_STATES.VICTORY) {
                        return;
                    }
                }
            }
        }
        player.getTerritories().addAll(conqueredTerritories);
        for (Territory justConqueredTerritory : conqueredTerritories) {
            justConqueredTerritory.setOwner(player);
        }
    }
    
    /**
     * @see PlayerType#fortification(GamePlayModel, String, String, int)
     */
    @Override
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        // Double armies in all territories that have neighbors owned by other players
        boolean hasArmiesMoved = false;
        Player player = gamePlayModel.getCurrentPlayer();
        for (Territory territory : player.getTerritories()) {
            for (String neighborName : territory.getNeighbors()) {
                Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
                if (neighbor.getOwner() != player) {
                    hasArmiesMoved = true;
                    if (territory.getArmies() <= Integer.MAX_VALUE / 2) {
                        territory.setArmies(territory.getArmies() * 2);
                    } else {
                        territory.setArmies(Integer.MAX_VALUE);
                    }
                    log.append("        " + territory.getName() + " has neighbor " +
                            neighborName + " owned by " + neighbor.getOwner().getPlayerName());
                    log.append("            " + player.getPlayerName() +
                            " doubles armies in " + territory.getName() + " to " + territory.getArmies());
                    break;
                }
            }
        }
        if (!hasArmiesMoved) {
            log.append("        " + player.getPlayerName() + " doesn't want to fortify any of his territory");
        }
        return "";
    }
    
    /**
     * @see PlayerType#moveArmiesToConqueredTerritory(GamePlayModel)
     */
    @Override
    public void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel) {
        // this Do nothing.
    }
}
