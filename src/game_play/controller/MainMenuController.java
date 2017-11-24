/* 
 * Risk Game Team 2
 * MainMenuController.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.controller;

import game_play.view.screens.MainMenuFrame;
import map_editor.controller.MapEditorController;
import shared_resources.helper.UIHelper;

import javax.swing.*;

/**
 * MainMenuController helps functioning the MainMenuFrame, allowing users to go to different screen of the application
 *
 * @author Team 2
 * @version 1.0
 */
public class MainMenuController extends JFrame {
    
    // region Attributes declaration
    private MainMenuFrame mainMenuFrame;
    // endregion
    
    // region Constructors
    
    /**
     * This method is used to launch main screen of application.
     */
    public MainMenuController() {
        mainMenuFrame = new MainMenuFrame();
        registerToBeListener();
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Register this controller to be the listener of all UI components.
     */
    private void registerToBeListener() {
        mainMenuFrame.addMapEditorButtonListener(e -> openMapEditorScreen());
        mainMenuFrame.addPlayGameButtonListener(e -> openPlayGameScreen());
        mainMenuFrame.addLoadGameButtonListener(e -> new GamePlayController(this).loadSavedGame());
        mainMenuFrame.addTournamentButtonListener(e -> openTournamentScreen());
        mainMenuFrame.addQuitButtonListener(e -> exitGame());
    }
    // endregion
    
    // region Methods to handle events
    
    /**
     * Disable MainMenuFrame, show MapEditorFrame.
     */
    private void openMapEditorScreen() {
        UIHelper.disableFrame(mainMenuFrame);
        new MapEditorController(this);
    }
    /**
     * Disable MainMenuFrame, show TournamentFrame.
     */
    private void openTournamentScreen() {
        UIHelper.disableFrame(mainMenuFrame);
        new TournamentController(this);
    }
    
    
    
    /**
     * Disable MainMenuFrame, show MapSelectorFrame
     */
    private void openPlayGameScreen() {
        UIHelper.disableFrame(mainMenuFrame);
        new MapSelectorController(this);
    }
    
    /**
     * Exit game.
     */
    private void exitGame() {
        UIHelper.closeFrame(mainMenuFrame);
    }
    
    /**
     * Gets the main menu frame object.
     *
     * @return the main menu frame
     */
    public MainMenuFrame getMainMenuFrame() {
        return mainMenuFrame;
    }
    
    // endregion
}
