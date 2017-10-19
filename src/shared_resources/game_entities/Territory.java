/* 
 * Risk Game Team 2
 * Territory.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import java.util.Vector;

/**
 * A territory/country in the map
 * Represented by a node in the graph
 * <ul>
 * <li> A territory must belong to one and only one continent
 * <li> A territory must be owned by one and only one player at a time
 * <li> A territory must have at least one neighbor/adjacent territory
 * <li> A territory must contain one or more armies.
 * </ul>
 *
 * @author Team 2
 * @version 1.0
 */
public class Territory {
    
    // region Attributes declaration
    private String name;
    private String continent;
    private Player owner;
    private int armies;
    private Vector<String> neighbors;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate a new territory object given its name and the continent it belongs to.
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
     * Instantiate new territory object given only its name.
     *
     * @param name territory name
     */
    public Territory(String name) {
        this.name = name;
        this.continent = "";
        this.neighbors = new Vector<>();
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the continent.
     *
     * @return the continent
     */
    public String getContinent() {
        return continent;
    }
    
    /**
     * Sets the continent.
     *
     * @param continent the new continent
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * Sets the owner.
     *
     * @param owner the new owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    /**
     * Gets the neighbors.
     *
     * @return the neighbors
     */
    public Vector<String> getNeighbors() {
        return neighbors;
    }
    
    /**
     * Sets the neighbors.
     *
     * @param neighbors the new neighbors
     */
    public void setNeighbors(Vector<String> neighbors) {
        this.neighbors = neighbors;
    }
    
    /**
     * Gets the armies.
     *
     * @return the armies
     */
    public int getArmies() {
        return armies;
    }
    
    /**
     * Sets the armies.
     *
     * @param armies the new armies
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Add a territory as new neighbor.
     *
     * @param territoryName territory name of the neighbor
     */
    public void addNeighbor(String territoryName) {
        if (!neighbors.contains(territoryName)) {
            neighbors.add(territoryName);
        }
    }
    
    /**
     * Removes the neighbor.
     *
     * @param territoryName the territory name
     */
    public void removeNeighbor(String territoryName) {
        if (neighbors.contains(territoryName)) {
            neighbors.remove(territoryName);
        }
    }
    
    /**
     * Removes the continent.
     */
    public void removeContinent() {
        continent = "";
    }
    
    /**
     * Check if the given Territory is a neighbor.
     *
     * @param territoryName the territory name
     *
     * @return true if given territory name is an neighbor, false otherwise
     */
    public boolean isNeighbor(String territoryName) {
        return neighbors.contains(territoryName);
    }
    
    /**
     * Check if the territory is in a given continent.
     *
     * @param continent name of the continent
     *
     * @return true if
     */
    public boolean belongToContinent(String continent) {
        return (this.continent.compareTo(continent) == 0);
    }
    
    /**
     * Check if the owner of the territory is a given player.
     *
     * @param playerID the player ID
     *
     * @return true, if is owned by
     */
    public boolean isOwnedBy(int playerID) {
        return (owner.getPlayerID() == playerID);
    }
    
    /**
     * Add a number of armies into the territory.
     *
     * @param addedArmies amount of armies to add
     */
    public void addArmies(int addedArmies) {
        if (addedArmies > 0) {
            armies += addedArmies;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Checks if is owned.
     *
     * @return true, if is owned
     */
    public boolean isOwned() {
        return (owner != null);
    }
    
    /**
     * Withdraw a number of armies from the territory.
     *
     * @param deductedArmies amount of armies to remove
     */
    public void reduceArmies(int deductedArmies) {
        if (deductedArmies > 0 && deductedArmies <= armies) {
            armies -= deductedArmies;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Get the number of neighbors of the territory.
     *
     * @return the count of neighbors
     */
    public int getNeighborsCount() {
        return neighbors.size();
    }
    
    /**
     * Facilitate comparision between two territory objects.
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
        return this.name.compareTo(territory.name) == 0;
    }
    // endregion
}
