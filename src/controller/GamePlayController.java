package controller;

import model.game_entities.GameMap;
import model.game_entities.Territory;
import model.ui_models.DropDownModel;
import model.ui_models.GamePlayModel;
import view.helpers.UIHelper;
import view.screens.GamePlayFrame;
import view.ui_components.ReinforcementPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static utilities.Config.GAME_STATES.FORTIFICATION;
import static utilities.Config.GAME_STATES.SETUP;

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
        gamePlayModel.setGameState(SETUP);
        
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
        gamePlayModel.addObserver(gamePlayFrame.getReinforcementPanel().getTradeCardsPanel());
        gamePlayModel.addObserver(gamePlayFrame.getFortificationPanel());
    }
    
    private void registerToBeListener() {
        // TODO: put the back in a menu
        //gamePlayFrame.getControlPanel().addBackButtonListener(e -> backToMainMenu());
        gamePlayFrame.getGameSetupPanel().addPlayButtonListener(e -> startTheGame());
        
        /* For Startup Panel */
        gamePlayFrame.getStartupPanel().addPlaceArmyButtonListener(e -> placeArmy());
        
        /* For Reinforcement Panel */
        gamePlayFrame.getReinforcementPanel().addTradeCardsButtonListener(e -> goToTradeCardsPanel());
        gamePlayFrame.getReinforcementPanel().addPlaceArmiesButtonListener(e -> distributeArmies());
        gamePlayFrame.getReinforcementPanel().addGoToFortificationButtonListener(e -> goToFortificationPhase());
        gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().addTradeCardsButtonListener(e -> tradeSelectedCards());
        gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().addBackToReinforcementListener(e -> backToReinforcementPanel());
        
        /* For Fortification Panel */
        gamePlayFrame.getFortificationPanel().addDoneButtonListener(e -> nextPlayer());
        gamePlayFrame.getFortificationPanel().addMoveArmiesButtonListener(e -> moveArmies());
        gamePlayFrame.getFortificationPanel().addSourceTerritoryDropdownListener(e -> updateTargetTerritoriesDropdown(
                String.valueOf(gamePlayFrame.getFortificationPanel().getSourceTerritoryDropdown().getSelectedItem())
        ));
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
     * Collect the selected cards from UI and trade them by calling the tradeInCards() from the model
     */
    private void tradeSelectedCards() {
        Vector<String> selectedCards = new Vector<>();
        for (Component component : gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().getCardList().getComponents()) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                selectedCards.add(checkBox.getText());
            }
        }
        String message = gamePlayModel.tradeInCards(selectedCards);
        
        // confirmation message
        UIHelper.displayMessage(gamePlayFrame, message);
    }
    
    /**
     * Shows the controller responsible for trading cards
     */
    private void goToTradeCardsPanel() {
        CardLayout cardLayout = (CardLayout) gamePlayFrame.getReinforcementPanel().getCardsPanel().getLayout();
        cardLayout.show(gamePlayFrame.getReinforcementPanel().getCardsPanel(), ReinforcementPanel.getTradeCardsPanelName());
    }
    
    private void backToReinforcementPanel() {
        CardLayout cardLayout = (CardLayout) gamePlayFrame.getReinforcementPanel().getCardsPanel().getLayout();
        cardLayout.show(gamePlayFrame.getReinforcementPanel().getCardsPanel(), ReinforcementPanel.getControlWrapperPanelName());
    }
    
    private void goToFortificationPhase() {
        // TODO: this needs fixing so it correctly returns to previous phase
        // TODO: (see true condition in the game and possibly have a setter for it under currentPlayer)
        // riskGame.moveArmiesFortification();
        if (gamePlayModel.getCurrentPlayer().getUnallocatedArmies() != 0 || gamePlayModel.getCurrentPlayer().getPlayersHand().size() >= 5) {
            UIHelper.displayMessage(gamePlayFrame, "You have to allocate all of your armies or trade in your cards");
        } else {
            gamePlayModel.setGameState(FORTIFICATION);
        }
        
    }
    // endregion
    
    // region For Fortification Phase
    /**
     * Advance the game to next player
     */
    private void nextPlayer() {
        gamePlayModel.setCurrPlayerToNextPlayer();
        gamePlayModel.addReinforcementForCurrPlayer();
//        new PhaseReinforcementController(this.gamePlayFrame);
    }
    
    /**
     * Move armies from selected source territory to selected target territory
     * If move is successful the action is disabled
     */
    private void moveArmies() {
        String sourceTerritory = String.valueOf(gamePlayFrame.getFortificationPanel().getSourceTerritoryDropdown().getSelectedItem());
        String targetTerritory = String.valueOf(gamePlayFrame.getFortificationPanel().getTargetTerritoryDropdown().getSelectedItem());
        String inputtedArmies = gamePlayFrame.getFortificationPanel().getArmiesToMoveField().getText();
        int quantity = 0;
        try {
            quantity = Integer.parseInt(inputtedArmies);
        } catch (ClassCastException | NumberFormatException nfe) {
            UIHelper.displayMessage(gamePlayFrame, "Invalid entry. Please re-enter a number.");
        }
        
        if ((quantity > 0) && targetTerritory.compareTo("No neighbors owned. Please select another territory") != 0) {
            String message = gamePlayModel.moveArmiesFortification(sourceTerritory, targetTerritory, quantity);
            UIHelper.displayMessage(gamePlayFrame,message);
        } else {
            UIHelper.displayMessage(gamePlayFrame,"Please validate your selection.");
        }
    }

    private void updateTargetTerritoriesDropdown(String selectedTerritory) {
        Vector<String> targetTerritoriesList = new Vector<>();
        Vector<String> neighbors = gamePlayModel.getGameMap().getATerritory(selectedTerritory).getNeighbors();
        for (String neighborName : neighbors) {  // if neighborName is owned by current player, add it to the lost
            if (gamePlayModel.getGameMap().getATerritory(neighborName).isOwnedBy(gamePlayModel.getCurrentPlayer().getPlayerID())
                    && neighborName.compareTo(selectedTerritory) != 0) {
                targetTerritoriesList.add(neighborName);
            }
        }
        if (targetTerritoriesList.size() == 0) {
            targetTerritoriesList.add("No neighbors owned. Please select another territory");
        }
        DropDownModel targetTerritoriesModel = new DropDownModel(targetTerritoriesList);
        gamePlayFrame.getFortificationPanel().getTargetTerritoryDropdown().setModel(targetTerritoriesModel);
    }
    // endregion
    
    // endregion
}
