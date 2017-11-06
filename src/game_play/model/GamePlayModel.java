/* 
 * Risk Game Team 2
 * GamePlayModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import shared_resources.game_entities.*;

import java.util.*;

import static shared_resources.utilities.Config.*;
import static shared_resources.utilities.Config.GAME_STATES.*;


/**
 * This class is responsible for updating the game states (phases) and broadcasting the changes
 * to the view, and carrying out appropriate Risk game play functions according to the game states.
 * The three main phases and the respective main functions during those phases are as follows:
 * <ol>
 * <li>Startup Phase
 * <ul>
 * <li>Initialize a new game
 * <li>Randomly (but fairly) distribute territories to players
 * </ul>
 * <li>Reinforcement Phase
 * <ul>
 * <li>Allow current player to trade in cards
 * <li>Calculate and give unallocated armies to current player
 * <li>Allow current player to allocate armies in territories
 * </ul>
 * <li>Attack Phase
 * <ul>
 * <li>Attack neighboring territories owned by other player(s)
 * <li>Give card(s) to current player if that player takes over any territories that turn
 * </ul>
 * <li>Fortification Phase
 * <ul>
 * <li>Allow current player to make one valid fortification move
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
     * Sets the army value.
     *
     * @param armyValue new army value
     */
    public void setArmyValue(int armyValue) {
        this.armyValue = armyValue;
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
        currentPlayer = players.firstElement();
        
        /* -- logging printout for demo -- */
        log.append("=======================================");
        log.append("============ Startup Phase ============");
        log.append("=======================================");
        log.append("Number of players: " + players.size());
        log.append("Number of territories: " + gameMap.getTerritoriesCount());
        for (Player player : players) {
            log.append("Player " + player.getPlayerID());
            log.append("\tInitial armies: " + player.getUnallocatedArmies());
            log.append("\tNumber of territories: " + gameMap.getTerritoriesOfPlayer(player).size());
            for (Territory territory : player.getTerritories()) {
                log.append("\t\t" + territory.getName());
            }
            log.append("\tNumber of cards: " + player.getPlayersHand().size());
            for (Card card : player.getPlayersHand()) {
                log.append("\t\t" + card.getCardType().name());
            }
        }
        log.append("Deck size: " + deck.size());
        log.append("-------------------------------------------");
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
        log.append("\t" + currentPlayer.getPlayerName() + " placed 1 army on " + territory);
        currentPlayer = getNextPlayer();
        
        /*
         * Get next player if current player's unallocated army is 0
         * Stop when current player still has unallocated army or all run out of army
         */
        int count = 1;
        while (currentPlayer.getUnallocatedArmies() == 0 && count < players.size()) {
            log.append("\t" + currentPlayer.getPlayerName() + " has no unallocated armies to place");
            currentPlayer = getNextPlayer();
            count++;
        }
        
        /* If all player run out of unallocated army, move to the next phase */
        if (count == players.size()) {
            log.append("All players placed all their unallocated armies");
            log.append("-------------------------------------------");
            
            updateGameMapTableModel();
            broadcastGamePlayChanges();
        } else {
            updateGameMapTableModel();
            broadcastGamePlayChanges();
        }
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
        log.append("Initializing " + numOfPlayers + " players...");
        
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
        log.append("Initializing deck...");
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
        log.append("Distributing territories...");
        
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
        Give player 1 all the territories of europe.
         */
        
        log.append("Distributing territories...");
        
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
     * unallocated armies.
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
     * Change the phase of the game to PLAY phase and let the first player's turn begins.
     */
    public void startTheGame() {
        setGameState(PLAY);
        currentPlayer = players.firstElement();
        currentPlayer.nextPhase();
        addReinforcementForCurrPlayer();
        updatePlayerTerritoriesModel();
        broadcastGamePlayChanges();
    }
    // endregion
    
    // region For Reinforcement Phase
    
    /**
     * Delegate the job to reinforcement() function of Player class
     * Broadcast the change to Observers.
     *
     * @param selectedCards Vector of Strings that details the type of cards in the player's possession
     *
     * @return String for the error message to validate the result of the trade in
     */
    public String tradeInCards(Vector<String> selectedCards) {
        String message = currentPlayer.reinforcement(this, selectedCards, null);
        broadcastGamePlayChanges();
        return message;
    }
    
    /**
     * Delegate the job to reinforcement() function of Player class
     * Broadcast the change to Observers.
     *
     * @param armiesToPlace {@literal Map<Territory, Integer>} that contains the key of
     *                      Territory objects and values of Integer to represent armies
     */
    public void placeArmiesReinforcement(Map<Territory, Integer> armiesToPlace) {
        currentPlayer.reinforcement(this, null, armiesToPlace);
        updateGameMapTableModel();
        broadcastGamePlayChanges();
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
            if (currentPlayer.getPlayerName().compareTo(entry.getValue().getContinentOwner(gameMap)) == 0) {
                armiesToGive += entry.getValue().getControlValue();
            }
        }

        /* -- logging for demo -- */
        log.append("=========================================");
        log.append("========== Reinforcement Phase ==========");
        log.append("=========================================");
    
        log.append("Player " + currentPlayer.getPlayerID());
        log.append("Number of territories owned by this player: " + currentPlayer.getTerritories().size());
        for (Territory territory : currentPlayer.getTerritories()) {
            log.append("\t" + territory.getName());
        }
        int continentCounter = 0;
        StringBuilder continentStr = new StringBuilder();
        for (Map.Entry<String, Continent> entry : gameMap.getContinents().entrySet()) {
            if (currentPlayer.getPlayerName().compareTo(entry.getValue().getContinentOwner(gameMap)) == 0) {
                continentCounter++;
                continentStr.append("\t").append(entry.getValue().getName()).append(", control value ").append(entry.getValue().getControlValue()).append("\n");
            }
        }
        log.append("Number of continents owned by this player: " + continentCounter);
        if (continentCounter != 0) {
            log.append(continentStr.toString());
        }
        log.append("-------------------------------------------------\n");
        /* ------------------------------- */
        
        currentPlayer.addUnallocatedArmies(armiesToGive);
    }
    
    // endregion

    // region For Attack Phase

    /**
     * Delegate the job to attack() of Player class.
     *
     * @param sourceTerritory String value of the name of the source Territory
     * @param targetTerritory String value of the name of the target Territory
     * @param numOfAtkDice    Integer value of the number of dice to be used by the attacker
     * @param numOfDefDice    Integer value of the number of dice to be used by the defender
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String declareAttack(String sourceTerritory, String targetTerritory, int numOfAtkDice, int numOfDefDice) {
        String message = currentPlayer.attack(this, sourceTerritory, targetTerritory, numOfAtkDice, numOfDefDice);
        updateGameMapTableModel();
        broadcastGamePlayChanges();

        return message;
    }

    /**
     * Delegate the job to conquer() of Player class.
     *
     * @param sourceTerritory String value of the name of the source Territory
     * @param targetTerritory String value of the name of the target Territory
     * @param armiesToMove    Integer value of the number of armies to be moved to the captured territory
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String captureTerritory(String sourceTerritory, String targetTerritory, int armiesToMove) {
        String message = gameMap.getATerritory(targetTerritory).getOwner().conquer(this, sourceTerritory, targetTerritory, armiesToMove);
        updateGameMapTableModel();
        broadcastGamePlayChanges();

        return message;
    }

    /**
     * Delegate the job to eliminate() of Player class.
     *
     * @param eliminatedPlayer Player object that has no more territories left and is declared eliminated
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String eliminatePlayer(Player eliminatedPlayer) {
        String message = currentPlayer.eliminated(eliminatedPlayer);
        updateGameMapTableModel();
        broadcastGamePlayChanges();

        return message;
    }
    
    /**
     * Gets a list of territories owned by a player that can attack another territory
     *
     * A valid territory is a territory that has at least one neighbors which is not owned by the player
     * and contains at least 2 armies
     *
     * @param player the player
     *
     * @return a list of territories' names in form of an array of strings
     */
    public String[] getValidAttackingTerritories(Player player) {
        Vector<String> territoriesList = new Vector<>();
        for (Territory territory : player.getTerritories()) {
            if (territory.getArmies() < 2 || getNeighborsNotOwnedBySamePlayer(territory.getName()).length == 0) {
                continue;
            }
            
            territoriesList.add(territory.getName());
        }
        return territoriesList.toArray(new String[territoriesList.size()]);
    }
    
    /**
     * Gets the list of all neighbors that are not owned by the same owner
     *
     * @param territoryName the territory name
     *
     * @return the list of neighbors in form of an array of territory names
     */
    public String[] getNeighborsNotOwnedBySamePlayer(String territoryName) {
        Territory territory = gameMap.getATerritory(territoryName);
        Vector<String> neighborsList = new Vector<>();
        for (String neighborName : territory.getNeighbors()) {
            Territory neighbor = gameMap.getATerritory(neighborName);
            if (!neighbor.isOwnedBy(territory.getOwner().getPlayerID())) {
                neighborsList.add(neighborName);
            }
        }
        return neighborsList.toArray(new String[neighborsList.size()]);
    }
    
    /**
     * Get the maximum number of attacking dice roll that attacker can use depending on the attacking territory
     *
     * @param territoryName the territory name
     *
     * @return the maximum number of dice roll that attacker may use
     */
    public int getMaxAttackingRoll(String territoryName) {
        Territory territory = gameMap.getATerritory(territoryName);
        int armies = territory.getArmies();
        if (armies >= 4) {
            return 3;
        } else {
            return armies - 1;
        }
    }
    // endregion

    // region For Fortification Phase
    
    /**
     * Delegate the job to fortification() of Player class.
     *
     * @param sourceTerritory String value of the name of the source Territory
     * @param targetTerritory String value of the name of the target Territory
     * @param noOfArmies      Integer value of the number of armies to be moved
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String moveArmiesFortification(String sourceTerritory, String targetTerritory, int noOfArmies) {
        String message = currentPlayer.fortification(this, sourceTerritory, targetTerritory, noOfArmies);
        updateGameMapTableModel();
        broadcastGamePlayChanges();
        
        return message;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Set the current player to be the next one in round-robin-fashion
     * Change the phase of the current player to Reinforcement/TradeCard
     * Broadcast the change to Observers.
     */
    public void nextPlayerTurn() {
        currentPlayer = getNextPlayer();
        currentPlayer.nextPhase();
        addReinforcementForCurrPlayer();
        updatePlayerTerritoriesModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Change the game phase of the current player to other phase.
     *
     * @param newGameStates the game phase
     */
    public void changePhaseOfCurrentPlayer(GAME_STATES newGameStates) {
        currentPlayer.setGameState(newGameStates);
        broadcastGamePlayChanges();
    }
    
    // endregion
    
    // region Private methods
    
    /**
     * Gets the next player.
     *
     * @return the next player
     */
    private Player getNextPlayer() {
        int currPlayerIndex = players.indexOf(currentPlayer);
        if (currPlayerIndex == players.size() - 1) {
            return players.get(0);
        } else {
            return players.get(currPlayerIndex + 1);
        }
    }
    
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
    
    /**
     * This is method used for testing gamemaps.
     */
    public void DemoForTests() {
        GameMap gamemap = new GameMap("3D.map");
        setGameMap(gamemap);
        initPlayers(2);
        //placeArmiesReinforcement(10);
        distributeTerritories();
        
    }
    
}