package controller;

import model.RiskGame;
import model.game_entities.Player;
import model.game_entities.Territory;
import model.ui_models.MapTableModel;
import model.ui_models.PlayerTerritoriesModel;
import utilities.Config;
import view.screens.GamePlayFrame;

import javax.swing.table.TableModel;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to read and set map filepath to the model, and dispatchToController
 * the view to displayJFrame the map.
 */
public class GamePlayController {
    private GamePlayFrame gamePlayFrame;
    private RiskGame gamePlayModel;
    private MainGameController callerController;
    private MapTableModel tableModel;
    private PlayerTerritoriesModel playerTerritoriesModel;
    private Player currentPlayer;
    
    /* Constructors */
    public GamePlayController(MainGameController mainGameController) {
        callerController = mainGameController;
        gamePlayFrame = new GamePlayFrame();
        gamePlayModel = RiskGame.getInstance();
        tableModel = gamePlayModel.getMapTableModel();
        
        // TODO: the following method calls should be moved to be part of StartupListener class which implements the ActionListener class
        /*
        initStartUp should only take 1 param like 'new StartupListener()'.
        Config.DEFAULT_MAP should be retrieved from the view's filepath value,
        default number of players should be retrieved from the view's number of players value
         */
        gamePlayModel.startupPhase(Config.DEFAULT_MAP, Config.DEFAULT_NUM_OF_PLAYERS);
        
        // get the correct player
        currentPlayer = gamePlayModel.getCurrPlayer();

        /* update the table model from a loaded map */
        try {
            tableModel.updateMapTableModel(gamePlayModel.getGameMap());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            gamePlayFrame.displayErrorMessage(e.toString());
        }
        
        /* set the model for the main table */
        gamePlayFrame.getGameMapTable().setModel(tableModel.getModel());
        
        /* set control panel */
        setControlPanel();
        
        /* Register Observer to Observable */
        tableModel.addObserver(gamePlayFrame.getGameMapTable());
        playerTerritoriesModel.addObserver(gamePlayFrame.getReinforcementControlPanel());
        currentPlayer.addObserver(gamePlayFrame.getReinforcementControlPanel());
        
        /* Register to be ActionListeners */
        gamePlayFrame.getReinforcementControlPanel().addTradeCardsButtonListener(e -> gamePlayModel.tradeInCards(currentPlayer));
        gamePlayFrame.getReinforcementControlPanel().addPlaceArmiesButtonListener(e -> distributeArmies());
        gamePlayFrame.getReinforcementControlPanel().addDoneButtonListener(e -> gamePlayModel.fortificationPhase());
        gamePlayFrame.getReinforcementControlPanel().addBackButtonListener(e -> backToMainMenu());
    }
    
    private void setControlPanel() {
        /* set the phase label */
        gamePlayFrame.getReinforcementControlPanel().setGameState(gamePlayModel.getGameState());
        
        /* set the player ID label */
        gamePlayFrame.getReinforcementControlPanel().setPlayerID(currentPlayer.getPlayerID());
        
        /* set the unallocated armies */
        gamePlayFrame.getReinforcementControlPanel().setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
        
        /* set the model for the player table */
        playerTerritoriesModel = new PlayerTerritoriesModel(currentPlayer);
        gamePlayFrame.getReinforcementControlPanel().getPlayerTerritoryTable().setModel(playerTerritoriesModel.getModel());
        gamePlayFrame.getReinforcementControlPanel().setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
        
    }
    
    
    /* Private methods */
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmies in the model
     */
    private void distributeArmies() {
        TableModel armiesData = gamePlayFrame.getReinforcementControlPanel().getPlayerTerritoryTable().getModel();
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
        if (runningSum <= currentPlayer.getUnallocatedArmies()) {
            gamePlayModel.placeArmies(armiesToPlace);
            // refresh the main table model TODO: find a more optimal way
            gamePlayModel.getMapTableModel().updateMapTableModel(gamePlayModel.getGameMap());
            setControlPanel();
            // refresh the
            gamePlayFrame.displayErrorMessage("The armies were placed successfully");
            // reset the armies to zero
            for (int r = 0; r < armiesData.getRowCount(); r++) {
                armiesData.setValueAt(0, r, 1);
            }
        } else {
            gamePlayFrame.displayErrorMessage("The total armies to allocate must be lesser or equal to the indicated total armies to place");
        }
    }
    
    /**
     * Close the current MapEditor screen and navigate back to the MainMenu screen
     */
    private void backToMainMenu() {
        callerController.invokeFrame();
        gamePlayFrame.dispatchEvent(new WindowEvent(gamePlayFrame, WindowEvent.WINDOW_CLOSING));
    }
    
}
