package model.game_entities;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

/**
 * Each Player in a new game has a unique ID number (starting from 1) and the isBot status
 * which determines whether or not that player is a bot or a human controlled Player.
 */
public class Player {
    // region Attributes declaration
    private static int nextID = 0;
    private Random rand;
    private Color color;
    
    private int playerID;
    private String playerName;
    private int unallocatedArmies;
    private Vector<Card> playersHand;
    private Vector<Territory> territories;
    // endregion
    
    // region Constructors
    public Player() {
        rand = new Random();
        playerID = ++Player.nextID;
        playerName = "Player " + playerID;
        playersHand = new Vector<>();
        territories = new Vector<>();
        setColor();
    }
    // endregion
    
    // region Getters & Setters
    public int getPlayerID() {
        return this.playerID;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public void setUnallocatedArmies(int unallocatedArmies) {
        this.unallocatedArmies = unallocatedArmies;
//        broadcastPlayerChanges();
    }
    
    public int getUnallocatedArmies() {
        return this.unallocatedArmies;
    }
    
    public Vector<Card> getPlayersHand() {
        return this.playersHand;
    }
    
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
    
    public void addTerritory(Territory territory) {
        if (!territories.contains(territory)) {
            territories.add(territory);
        }
    }
    
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
    
    /**
     * Player color is randomly generated when a new player object is created
     */
    private void setColor() {
        float r, g, b;
        r = (float) (rand.nextFloat() / 2f + 0.5);
        g = (float) (rand.nextFloat() / 2f + 0.5);
        b = (float) (rand.nextFloat() / 2f + 0.5);
        this.color = new Color(r, g, b);
    }
    // endregion
    
    
}
