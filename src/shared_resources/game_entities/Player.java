/* 
 * Risk Game Team 2
 * Player.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import java.awt.*;
import java.util.Vector;

import static shared_resources.utilities.Config.PLAYER_COLOR;

/**
 * Each Player in a new game has a unique ID number (starting from 1) and the isBot status
 * which determines whether or not that player is a bot or a human controlled Player.
 */
public class Player {
    
    // region Attributes declaration
    /** The next ID. */
    private static int nextID = 0;
    
    /** The color. */
    private Color color;
    
    /** The player ID. */
    private int playerID;
    
    /** The player name. */
    private String playerName;
    
    /** The unallocated armies. */
    private int unallocatedArmies;
    
    /** The players hand. */
    private Vector<Card> playersHand;
    
    /** The territories. */
    private Vector<Territory> territories;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new player.
     */
    public Player() {
        playerID = ++Player.nextID;
        playerName = "Player " + playerID;
        playersHand = new Vector<>();
        territories = new Vector<>();
        color = PLAYER_COLOR[playerID - 1];
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the player ID.
     *
     * @return the player ID
     */
    public int getPlayerID() {
        return this.playerID;
    }
    
    /**
     * Gets the player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }
    
    /**
     * Sets the player name.
     *
     * @param playerName the new player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    /**
     * Gets the color of a player.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Gets the unallocated armies.
     *
     * @return the unallocated armies
     */
    public int getUnallocatedArmies() {
        return this.unallocatedArmies;
    }
    
    /**
     * Sets the unallocated armies.
     *
     * @param unallocatedArmies the new unallocated armies
     */
    public void setUnallocatedArmies(int unallocatedArmies) {
        this.unallocatedArmies = unallocatedArmies;
    }
    
    /**
     * Gets the players hand.
     *
     * @return the players hand
     */
    public Vector<Card> getPlayersHand() {
        return this.playersHand;
    }
    
    /**
     * Gets the territories.
     *
     * @return the territories
     */
    public Vector<Territory> getTerritories() {
        return territories;
    }
    
    // endregion
    
    // region Public methods
    
    /**
     * Reduces the number of unallocated armies for this player by the specified number.
     *
     * @param num The int index of the number of unallocated armies to reduce
     */
    public void reduceUnallocatedArmies(int num) {
        this.unallocatedArmies -= num;
    }
    
    /**
     * Increases the number of unallocated armies for this player by the specified number.
     *
     * @param num The int index of the number o unallocated armies to add
     */
    public void addUnallocatedArmies(int num) {
        this.unallocatedArmies += num;
    }
    
    /**
     * Adds a card to the player's hand.
     *
     * @param card An object of Card class to be added to the players hand
     */
    public void addCardToPlayersHand(Card card) {
        this.playersHand.add(card);
    }
    
    /**
     * Adds the territory.
     *
     * @param territory the territory
     */
    public void addTerritory(Territory territory) {
        if (!territories.contains(territory)) {
            territories.add(territory);
        }
    }
    
    /**
     * Removes the territory.
     *
     * @param territoryName the territory name
     */
    public void removeTerritory(String territoryName) {
        for (Territory territory : territories) {
            if (territory.getName().compareTo(territoryName) == 0) {
                territories.remove(territory);
                return;
            }
        }
    }
    
    /**
     * Override equals method to check whether or not two Player objects are the same.
     *
     * @param other The other object to compare with the Player object.
     *
     * @return Boolean index that tells whether or not the two Player objects have the same attribute values
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        Player tempPlayer = (Player) other;
        if (this.playerID == tempPlayer.playerID
                && ((this.playerName == null && tempPlayer.playerName == null)
                || this.playerName.equals(tempPlayer.playerName))
                && this.unallocatedArmies == tempPlayer.unallocatedArmies) {
            return true;
        }
        return false;
    }
    
    /**
     * Resets the static counter, nextID, in Player class to zero.
     */
    public static void resetStaticNextID() {
        nextID = 0;
    }
    // endregion
    
    // region Private methods
    // endregion
    
}
