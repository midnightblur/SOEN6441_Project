/*
 * @file  Driver.java
 * @brief
 *
 *
 *
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
import controller.MainMenuController;

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
