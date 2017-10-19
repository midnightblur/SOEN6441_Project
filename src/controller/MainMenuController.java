/* 
 * Risk Game Team 2
 * MainMenuController.java
 * Version 1.0
 * Oct 18, 2017
 */
package controller;

import view.helpers.UIHelper;
import view.screens.MainMenuFrame;
import view.screens.MapSelectorFrame;

import javax.swing.*;

/**
 * The Class MainGameController  is
 * responsible for launching different game screens.
 *
 * @author Team 2
 * @version 1.0
 */
public class MainMenuController extends JFrame {
    
    // region Attributes declaration
    /**
     * The main menu frame.
     */
    private MainMenuFrame mainMenuFrame;
    
    /**
     * The map selector frame.
     */
    private MapSelectorFrame mapSelectorFrame;
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
     * Gets the main menu frame.
     *
     * @return the main menu frame
     */
    public MainMenuFrame getMainMenuFrame() {
        return mainMenuFrame;
    }
    
    // endregion
    
    // region Public methods
    
    // endregion
    
    // region Private methods
    
    /**
     * Register to be listener.
     */
    private void registerToBeListener() {
        mainMenuFrame.addMapEditorButtonListener(e -> openMapEditorScreen());
        mainMenuFrame.addPlayGameButtonListener(e -> openPlayGameScreen());
        mainMenuFrame.addQuitButtonListener(e -> exitGame());
    }
    
    /**
     * Open map editor screen.
     */
    private void openMapEditorScreen() {
        UIHelper.disableFrame(mainMenuFrame);
        new MapEditorController(this);
    }
    
    /**
     * Open play game screen.
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
    // endregion
}
