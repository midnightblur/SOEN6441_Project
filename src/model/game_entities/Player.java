/*
  @file  Player.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package model.game_entities;

import utilities.Config;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

/**
 * Each Player in a new game has a unique ID number (starting from 1) and the isBot status
 * which determines whether or not that player is a bot or a human controlled Player.
 */
public class Player {
    
    /** The next ID. */
    // region Attributes declaration
    private static int nextID = 0;
    
    /** The rand. */
    private Random rand;
    
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
    
    /**
     * Instantiates a new player.
     */
    // region Constructors
    public Player() {
        rand = new Random();
        playerID = ++Player.nextID;
        playerName = "Player " + playerID;
        playersHand = new Vector<>();
        territories = new Vector<>();
        color = Config.PLAYER_COLOR[playerID - 1];
    }
    // endregion
    
    /**
     * Gets the player ID.
     *
     * @return the player ID
     */
    // region Getters & Setters
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
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
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
     * Sets the unallocated armies.
     *
     * @param unallocatedArmies the new unallocated armies
     */
    public void setUnallocatedArmies(int unallocatedArmies) {
        this.unallocatedArmies = unallocatedArmies;
//        broadcastPlayerChanges();
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
//        broadcastPlayerChanges();
    }
    
    /**
     * Increases the number of unallocated armies for this player by the specified number.
     *
     * @param num The int index of the number o unallocated armies to add
     */
    public void addUnallocatedArmies(int num) {
        this.unallocatedArmies += num;
//        broadcastPlayerChanges();
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
    // endregion
    
    // region Private methods
    // endregion
    
    
}
