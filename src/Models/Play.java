package Models;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 1) start up phase,
 * 2) player phase (reinforcement phase, attack phase, fortification phase)
 * 3) reinforcement phase
 */
public class Play {
    public static void main(String[] args) {
        RiskGame game = new RiskGame();

    }
}


/**
 * Class initiates Risk Game with the welcome message, followed by the following features:
 * 1) load map
 * 2) determine number of players
 * 3) set number of cards in deck
 * 4) randomly (but fairly) distribute countries among all players
 * 5) start turn (repeated in round-robin fashion until only one active player remains)
 *      a) reinforcement phase
 *      b) attack phase
 *      c) fortifications phase
 * 6) end of game
 */
class RiskGame {
    private boolean isPlaying;
    private int currPlayers;
    private int humanPlayers;
    private int numOfTerritories;
    private int numOfContinents;

    /**
     * Constructor for RiskGame. Starts the game and initializes class attributes.
     */
    public RiskGame() {
        isPlaying = true;

        welcomeMsg();

        // TODO: 1) load map -- map import & parsing feature
        numOfTerritories = 10;
        numOfContinents = 3;

        countPlayers();

        setDeck();

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

    public void setNumOfTerritories(int numOfTerritories) {
        this.numOfTerritories = numOfTerritories;
    }

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

    /**
     * Sets the number of cards in the deck according to the total number of territories in the map
     */
    public void setDeck() {
        System.out.println("git test");
    }

    /**
     * Distributes the territories in the map randomly to the players. Although the territories
     * are distributed randomly, the number of territories should be as evenly distributed as
     * possible between all of the players.
     */
    public void distributeTerritories() {
        System.out.println("git test");
    }

}