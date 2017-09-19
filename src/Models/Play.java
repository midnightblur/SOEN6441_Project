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
    private boolean is_playing;
    private int curr_players;
    private int human_players;
    private int num_of_territories;
    private int num_of_continents;

    /**
     * Constructor for RiskGame. Starts the game and initializes class attributes.
     */
    public RiskGame() {
        is_playing = true;

        welcome_msg();

        // TODO: 1) load map -- map import & parsing feature
        num_of_territories = 10;
        num_of_continents = 3;

        countPlayers();

        setDeck();

        distributeTerritories();
    }

    /**
     * Getters and Setters methods for class RiskGame's private attributes
     */
    public boolean getIs_playing() {
        return this.is_playing;
    }

    public void setIs_playing(boolean is_playing) {
        this.is_playing = is_playing;
    }

    public int getCurr_players() {
        return this.curr_players;
    }

    public void setCurr_players(int curr_players) {
        this.curr_players = curr_players;
    }

    public int getHuman_players() {
        return this.human_players;
    }

    public void setHuman_players(int human_players) {
        this.human_players = human_players;
    }

    public int getNum_of_territories() {
        return this.num_of_territories;
    }

    public void setNum_of_territories(int num_of_territories) {
        this.num_of_territories = num_of_territories;
    }

    public int getNum_of_continents() {
        return this.num_of_continents;
    }

    public void setNum_of_continents(int num_of_continents) {
        this.num_of_continents = num_of_continents;
    }


    /**
     * Prints out the welcome message at the beginning of the game
     */
    private void welcome_msg() {
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
                curr_players = sc.nextInt();
                if (curr_players >= 2 && curr_players <= num_of_territories) {
                    flag = true;
                }
                else {
                    System.out.print("Number of players must be between 2 and " + num_of_territories + ". ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Number of players must be between 2 and " + num_of_territories + ". ");
                flag = false;
            }
        } while (!flag);

        flag = false;
        do {
            try {
                System.out.print("Please enter the number of human players: ");
                human_players = sc.nextInt();
                if (human_players >= 0 && human_players <= curr_players) {
                    flag = true;
                }
                else {
                    System.out.print("Please enter an integer between 0 and " + curr_players + ". ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Please enter an integer between 0 and " + curr_players + ". ");
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