/* 
 * Risk Game Team 2
 * Driver.java
 * Version 1.0
 * Oct 18, 2017
 */
import game_play.controller.MainMenuController;

/**
 * Main driver class is responsible for starting the Application.
 */
public class Driver {
    
    /**
     * Start the application by making a Main Game Controller.
     *
     * @param args CLI arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        new MainMenuController();
    }
}
