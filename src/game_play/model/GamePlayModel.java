/* 
 * Risk Game Team 2
 * GamePlayModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import shared_resources.game_entities.*;

import java.util.*;

import static shared_resources.utilities.Config.GAME_STATES;
import static shared_resources.utilities.Config.GAME_STATES.*;
import static shared_resources.utilities.Config.INITIAL_ARMY_RATIO;

/**
 * This class is responsible for updating the game states (phases) and broadcasting the changes
 * to the view, and carrying out appropriate Risk game play functions according to the game states.
 * The three main phases and the respective main functions during those phases are as follows:
 * <ol>
 * <li>Startup Phase
 * <ul>
 *     <li>Initialize a new game
 *     <li>Randomly (but fairly) distribute territories to players
 * </ul>
 * <li>Reinforcement Phase
 * <ul>
 *     <li>Allow current player to trade in cards
 *     <li>Calculate and give unallocated armies to current player
 *     <li>Allow current player to allocate armies in territories
 * </ul>
 * <li>Attack Phase
 * <ul>
 *     <li>Attack neighboring territories owned by other player(s)
 *     <li>Give card(s) to current player if that player takes over any territories that turn
 * </ul>
 * <li>Fortification Phase
 * <ul>
 *     <li>Allow current player to make one valid fortification move
 * </ul>
 * </ol>
 *
 * @author Team 2
 * @version 1.0
 */
public class GamePlayModel extends Observable {
    
    // region Attributes declaration
    private static final int DEFAULT_ARMY_VALUE = 5;
    private static GamePlayModel instance = null;
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private GAME_STATES gameState;
    private Player currentPlayer;
    private PlayerTerritoriesModel playerTerritoriesModel;
    private int armyValue;
    private Vector<Card> deck;
    private Vector<Player> players;
    private Random rand;
    // endregion
    
    // region Constructors
    
    /**
     * Private constructor preventing any other class from instantiating GamePlayModel object.
     */
    private GamePlayModel() {
        armyValue = DEFAULT_ARMY_VALUE;
        mapTableModel = new MapTableModel();
        deck = new Vector<>();
        players = new Vector<>();
        gameState = ENTRY_MENU;
        rand = new Random();
        playerTerritoriesModel = new PlayerTerritoriesModel();
    }
    
    /**
     * Static instance method to determine if an object of GamePlayModel already exists.
     *
     * @return instance of the singleton GamePlayModel object
     */
    public static GamePlayModel getInstance() {
        if (instance == null) {
            instance = new GamePlayModel();
        }
        return instance;
    }
    // endregion
    
    // region Getters and Setters
    /**
     * Gets the game map.
     *
     * @return the game map
     */
    public GameMap getGameMap() {
        return gameMap;
    }
    
    /**
     * Sets new gameMap.
     *
     * @param gameMap New value of gameMap.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Gets the map table game_entities.
     *
     * @return the map table game_entities
     */
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
    
    /**
     * Gets the players.
     *
     * @return the players
     */
    public Vector<Player> getPlayers() {
        return this.players;
    }
    
    /**
     * Gets the deck.
     *
     * @return the deck
     */
    public Vector<Card> getDeck() {
        return this.deck;
    }
    
    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    
    /**
     * Sets the current player.
     *
     * @param player the new current player
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        if (gameState == REINFORCEMENT) {
            addReinforcementForCurrPlayer();
            updatePlayerTerritoriesModel();
        }
        broadcastGamePlayChanges();
    }
    
    /**
     * Gets the army value.
     *
     * @return the army value
     */
    public int getArmyValue() {
        return this.armyValue;
    }
    
    /**
     * Gets the game state.
     *
     * @return the game state
     */
    public GAME_STATES getGameState() {
        return this.gameState;
    }
    
    /**
     * Sets the game state.
     *
     * @param gameStates the new game state
     */
    public void setGameState(GAME_STATES gameStates) {
        this.gameState = gameStates;
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Gets the player territories game_entities.
     *
     * @return the player territories game_entities
     */
    public PlayerTerritoriesModel getPlayerTerritoriesModel() {
        return playerTerritoriesModel;
    }
    
    /**
     * Gets the current player territories.
     *
     * @return the current player territories
     */
    public Vector<String> getCurrentPlayerTerritories() {
        Vector<String> territoriesList = new Vector<>();
        for (Territory territory : currentPlayer.getTerritories()) {
            territoriesList.add(territory.getName());
        }
        return territoriesList;
    }
    
    /**
     * Gets the next player.
     *
     * @return the next player
     */
    public Player getNextPlayer() {
        int currPlayerIndex = players.indexOf(currentPlayer);
        if (currPlayerIndex == players.size() - 1) {
            return players.get(0);
        } else {
            return players.get(currPlayerIndex + 1);
        }
    }
    // endregion
    
    // region For Startup Phase
    /**
     * Initializes a new game with the specified number of players. This method involves
     * initialization of the specified number of players, the size of the deck of cards depending
     * on the total number of territories, random (but fair) distribution of territories to the
     * players, and the initial armies for each player to allocate. This method also assigns
     * one army in all of the territories using the owner players' unallocated armies.
     *
     * @param numOfPlayers The specified integer of the number of players that will play the game
     */
    public void initializeNewGame(int numOfPlayers) {
        gameState = STARTUP;

         /* Initialization of game attributes */
        Player.resetStaticNextID();
        initPlayers(numOfPlayers);
        initDeck();
//        distributeTerritoriesRiggedForDemo();  // uncomment this method and comment out distributeTerritories() for demo
        distributeTerritories();
        giveInitialArmies();
        setCurrentPlayer(players.firstElement());

        /* Hand out cards for build 1 presentation. To be commented out for normal game play */
        int cardsToHandOut = 0;
        for (Player player : players) {
            if (gameMap.getTerritoriesCount() / numOfPlayers >= 5) {
                cardsToHandOut = 5;
            } else {
                cardsToHandOut = gameMap.getTerritoriesCount() / numOfPlayers;
            }
            for (int i = 0; i < cardsToHandOut; i++) {
                player.addCardToPlayersHand(drawCard());
            }
        }
        
        /* -- console printout for demo -- */
        System.out.println();
        System.out.println("-- Startup Phase Status Console Printout --");
        System.out.println("Number of players: " + players.size());
        System.out.println("Number of territories: " + gameMap.getTerritoriesCount());
        for (Player player : players) {
            System.out.println("Player " + player.getPlayerID());
            System.out.println("\tInitial armies: " + player.getUnallocatedArmies());
            System.out.println("\tNumber of territories: " + gameMap.getTerritoriesOfPlayer(player).size());
            for (Territory territory : player.getTerritories()) {
                System.out.println("\t\t" + territory.getName());
            }
            System.out.println("\tNumber of cards: " + player.getPlayersHand().size());
            for (Card card : player.getPlayersHand()) {
                System.out.println("\t\t" + card.getCardType().name());
            }
        }
        System.out.println("Deck size: " + deck.size());
        System.out.println("-------------------------------------------");
        /* ------------------------------- */
        
        assignOneArmyPerTerritory();
        
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * This method allows the players to allocate an unallocated army to a territory that
     * the player owns.
     *
     * @param territory The name of the territory as String to place an army on
     */
    public void placeArmyStartup(String territory) {
        currentPlayer.reduceUnallocatedArmies(1);
        gameMap.getATerritory(territory).addArmies(1);
        setCurrentPlayer(getNextPlayer());
        
        /*
         * Get next player if current player's unallocated army is 0
         * Stop when current player still has unallocated army or all run out of army
         */
        int count = 1;
        while (currentPlayer.getUnallocatedArmies() == 0 && count < players.size()) {
            setCurrentPlayer(getNextPlayer());
            count++;
        }
        
        /* If all player run out of unallocated army, move to the next phase */
        if (count == players.size()) {
            setGameState(REINFORCEMENT);
            setCurrentPlayer(players.firstElement());
        }
        
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Draws a random card from the deck and returns it.
     *
     * @return Card object
     */
    public Card drawCard() {
        int index = rand.nextInt(deck.size());
        Card card = deck.elementAt(index);
        deck.remove(deck.elementAt(index));
        deck.trimToSize();
        return card;
    }
    
    /**
     * Private helper method to initialize the players according to
     * the number of players (currPlayers).
     *
     * @param numOfPlayers the num of players
     */
    private void initPlayers(int numOfPlayers) {
        System.out.println("Initializing players...");
        
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player());
        }
    }
    
    /**
     * Sets a deck that contains cards from Card class with equal distribution of all the three card types.
     * The total number of cards is set to the closest value to the total number of territories
     * that is a factor of three, and is greater or equal to the total number of territories.
     */
    private void initDeck() {
        System.out.println("Initializing deck...");
        int typeNumber = 0;
        int numOfCards = gameMap.getTerritoriesCount() + (gameMap.getTerritoriesCount() % Card.getTypesCount()) * Card.getTypesCount();
        for (int i = 0; i < numOfCards; i++) {
            if (typeNumber >= Card.getTypesCount()) {
                typeNumber = 0;
            }
            deck.add(new Card(Card.CARD_TYPE.values()[typeNumber]));
            typeNumber++;
        }
    }
    
    /**
     * Distributes the territories in the map randomly to the players (in Round-Robin fashion).
     * Although the territories are distributed randomly, the number of territories are as
     * evenly distributed as possible between all of the players.
     */
    private void distributeTerritories() {
        System.out.println("Distributing territories...");
        
        ArrayList<String> territoryArrList = new ArrayList<>();
        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
            territoryArrList.add(entry.getValue().getName());
        }
        
        int playerIndex = 0;
        for (int i = 0; i < gameMap.getTerritoriesCount(); i++) {
            if (playerIndex >= players.size()) {
                playerIndex = 0;
            }
            int territoryIndex = rand.nextInt(territoryArrList.size());
            Territory territory = gameMap.getATerritory(territoryArrList.get(territoryIndex));
            Player player = players.elementAt(playerIndex);
            territory.setOwner(player);
            player.addTerritory(territory);
            
            playerIndex++;
            territoryArrList.remove(territoryIndex);
        }
    }
    
    /**
     * Rigged version of distribution of territories for the sole purpose of
     * demoing for build 1 presentation. Applicable only on 'World.map' and with
     * 6 players, otherwise will throw errors. Player 1 gets assigned all of
     * the territories in Europe to demonstrate that the continent control value
     * of Europe gets added to the calculation of the number of reinforcement armies.
     */
    private void distributeTerritoriesRiggedForDemo() {
        /*
        For build 1 demo purposes only using World.map and 6 players.
        Give player 1 all the territories of europ.
         */
        
        System.out.println("Distributing territories...");
        
        ArrayList<String> territoryArrList = new ArrayList<>();
        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
            territoryArrList.add(entry.getValue().getName());
        }
        
        for (int i = 0; i < gameMap.getAContinent("europe").getTerritories().size(); i++) {
            int territoryIndex = territoryArrList.indexOf(gameMap.getAContinent("europe").getTerritories().get(i));
            Territory territory = gameMap.getATerritory(territoryArrList.get(territoryIndex));
            Player player = players.elementAt(0);
            territory.setOwner(player);
            player.addTerritory(territory);
            territoryArrList.remove(territoryIndex);
        }
        
        int playerIndex = 1;
        for (int i = 0; i < gameMap.getTerritoriesCount() - 7; i++) {
            if (playerIndex >= players.size()) {
                playerIndex = 1;
            }
            int territoryIndex = rand.nextInt(territoryArrList.size());
            Territory territory = gameMap.getATerritory(territoryArrList.get(territoryIndex));
            Player player = players.elementAt(playerIndex);
            territory.setOwner(player);
            player.addTerritory(territory);
            
            playerIndex++;
            territoryArrList.remove(territoryIndex);
        }
    }
    
    /**
     * This method gives initial armies per player according to the following algorithm:
     * <ul>
     * <li>[# of initial armies = (total# of territories) * (2.75) / (total# of players)].
     * </ul>
     */
    private void giveInitialArmies() {
        int armiesToGive = (int) (gameMap.getTerritoriesCount() * INITIAL_ARMY_RATIO / players.size());
        for (Player player : players) {
            player.setUnallocatedArmies(armiesToGive);
        }
    }
    
    /**
     * For every player, this method automatically assigns one army to all of the territories
     * that player owns. The placed armies get spent from the players' initial given number of
     * unallocated armies
     */
    private void assignOneArmyPerTerritory() {
        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
            entry.getValue().addArmies(1);
        }
        for (Player player : players) {
            player.reduceUnallocatedArmies(gameMap.getTerritoriesOfPlayer(player).size());
        }
    }
    
    // endregion
    
    // region For Reinforcement Phase
    /**
     * This method processes the exchange of cards to the armies if the user selected cards
     * make a valid set. The method checks for a selection of exactly three cards, and checks
     * for either three cards of the same type, or 3 cards one of each type. If so, those cards
     * are removed from the players hand, and the number of armies (unallocated armies) are
     * rewarded according to the current army value. The army value starts at 5 at the beginning
     * of the game, but increases by 5 every time a player makes a valid card exchange move.
     *
     * @param selectedCards Vector of Strings that details the type of cards in the player's possession
     *
     * @return String for the error message to validate the result of the trade in
     */
    public String tradeInCards(Vector<String> selectedCards) {
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
                for (int i = 0; i < currentPlayer.getPlayersHand().size(); i++) {
                    if (currentPlayer.getPlayersHand().get(i).getCardType().name().compareTo(selectedCards.firstElement()) == 0) {
                        counter++;
                    }
                }
                if (counter >= 3) {
                    Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.firstElement()));
                    for (int i = 0; i < selectedCards.size(); i++) {
                        currentPlayer.getPlayersHand().remove(tempCard);
                        deck.add(tempCard);
                    }
                    currentPlayer.getPlayersHand().trimToSize();
                    currentPlayer.addUnallocatedArmies(armyValue);
                    armyValue += 5;
                }
            } else if (choice == 2) {  // for one of each exchange
                for (int cardIndex = 0; cardIndex < selectedCards.size(); cardIndex++) {
                    Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.elementAt(cardIndex)));
                    if (currentPlayer.getPlayersHand().contains(tempCard)) {
                        counter++;
                    }
                }
                if (counter == 3) {
                    for (int cardIndex = 0; cardIndex < selectedCards.size(); cardIndex++) {
                        Card tempCard = new Card(Card.CARD_TYPE.valueOf(selectedCards.elementAt(cardIndex)));
                        currentPlayer.getPlayersHand().remove(tempCard);
                        deck.add(tempCard);
                    }
                    currentPlayer.getPlayersHand().trimToSize();
                    currentPlayer.addUnallocatedArmies(armyValue);
                    armyValue += 5;
                }
            } else {
                broadcastGamePlayChanges();
                return "No cards traded in!\nPlease select 3 cards of the same type or one of each type.";
            }
            broadcastGamePlayChanges();
            return "Cards successfully traded in!";
        } else {
            broadcastGamePlayChanges();
            return "No cards traded in!\nPlease select exactly 3 cards.\n(all of same type or one of each type)";
        }
    }
    
    /**
     * The reinforcement phase includes allowing the players to hand in their cards for
     * armies (or force them to if they have more than or equal to 5 cards), assign
     * to-be-allocated armies to the players according to the number of territories and
     * continents they control (to a minimum of 3), and allows players to place those armies.
     */
    public void addReinforcementForCurrPlayer() {
        // Assign players number of armies to allocate (minimum 3) depending on the players' territories.
        int armiesToGive = Math.max(3, gameMap.getTerritoriesOfPlayer(currentPlayer).size() / 3);
        
        // Assign players additional number armies to allocate if that player owns a continent.
        for (Map.Entry<String, Continent> entry : gameMap.getContinents().entrySet()) {
            if (currentPlayer.getPlayerName().compareTo(entry.getValue().getContinentOwner()) == 0) {
                armiesToGive += entry.getValue().getControlValue();
            }
        }
        
          /* -- console printout for demo -- */
        System.out.println();
        System.out.println("-- Reinforcement Phase Status Console Printout --");
        System.out.println("Player " + currentPlayer.getPlayerID());
        System.out.println("Number of territories owned by this player: " + currentPlayer.getTerritories().size());
        for (Territory territory : currentPlayer.getTerritories()) {
            System.out.println("\t" + territory.getName());
        }
        int continentCounter = 0;
        String continentStr = "";
        for (Map.Entry<String, Continent> entry : gameMap.getContinents().entrySet()) {
            if (currentPlayer.getPlayerName().compareTo(entry.getValue().getContinentOwner()) == 0) {
                continentCounter++;
                continentStr += "\t" + entry.getValue().getName()
                        + ", control value " + entry.getValue().getControlValue() + "\n";
            }
        }
        System.out.println("Number of continents owned by this player: " + continentCounter);
        if (continentCounter != 0) {
            System.out.println(continentStr);
        }
        System.out.print("-------------------------------------------------\n");
        /* ------------------------------- */
        
        currentPlayer.addUnallocatedArmies(armiesToGive);
    }
    
    /**
     * A method that allows a player to place armies to the territories as specified
     * from the Reinforcement Phase view panel.
     *
     * @param armiesToPlace {@literal Map<Territory, Integer>} that contains the key of
     *                      Territory objects and values of Integer to represent armies
     */
    public void placeArmiesReinforcement(Map<Territory, Integer> armiesToPlace) {
        for (Map.Entry<Territory, Integer> entry : armiesToPlace.entrySet()) {
            entry.getKey().addArmies(entry.getValue());
            currentPlayer.reduceUnallocatedArmies(entry.getValue());
        }
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    // endregion
    
    // region For Fortification Phase
    /**
     * The method gives a player an option to move any number of armies from one country to
     * another. The method only allows only one such move that is valid, which requires that
     * the two countries that the player picks must be owned by that player, be different
     * territories from one another, be adjacent to one another, and must have more armies
     * in the territory than the number of armies specified by the player (a territory must
     * have more than 1 army at minimum).
     *
     * @param sourceTerritory String value of the name of the source Territory
     * @param targetTerritory String value of the name of the target Territory
     * @param noOfArmies      Integer value of the number of armies to be moved
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String moveArmiesFortification(String sourceTerritory, String targetTerritory, int noOfArmies) {
        Territory fromTerritory = gameMap.getATerritory(sourceTerritory);
        Territory toTerritory = gameMap.getATerritory(targetTerritory);
        
        // Validate if the two territories are owned by the player, are different, and are neighbors.
        if (!fromTerritory.isOwnedBy(currentPlayer.getPlayerID()) ||
                !toTerritory.isOwnedBy(currentPlayer.getPlayerID()) ||
                fromTerritory == toTerritory) {
            return "No armies moved!\nYou must pick two Territories that are neighbors.";
        }
        
        if (fromTerritory.getArmies() == 1 || fromTerritory.getArmies() <= noOfArmies) {
            return "No armies moved!\nYou must always have at least 1 army in each Territory";
        }
        
        fromTerritory.reduceArmies(noOfArmies);
        toTerritory.addArmies(noOfArmies);
        
        setGameState(REINFORCEMENT);
        setCurrentPlayer(getNextPlayer());
        
        return "Successfully moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory + ".";
    }
    // endregion
    
    // region Private methods
    /**
     * Update the GameMapTableModel according to the newly updated GameMap object.
     */
    private void updateGameMapTableModel() {
        mapTableModel.updateMapTableModel(gameMap, gameState);
    }
    
    /**
     * Method to update the GamePlayModel and notify the Observer.
     */
    private void broadcastGamePlayChanges() {
        setChanged();
        notifyObservers(this);
    }
    
    /**
     * Update player territories game_entities.
     */
    private void updatePlayerTerritoriesModel() {
        playerTerritoriesModel.updateMapTableModel(currentPlayer);
    }
    // endregion
}