package model.game_entities;

import model.RiskGame;

import java.util.Objects;
import java.util.Vector;

/**
 * Continent is a connected subgraph of the whole map
 * One continent contains several territories
 * Continents don't share mutual territory
 * Each continent has a control value as an integer
 */
public class Continent {
    /* Private data member of model.game_entities.Continent class */
    private String name;
    private Vector<String> territories;
    private int controlValue;

    /* Constructors */
    
    /**
     * Instantiate new continent object given its name and its control value
     *
     * @param name
     * @param controlValue
     */
    public Continent(String name, int controlValue) {
        this.name = name;
        this.controlValue = controlValue;
        this.territories = new Vector<>();
    }

    /**
     *
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    
    public int getControlValue() {
        return controlValue;
    }

    /**
     *
     * @param controlValue
     */
    
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

    /**
     *
     * @return
     */
    
    public Vector<String> getTerritories() {
        return territories;
    }

    /**
     *
     * @param territories
     */
    
    public void setTerritories(Vector<String> territories) {
        this.territories = territories;
    }


    public void addTerritory(String territoryName) {
        if (!territories.contains(territoryName)) {
            territories.add(territoryName);
        }
    }
    
    /**
     * Remove a territory from the list of territories belong to the continent
     *
     * @param territoryName
     */
    public void removeTerritory(String territoryName) {
        for (String territory : territories) {
            if (territory.compareTo(territoryName) == 0) {
                territories.remove(territory);
                return;
            }
        }
    }
    
    /**
     * Check if the continent contain a given territoryName
     *
     * @param territoryName
     *
     * @return
     */
    public boolean isContain(String territoryName) {
        return (territories.contains(territoryName));
    }
    
    /**
     * Get the number of territories belong to the continent
     *
     * @return
     */
    public int getTerritoriesCount() {
        return territories.size();
    }
    
    /**
     * Calculate the number of armies within the continent
     *
     * @return the count of all armies within a given continent
     *
     * @throws NullPointerException if territories are not yet allocated to players
     */
    public int getContinentArmies() throws NullPointerException {
        int count = 0;
        for (String territoryName : territories) {
            count += RiskGame.getInstance().getGameMap().getATerritory(territoryName).getArmies();
        }
        return count;
    }
    
    /**
     * Determine if a continent was fully conquered and return the id of the owner if so
     *
     * @return the id of the owner having conquered all territories within this continent
     *
     * @throws NullPointerException if territories are not yet allocated to players
     */
    public String getContinentOwner() throws NullPointerException {
        String continentOwner = "";
        if (RiskGame.getInstance().getGameMap().getATerritory(territories.get(0)).isOwned()) {
            continentOwner = "Player " + RiskGame.getInstance().getGameMap().getATerritory(territories.get(0)).getOwner().getPlayerID();
            for (String territoryName : territories) {
                if (!Objects.equals(continentOwner, "Player " + RiskGame.getInstance().getGameMap().getATerritory(territoryName).getOwner().getPlayerID()))
                    return "nobody owns it yet";
            }
        }
        return continentOwner;
    }
    
    /**
     * Facilitate the comparision between two continent objects
     *
     * @param other
     *
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Continent))
            return false;
        
        Continent continent = (Continent) other;
        if (this.name.compareTo(continent.name) == 0)
            return true;
        
        return false;
    }
}
