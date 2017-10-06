package model;

import util.Config;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

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
    private int currPlayers;
    private int numOfTerritories;
    private int numOfContinents;
    private int numOfCards;
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
    public int getCurrPlayers() {
        return this.currPlayers;
    }
    
    public void setCurrPlayers(int currPlayers) {
        this.currPlayers = currPlayers;
    }
    
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
        
        if (currPlayers > 1 && currPlayers <= gameMap.getTerritoriesCount()) {
            this.currPlayers = currPlayers;
        } else {
            System.err.println("Invalid number of players. Should catch it in the view.");
        }
        
        initPlayers();
        initDeck();
        distributeTerritories();
        giveArmiesToPlayers();
        placeArmies();
        
        
        System.out.println("number of territories: " + gameMap.getTerritoriesCount());
        // testing count players
        for (int i=0; i<players.size(); i++) {
            System.out.println("player " + players.get(i).getPlayerID());
        }
        // testing deck initialization
        System.out.println("deck size: " + deck.size());
        // testing territory dist
        for (int i=0; i<players.size(); i++) {
            System.out.println("player " + players.get(i).getPlayerID());
        }
        for (int i=0; i<gameMap.getTerritoriesCount(); i++) {
            System.out.println("territory name: " + gameMap.g)
        }
        System.out.println("")
        for (int i=0; i<)
        System.out.println()
    }
    
    /**
     * private void method to initialize the players according to
     * the number of players (currPlayers).
     */
    private void initPlayers() {
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
        
        numOfCards = gameMap.getTerritoriesCount() +
                (gameMap.getTerritoriesCount() % Card.getTypesCount()) * Card.getTypesCount();
        for (Card.CARD_TYPE cardType : Card.CARD_TYPE.values()) {
            deck.add(new Card(cardType));
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
        for (int i = 0; i < numOfTerritories; i++) {
            int territoryIndex = rand.nextInt(territoryArrList.size());
            gameMap.getATerritory(territoryArrList.get(territoryIndex)).setOwner(players.elementAt(playerIndex));
            if (playerIndex < currPlayers) {
                playerIndex++;
            } else {
                playerIndex = 0;
            }
            System.out.print("player Index: " + playerIndex);
            territoryArrList.remove(territoryIndex);
        }
        
        // TODO: test for territory distribution
    }
    
    /**
     * (# of terriroties) * (2.75) / (# of players) = number of armies per player
     */
    public void giveArmiesToPlayers() {
    
    }
    
    /**
     *
     */
    public void placeArmies() {
    
    }
}