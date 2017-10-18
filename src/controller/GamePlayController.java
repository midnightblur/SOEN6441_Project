package controller;

import model.game_entities.GameMap;
import model.ui_models.GamePlayModel;
import view.helpers.UIHelper;
import view.screens.GamePlayFrame;

import javax.swing.*;

import static utilities.Config.GAME_STATES.SETUP_PHASE;

/**
 * This class is used as a controller to read and set map filepath
 * to the model, and dispatchToController
 * the view to displayJFrame the map.
 *
 * @author
 * @version 1.0
 */
public class GamePlayController {
    // region Attributes declaration
    private MapSelectorController callerController;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    // endregion
    
    // region Constructors
    
    /**
     * Constructor for the mainGameController
     * responsible for launching the game phases
     *
     * @param gameMap The already loaded map for the game
     */
    public GamePlayController(MapSelectorController callerController, GameMap gameMap) {
        this.callerController = callerController;
        gamePlayModel = GamePlayModel.getInstance();
        gamePlayFrame = new GamePlayFrame();
    
        registerObserversToObservable();
    
        registerToBeListener();
        
        gamePlayModel.setGameMap(gameMap);
        gamePlayModel.setGameState(SETUP_PHASE);

        gamePlayFrame.getGameMapTable().setModel(gamePlayModel.getMapTableModel().getModel());
    }
    // endregion
    
    // region Private methods
    private void registerObserversToObservable() {
        gamePlayModel.addObserver(gamePlayFrame);
        gamePlayModel.addObserver(gamePlayFrame.getGameMapTable());
        gamePlayModel.addObserver(gamePlayFrame.getGameSetupPanel());
        gamePlayModel.addObserver(gamePlayFrame.getStartupPanel());
        gamePlayModel.addObserver(gamePlayFrame.getReinforcementPanel());
        gamePlayModel.addObserver(gamePlayFrame.getFortificationPanel());
    }
    
    private void registerToBeListener() {
        // TODO: put the back in a menu
        //gamePlayFrame.getControlPanel().addBackButtonListener(e -> backToMainMenu());
        gamePlayFrame.getGameSetupPanel().addPlayButtonListener(e -> startTheGame());
        gamePlayFrame.getStartupPanel().addPlaceArmyButtonListener(e -> placeArmy());
    }
    
    // region For Setup Phase
    private void startTheGame() {
        /* initialize the game */
        try {
            int enteredPlayers = Integer.parseInt(gamePlayFrame.getGameSetupPanel().getPlayerCount().getText());
            if ((enteredPlayers >= 1) && (enteredPlayers <= gamePlayModel.getGameMap().getMaxPlayers())) {
                gamePlayModel.initializeNewGame(enteredPlayers);
            } else {
                UIHelper.displayMessage(gamePlayFrame,"You must enter an amount of players between 1 and " + gamePlayModel.getGameMap().getMaxPlayers());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry. Please re-enter a number.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    // endregion
    
    // region For Startup Phase
    /**
     * Place an army to the selected territory.
     */
    private void placeArmy() {
        String selectedTerritoryName = String.valueOf(gamePlayFrame.getStartupPanel().getTerritoryDropdown().getSelectedItem());
        if (selectedTerritoryName.compareTo("") != 0) {
            gamePlayModel.placeArmyStartup(selectedTerritoryName);
        } else {
            UIHelper.displayMessage(gamePlayFrame, "Please validate your selection.");
        }
    }
    
    /**
     * Advance the game to next player, or to the reinforcement phase if all the
     * players have exhausted all unallocated armies.
     */
    public void nextPlayer() {
        for (int i = 0; i < gamePlayModel.getPlayers().size(); i++) {
            if (gamePlayModel.getNextPlayer().getUnallocatedArmies() > 0) {
                gamePlayModel.setCurrPlayerToNextPlayer();
                return;
            } else {
                gamePlayModel.setCurrPlayerToNextPlayer();
            }
        }
        gamePlayModel.setCurrentPlayer(gamePlayModel.getPlayers().firstElement());
        gamePlayModel.reinforcementPhase();
    }
    // endregion
    
    // region For Reinforcement Phase
    // endregion
    
    // region For Fortification Phase
    // endregion

    // endregion
}
