package model.ui_models;

import model.game_entities.*;

import java.util.*;

import static utilities.Config.GAME_STATES;
import static utilities.Config.GAME_STATES.ENTRY_MENU;
import static utilities.Config.GAME_STATES.REINFORCEMENT;
import static utilities.Config.GAME_STATES.STARTUP;
import static utilities.Config.INITIAL_ARMY_RATIO;

/**
 * Class initiates RiskGame with the welcome message, followed by the following features:
 * 1) determine number of players
 * 2) set number of cards in deck
 * 3) randomly (but fairly) distribute countries among all players
 * 4) start turn (repeated in round-robin fashion until only one active player remains)
 * a) reinforcement phase
 * b) attack phase
 * c) fortifications phase
 * 5) end of game
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
     * private constructor preventing any other class from instantiating.
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
     * Static instance method to determine if an object of RiskGame already exists
     *
     * @return instance of the singleton object
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
     * Sets new gameMap.
     *
     * @param gameMap New value of gameMap.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    public GameMap getGameMap() {
        return gameMap;
    }
    
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
    
    public Vector<Player> getPlayers() {
        return this.players;
    }
    
    public Vector<Card> getDeck() {
        return this.deck;
    }
    
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        if (gameState == REINFORCEMENT) {
            addReinforcementForCurrPlayer();
            updatePlayerTerritoriesModel();
        }
    }
    
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    
    public int getArmyValue() {
        return this.armyValue;
    }
    
    public GAME_STATES getGameState() {
        return this.gameState;
    }
    
    public void setGameState(GAME_STATES gameStates) {
        this.gameState = gameStates;
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    public PlayerTerritoriesModel getPlayerTerritoriesModel() {
        return playerTerritoriesModel;
    }
    
    // endregion
    
    // region Public methods
    public Vector<String> getCurrentPlayerTerritories() {
        Vector<String> territoriesList = new Vector<>();
        for (Territory territory : currentPlayer.getTerritories()) {
            territoriesList.add(territory.getName());
        }
        return territoriesList;
    }
    
    /**
     * Loads the map specified by the filepath, and initializes the game attributes
     * for a new instance of Risk Game using the specified number of players.
     *
     * @param numOfPlayers The specified integer of the number of players that will play the game
     */
    public void initializeNewGame(int numOfPlayers) {
        gameState = STARTUP;

         /* Initialization of game attributes */
        initPlayers(numOfPlayers);
        initDeck();
        distributeTerritories();
        giveInitialArmies();
        assignOneArmyPerTerritory();
        setCurrentPlayer(players.firstElement());
        
        System.out.println("num of players: " + players.size());
        for (Player player : players) {
            System.out.println("player" + player.getPlayerID() + " init armies: " + player.getUnallocatedArmies());
            System.out.println("num of territories of player" + player.getPlayerID() + ": " + gameMap.getTerritoriesOfPlayer(player).size());
        }
        System.out.println("deck size: " + deck.size());

          /* Hand out cards for build 1 presentation. To be commented out for normal game play */
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCardToPlayersHand(drawCard());
            }
            System.out.println("player" + player.getPlayerID() + "'s hand: (" + player.getPlayersHand().size() + ")");
        }
        
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    public Player getNextPlayer() {
        int currPlayerIndex = players.indexOf(currentPlayer);
        if (currPlayerIndex == players.size() - 1) {
            return players.get(0);
        } else {
            return players.get(currPlayerIndex + 1);
        }
    }
    //endregion
    
    // region For Startup Phase
    
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
     * A method that allows a player to place armies to the territories as specified
     * from the Reinforcement Phase view panel.
     *
     * @param armiesToPlace Map that contains the key of Territory objects and values
     *                      of Integer to represent armies
     */
    public void placeArmiesReinforcement(Map<Territory, Integer> armiesToPlace) {
        for (Map.Entry<Territory, Integer> entry : armiesToPlace.entrySet()) {
            System.out.println("territory " + entry.getKey().getName() + "'s old army value: " + entry.getKey().getArmies());
            System.out.println("previous unallocated armies: " + currentPlayer.getUnallocatedArmies());
            entry.getKey().addArmies(entry.getValue());
            currentPlayer.reduceUnallocatedArmies(entry.getValue());
            System.out.println("territory " + entry.getKey().getName() + "'s new army value: " + entry.getKey().getArmies());
            System.out.println("changed unallocated armies: " + currentPlayer.getUnallocatedArmies());
        }
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    // endregion
    
    // region For Reinforcement Phase
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
        currentPlayer.addUnallocatedArmies(armiesToGive);
    }
    // endregion
    
    // region Card related helper methods
    
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
     * This method changes the game state to TRADE_IN and processes the exchange
     * of cards to the armies if the user selected cards make a valid set. The method
     * checks for a selection of exactly three cards, and checks for either three cards
     * of the same type, or 3 cards one of each type. If so, those cards are removed
     * from the players hand, and the number of armies (unallocated armies) are rewarded
     * according to the current army value. The army value starts at 5 at the beginning
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
    
    // endregion
    
    // region For Fortification Phase
    /**
     * The method gives a player an option to move any number of armies from one country to
     * another. The method only allows only one such move that is valid, which requires that
     * the two countries that the player picks must be owned by that player, be different
     * territories from one another, and must have more armies in the territory than the number
     * of armies specified by the player (a territory must have more than 1 army at minimum).
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
        
        broadcastGamePlayChanges();
        return "Successfully moved " + noOfArmies + " armies from " + sourceTerritory + " to " + targetTerritory + ".";
    }
    // endregion
    
    // region Private methods
    
    /**
     * Private helper method to initialize the players according to
     * the number of players (currPlayers).
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
     * Distributes the territories in the map randomly to the players. Although the territories
     * are distributed randomly, the number of territories should be as evenly distributed as
     * possible between all of the players.
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
        
        // ------------ comment out above and use below ------------
        /*
        For build 1 demo purposes only using World.map and 6 players.
        Give player 1 all the territories.
         */

//        System.out.println("Distributing territories...");
//
//        ArrayList<String> territoryArrList = new ArrayList<>();
//        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
//            territoryArrList.add(entry.getValue().getName());
//        }
//
//        for (int i = 0; i < gameMap.getAContinent("europe").getTerritories().size(); i++) {
//            int territoryIndex = territoryArrList.indexOf(gameMap.getAContinent("europe").getTerritories().get(i));
//            Territory territory = gameMap.getATerritory(territoryArrList.get(territoryIndex));
//            Player player = players.elementAt(0);
//            territory.setOwner(player);
//            player.addTerritory(territory);
//            territoryArrList.remove(territoryIndex);
//        }
//
//        int playerIndex = 1;
//        for (int i = 0; i < gameMap.getTerritoriesCount() - 7; i++) {
//            if (playerIndex >= players.size()) {
//                playerIndex = 1;
//            }
//            int territoryIndex = rand.nextInt(territoryArrList.size());
//            Territory territory = gameMap.getATerritory(territoryArrList.get(territoryIndex));
//            Player player = players.elementAt(0);
//            territory.setOwner(player);
//            player.addTerritory(territory);
//
//            playerIndex++;
//            territoryArrList.remove(territoryIndex);
//        }
        // ---------------------------------------------------------
    }
    
    /**
     * This method gives initial armies per player according to the following algorithm:
     * [# of initial armies = (total# of territories) * (2.75) / (total# of players)].
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
    
    /**
     * Method to update the GamePlayModel and notify the Observer.
     */
    private void broadcastGamePlayChanges() {
        setChanged();
        notifyObservers(this);
    }
    
    /**
     * Update the GameMapTableModel according to the newly updated GameMap object
     */
    private void updateGameMapTableModel() {
        mapTableModel.updateMapTableModel(gameMap, gameState);
    }
    
    private void updatePlayerTerritoriesModel() {
        playerTerritoriesModel.updateMapTableModel(currentPlayer);
    }
    // endregion
}