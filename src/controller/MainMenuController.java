package controller;

import model.RiskGame;
import utilities.Config;
import view.screens.MainMenuFrame;

import java.awt.event.WindowEvent;

/**
 * The Main Game Controller class is
 * responsible for launching different game screens
 */
public class MainMenuController {
    private MainMenuFrame mainMenuFrame;

    /**
     * This method is used to launch main screen of application
     */
    public MainMenuController() {
        mainMenuFrame = new MainMenuFrame();
        registerToBeListener();
    }

    public void invokeFrame() {
        mainMenuFrame.setVisible(true);
        mainMenuFrame.setEnabled(true);
    }
    
    private void registerToBeListener() {
        mainMenuFrame.addMapEditorButtonListener(e -> openMapEditorScreen());
        mainMenuFrame.addPlayGameButtonListener(e -> openPlayGameScreen());
        // TODO: mainMenuFrame.addPlayGameButtonListener(e -> new StartupController(new GamePlayFrame()));
        mainMenuFrame.addQuitButtonListener(e -> exitGame());
    }
    
    private void openMapEditorScreen() {
        mainMenuFrame.setVisible(false);
        mainMenuFrame.setEnabled(false);
        
        new MapEditorController(this);
    }
    
    private void openPlayGameScreen() {
        mainMenuFrame.setVisible(false);
        mainMenuFrame.setEnabled(false);

        /* initialize the game */
        RiskGame.getInstance().initializeNewGame(Config.DEFAULT_MAP, Config.DEFAULT_NUM_OF_PLAYERS);
        new GamePlayController(this);
    }
    
    private void exitGame() {
        mainMenuFrame.dispatchEvent(new WindowEvent(mainMenuFrame, WindowEvent.WINDOW_CLOSING));
    }
}
