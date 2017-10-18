package controller;

import model.game_entities.GameMap;
import model.game_entities.Territory;
import model.ui_models.GamePlayModel;
import view.helpers.UIHelper;
import view.screens.GamePlayFrame;
import view.ui_components.ReinforcementPanel;

import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
        
        /* For Startup Panel */
        gamePlayFrame.getStartupPanel().addPlaceArmyButtonListener(e -> placeArmy());
        
        /* For Reinforcement Panel */
        gamePlayFrame.getReinforcementPanel().addTradeCardsButtonListener(e -> tradeCards());
        gamePlayFrame.getReinforcementPanel().addPlaceArmiesButtonListener(e -> distributeArmies());
        gamePlayFrame.getReinforcementPanel().addGoToFortificationButtonListener(e -> goBackToFortificationPhase());
    }
    
    // region For Setup Phase
    private void startTheGame() {
        /* initialize the game */
        try {
            int enteredPlayers = Integer.parseInt(gamePlayFrame.getGameSetupPanel().getPlayerCount().getText());
            if ((enteredPlayers >= 1) && (enteredPlayers <= gamePlayModel.getGameMap().getMaxPlayers())) {
                gamePlayModel.initializeNewGame(enteredPlayers);
            } else {
                UIHelper.displayMessage(gamePlayFrame, "You must enter an amount of players between 1 and " + gamePlayModel.getGameMap().getMaxPlayers());
            }
        } catch (ClassCastException | NumberFormatException e) {
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
        gamePlayModel.placeArmyStartup(selectedTerritoryName);
    }
    // endregion
    
    // region For Reinforcement Phase
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmiesReinforcement in the model
     */
    private void distributeArmies() {
        TableModel armiesData = gamePlayFrame.getReinforcementPanel().getPlayerTerritoryTable().getModel();
        String territoryName;
        int armies;
        int runningSum = 0;
        Map<Territory, Integer> armiesToPlace = new HashMap<>();
        for (int r = 0; r < armiesData.getRowCount(); r++) {
            armies = Integer.parseInt(armiesData.getValueAt(r, 1).toString());
            if (armies > 0) {   // only add entries that have more than 0 armies to be placed
                runningSum += armies;
                territoryName = armiesData.getValueAt(r, 0).toString();
                armiesToPlace.put(gamePlayModel.getGameMap().getATerritory(territoryName), armies);
            }
        }
        if (runningSum > 0 && runningSum <= gamePlayModel.getCurrentPlayer().getUnallocatedArmies()) {
            gamePlayModel.placeArmiesReinforcement(armiesToPlace);
            UIHelper.displayMessage(gamePlayFrame, "The armies were placed successfully");
            // reset the armies to zero
            for (int r = 0; r < armiesData.getRowCount(); r++) {
                armiesData.setValueAt(0, r, 1);
            }
        } else {
            UIHelper.displayMessage(gamePlayFrame, "The total armies to allocate must be lesser or equal to the indicated total armies to place");
        }
    }
    
    /**
     * Shows the controller responsible for trading cards
     */
    private void tradeCards() {
        CardLayout cardLayout = (CardLayout) gamePlayFrame.getReinforcementPanel().getCardsPanel().getLayout();
        cardLayout.show(gamePlayFrame.getReinforcementPanel().getCardsPanel(), ReinforcementPanel.getTradeCardsPanelName());
    }
    
    private void goBackToFortificationPhase() {
        // TODO: this needs fixing so it correctly returns to previous phase
        // TODO: (see true condition in the game and possibly have a setter for it under currentPlayer)
        // riskGame.fortificationPhase();
        
        new PhaseFortificationController(gamePlayFrame);
        
    }
    // endregion
    
    // region For Fortification Phase
    // endregion
    
    // endregion
}
