package model.game_entities;

import java.util.Vector;

/**
 * A territory/country in the map
 * Represented by a node in the graph
 * A territory must belong to one and only one continent
 * A territory must be owned by one and only one player at a time
 * A territory must have at least one neighbor/adjacent territory
 * A territory must contain one or more armies
 */
public class Territory {
    /* Private data members of model.game_entities.Territory class */
    private String name;
    private String continent;
    private Player owner;
    private int armies;
    private Vector<String> neighbors;

    /* Constructors */
    
    /**
     * Instantiate a new territory object given its name and the continent it belongs to
     *
     * @param name
     * @param continent
     */
    public Territory(String name, String continent) {
        this.name = name;
        this.continent = continent;
        this.neighbors = new Vector<>();
    }
    
    public Territory(String name) {
        this.name = name;
        this.continent = "";
        this.neighbors = new Vector<>();
    }
    
    /* Getters & Setters */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getContinent() {
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    public Vector<String> getNeighbors() {
        return neighbors;
    }
    
    public void setNeighbors(Vector<String> neighbors) {
        this.neighbors = neighbors;
    }
    
    public int getArmies() {
        return armies;
    }
    
    public void setArmies(int armies) {
        this.armies = armies;
    }
    
    /* Public methods */
    
    /**
     * Add a territory as new neighbour
     *
     * @param territoryName
     */
    public void addNeighbor(String territoryName) {
        if (!neighbors.contains(territoryName)) {
            neighbors.add(territoryName);
        }
    }
    
    public void removeNeighbour(String territoryName) {
        if (neighbors.contains(territoryName)) {
            neighbors.remove(territoryName);
        }
    }
    
    public void removeContinent() {
        continent = "";
    }
    
    /**
     * Check if the given Territory is a neighbour
     *
     * @param territoryName
     *
     * @return
     */
    public boolean isNeighbor(String territoryName) {
        return neighbors.contains(territoryName);
    }
    
    /**
     * Check if the territory is in a given continent
     *
     * @param continent
     *
     * @return
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
     * @param addedArmies
     */
    public void addArmies(int addedArmies) {
        if (addedArmies > 0) {
            armies += addedArmies;
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
     * @param deductedArmies
     */
    public void reduceArmies(int deductedArmies) {
        if (deductedArmies > 0 && deductedArmies <= armies) {
            armies -= deductedArmies;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Get the number of neighbours of the territory
     *
     * @return
     */
    public int getNeighborsCount() {
        return neighbors.size();
    }
    
    /**
     * Facilitate comparision between two territory objects
     *
     * @param other
     *
     * @return
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
}
