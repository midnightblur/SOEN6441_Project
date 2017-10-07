package model;

import com.sun.security.auth.SolarisNumericUserPrincipal;
import util.Config;

import java.util.*;

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
public class RiskGame {
    private int numOfTerritories;
    private int numOfContinents;
    private Vector<Card> deck = new Vector<>();
    private Vector<Player> players = new Vector<>();
    private GameMap gameMap;
    private static RiskGame instance = null;
    private Config.GAME_STATES gameState = Config.GAME_STATES.ENTRY_MENU;
    
    /**
     * private constructor preventing any other class from instantiating.
     */
    private RiskGame() {
    }
    
    /**
     * Static instance method to determine if an object of RiskGame already exists
     *
     * @return instance of the singleton object
     */
    public static RiskGame getInstance() {
        if (instance == null) {
            instance = new RiskGame();
        }
        return instance;
    }
    
    /**
     * Getters and Setters methods for class RiskGame's private attributes
     */
    public int getNumOfTerritories() {
        return this.numOfTerritories;
    }

    /*
    public void setNumOfTerritories(int numOfTerritories) {
        this.numOfTerritories = numOfTerritories;
    }
    */
    
    public int getNumOfContinents() {
        return this.numOfContinents;
    }
    
    public void setNumOfContinents(int numOfContinents) {
        this.numOfContinents = numOfContinents;
    }
    
    /* Public methods */
    
    public Config.GAME_STATES getGameState() {
        return this.gameState;
    }
    
    public void setGameState(Config.GAME_STATES GAMESTATES) {
        this.gameState = GAMESTATES;
    }
    
    /**
     * Initiates the game map according to the filepath, sets the number of
     * players playing the game, sets the deck of cards, and distributes
     * territories to the players randomly.
     *
     * @param filepath:    String value of the path to a valid map file.
     * @param currPlayers: int value of the initial number of players.
     */
    public void initStartup(String filepath, int currPlayers) {
        try {
            this.gameMap = GameMapHandler.loadGameMap(filepath);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        // TODO: handle this error someplace else?
        if (!(currPlayers > 1 && currPlayers <= gameMap.getTerritoriesCount())) {
            System.err.println("Invalid number of players. Should catch it in the view.");
            return;
        }
        
        initPlayers(currPlayers);
        initDeck();
        distributeTerritories();
        giveInitialArmies();
        
        // testing players armies
        for (Player player : players) {
            System.out.println("player " + player.getPlayerID() + "'s unallocated armies before allocation: "
                    + player.getUnallocatedArmies());
        }
        
        placeArmies();
        
        
        
        // TESTING... TO BE PUT INTO JUNIT TESTING LATER ON
        System.out.println("number of territories: " + gameMap.getTerritoriesCount());
        // testing count players
        System.out.println("list of players:");
        for (int i=0; i<players.size(); i++) {
            System.out.println("\tplayer " + players.get(i).getPlayerID());
        }
        // testing deck initialization
        System.out.println("deck size: " + deck.size());
        // testing territory dist
        for (Player player : players) {
            System.out.println("player " + player.getPlayerID() + "'s territories: (total: "
                    + gameMap.getTerritoriesOfPlayer(player).size() + ")");
            for (Map.Entry<String, Territory> entry : gameMap.getTerritoriesOfPlayer(player).entrySet()) {
                System.out.println("\t" + entry.getValue().getName());
            }
        }
        // testing army placement
        for (Player player : players) {
            System.out.println("player " + player.getPlayerID() + ":");
            for (Map.Entry<String, Territory> entry : gameMap.getTerritoriesOfPlayer(player).entrySet()) {
                System.out.println("\t" + entry.getValue().getName()
                        + " (num of armies: " + entry.getValue().getArmies() + ")." );
            }
        }
    }
    
    /**
     * private void method to initialize the players according to
     * the number of players (currPlayers).
     */
    private void initPlayers(int currPlayers) {
        System.out.println("Initializing players...");
        
        for (int i = 0; i < currPlayers; i++) {
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
        int numOfCards = gameMap.getTerritoriesCount() +
                (gameMap.getTerritoriesCount() % Card.getTypesCount()) * Card.getTypesCount();
        for (int i = 0; i < numOfCards; i++) {
            if (!(typeNumber < Card.getTypesCount())) {
                typeNumber = 0;
            }
            deck.add(new Card(typeNumber));
            typeNumber++;
        }

        /*
        // test deck initialization
        System.out.println("card total count: " + numOfCards);
        for (int i=0; i<numOfCards; i++) {
            System.out.println("card " + (i+1) + ": " + deck.get(i).getCardType());
        }
        */
    }

//    /**
//     * Draws a random card from the deck.
//     */
//    public void drawCard() {
//        Random rand = new Random();
//        int index = rand.nextInt(deck.size());
//        Card drawn = deck.elementAt(index);
//        deck.remove(deck.elementAt(index));
//        deck.trimToSize();
//
//        /*
//        // test print card type
//        System.out.println("card type: " + drawn.getCardType());
//        */
//    }
    
    /**
     * Distributes the territories in the map randomly to the players. Although the territories
     * are distributed randomly, the number of territories should be as evenly distributed as
     * possible between all of the players.
     */
    public void distributeTerritories() {
        System.out.println("Distributing territories...");
        
        ArrayList<String> territoryArrList = new ArrayList<>();
        for (Map.Entry<String, Territory> entry : gameMap.getTerritories().entrySet()) {
            territoryArrList.add(entry.getValue().getName());
        }
        
        Random rand = new Random();
        int playerIndex = 0;
        for (int i = 0; i < gameMap.getTerritoriesCount(); i++) {
            if (!(playerIndex < players.size())) {
                playerIndex = 0;
            }
            int territoryIndex = rand.nextInt(territoryArrList.size());
            gameMap.getATerritory(territoryArrList.get(territoryIndex)).setOwner(players.elementAt(playerIndex));
            playerIndex++;
            territoryArrList.remove(territoryIndex);
        }
    }
    
    /**
     * This method gives initial armies per player according to the following algorithm:
     * [# of initial armies = (# of territories) * (2.75) / (# of players)]
     */
    public void giveInitialArmies() {
        int armiesToGive = (int) (gameMap.getTerritoriesCount() * Config.INITIAL_ARMY_RATIO / players.size());
        for (Player player : players) {
            player.setUnallocatedArmies(armiesToGive);
        }
    }
    
    /**
     * This method allows the players to allocate all of the unallocated armies in a
     * round-robin fashion.
     */
    public void placeArmies() {
        boolean noMoreArmies = false;
        Random rand = new Random();
        int playerIndex = 0;
        while (!noMoreArmies) {
            ArrayList<Territory> territoryList = new ArrayList<>();
            if (!(playerIndex < players.size())) {
                playerIndex = 0;
            }
            // TODO: get rid of this line following print out line.
            System.out.println("player " + players.elementAt(playerIndex).getPlayerID() + "'s turn to allocate army.");
            for (Map.Entry<String, Territory> entry :
                    gameMap.getTerritoriesOfPlayer(players.elementAt(playerIndex)).entrySet()) {
                if (entry.getValue().getOwner().equals(players.elementAt(playerIndex))) {
                    territoryList.add(entry.getValue());
                }
            }
            int territoryIndex = rand.nextInt(territoryList.size());
            territoryList.get(territoryIndex).addArmies(1);
            players.elementAt(playerIndex).reduceUnallocatedArmies(1);
            playerIndex++;
            noMoreArmies = true;
            for (Player player : players) {
                if (player.getUnallocatedArmies() != 0) {
                    noMoreArmies = false;
                }
            }
        }
    }
}