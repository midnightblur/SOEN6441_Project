package model;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Class initiates Risk Game with the welcome message, followed by the following features:
 * 1) determine number of players
 * 2) set number of cards in deck
 * 3) randomly (but fairly) distribute countries among all players
 * 4) start turn (repeated in round-robin fashion until only one active player remains)
 *      a) reinforcement phase
 *      b) attack phase
 *      c) fortifications phase
 * 5) end of game
 */
public class RiskGame {
    private boolean isPlaying;
    private int currPlayers;
    private int humanPlayers;
    private int numOfTerritories;
    private int numOfContinents;
    private int numOfCards;
    private Vector<Card> deck = new Vector<>();

    /**
     * Constructor for RiskGame. Starts the game and initializes class attributes.
     */
    public RiskGame(GameMap gameMap) {
        isPlaying = true;
        numOfTerritories = gameMap.getTerritoriesCount();

        welcomeMsg();

        countPlayers();
        displayGameInfo();

        setDeck();
        /*
        // test random card draw
        for (int i=0; i<42; i++) {
            drawCard();
        }
        */


        distributeTerritories();
    }

    /**
     * Getters and Setters methods for class RiskGame's private attributes
     */
    public boolean getPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public int getCurrPlayers() {
        return this.currPlayers;
    }

    public void setCurrPlayers(int currPlayers) {
        this.currPlayers = currPlayers;
    }

    public int getHumanPlayers() {
        return this.humanPlayers;
    }

    public void setHumanPlayers(int humanPlayers) {
        this.humanPlayers = humanPlayers;
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


    /**
     * Prints out the welcome message at the beginning of the game
     */
    private void welcomeMsg() {
        System.out.println();
        System.out.println("==================================");
        System.out.println("-  Welcome to Team2's Risk Game  -");
        System.out.println("==================================");
        System.out.println();
    }

    /**
     * Prompts the users to determine the total number of players in the game
     * and the number of human players. The number of players must be between
     * 2 to number of territories in the map. The number of human players must
     * be less than the number of players.
     */
    private void countPlayers() {
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        do {
            try {
                System.out.print("Please enter the number of players: ");
                currPlayers = sc.nextInt();
                if (currPlayers >= 2 && currPlayers <= numOfTerritories) {
                    flag = true;
                }
                else {
                    System.out.print("Number of players must be between 2 and " + numOfTerritories + ". ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Number of players must be between 2 and " + numOfTerritories + ". ");
                flag = false;
            }
        } while (!flag);

        flag = false;
        do {
            try {
                System.out.print("Please enter the number of human players: ");
                humanPlayers = sc.nextInt();
                if (humanPlayers >= 0 && humanPlayers <= currPlayers) {
                    flag = true;
                }
                else {
                    System.out.print("Please enter an integer between 0 and " + currPlayers + ". ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Please enter an integer between 0 and " + currPlayers + ". ");
                flag = false;
            }
        } while (!flag);
    }

    public void displayGameInfo() {
        System.out.println();
        System.out.println("Total number of territories: " + numOfTerritories);
        System.out.println("Total number of players: " + currPlayers + " (human players: " + humanPlayers + ")");
        System.out.println();
    }

    /**
     * Sets a deck that contains cards from Card class with equal distribution of all the three card types.
     * The total number of cards is set to the closest value to the total number of territories
     * that is a factor of three, and is greater or equal to the total number of territories.
     */
    public void setDeck() {
        System.out.println("Initializing deck...");
        int cardTypes = Card.getTypesCount();
        numOfCards = numOfTerritories + (numOfTerritories % cardTypes) * cardTypes;
        System.out.println("numOfCards: " + numOfCards);
        for (int i=0; i<numOfCards; i++) {
            deck.add(new Card(i%cardTypes));
        }

        /*
        // test deck initialization
        System.out.println("card total count: " + numOfCards);
        for (int i=0; i<numOfCards; i++) {
            System.out.println("card " + (i+1) + ": " + deck.get(i).getType());
        }
        */
    }

    /**
     * Draws a random card from the deck.
     */
    public void drawCard() {
        Random rand = new Random();
        int index = rand.nextInt(deck.size());
        Card drawn = deck.elementAt(index);
        deck.remove(deck.elementAt(index));
        deck.trimToSize();

        /*
        // test print card type
        System.out.println("card type: " + drawn.getType());
        */
    }

    /**
     * Distributes the territories in the map randomly to the players. Although the territories
     * are distributed randomly, the number of territories should be as evenly distributed as
     * possible between all of the players.
     */
    public void distributeTerritories() {
        System.out.println("Distributing territories...");

    }

}