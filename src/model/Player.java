package model;

import java.awt.*;
import java.util.Random;

/**
 * Each Player in a new game has a unique ID number (starting from 1) and the isBot status
 * which determines whether or not that player is a bot or a human controlled Player.
 */
public class Player {
    private static int nextID = 0;
    Random rand = new Random();
    // Will produce only bright / light colours:
    private Color color;
    
    // Private data member of model.Player class
    private int playerID;
    private String playerName;
    private int unallocatedArmies;
    
    public Player() {
        this.playerID = ++Player.nextID;
        setColor();
    }
    
    /* Getters & Setters */
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
    }
    
    public int getUnallocatedArmies() {
        return this.unallocatedArmies;
    }
    
    // public methods
    
    /**
     * Reduces the number of unallocated armies for this player by the specified number.
     * @param num
     */
    public void reduceUnallocatedArmies(int num) {
        this.unallocatedArmies -= num;
    }
    
    /**
     * Increases the number of unallocated armies for this player by the specified number.
     * @param num
     */
    public void addUnallocatedArmies(int num) {
        this.unallocatedArmies += num;
    }
    
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
    
    /**
     * Overriden equals method to check whether or not two Player objects are the same.
     * @param other: the other object to compare with the Player object.
     * @return
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
}
