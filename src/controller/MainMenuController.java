package controller;

import view.helpers.UIHelper;
import view.screens.MainMenuFrame;
import view.screens.MapSelectorFrame;

import javax.swing.*;

/**
 * The Class MainGameController  is
 * responsible for launching different game screens
 *
 * @author
 * @version 1.0
 */
public class MainMenuController extends JFrame {
    // region Attributes declaration
    private MainMenuFrame mainMenuFrame;
    private MapSelectorFrame mapSelectorFrame;
    // endregion
    
    // region Constructors
    
    /**
     * This method is used to launch main screen of application
     */
    public MainMenuController() {
        mainMenuFrame = new MainMenuFrame();
        registerToBeListener();
    }
    // endregion
    
    // region Public methods
    
    // endregion
    
    // region Private methods
    private void registerToBeListener() {
        mainMenuFrame.addMapEditorButtonListener(e -> openMapEditorScreen());
        mainMenuFrame.addPlayGameButtonListener(e -> openPlayGameScreen());
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
        
        new MapSelectorController(this);
    }
    
    private void exitGame() {
        UIHelper.closeFrame(mainMenuFrame);
    }
    // endregion
}
