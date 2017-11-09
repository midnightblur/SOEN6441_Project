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

import static shared_resources.utilities.Config.GAME_STATES.REINFORCEMENT;
import static shared_resources.utilities.Config.PLAYER_COLOR;
import static shared_resources.utilities.Config.log;

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
    private GamePlayModel.PLAYER_STATUS playerStatus;
    private boolean hasConqueredTerritories;
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
        playerStatus = GamePlayModel.PLAYER_STATUS.IN_GAME;
        hasConqueredTerritories = false;
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Resets the static counter, nextID, in Player class to zero.
     */
    public static void resetStaticNextID() {
        nextID = 0;
    }
    
    /**
     * Gets the player ID.
     *
     * @return the player ID
     */
    public int getPlayerID() {
        return this.playerID;
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
    
    /**
     * Obtains the continents owned by this player
     *
     * @param gameMap the game map
     *
     * @return a vector of continents owned by this player
     */
    public Vector<Continent> getContinents(GameMap gameMap) {
        Vector<Continent> continents = new Vector<>();
        for (Continent c : gameMap.getContinents().values()) {
            if (c.getContinentOwner(gameMap).equals(getPlayerName())) {
                continents.add(c);
            }
        }
        return continents;
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
     * Get the total armies for this player
     *
     * @return the number of armies for this player
     */
    public int getTotalArmiesCount() {
        int armies = 0;
        for (Territory t : getTerritories()) {
            armies += t.getArmies();
        }
        return armies;
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
     * Gets the players hand.
     *
     * @return the players hand
     */
    public Vector<Card> getPlayersHand() {
        return this.playersHand;
    }
    
    /**
     * Gets the player status
     *
     * @return the status of passed player object
     */
    public GamePlayModel.PLAYER_STATUS getPlayerStatus() {
        return playerStatus;
    }
    
    /**
     * Sets the player status
     *
     * @param playerStatus the status to be set for this player object
     */
    public void setPlayerStatus(GamePlayModel.PLAYER_STATUS playerStatus) {
        this.playerStatus = playerStatus;
    }
    
    /**
     * Check if the player has conquered territories before going to fortification phase
     *
     * @return true if player conquered, false otherwise
     */
    public boolean hasConqueredTerritories() {
        return hasConqueredTerritories;
    }
    
    /**
     * Sets the "has conquered territories" attribute of the player to false
     *
     * @param hasConqueredTerritories the boolean indicating if player conquered territories
     */
    public void setHasConqueredTerritories(boolean hasConqueredTerritories) {
        this.hasConqueredTerritories = hasConqueredTerritories;
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
     * Adds the territory.
     *
     * @param territory the territory
     */
    public void addTerritory(Territory territory) {
        if (!territories.contains(territory)) {
            territories.add(territory);
        }
    }
    
    // endregion
    
    // region Public methods
    
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
     * Implement the Reinforcement Phase of a particular player
     *
     * @param gamePlayModel the game play model
     * @param selectedCards the selected cards
     * @param armiesToPlace the amount of armies to be placed
     *
     * @return the message to user if reinforcement was successful or not
     */
    public String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace) {
        switch (gameState) {
            case TRADE_CARDS:
                log.append(gamePlayModel.getCurrentPlayer().getPlayerName() + " wants to trade-in cards...");
                return tradeInCards(gamePlayModel, selectedCards);
            case REINFORCEMENT:
                log.append(gamePlayModel.getCurrentPlayer().getPlayerName() + " wants to distribute armies...");
                distributeArmies(armiesToPlace);
                break;
        }
        return "";
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
    public String tradeInCards(GamePlayModel gamePlayModel, Vector<String> selectedCards) {
        int previousUnallocatedArmies = this.getUnallocatedArmies();
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
                for (Card aPlayersHand : playersHand) {
                    if (aPlayersHand.getCardType().name().compareTo(selectedCards.firstElement()) == 0) {
                        counter++;
                    }
                }
                if (counter >= 3) {
                    Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.firstElement()));
                    for (int i = 0; i < selectedCards.size(); i++) {
                        playersHand.remove(tempCard);
                        gamePlayModel.getDeck().add(tempCard);
                        log.append(playerName + " is trading card " + tempCard.getCardType() + ", having an army value of " + gamePlayModel.getArmyValue());
                    }
                    playersHand.trimToSize();
                    addUnallocatedArmies(gamePlayModel.getArmyValue());
                    gamePlayModel.setArmyValue(gamePlayModel.getArmyValue() + 5);
                    log.append("Total armies gained by " + playerName + " is " + (this.getUnallocatedArmies() - previousUnallocatedArmies));
                    log.append("New army value is now " + gamePlayModel.getArmyValue());
                    
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
                        log.append(playerName + " is trading card " + tempCard.getCardType() + ", having an army value of " + gamePlayModel.getArmyValue());
                    }
                    playersHand.trimToSize();
                    addUnallocatedArmies(gamePlayModel.getArmyValue());
                    gamePlayModel.setArmyValue(gamePlayModel.getArmyValue() + 5);
                    log.append("Total armies gained by " + playerName + " is " + (this.getUnallocatedArmies() - previousUnallocatedArmies));
                    log.append("New army value is now " + gamePlayModel.getArmyValue());
                }
                
                
            } else {
                return "No cards traded in!\nPlease select 3 cards of the same type or one of each type.";
            }
            setGameState(REINFORCEMENT);
            return "Cards successfully traded in!";
        } else {
            return "No cards traded in!\nPlease select exactly 3 cards.\n(all of same type or one of each type)";
        }
    }
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmiesReinforcement in the game_entities.
     *
     * @param armiesToPlace the amount of armies to place
     */
    public void distributeArmies(Map<Territory, Integer> armiesToPlace) {
        for (Map.Entry<Territory, Integer> entry : armiesToPlace.entrySet()) {
            entry.getKey().addArmies(entry.getValue());
            log.append(playerName + " placed " + entry.getValue() + " armies on " + entry.getKey().getName());
            reduceUnallocatedArmies(entry.getValue());
        }
    }
    
    /**
     * Gets the unallocated armies.
     *
     * @return the unallocated armies
     */
    public int getUnallocatedArmies() {
        return this.unallocatedArmies;
    }
    
    // region Reinforcement Phase
    
    /**
     * Sets the unallocated armies.
     *
     * @param unallocatedArmies the new unallocated armies
     */
    public void setUnallocatedArmies(int unallocatedArmies) {
        this.unallocatedArmies = unallocatedArmies;
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
     * Reduces the number of unallocated armies for this player by the specified number.
     *
     * @param num The int index of the number of unallocated armies to reduce
     */
    public void reduceUnallocatedArmies(int num) {
        this.unallocatedArmies -= num;
    }
    // endregion
    
    // region Attack Phase
    
    /**
     * Implements the Attack Phase of particular player.
     *
     * This method allows a player to make an attack move with an opponent player based on the
     * current battle state of the game. The method rolls the number of dice for the attacker
     * and the defender, and decides the outcome of the battle by comparing the highest roll
     * depending on the number of dice used by both players. Then also checks if the attacking
     * player in current battle has conquered any territories, or eliminated any players in
     * that attack turn.
     *
     * @param gamePlayModel the game play model
     *
     * @return the message to the user if attack phase was successful or not
     */
    public String attack(GamePlayModel gamePlayModel) {
        Battle currentBattle = gamePlayModel.getCurrentBattle();
        Territory attackingTerritory = currentBattle.getAttackingTerritory();
        Territory defendingTerritory = currentBattle.getDefendingTerritory();
        int numOfAtkDice = currentBattle.getAttackerDice().getRollsCount();
        int numOfDefDice = currentBattle.getDefenderDice().getRollsCount();

        /* Both players roll dice */
        currentBattle.attackerRollDice();
        currentBattle.defenderRollDice();
        log.append(playerName + " is attacking from " + attackingTerritory.getName() + " > dice rolled: " +
                currentBattle.getAttackerDice().getRollsResult().toString() + " vs. " + defendingTerritory.getName() +
                " > dice rolled: " + currentBattle.getDefenderDice().getRollsResult().toString());

        /* Decide the battle */
        // Compare the best result of both players
        int bestOfAttacker = currentBattle.getAttackerDice().getTheBestResult();
        int bestOfDefender = currentBattle.getDefenderDice().getTheBestResult();
        decideResult(currentBattle, attackingTerritory, defendingTerritory, bestOfAttacker, bestOfDefender);
        
        // If both players roll at least 2 dice
        if (numOfAtkDice >= 2 && numOfDefDice >= 2) {
            int secondBestOfAttacker = currentBattle.getAttackerDice().getSecondBestResult();
            int secondBestOfDefender = currentBattle.getDefenderDice().getSecondBestResult();
            decideResult(currentBattle, attackingTerritory, defendingTerritory, secondBestOfAttacker, secondBestOfDefender);
        }
        log.append("Defended territory " + defendingTerritory.getName() + " loses " + currentBattle.getDefenderLossCount() + " armies");
        log.append("Attacker territory " + attackingTerritory.getName() + " loses " + currentBattle.getAttackerLossCount() + " armies");
        
        return "";
    }
    
    /**
     * This method decides the outcome of the current battle by comparing the attacker's dice
     * roll value and the defender's dice roll value. Depending on the result, the method
     * increases the lose count for the player who rolled a lower value than the opponent.
     *
     * @param currentBattle      Battle object of the current battle state
     * @param attackingTerritory Territory object of the territory that is attacking
     * @param defendingTerritory Territory object of the territory that is defending
     * @param attackerRoll       Integer value of the attacker's dice roll
     * @param defenderRoll       Integer value of the defender's dice roll
     */
    private void decideResult(Battle currentBattle, Territory attackingTerritory, Territory defendingTerritory,
                              int attackerRoll, int defenderRoll) {
        if (attackerRoll > defenderRoll) { // the attacker wins
            defendingTerritory.reduceArmies(1);
            currentBattle.increaseDefenderLossCount();
        } else { // the defender wins
            attackingTerritory.reduceArmies(1);
            currentBattle.increaseAttackerLossCount();
        }
    }
    
    /**
     * This method gives control of the conquered territory to the attacking player who eliminated all of the armies
     * in an opponent's defending territory, and makes the conquering player to move a number of armies to it from the
     * attacking territory. A randomly drawn card from the deck is also given to the conquering player.
     *
     * @param gamePlayModel The GamePlayModel containing the state of the game
     * @param armiesToMove  The number of armies to move
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String conquer(GamePlayModel gamePlayModel, int armiesToMove) {
        Territory attackingTerritory = gamePlayModel.getCurrentBattle().getAttackingTerritory();
        Territory defendingTerritory = gamePlayModel.getCurrentBattle().getDefendingTerritory();
        
        /* Change owner of the conquered territory, and move armies */
        attackingTerritory.reduceArmies(armiesToMove);
        defendingTerritory.addArmies(armiesToMove);
        log.append(attackingTerritory.getOwner().getPlayerName() + " conquered " + defendingTerritory.getName());
        
        return "";
    }
    
    /**
     * This method gives all of the current cards of the eliminated Player (from the latest attack) to the conquering
     * player.
     *
     * @param eliminatedPlayer Player object that has no more territories left and is declared eliminated
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String eliminated(Player eliminatedPlayer) {
        if (eliminatedPlayer.playersHand.size() != 0) {
            for (Card card : eliminatedPlayer.playersHand) {
                this.addCardToPlayersHand(card);
            }
        }
        
        return "";
    }
    
    /**
     * Adds a card to the player's hand.
     *
     * @param card An object of Card class to be added to the players hand
     */
    public void addCardToPlayersHand(Card card) {
        this.playersHand.add(card);
    }
    // endregion
    
    // region Fortification Phase
    
    /**
     * Implement the Fortification Phase of a particular player.
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
        
        log.append(playerName + " moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory);
        return "Successfully moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory + ".";
    }
    
    /**
     * Set the next player phase depending on the current phase and current state of the player
     */
    public void nextPhase() {
        switch (gameState) {
            case TRADE_CARDS:
                gameState = Config.GAME_STATES.REINFORCEMENT;
                break;
            case REINFORCEMENT:
                gameState = Config.GAME_STATES.ATTACK_PREPARE;
                break;
            case ATTACK_PREPARE:
            case ATTACK_BATTLE:
                gameState = Config.GAME_STATES.FORTIFICATION;
                break;
            case FORTIFICATION:
                if (playersHand.size() >= 5) {
                    gameState = Config.GAME_STATES.TRADE_CARDS;
                } else {
                    gameState = Config.GAME_STATES.REINFORCEMENT;
                }
                break;
            default: // the player does not have a game state in his very first turn
                if (playersHand.size() >= 5) {
                    gameState = Config.GAME_STATES.TRADE_CARDS;
                } else {
                    gameState = Config.GAME_STATES.REINFORCEMENT;
                }
                break;
        }
    }
    // endregion
}
