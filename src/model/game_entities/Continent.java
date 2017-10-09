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
    private Vector<Territory> territories;
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
    
    /* Getters & Setters */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getControlValue() {
        return controlValue;
    }
    
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }
    
    public Vector<Territory> getTerritories() {
        return territories;
    }
    
    public void setTerritories(Vector<Territory> territories) {
        this.territories = territories;
    }
    
    /* Public methods */
    
    /**
     * Add a territory to the list of territories belonging to the continent
     *
     * @param territory
     */
    public void addTerritory(Territory territory) {
        if (!territories.contains(territory))
            territories.add(territory);
    }
    
    /**
     * Remove a territory from the list of territories belong to the continent
     *
     * @param territoryName
     */
    public void removeTerritory(String territoryName) {
        int i = 0;
        for (Territory territory : territories) {
            if (territory.getName().compareTo(territoryName) == 0) {
                territories.remove(i);
                return;
            }
            i++;
        }
    }
    
    /**
     * Check if the continent contain a given territory
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
     * Calculate the number of armies within the territory
     *
     * @return the count of all armies within a given continent
     *
     * @throws NullPointerException if territories are not yet allocated to players
     */
    public int getContinentArmies() throws NullPointerException {
        int count = 0;
        for (Territory territory : territories) {
            count += RiskGame.getInstance().getGameMap().getATerritory(territory.getName()).getArmies();
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
        if (RiskGame.getInstance().getGameMap().getATerritory(territories.get(0).getName()).isOwned()) {
            continentOwner = "Player " + RiskGame.getInstance().getGameMap().getATerritory(territories.get(0).getName()).getOwner().getPlayerID();
            for (Territory territory : territories) {
                if (!Objects.equals(continentOwner, "Player " + RiskGame.getInstance().getGameMap().getATerritory(territory.getName()).getOwner().getPlayerID()))
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
