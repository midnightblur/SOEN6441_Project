/* 
 * Risk Game Team 2
 * Player.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import shared_resources.utilities.Config;

import java.awt.*;
import java.util.Vector;

import static shared_resources.utilities.Config.PLAYER_COLOR;

/**
 * Each Player in a new game has a unique ID number (starting from 1) and the isBot status
 * which determines whether or not that player is a bot or a human controlled Player.
 *
 * @author Team 2
 * @version 1.0
 */
public class Player {
    
    // region Attributes declaration
    private static int nextID = 0;
    private Color color;
    private int playerID;
    private String playerName;
    private int unallocatedArmies;
    private Vector<Card> playersHand;
    private Vector<Territory> territories;
    private Config.GAME_STATES gameState;
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
        gameState = Config.GAME_STATES.PLAYER_REINFORCEMENT;
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
    
    /**
     * Gets the current phase of the player
     *
     * @return the game phase
     */
    public Config.GAME_STATES getGameState() {
        return gameState;
    }
    
    /**
     * Set the game phase for the player
     *
     * @param gameState the next phase
     */
    public void setGameState(Config.GAME_STATES gameState) {
        this.gameState = gameState;
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
        if (other == this) {
            return true;
        }
        if (!(other instanceof Player)) {
            return false;
        }
        
        Player tempPlayer = (Player) other;
        return this.playerID == tempPlayer.playerID
                && ((this.playerName == null && tempPlayer.playerName == null)
                || this.playerName.equals(tempPlayer.playerName))
                && this.unallocatedArmies == tempPlayer.unallocatedArmies;
    }
    
    /**
     * Resets the static counter, nextID, in Player class to zero.
     */
    public static void resetStaticNextID() {
        nextID = 0;
    }
    // endregion
    
    // region Private methods
    
    /**
     * Implement the Reinforcement Phase of a particular player
     */
    private void reinforcement() {
    
    }
    
    /**
     * Implement the Attack Phase of a particular player
     */
    private void attack() {
    
    }
    
    /**
     * Implement the Foritifcation Phase of a particular player
     */
    private void fortification() {
    
    }
    // endregion
}
