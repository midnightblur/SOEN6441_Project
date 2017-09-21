package Models;

/**
 * Each Player in a new game has a unique ID number (starting from 1) and the isBot status
 * which determines whether or not that player is a bot or a human controlled Player.
 */
public class Player {
    private static int nextID = 0;

    // Private data member of Models.Player class
    private int playerID;
    private String playerName;
    private boolean isBot;

    public Player() {
        this.playerID = ++Player.nextID;
        this.isBot = true;
    }

    public Player(boolean isBot) {
        this.playerID = ++Player.nextID;
        this.isBot = isBot;
    }

    // Getters & Setters

    public int getID() {
        return this.playerID;
    }

    public boolean getIsBot() {
        return this.isBot;
    }
}
