/* 
 * Risk Game Team 2
 * Player.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import game_play.model.GamePlayModel;
import shared_resources.utilities.Config;

import java.awt.*;
import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.GAME_STATES.PLAYER_REINFORCEMENT;
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
        gameState = Config.GAME_STATES.PLAY;
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
                || this.playerName.compareTo(tempPlayer.playerName) == 0)
                && this.unallocatedArmies == tempPlayer.unallocatedArmies;
    }
    
    /**
     * Resets the static counter, nextID, in Player class to zero.
     */
    public static void resetStaticNextID() {
        nextID = 0;
    }
    
    // region Reinforcement Phase
    /**
     * Implement the Reinforcement Phase of a particular player
     */
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        switch (gameState) {
            case PLAYER_TRADE_CARDS:
                return tradeInCards(gamePlayModel, selectedCards);
            case PLAYER_REINFORCEMENT:
                distributeArmies(gamePlayModel, armiesToPlace);
                break;
        }
        return "";
    }
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmiesReinforcement in the game_entities.
     */
    private void distributeArmies(GamePlayModel gamePlayModel, Map<Territory, Integer> armiesToPlace) {
        for (Map.Entry<Territory, Integer> entry : armiesToPlace.entrySet()) {
            entry.getKey().addArmies(entry.getValue());
            reduceUnallocatedArmies(entry.getValue());
        }
    }
    
    /**
     * This method processes the exchange of cards to the armies if the user selected cards
     * make a valid set. The method checks for a selection of exactly three cards, and checks
     * for either three cards of the same type, or 3 cards one of each type. If so, those cards
     * are removed from the players hand, and the number of armies (unallocated armies) are
     * rewarded according to the current army value. The army value starts at 5 at the beginning
     * of the game, but increases by 5 every time a player makes a valid card exchange move.
     *
     * @param gamePlayModel The Game Play Model containing all the state of the game
     * @param selectedCards Vector of Strings that details the type of cards in the player's possession
     *
     * @return String for the error message to validate the result of the trade in
     */
    private String tradeInCards(GamePlayModel gamePlayModel, Vector<String> selectedCards) {
        if (selectedCards.size() == 3) {
            /* check if selected cards are three of a kind or one of each */
            int choice = 0;
            if (selectedCards.elementAt(0).equals(selectedCards.elementAt(1))
                    && selectedCards.elementAt(0).equals(selectedCards.elementAt(2))) {
                choice = 1;
            } else if (!selectedCards.elementAt(0).equals(selectedCards.elementAt(1))
                    && !selectedCards.elementAt(0).equals(selectedCards.elementAt(2))
                    && !selectedCards.elementAt(1).equals(selectedCards.elementAt(2))) {
                choice = 2;
            }
            
            /* carry out card trading according to the choice */
            int counter = 0;
            if (choice == 1) {  // for three of a kind exchange
                for (int i = 0; i < playersHand.size(); i++) {
                    if (playersHand.get(i).getCardType().name().compareTo(selectedCards.firstElement()) == 0) {
                        counter++;
                    }
                }
                if (counter >= 3) {
                    Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.firstElement()));
                    for (int i = 0; i < selectedCards.size(); i++) {
                        playersHand.remove(tempCard);
                        gamePlayModel.getDeck().add(tempCard);
                    }
                    playersHand.trimToSize();
                    addUnallocatedArmies(gamePlayModel.getArmyValue());
                    gamePlayModel.setArmyValue(gamePlayModel.getArmyValue() + 5);
                }
            } else if (choice == 2) {  // for one of each exchange
                for (int cardIndex = 0; cardIndex < selectedCards.size(); cardIndex++) {
                    Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.elementAt(cardIndex)));
                    if (playersHand.contains(tempCard)) {
                        counter++;
                    }
                }
                if (counter == 3) {
                    for (int cardIndex = 0; cardIndex < selectedCards.size(); cardIndex++) {
                        Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.elementAt(cardIndex)));
                        playersHand.remove(tempCard);
                        gamePlayModel.getDeck().add(tempCard);
                    }
                    playersHand.trimToSize();
                    addUnallocatedArmies(gamePlayModel.getArmyValue());
                    gamePlayModel.setArmyValue(gamePlayModel.getArmyValue() + 5);
                }
            } else {
                return "No cards traded in!\nPlease select 3 cards of the same type or one of each type.";
            }
            setGameState(PLAYER_REINFORCEMENT);
            return "Cards successfully traded in!";
        } else {
            return "No cards traded in!\nPlease select exactly 3 cards.\n(all of same type or one of each type)";
        }
    }
    // endregion
    
    /**
     * Implement the Attack Phase of a particular player
     */
    public void attack(GamePlayModel gamePlayModel) {
    
    }
    
    // region Fortification Phase
    /**
     * Implement the Foritifcation Phase of a particular player
     *
     * The method gives a player an option to move any number of armies from one country to
     * another. The method only allows only one such move that is valid, which requires that
     * the two countries that the player picks must be owned by that player, be different
     * territories from one another, be adjacent to one another, and must have more armies
     * in the territory than the number of armies specified by the player (a territory must
     * have more than 1 army at minimum).
     *
     * @param gamePlayModel   The GamePlayModel containing the state of the game
     * @param sourceTerritory String value of the name of the source Territory
     * @param targetTerritory String value of the name of the target Territory
     * @param noOfArmies      Integer value of the number of armies to be moved
     *
     * @return String value of the messages that will be displayed to the user
     *
     */
    public String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies) {
        Territory fromTerritory = gamePlayModel.getGameMap().getATerritory(sourceTerritory);
        Territory toTerritory = gamePlayModel.getGameMap().getATerritory(targetTerritory);
    
        // Validate if the two territories are owned by the player, are different, and are neighbors.
        if (!fromTerritory.isOwnedBy(playerID) ||
                !toTerritory.isOwnedBy(playerID) ||
                fromTerritory == toTerritory) {
            return "No armies moved!\nYou must pick two Territories that are neighbors.";
        }
    
        if (fromTerritory.getArmies() == 1 || fromTerritory.getArmies() <= noOfArmies) {
            return "No armies moved!\nYou must always have at least 1 army in each Territory";
        }
    
        fromTerritory.reduceArmies(noOfArmies);
        toTerritory.addArmies(noOfArmies);
    
        return "Successfully moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory + ".";
    }
    
    /**
     * Set the next player phase depending on the current phase and current state of the player
     */
    public void nextPhase() {
        switch (gameState) {
            case PLAYER_TRADE_CARDS:
                gameState = Config.GAME_STATES.PLAYER_REINFORCEMENT;
                break;
            case PLAYER_REINFORCEMENT:
                gameState = Config.GAME_STATES.PLAYER_ATTACK;
                break;
            case PLAYER_ATTACK:
                gameState = Config.GAME_STATES.PLAYER_FORTIFICATION;
                break;
            case PLAYER_FORTIFICATION:
                if (playersHand.size() >= 5) {
                    gameState = Config.GAME_STATES.PLAYER_TRADE_CARDS;
                } else {
                    gameState = Config.GAME_STATES.PLAYER_REINFORCEMENT;
                }
                break;
            default: // the player does not have a game state in his very first turn
                if (playersHand.size() >= 5) {
                    gameState = Config.GAME_STATES.PLAYER_TRADE_CARDS;
                } else {
                    gameState = Config.GAME_STATES.PLAYER_REINFORCEMENT;
                }
                break;
        }
    }
    // endregion
}
