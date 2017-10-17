package model.game_entities;

import java.util.Observable;
import java.util.Vector;

/**
 * A territory/country in the map
 * Represented by a node in the graph
 * A territory must belong to one and only one continent
 * A territory must be owned by one and only one player at a time
 * A territory must have at least one neighbor/adjacent territory
 * A territory must contain one or more armies
 */
public class Territory extends Observable {
    //region Attributes declaration
    private String name;
    private String continent;
    private Player owner;
    private int armies;
    private Vector<String> neighbors;
    //endregion

    //region Constructors
    /**
     * Instantiate a new territory object given its name and the continent it belongs to
     *
     * @param name      territory name
     * @param continent continent name
     */
    public Territory(String name, String continent) {
        this.name = name;
        this.continent = continent;
        this.neighbors = new Vector<>();
    }
    
    /**
     * Instantiate new territory object given only its name
     *
     * @param name territory name
     */
    public Territory(String name) {
        this.name = name;
        this.continent = "";
        this.neighbors = new Vector<>();
    }
    //endregion
    
    /* Getters & Setters */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        broadcastTerritoryChanges();
    }
    
    public String getContinent() {
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
        broadcastTerritoryChanges();
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
        broadcastTerritoryChanges();
    }
    
    public Vector<String> getNeighbors() {
        return neighbors;
    }
    
    public void setNeighbors(Vector<String> neighbors) {
        this.neighbors = neighbors;
        broadcastTerritoryChanges();
    }
    
    public int getArmies() {
        return armies;
    }
    
    public void setArmies(int armies) {
        this.armies = armies;
        broadcastTerritoryChanges();
    }
    
    //region Public methods
    /**
     * Add a territory as new neighbor
     *
     * @param territoryName territory name of the neighbor
     */
    public void addNeighbor(String territoryName) {
        if (!neighbors.contains(territoryName)) {
            neighbors.add(territoryName);
            broadcastTerritoryChanges();
        }
    }
    
    public void removeNeighbor(String territoryName) {
        if (neighbors.contains(territoryName)) {
            neighbors.remove(territoryName);
            broadcastTerritoryChanges();
        }
    }
    
    public void removeContinent() {
        continent = "";
    }
    
    /**
     * Check if the given Territory is a neighbor
     *
     * @param territoryName
     *
     * @return true if given territory name is an neighbor, false otherwise
     */
    public boolean isNeighbor(String territoryName) {
        return neighbors.contains(territoryName);
    }
    
    /**
     * Check if the territory is in a given continent
     *
     * @param continent name of the continent
     *
     * @return true if
     */
    public boolean belongToContinent(String continent) {
        return (this.continent.compareTo(continent) == 0);
    }
    
    /**
     * Check if the owner of the territory is a given player
     *
     * @param playerID
     *
     * @return
     */
    public boolean isOwnedBy(int playerID) {
        return (owner.getPlayerID() == playerID);
    }
    
    /**
     * Add a number of armies into the territory
     *
     * @param addedArmies amount of armies to add
     */
    public void addArmies(int addedArmies) {
        if (addedArmies > 0) {
            armies += addedArmies;
            broadcastTerritoryChanges();
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public boolean isOwned() {
        return (owner != null);
    }
    
    /**
     * Withdraw a number of armies from the territory
     *
     * @param deductedArmies amount of armies to remove
     */
    public void reduceArmies(int deductedArmies) {
        if (deductedArmies > 0 && deductedArmies <= armies) {
            armies -= deductedArmies;
            broadcastTerritoryChanges();
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Get the number of neighbors of the territory
     *
     * @return the count of neighbors
     */
    public int getNeighborsCount() {
        return neighbors.size();
    }
    
    /**
     * Facilitate comparision between two territory objects
     *
     * @param other other object
     *
     * @return true if equals, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Territory)) {
            return false;
        }
        
        Territory territory = (Territory) other;
        if (this.name.compareTo(territory.name) == 0) {
            return true;
        }
        
        return false;
    }
    //endregion
    
    //region Private methods
    /**
     * Method to update the GamePlayModel and notify the Observer.
     */
    private void broadcastTerritoryChanges() {
        setChanged();
        notifyObservers();
    }
    //endregion
}
