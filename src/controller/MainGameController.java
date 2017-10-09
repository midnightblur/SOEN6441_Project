package controller;

import view.screens.MainMenuFrame;

import java.awt.event.WindowEvent;

public class MainGameController {
    private MainMenuFrame mainMenuFrame;
    
    public MainGameController() {
        mainMenuFrame = new MainMenuFrame();
        mainMenuFrame.addMapEditorButtonListener(e -> openMapEditorScreen());
        mainMenuFrame.addPlayGameButtonListener(e -> openPlayGameScreen());
        mainMenuFrame.addQuitButtonListener(e -> exitGame());
    }
    
    private void openMapEditorScreen() {
        mainMenuFrame.setVisible(false);
        mainMenuFrame.setEnabled(false);
        
        MapEditorController mapEditorController = new MapEditorController();
    }
    
    private void openPlayGameScreen() {
        mainMenuFrame.setVisible(false);
        mainMenuFrame.setEnabled(false);
    }
    
    private void exitGame() {
        mainMenuFrame.dispatchEvent(new WindowEvent(mainMenuFrame, WindowEvent.WINDOW_CLOSING));
    }
}
