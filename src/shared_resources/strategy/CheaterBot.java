package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Battle;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The cheater AI strategy. Reinforcement doubles the number of armies on all its countries, attacks automatically
 * conquer all the neighbors of all its countries, and fortification doubles the number of armies on its countries
 * that have neighbors that belong to other players
 */
public class CheaterBot extends Bot {
    @Override
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        // Double armies in all territories - fantastic @__@
        Player player = gamePlayModel.getCurrentPlayer();
        for (Territory territory : player.getTerritories()) {
            territory.setArmies(territory.getArmies() * 2);
            log.append("        " + player.getPlayerName() +
                        " doubles armies in " + territory.getName() + " to " + territory.getArmies());
        }
        return "";
    }

    @Override
    public void attack(GamePlayModel gamePlayModel) {
        // Conquer all neighbors of all its territories without losing an army - awesome :))
        Player player = gamePlayModel.getCurrentPlayer();
        Vector<Territory> conqueredTerritories = new Vector<>();
        for (Territory territory : player.getTerritories()) {
            for (String neighborName : territory.getNeighbors()) {
                Territory neighbor = gamePlayModel.getGameMap().getATerritory(neighborName);
                if (neighbor.getOwner() != player) {
                    gamePlayModel.setCurrentBattle(new Battle(player, territory, 1,
                            neighbor.getOwner(), neighbor, 1));
                    conqueredTerritories.add(neighbor);
                    log.append("        " + neighbor.getName() + " of " +
                            neighbor.getOwner().getPlayerName() +" has been conquered by " + player.getPlayerName() +
                            " from " + territory.getName());
                    neighbor.getOwner().removeTerritory(neighborName);
                    neighbor.setOwner(player);
                    gamePlayModel.eliminatePlayerIfCan();
                }
            }
        }
        player.getTerritories().addAll(conqueredTerritories);
    }

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
                    territory.setArmies(territory.getArmies() * 2);
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
    
    @Override
    void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel) {
        // Do nothing
    }
}
