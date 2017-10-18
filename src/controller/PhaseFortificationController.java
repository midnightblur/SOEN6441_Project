package controller;

import model.game_entities.Player;
import model.ui_models.FortificationModel;
import model.ui_models.GamePlayModel;
import view.helpers.UIHelper;
import view.screens.GamePlayFrame;
import view.ui_components.FortificationPanel;

import javax.swing.*;

import static model.ui_models.GamePlayModel.getInstance;
import static utilities.Config.GAME_STATES.FORTIFICATION;
import static view.helpers.UIHelper.setDivider;

/**
 * This is the FortificationController class
 */
public class PhaseFortificationController {
    private FortificationPanel fortificationPanel;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    private Player currentPlayer;
    private FortificationModel fortificationModel;
    
    
    /* Constructors */
    
    /**
     * Constructor for the Fortification Controller
     * responsible for placing armies to player territories
     *
     * @param gamePlayFrame the main frame
     */
    public PhaseFortificationController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        fortificationPanel = new FortificationPanel();
        fortificationModel = new FortificationModel();
        
        gamePlayFrame.getContentPane().setRightComponent(fortificationPanel);
        setDivider(gamePlayFrame.getContentPane());
        gamePlayModel = getInstance();
        currentPlayer = gamePlayModel.getCurrentPlayer();
        
        gamePlayModel.setGameState(FORTIFICATION);

        /* Register Observer to Observable */
        gamePlayModel.addObserver(fortificationPanel);
//        currentPlayer.addObserver(fortificationPanel);
        fortificationModel.addObserver(fortificationPanel);

        /* Register to be ActionListeners */
        fortificationPanel.addDoneButtonListener(e -> nextPlayer());
        fortificationPanel.addMoveArmiesButtonListener(e -> moveArmies());
        fortificationPanel.addSourceTerritoryDropdownListener(e -> fortificationModel.setTargetTerritoriesList(
                fortificationPanel.getSourceTerritoryDropdown().getSelectedItem().toString()));
    
        /* set control panel */
        populateFortificationPanel();
    }
    
    /* Private methods */
    
    
    /**
     * Move armies from selected source territory to selected target territory
     * If move is successful the action is disabled
     */
    private void moveArmies() {
        String source = fortificationPanel.getSourceTerritoryDropdown().getSelectedItem().toString();
        String target = fortificationPanel.getTargetTerritoryDropdown().getSelectedItem().toString();
        String quantity = fortificationPanel.getArmiesToMoveField().getText();
        int iQuantity = 0;
        try {
            iQuantity = Integer.parseInt(quantity);
        } catch (ClassCastException | NumberFormatException nfe) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry. Please re-enter a number.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
        }
        if (!quantity.equals("") && (iQuantity > 0) && !target.equals("No neighbors owned. Please select another territory")) {
            fortificationPanel.getMoveArmiesButton().setEnabled(true);
            String message = gamePlayModel.fortificationPhase(source, target, quantity);
            gamePlayModel.getMapTableModel().updateMapTableModel(gamePlayModel.getGameMap(), gamePlayModel.getGameState());
            // disable the button once armies are moved
            if (message.toLowerCase().contains("success")) {
                fortificationPanel.getMoveArmiesButton().setEnabled(false);
            }
            UIHelper.displayMessage(gamePlayFrame, message);
        } else {
            UIHelper.displayMessage(gamePlayFrame,"Please validate your selection.");
        }
    }
    
    /**
     * Populate the fortification control panel with updated model data
     */
    private void populateFortificationPanel() {
        /* set the phase label */
        fortificationPanel.setGameState(gamePlayModel.getGameState());
        
        /* set the player ID label */
        fortificationPanel.setPlayerID(currentPlayer.getPlayerID());
        
        /* set the source dropdown */
        fortificationPanel.getSourceTerritoryDropdown().setModel(fortificationModel.getSourceTerritoriesList());
        
        /* set the target dropdown */
        fortificationPanel.getTargetTerritoryDropdown().setModel(fortificationModel.getTargetTerritoriesList());
        fortificationModel.setTargetTerritoriesList(fortificationPanel.getSourceTerritoryDropdown().getSelectedItem().toString());
    }
    
    /**
     * Advance the game to next player
     */
    public void nextPlayer() {
        gamePlayModel.setCurrPlayerToNextPlayer();
        gamePlayModel.addReinforcementForCurrPlayer();
//        new PhaseReinforcementController(this.gamePlayFrame);
    }
    
}
