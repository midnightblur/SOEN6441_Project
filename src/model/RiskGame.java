package model;

import model.game_entities.Card;
import model.game_entities.GameMap;
import model.game_entities.Player;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;
import model.ui_models.MapTableModel;
import utilities.Config;

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
public class RiskGame extends Observable {
    private int armyValue = 5;
    private int numOfContinents;
    private Vector<Card> deck = new Vector<>();
    private Vector<Player> players = new Vector<>();
    private GameMap gameMap;
    private static RiskGame instance = null;
    private Config.GAME_STATES gameState = Config.GAME_STATES.ENTRY_MENU;
    private boolean playing = false;
    private Random rand = new Random();
    private Player currPlayer;
    
    private MapTableModel mapTableModel;
    
    /**
     * private constructor preventing any other class from instantiating.
     */
    private RiskGame() {
        mapTableModel = new MapTableModel();
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

    // region Getter and Setter methods for class RiskGame's private attributes

    /* Getter and Setter methods for class RiskGame's private attributes */

    public GameMap getGameMap() {
        return gameMap;
    }
    
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
    
    public int getNumOfContinents() {
        return this.numOfContinents;
    }
    
    public void setNumOfContinents(int numOfContinents) {
        this.numOfContinents = numOfContinents;
    }
    
    public Vector<Player> getPlayers() {
        return this.players;
    }
    
    public Vector<Card> getDeck() {
        return this.deck;
    }

    public void setCurrPlayer(Player player) {
        this.currPlayer = player;
    }

    public Player getCurrPlayer() {
        return this.currPlayer;
    }
    
    /* Public methods */
    
    public Config.GAME_STATES getGameState() {
        return this.gameState;
    }
    
    public void setGameState(Config.GAME_STATES GAMESTATES) {
        this.gameState = GAMESTATES;
    }

    // endregion
    
    /**
     * Initiates the startup phase before game play. Sets the game map according
     * to the filepath, sets the number of players playing the game, sets the
     * deck of cards, and distributes territories to the players randomly.
     *
     * @param filepath The String value of the path to a valid map file
     * @param currPlayers The int value of the initial number of players
     */
    public void startupPhase(String filepath, int currPlayers) {
        try {
            this.gameMap = GameMapHelper.loadGameMap(filepath);
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
        placeArmies();
        setCurrPlayer(players.firstElement());
        this.setGameState(Config.GAME_STATES.REINFORCEMENT_PHASE);
        broadcastGamePlayChanges();
    }
    
    /**
     * Initiates the actual game play. Consists of the round-robin fashion of
     * players' turns that include reinforcement phase, attack phase, and fortification phase.
     */
    public void playPhases() {
        /* Hand out cards for build 1 presentation. To be commented out for normal game play */
        for (Player player : players) {
            System.out.println("player1's hand: (" + player.getPlayersHand().size() + ")");
            for (int i = 0; i < player.getPlayerID(); i++) {
                player.addCardToPlayersHand(drawCard());
                System.out.println("\t" + drawCard().getCardType() + " card");
            }
        }
        
        playing = true;
        while(playing) {
            for (Player player : players) {
                setCurrPlayer(player);
                reinforcementPhase(player);
//                /* turn playing to false at the end of the attacking phase if player.size() is 1 */
//                attackingPhase(player);
                fortificationPhase(player);
            }
        }
        System.out.println("Player " + players.get(0).getPlayerID() + " wins!");
    }
    
    /**
     * The reinforcement phase includes allowing the players to hand in their cards for
     * armies (or force them to if they have more than or equal to 5 cards), assign
     * to-be-allocated armies to the players according to the number of territories they
     * control (to a minimum of 3), and allows players to place those armies.
     *
     * @param player
     */
    public void reinforcementPhase(Player player) {
        // TODO: assign "Trade Cards" button listener for the 'true' value in the while condition
        // Give option to trade cards for each player
        while (player.getPlayersHand().size() >= 5 || true) {
            this.setGameState(Config.GAME_STATES.REINFORCEMENT_PHASE);
            broadcastGamePlayChanges();
            tradeInCards(player);
        }

//        this.setGameState(Config.GAME_STATES.REINFORCEMENT_PHASE);
//        broadcastGamePlayChanges();
        
        // Assign players number of armies to allocate (minimum 3) depending on the players' territories.
        int armiesToGive = gameMap.getTerritoriesOfPlayer(player).size() / 3;
        if (armiesToGive < 3) {
            armiesToGive = 3;
        }
        player.setUnallocatedArmies(armiesToGive);

        // Place unallocated armies.
        placeArmies(player);
    }
    
    /**
     *
     * @param player
     */
    public void fortificationPhase(Player player) {
        
        // @Brian the model should not know anything about the view, therefore:
        // we need an setter here in the model that takes ( array[terr][int armies])
        //
        // TODO: assign "Done" button listener for the 'true' value in the while condition
        while (true) {
            this.setGameState(Config.GAME_STATES.FORTIFICATION_PHASE);
            broadcastGamePlayChanges();

            boolean moved = false;
            while (!moved) {  // show "Move Armies" button only while no valid move has been made.

                // TODO: assign "From (Territory)" and "To (Territory)" and "Army/Armies" button listener for the two variables
                Territory territoryFrom = gameMap.getArbitraryTerritory();  // TODO: one of the territories of player from button listener
                Territory territoryTo = gameMap.getArbitraryTerritory();  // TODO: another one of the territories of player from button listener
                int army = 1;  // TODO: army/armies value specified by the player from form listener

                // validate if the two territories are owned by the player, are different, and are neighbours.
                if (territoryFrom.isOwnedBy(player.getPlayerID()) && !territoryFrom.equals(territoryTo)
                        && territoryFrom.isNeighbor(territoryTo.getName())) {
                    if (territoryFrom.getArmies() != 1 && army < territoryFrom.getArmies()) {
                        territoryFrom.reduceArmies(army);
                        territoryTo.addArmies(army);
                        moved = true;
                    } else {
                        System.out.println("You do not have enough armies in " + territoryFrom.getName()
                                + " to make that move");
                    }
                }
            }
        }
    }
    
    /**
     * Method to initialize the players according to
     * the number of players (currPlayers).
     */
    public void initPlayers(int currPlayers) {
        System.out.println("Initializing players...");
        
        for (int i = 0; i < currPlayers; i++) {
            players.add(new Player());
        }
    }

    // region Card related helper methods

    /**
     * Sets a deck that contains cards from Card class with equal distribution of all the three card types.
     * The total number of cards is set to the closest value to the total number of territories
     * that is a factor of three, and is greater or equal to the total number of territories.
     */
    public void initDeck() {
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
     * Allows players to trade in their cards (or force them to if they have more than
     * or equal to 5 cards). The method checks the user's option to trade cards between
     * 'three of a kind' and 'one of each'. The 'three of a kind' option checks to see
     * if a player has 3 cards of the same card type, and if so, removes them from that
     * player's hand and gives back a number of armies according to the army value. The
     * 'one of each' option check to see if a player has 3 cards with one of each card
     * type, and if so, removes them from that player's hand and gives back a number of
     * armies according to the army value. The army value is first set as '5' at the
     * beginning of the game, but increases every time a player successfully trades in
     * 3 pairs of cards.
     *
     * @param player The object of a Player class
     */
    public void tradeInCards(Player player) {
        System.out.println("-- Entered 'Trade In Cards' panel --");
        
        // TODO: change these button variables to appropriate listeners
        int choice; // 0 -> no trade ; 1 -> three of a kind ; 2 -> one of each
        choice = rand.nextInt(3);
        
        int counter = 0;
        if (choice == 1) {  // check if there are three of the same kind (order of Infantry > Cavalry > Artillery).
            for (int cardIndex = 0; cardIndex < Card.getTypesCount(); cardIndex++) {
                counter = 0;
                for (int i = 0; i < player.getPlayersHand().size(); i++) {
                    if (player.getPlayersHand().get(i).getCardType().equals(Card.cardTypes.elementAt(cardIndex))) {
                        counter++;
                    }
                }
                if (counter >= 3) {
                    int deleteCounter = 0;
                    for (Card card : player.getPlayersHand()) {
                        if (card.getCardType().equals(Card.cardTypes.elementAt(cardIndex))) {
                            player.getPlayersHand().remove(card);
                            deleteCounter++;
                        }
                        if (deleteCounter >= 3) {
                            break;
                        }
                    }
                    player.getPlayersHand().trimToSize();
                    player.addUnallocatedArmies(armyValue);
                    armyValue += 5;
                    break;
                }
            }
        } else if (choice == 2) {  // check if there are one for each card type.
            for (int cardIndex = 0; cardIndex < Card.getTypesCount(); cardIndex++) {
                counter = 0;
                for (int i = 0; i < player.getPlayersHand().size(); i++) {
                    if (player.getPlayersHand().get(i).getCardType().equals(Card.cardTypes.elementAt(cardIndex))) {
                        counter++;
                        break;
                    }
                }
            }
            if (counter == 3) {
                for (int i = 0; i < Card.getTypesCount(); i++) {
                    for (Card card : player.getPlayersHand()) {
                        if (card.getCardType().equals(Card.cardTypes.elementAt(i))) {
                            player.getPlayersHand().remove(card);
                            player.getPlayersHand().trimToSize();
                            break;
                        }
                    }
                }
                player.addUnallocatedArmies(armyValue);
                armyValue += 5;
            }
        }
    }

    // endregion

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
     * [# of initial armies = (total# of territories) * (2.75) / (total# of players)].
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
        int playerIndex = 0;
        while (!noMoreArmies) {
            ArrayList<Territory> territoryList = new ArrayList<>();
            if (!(playerIndex < players.size())) {
                playerIndex = 0;
            }
            // Add a player's territories to list if they do not contain any armies
            for (Map.Entry<String, Territory> entry :
                    gameMap.getTerritoriesOfPlayer(players.elementAt(playerIndex)).entrySet()) {
                if (entry.getValue().getArmies() == 0) {
                    territoryList.add(entry.getValue());
                }
            }
            // If there are no territories without any armies, then add all of player's territories to the list.
            if (territoryList.size() == 0) {
                for (Map.Entry<String, Territory> entry :
                        gameMap.getTerritoriesOfPlayer(players.elementAt(playerIndex)).entrySet()) {
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
    
    /**
     * Overloaded method to place armies for a specific player until the player
     * has no more armies to place.
     *
     * @param player The object of Player class
     */
    public void placeArmies(Player player) {
        while (player.getUnallocatedArmies() != 0) {
            ArrayList<Territory> territoryList = new ArrayList<>();
            // Add a player's territories to list if they do not contain any armies
            for (Map.Entry<String, Territory> entry :
                    gameMap.getTerritoriesOfPlayer(player).entrySet()) {
                if (entry.getValue().getArmies() == 0) {
                    territoryList.add(entry.getValue());
                }
            }
            // If there are no territories without any armies, then add all of player's territories to the list.
            if (territoryList.size() == 0) {
                for (Map.Entry<String, Territory> entry :
                        gameMap.getTerritoriesOfPlayer(player).entrySet()) {
                    territoryList.add(entry.getValue());
                }
            }
            int territoryIndex = rand.nextInt(territoryList.size());
            territoryList.get(territoryIndex).addArmies(1);
            player.reduceUnallocatedArmies(1);
        }
    }
    
    /**
     * Method to update the GamePlayModel and notify the Observer.
     */
    private void broadcastGamePlayChanges() {
        setChanged();
        notifyObservers();
    }
    
    
    public void placeArmiesFroUserSelection(Map<Territory, Integer> armiesToPlace) {
        // TODO: implement
    }
}