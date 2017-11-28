/* 
 * Risk Game Team 2
 * GamePlayModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import game_play.view.screens.StrategyDialog;
import shared_resources.game_entities.*;
import shared_resources.strategy.Bot;
import shared_resources.strategy.PlayerType;

import java.io.Serializable;
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
public class GamePlayModel extends Observable implements Serializable {
    // region Attributes declaration
    private static final long serialVersionUID = 42L;
    private static final int DEFAULT_ARMY_VALUE = 5;
    private static final int MAX_ATTACK_TURN = 50;
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private GAME_STATES gameState;
    private Player currentPlayer;
    private PlayerTerritoriesModel playerTerritoriesModel;
    private int armyValue;
    private Vector<Card> deck;
    private Vector<Player> players;
    private Random rand;
    private Battle currentBattle;
    private boolean needDefenderReaction;
    private int maxTurns;
    private int turnCounter = 1;
    private Player winner;
    private int attackCounter;

    // endregion
    
    // region Constructors

    /**
     * Public GamePlayModel constructor.
     */
    public GamePlayModel() {
        maxTurns = 50;
        armyValue = DEFAULT_ARMY_VALUE;
        mapTableModel = new MapTableModel();
        deck = new Vector<>();
        players = new Vector<>();
        gameState = ENTRY_MENU;
        rand = new Random();
        playerTerritoriesModel = new PlayerTerritoriesModel();
        needDefenderReaction = false;
    }

    /**
     * Public GamePlayModel copy constructor (used in Tournament Mode game play).
     *
     * @param gamePlayModel
     */
    public GamePlayModel(GamePlayModel gamePlayModel) {
        this.gameMap = gamePlayModel.gameMap;
        this.mapTableModel = gamePlayModel.mapTableModel;
        this.gameState = gamePlayModel.gameState;
        this.currentPlayer = gamePlayModel.currentPlayer;
        this.playerTerritoriesModel = gamePlayModel.playerTerritoriesModel;
        this.armyValue = gamePlayModel.armyValue;
        this.deck = gamePlayModel.deck;
        this.players = gamePlayModel.players;
        this.rand = gamePlayModel.rand;
        this.currentBattle = gamePlayModel.currentBattle;
        this.maxTurns = gamePlayModel.maxTurns;
        this.turnCounter = gamePlayModel.turnCounter;
        this.broadcastGamePlayChanges();
    }

    /**
     * Method to be used when restoring a saved game or making a copy of the game to play
     *
     * @param gamePlayModel the restored object
     */
    public void setGamePlayModel(GamePlayModel gamePlayModel) {
        this.gameMap = gamePlayModel.gameMap;
        this.mapTableModel = gamePlayModel.mapTableModel;
        this.gameState = gamePlayModel.gameState;
        this.currentPlayer = gamePlayModel.currentPlayer;
        this.playerTerritoriesModel = gamePlayModel.playerTerritoriesModel;
        this.armyValue = gamePlayModel.armyValue;
        this.deck = gamePlayModel.deck;
        this.players = gamePlayModel.players;
        this.rand = gamePlayModel.rand;
        this.currentBattle = gamePlayModel.currentBattle;
        this.maxTurns = gamePlayModel.maxTurns;
        this.turnCounter = gamePlayModel.turnCounter;
        this.broadcastGamePlayChanges();
    }
    // endregion
    
    // region Getters and Setters
    public int getMaxAttackTurn() {
        return MAX_ATTACK_TURN;
    }
    
    public int getAttackCounter() {
        return attackCounter;
    }
    
    public void setAttackCounter(int attackCounter) {
        this.attackCounter = attackCounter;
    }
    
    /**
     * Method to update the GamePlayModel and notify the Observer.
     */
    private void broadcastGamePlayChanges() {
        setChanged();
        notifyObservers(this);
    }
    
    /**
     * Gets the current battle of the game
     *
     * @return the current battle of the game
     */
    public Battle getCurrentBattle() {
        return currentBattle;
    }
    
    /**
     * Set the current battle
     *
     * @param newBattle the new battle
     */
    public void setCurrentBattle(Battle newBattle) {
        this.currentBattle = newBattle;
    }
    
    /**
     * Gets the maximum turns a game can be played
     */
    public int getMaxTurns() {
        return maxTurns;
    }
    
    /**
     * Sets the maximum turns a game can be played
     *
     * @param maxTurns the value to be set as max
     */
    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }
    
    /**
     * Gets the game turn counter
     *
     * @return how many turns the game run
     */
    public int getTurnCounter() {
        return turnCounter;
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
     * Gets a player by his name
     *
     * @param playerName the player name
     *
     * @return the player
     */
    public Player getAPlayer(String playerName) {
        for (Player player : players) {
            if (player.getPlayerName().compareTo(playerName) == 0) {
                return player;
            }
        }
        return null;
    }
    
    /**
     * Obtain the game winner
     *
     * @return the name for the winner of the game
     */
    public String getWinner() {
        if (gameState == VICTORY) {
            return winner.getPlayerName() + " [" + winner.getPlayerType().getClass().getSimpleName() + "]";
        } else {
            return "Draw";
        }
    }
    
    /**
     * Check whether the game need defender's reaction
     *
     * @return true if need to wait for defender reaction, false otherwise
     */
    public boolean isNeedDefenderReaction() {
        return needDefenderReaction;
    }
    
    /**
     * Sets if the game play need defender reaction
     *
     * @param needDefenderReaction need defender reaction
     */
    public void setNeedDefenderReaction(boolean needDefenderReaction) {
        this.needDefenderReaction = needDefenderReaction;
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
        log.append("\n##############################################");
        log.append("=== STARTING NEW GAME ON MAP " + gameMap.getMapName() + " ===");
        log.append("##############################################");
        log.append("    Number of continents: " + gameMap.getContinentsCount());
        log.append("    Number of territories: " + gameMap.getTerritoriesCount());
         /* Initialization of game attributes */
        Player.resetStaticNextID();
        initPlayers(numOfPlayers);
        initDeck();
        distributeTerritories();
        giveInitialArmies();
        currentPlayer = players.firstElement();

//        // ======= TODO: delete this fxn later
//        for (int i=0; i<4; i++) {
//            players.get(0).addCardToPlayersHand(drawCard());
//        }
//        // ===================================
        
        assignOneArmyPerTerritory();
        updateGameMapTableModel();
        broadcastGamePlayChanges();
        log.append("    Deck size: " + deck.size());
    }
    
    /**
     * Private helper method to initialize the players according to
     * the number of players (currPlayers).
     *
     * @param numOfPlayers the num of players
     */
    public void initPlayers(int numOfPlayers) {
        log.append("Initializing " + numOfPlayers + " players...");
        
        for (int i = 0; i < numOfPlayers; i++) {
            Player player = new Player();
            players.add(player);
            log.append("    Add " + player.getPlayerName() + " to the game");
        }
        
        log.append("    Finish initializing players");
    }
    
    /**
     * Sets a deck that contains cards from Card class with equal distribution of all the three card types.
     * The total number of cards is set to the closest value to the total number of territories
     * that is a factor of three, and is greater or equal to the total number of territories.
     */
    private void initDeck() {
        int typeNumber = 0;
        int numOfCards = gameMap.getTerritoriesCount(); // Number of cards depends on number of territories in the map
        if (gameMap.getTerritoriesCount() % Card.getTypesCount() != 0) {
            numOfCards += Card.getTypesCount() - (gameMap.getTerritoriesCount() % Card.getTypesCount());
        }
        log.append("Initializing deck of " + numOfCards + " cards");
        for (int i = 0; i < numOfCards; i++) {
            if (typeNumber >= Card.getTypesCount()) {
                typeNumber = 0;
            }
            Card card = new Card(Card.CARD_TYPE.values()[typeNumber]);
            deck.add(card);
            log.append("    Add " + card.getCardType() + " to the deck");
            typeNumber++;
        }
        log.append("    Finish initializing deck");
    }
    
    /**
     * Distributes the territories in the map randomly to the players (in Round-Robin fashion).
     * Although the territories are distributed randomly, the number of territories are as
     * evenly distributed as possible between all of the players.
     */
    private void distributeTerritories() {
        log.append("Distributing territories to players");
        
        /* Prepare the territories list */
        ArrayList<String> territoryArrList = new ArrayList<>();
        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
            territoryArrList.add(entry.getValue().getName());
        }
        
        /* Distribute territories to players randomly but evenly */
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
            log.append("    Assign " + territory.getName() + " to " + player.getPlayerName());
            playerIndex++;
            territoryArrList.remove(territoryIndex);
        }
        log.append("    Finish assigning territories to players");
    }
    
    /**
     * This method gives initial armies per player according to the following algorithm:
     * <ul>
     * <li>[# of initial armies = (total# of territories) * (2.75) / (total# of players)].
     * </ul>
     */
    private void giveInitialArmies() {
        int armiesToGive = (int) (gameMap.getTerritoriesCount() * INITIAL_ARMY_RATIO / players.size());
        log.append("Give initial armies = (total# of territories) * (2.75) / (total# of players) = [ " + gameMap.getTerritoriesCount() + " * " + INITIAL_ARMY_RATIO + " / " + players.size() + " ]");
        for (Player player : players) {
            log.append("    " + player.getPlayerName() + " receives " + armiesToGive + " armies");
            player.setUnallocatedArmies(armiesToGive);
        }
    }
    
    // endregion
    
    // region For Startup Phase
    
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
    // endregion
    
    // region For Startup Phase
    
    /**
     * Update the GameMapTableModel according to the newly updated GameMap object.
     */
    private void updateGameMapTableModel() {
        mapTableModel.updateMapTableModel(gameMap, gameState);
    }
    
    /**
     * A subset of GamePlayModel#initializeNewGame(int) used to start the game
     * once players are already allocated in Tournament mode
     *
     * @see GamePlayModel#initializeNewGame(int)
     */
    public void initializeNewGameForTournament() {
        log.append("\n##############################################");
        log.append("=== STARTING NEW GAME ON MAP " + gameMap.getMapName() + " ===");
        log.append("##############################################");
        log.append("    Number of continents: " + gameMap.getContinentsCount());
        log.append("    Number of territories: " + gameMap.getTerritoriesCount());
        initDeck();
        distributeTerritories();
        giveInitialArmies();
        currentPlayer = players.firstElement();
        assignOneArmyPerTerritory();
        // Distribute one by one armies in Round-Robin fashion for bots
        for (Player player : players) {
            if (Bot.class.isAssignableFrom(player.getPlayerType().getClass())) {
                String randomTerritory = player.getTerritories().elementAt((int) (Math.random() * (player.getTerritories().size() - 1))).getName();
                placeArmyStartup(randomTerritory);
            }
        }
        updateGameMapTableModel();
        broadcastGamePlayChanges();
        log.append("    Deck size: " + deck.size());
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
        log.append("    " + currentPlayer.getPlayerName() + " placed 1 army on " + territory);
        currentPlayer = getNextPlayer();
        
        /*
         * Get next player if current player's unallocated army is 0
         * Stop when current player still has unallocated army or all run out of army
         */
        int count = 1;
        while (currentPlayer.getUnallocatedArmies() == 0 && count < players.size()) {
            log.append("    " + currentPlayer.getPlayerName() + " has no unallocated armies to place");
            currentPlayer = getNextPlayer();
            count++;
        }
        
        /* If all player run out of unallocated army, move to the next phase */
        if (count == players.size()) {
            log.append("    All players placed all their unallocated armies");
        }
        
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Gets the next player.
     *
     * @return the next player
     */
    private Player getNextPlayer() {
        PLAYER_STATUS playerStatus;
        Player player = currentPlayer;
        do {
            int currPlayerIndex = players.indexOf(player);
            if (currPlayerIndex == players.size() - 1) {
                player = players.get(0);
            } else {
                player = players.get(currPlayerIndex + 1);
            }
            playerStatus = player.getPlayerStatus();
        } while (playerStatus == PLAYER_STATUS.ELIMINATED); // only get players who are still in the game
        return player;
    }
    
    /**
     * Initialization of a new game for the sole purposes of testing. This method utilizes the
     * rigged (fixed) version of distributing armies to the players instead of the standard
     * random distribution method.
     *
     * @param numOfPlayers The specified integer of the number of players that will play the game
     */
    public void fixedInitializeNewGame(int numOfPlayers) {
         /* Initialization of game attributes */
        Player.resetStaticNextID();
        initPlayers(numOfPlayers);
        initDeck();
        fixedDistributeTerritories();  // rigged and fixed distribution of the territories
        giveInitialArmies();
        currentPlayer = players.firstElement();
        
        assignOneArmyPerTerritory();
    }
    
    /**
     * Rigged version of distribution of territories for the sole purpose of testing. The
     * territories are first sorted in alphanumerical order before being assigned to the
     * players in round robin fashion from the top of the territory list.
     */
    private void fixedDistributeTerritories() {
        ArrayList<String> territoryArrList = new ArrayList<>();
        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
            territoryArrList.add(entry.getValue().getName());
        }
        
        Collections.sort(territoryArrList);  // sort the territories in order
        
        int playerIndex = 0;
        for (int i = 0; i < gameMap.getTerritoriesCount(); i++) {
            if (playerIndex >= players.size()) {
                playerIndex = 0;
            }
            Territory territory = gameMap.getATerritory(territoryArrList.get(0));
            Player player = players.elementAt(playerIndex);
            territory.setOwner(player);
            player.addTerritory(territory);
            
            playerIndex++;
            territoryArrList.remove(0);
        }
    }
    
    /**
     * Change the phase of the game to PLAY phase and let the first player's turn begins.
     */
    public void startTheGame() {
        setGameState(PLAY);
        
        log.append("==============================================");
        log.append("The game starts");
        currentPlayer = players.firstElement();
        log.append("==============================================");
        log.append(currentPlayer.getPlayerName() + "[turn #" + turnCounter + "]. Player type: " + currentPlayer.getPlayerType().getClass().getSimpleName());
        log.append("    " + currentPlayer.getPlayerName() + " is " + currentPlayer.getPlayerType().getClass().getSimpleName());
        currentPlayer.nextPhase();
        addReinforcementForCurrPlayer();
        updatePlayerTerritoriesModel();
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
        
        // For logging display player's continent content
        log.append("    " + currentPlayer.getPlayerName() + " owns " + currentPlayer.getTerritories().size() + " territories: ");
        for (Territory territory : currentPlayer.getTerritories()) {
            String format = "%-50s%-4s";
            String firstColumn = "     " + territory.getName();
            String secondColumn = "\twith " + territory.getArmies() + " armies";
            log.append(String.format(format, firstColumn, secondColumn));
        }
        int continentCounter = 0;
        StringBuilder continentStr = new StringBuilder();
        for (Map.Entry<String, Continent> entry : gameMap.getContinents().entrySet()) {
            if (currentPlayer.getPlayerName().compareTo(entry.getValue().getContinentOwner(gameMap)) == 0) {
                continentCounter++;
                continentStr.append(entry.getValue().getName()).append(", control value ").append(entry.getValue().getControlValue()).append("\n");
            }
        }
        log.append("    " + currentPlayer.getPlayerName() + " owns " + continentCounter + " continents: ");
        if (continentCounter != 0) {
            log.append("        " + continentStr.toString());
        }
        
        currentPlayer.addUnallocatedArmies(armiesToGive);
    }
    
    /**
     * Update player territories game_entities.
     */
    private void updatePlayerTerritoriesModel() {
        playerTerritoriesModel.updateMapTableModel(currentPlayer, this);
    }
    
    /**
     * Change players' type according to selection from the UI
     *
     * @param opts the users' selection
     */
    public void setPlayersType(StrategyDialog.BehaviourOptions[] opts) {
        String chosenStrategy;
        for (int i = 0; i < opts.length; i++) {
            chosenStrategy = opts[i].getGroup().getSelection().getActionCommand();
            try {
                Class<?> strategyClass = Class.forName(StrategyDialog.getSTRATEGY_PATH() + "." + chosenStrategy);
                players.get(i).setPlayerType((PlayerType) strategyClass.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        broadcastGamePlayChanges();
    }
    
    /**
     * Delegate the job to reinforcement() function of Player class
     * Broadcast the change to Observers.
     *
     * @param selectedCards Vector of Strings that details the type of cards in the player's possession
     *
     * @return String for the error message to validate the result of the trade in
     */
    public String tradeInCards(Vector<String> selectedCards) {
        log.append("    " + currentPlayer.getPlayerName() + " starts trading cards");
        String message = currentPlayer.reinforcement(this, selectedCards, null);
        broadcastGamePlayChanges();
        return message;
    }
    // endregion
    
    // region For Reinforcement Phase
    
    /**
     * Delegate the job to reinforcement() function of Player class
     * Broadcast the change to Observers.
     *
     * @param armiesToPlace {@literal Map<Territory, Integer>} that contains the key of
     *                      Territory objects and values of Integer to represent armies
     */
    public void placeArmiesReinforcement(Map<Territory, Integer> armiesToPlace) {
        currentPlayer.reinforcement(this, null, armiesToPlace);
        if (currentPlayer.isHuman() && currentPlayer.getUnallocatedArmies() == 0 && !currentPlayer.ableToTradeCards()) {
            log.append("    " + currentPlayer.getPlayerName() + " has no unallocated army left and no valid cards set to trade");
            currentPlayer.nextPhase();
        }
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Delegate the job to conquer() function of Player class
     *
     * @param armiesToMove the number of armies to move
     */
    public void moveArmiesToConqueredTerritory(int armiesToMove) {
        currentPlayer.conquer(this, armiesToMove);
        moveToFortificationIfPossible();
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * If current player cannot attack anymore, move to Fortification automatically
     */
    private void moveToFortificationIfPossible() {
        if (!currentPlayer.ableToAttack(gameMap)) {
            log.append("        " + currentPlayer.getPlayerName() + " cannot attack anymore");
            currentPlayer.nextPhase();
            if (!currentPlayer.ableToForitfy(gameMap)) {
                log.append("        " + currentPlayer.getPlayerName() + " cannot fortify");
                nextPlayerTurn();
            }
        }
    }
    
    /**
     * Set the current player to be the next one in round-robin-fashion
     * Change the phase of the current player to Reinforcement/TradeCard
     * Broadcast the change to Observers.
     */
    public void nextPlayerTurn() {
        currentPlayer = getNextPlayer();
        
        /* increase the turn counter starting from the first reinforcement phase, and for every player's turn */
        if (gameState == PLAY) {
            turnCounter++;
        }
        log.append("==============================================");
        log.append(currentPlayer.getPlayerName() + "'s turn begins [turn #" + turnCounter + "]. Player type: " + currentPlayer.getPlayerType().getClass().getSimpleName());
        log.append("    " + currentPlayer.getPlayerName() + " is " + currentPlayer.getPlayerType().getClass().getSimpleName());
        currentPlayer.nextPhase();
        addReinforcementForCurrPlayer();
        updatePlayerTerritoriesModel();
        broadcastGamePlayChanges();
    }
    // endregion
    
    // region For Attack Phase
    
    /**
     * Change the player's state to ATTACK_PREPARE
     */
    public void prepareNewAttack() {
        currentPlayer.setGameState(ATTACK_PREPARE);
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * Delegate the job to attack() of Player class.
     *
     * @param attackingTerritoryName String value of the name of the attacking Territory
     * @param defendingTerritoryName String value of the name of the defending Territory
     * @param numOfAtkDice           Integer value of the number of dice to be used by the attacker
     * @param numOfDefDice           Integer value of the number of dice to be used by the defender
     *
     * @return String value of the messages that will be displayed to the user
     */
    public String declareAttack(String attackingTerritoryName, String defendingTerritoryName, int numOfAtkDice, int numOfDefDice) {
        String message = "";
        Player attacker = currentPlayer;
        Territory attackingTerritory = gameMap.getATerritory(attackingTerritoryName);
        Player defender = gameMap.getATerritory(defendingTerritoryName).getOwner();
        Territory defendingTerritory = gameMap.getATerritory(defendingTerritoryName);
        
        // Create the battle
        currentBattle = new Battle(attacker, attackingTerritory, numOfAtkDice, defender, defendingTerritory, numOfDefDice);
        currentPlayer.setGameState(ATTACK_BATTLE);
        
        log.append("    " + currentBattle.getAttacker().getPlayerName() + " attacks from " + attackingTerritory.getName() +
                " to " + defendingTerritory.getName() + " of " + defender.getPlayerName());
        log.append("        " + currentBattle.getAttacker().getPlayerName() + " chooses " + numOfAtkDice + " dice");
        log.append("        " + currentBattle.getDefender().getPlayerName() + " chooses " + numOfDefDice + " dice");
        
        /* Let current human player does his part */
        currentPlayer.attack(this);
        
        /* Decide the battle */
        decideBattleResultIfPossible();
        
        // If the defending territory has been conquered
        if (currentBattle.getDefendingTerritory().getArmies() == 0) {
            log.append("        " + defendingTerritoryName + " has been conquered by " + attacker.getPlayerName());
            attacker.setHasConqueredTerritories(true);
            
            // Change the owner of this territory to the attacker
            defender.removeTerritory(defendingTerritory.getName());
            defendingTerritory.setOwner(attacker);
            attacker.addTerritory(defendingTerritory);
            
            // Check if the defender has been eliminated
            eliminatePlayerIfPossible();
        }
        moveToFortificationIfPossible();
        
        updateGameMapTableModel();
        broadcastGamePlayChanges();
        
        return message;
    }
    
    private void decideBattleResultIfPossible() {
        if (currentBattle != null) {
            int numOfAtkDice = currentBattle.getAttackerDice().getRollsCount();
            int numOfDefDice = currentBattle.getDefenderDice().getRollsCount();
            
            // Compare the best result of both players
            int bestOfAttacker = currentBattle.getAttackerDice().getTheBestResult();
            int bestOfDefender = currentBattle.getDefenderDice().getTheBestResult();
            decideResult(bestOfAttacker, bestOfDefender);
            
            // If both players roll at least 2 dice
            if (numOfAtkDice >= 2 && numOfDefDice >= 2) {
                int secondBestOfAttacker = currentBattle.getAttackerDice().getSecondBestResult();
                int secondBestOfDefender = currentBattle.getDefenderDice().getSecondBestResult();
                decideResult(secondBestOfAttacker, secondBestOfDefender);
            }
        }
    }
    
    /**
     * This method gives all of the current cards of the eliminated Player (from the latest attack) to the conquering
     * And remove defeated player from the game
     */
    public void eliminatePlayerIfPossible() {
        Player attacker = currentBattle.getAttacker();
        Player defender = currentBattle.getDefender();
        if (defender.getTerritories().size() == 0) {
            // Remove him from the game
            defender.setPlayerStatus(PLAYER_STATUS.ELIMINATED);
            log.append("    " + currentPlayer.getPlayerName() + " just eliminated " + defender.getPlayerName());
            log.append("        " + defender.getPlayerName() + " has been eliminated");
            
            // Give all of defender's cards to the attacker
            log.append("        Start giving all " + defender.getPlayerName() + "'s cards to " + attacker.getPlayerName());
            if (defender.getPlayersHand().size() == 0) {
                log.append("            " + defender.getPlayerName() + " has no card");
            } else {
                for (Card card : defender.getPlayersHand()) {
                    attacker.addCardToPlayersHand(card);
                    log.append("            Give " + card.getCardType() + " to " + attacker.getPlayerName());
                }
            }
        }
        
        // Declare winner if there is only 1 player left
        if (gameVictory(attacker)) {
            winner = attacker;
            setGameState(VICTORY);
            String message = attacker.getPlayerName() + " wins the game!";
            log.append("\n");
            log.append("!!!!!!!!!!!!!!!!!! " + message + "!!!!!!!!!!!!!!!!!!");
        }
        
        updateGameMapTableModel();
        broadcastGamePlayChanges();
    }
    
    /**
     * This method decides the outcome of the current battle by comparing the attacker's dice
     * roll value and the defender's dice roll value. Depending on the result, the method
     * increases the lose count for the player who rolled a lower value than the opponent.
     *
     * @param bestOfAttacker Integer value of the attacker's dice roll
     * @param bestOfDefender Integer value of the defender's dice roll
     */
    public void decideResult(int bestOfAttacker, int bestOfDefender) {
        Territory attackingTerritory = currentBattle.getAttackingTerritory();
        Territory defendingTerritory = currentBattle.getDefendingTerritory();
        if (bestOfAttacker > bestOfDefender) { // the attacker wins
            log.append("            Attacker " + currentBattle.getAttacker().getPlayerName() + " has " + bestOfAttacker +
                    ", defender " + currentBattle.getDefender().getPlayerName() + " has " + bestOfDefender +
                    ", attacker wins");
            defendingTerritory.reduceArmies(1);
            log.append("            " + currentBattle.getDefender().getPlayerName() + "'s " +
                    defendingTerritory.getName() + " loses 1 army");
            currentBattle.increaseDefenderLossCount();
            
        } else { // the defender wins
            log.append("            Attacker " + currentBattle.getAttacker().getPlayerName() + " has " + bestOfAttacker +
                    ", defender " + currentBattle.getDefender().getPlayerName() + " has " + bestOfDefender +
                    ", defender wins");
            attackingTerritory.reduceArmies(1);
            log.append("            " + currentBattle.getAttacker().getPlayerName() + "'s " +
                    attackingTerritory.getName() + " loses 1 army");
            currentBattle.increaseAttackerLossCount();
            
        }
    }
    
    /**
     * Determine if game is over by winning all continents
     *
     * @param attackingPlayer the player that attacks
     *
     * @return true if attacking player won the entire game, false otherwise
     */
    public boolean gameVictory(Player attackingPlayer) {
        boolean isVictory = true;
        for (Player player : players) {
            if (player != attackingPlayer && player.getPlayerStatus() != PLAYER_STATUS.ELIMINATED) {
                isVictory = false;
                break;
            }
        }
        if (isVictory) {
            attackingPlayer.setGameState(VICTORY);
            setGameState(VICTORY);
        }
        return isVictory;
    }
    
    /**
     * Draw a card from the deck for the player if he conquered at least 1 territory in his turn
     *
     * @param attacker the attacker
     */
    public void drawCardForWinner(Player attacker) {
        if (deck.size() != 0) {
            Card card = drawCard();
            attacker.addCardToPlayersHand(card);
            log.append("        " + attacker.getPlayerName() + " received the " + card.getCardType().name() + " card");
        } else {
            log.append("        " + attacker.getPlayerName() + " doesn't receive any card since the deck has run out of card");
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
        log.append("    " + card.getCardType() + " is removed from the deck");
        deck.trimToSize();
        return card;
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
     * Get the maximum number of attacking dice roll that attacker can use depending on the attacking territory's armies
     *
     * @return the maximum number of dice roll that attacker may use
     */
    public int getMaxAttackingRoll(String attackingTerritoryName) {
        Territory attackingTerritory = getGameMap().getATerritory(attackingTerritoryName);
        return Math.min(3, attackingTerritory.getArmies() - 1);
    }
    
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
    
    public void performBattleIfPossible() {
        if (currentBattle != null) {
            log.append("        Battle between " + currentBattle.getAttacker().getPlayerName() +
                    "'s " + currentBattle.getAttackingTerritory().getName() +
                    " and " + currentBattle.getDefender().getPlayerName() +
                    "'s " + currentBattle.getDefendingTerritory().getName());
        
        /* Both players roll dice */
            currentBattle.attackerRollDice();
            log.append("            " + currentBattle.getAttacker().getPlayerName() + " roll dice: " +
                    currentBattle.getAttackerDice().getRollsResult());
            currentBattle.defenderRollDice();
            log.append("            " + currentBattle.getDefender().getPlayerName() + " roll dice: " +
                    currentBattle.getDefenderDice().getRollsResult());
        }
    }
    
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
        if (message.contains("Successfully moved")) {
            if (gameState != VICTORY) {
                nextPlayerTurn();
            }
        }
        updateGameMapTableModel();
        broadcastGamePlayChanges();
        
        return message;
    }
    // endregion
    
    // region For Fortification Phase
    
    /**
     * Change the game phase of the current player to other phase.
     *
     * @param newGameStates the game phase
     */
    public void changePhaseOfCurrentPlayer(GAME_STATES newGameStates) {
        currentPlayer.setGameState(newGameStates);
        log.append("    " + currentPlayer.getPlayerName() + " move to " + currentPlayer.getGameState() + " phase");
        broadcastGamePlayChanges();
    }
    // endregion
    
    // region Public methods
    
    // region Private methods
    public void letBotsPlay() {
        if (turnCounter <= maxTurns && gameState != VICTORY) {
            // Bots reinforce and declare attack if it wants
            botsReinforcement();
            attackCounter = 0;
            botsAttack();
        }
    }
    
    // endregion
    
    public void botsAttack() {
        if (attackCounter >= MAX_ATTACK_TURN) {
            broadcastGamePlayChanges();
        } else {
            currentPlayer.attack(this);
            attackCounter++;
            // If bots declare new attack, let defender choose number of defending dice
            if (currentBattle != null && !currentPlayer.isCheaterBot()) {
                Player defender = currentBattle.getDefender();
                if (defender.isHuman()) {
                    needDefenderReaction = true;
                    broadcastGamePlayChanges();
                } else {
                    int defendingDice = defender.botChooseDefendingDice(currentBattle.getMaxDefendingRoll());
                    currentBattle.setDefendingDice(defendingDice);
                    botsFortification(true);
                }
            } else { // If bots quits attacking or cannot attack anymore
                if (currentPlayer.hasConqueredTerritories()) {
                    drawCardForWinner(currentPlayer);
                    currentPlayer.setHasConqueredTerritories(false);
                }
                botsFortification(false);
            }
        }
    }
    
    private void conquerTerritoryIfPossible() {
        currentPlayer.moveArmiesToConqueredTerritory(this);
    }
    
    public void botsFortification(boolean continueAttack) {
        // Prevent normal rules from applying to Cheater Bot
        if (!currentPlayer.isCheaterBot()) {
            performBattleIfPossible();
            decideBattleResultIfPossible();
            conquerTerritoryIfPossible();
        }
        currentBattle = null;
        
        if (gameState == VICTORY) {
            broadcastGamePlayChanges();
            return;
        }
        
        if (!continueAttack) {
            // Fortification phase
            currentPlayer.nextPhase();
            currentPlayer.fortification(this, null, null, -1);
            nextPlayerTurn();
        } else {
            botsAttack();
        }

//        updateGameMapTableModel();
//        broadcastGamePlayChanges();
    }
    
    /**
     * This function lets the game advance when the current player is a bot until a human player's turn
     */
    private void botsReinforcement() {
        // Reinforcement phase
        currentPlayer.reinforcement(this, null, null);
        currentPlayer.nextPhase();
    }
    
    /**
     * The player status
     */
    public enum PLAYER_STATUS {
        IN_GAME, ELIMINATED
    }
    // endregion
}