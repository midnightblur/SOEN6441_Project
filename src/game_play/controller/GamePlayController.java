/* 
 * Risk Game Team 2
 * GamePlayController.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.controller;

import game_play.model.DropDownModel;
import game_play.model.GamePlayModel;
import game_play.view.screens.ConquerDialog;
import game_play.view.screens.DefendingDialog;
import game_play.view.screens.GamePlayFrame;
import game_play.view.ui_components.FortificationPanel;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.helper.UIHelper;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.GAME_STATES.*;

/**
 * GamePlayController is responsible for coordinating the GamePlayModel and GamePlayFrame
 * Listen to user input from the views
 * Interpret user's input and call appropriate functions from the models
 * In some case, directly update the views if it doesn't involve the models
 *
 * @author Team 2
 * @version 1.0
 */
public class GamePlayController {
    
    // region Attributes declaration
    private MainMenuController callerController;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    // endregion
    
    // region Constructors
    
    /**
     * Constructor for the mainGameController
     * Responsible for initializing all the data and UI required to play the game.
     *
     * @param callerController The controller who calls this controller (to help go back to previous screen
     * @param gameMap          The valid game map loaded in the map selector screen
     */
    public GamePlayController(MainMenuController callerController, GameMap gameMap) {
        this.callerController = callerController;
        gamePlayModel = new GamePlayModel();
        gamePlayFrame = new GamePlayFrame(callerController);
        
        registerObserversToObservable();
        
        registerToBeListener();
        
        gamePlayModel.setGameMap(gameMap);
        gamePlayModel.setGameState(SETUP);
        
        gamePlayFrame.getGameMapTable().setModel(gamePlayModel.getMapTableModel().getModel());
    }
    // endregion
    
    // region Private methods
    
    /**
     * Register the views to be observers of the GamePlayModel.
     */
    private void registerObserversToObservable() {
        gamePlayModel.addObserver(gamePlayFrame);
        gamePlayModel.addObserver(gamePlayFrame.getWorldDominationPanel());
        gamePlayModel.addObserver(gamePlayFrame.getGameMapTable());
        gamePlayModel.addObserver(gamePlayFrame.getGameSetupPanel());
        gamePlayModel.addObserver(gamePlayFrame.getStartupPanel());
        gamePlayModel.addObserver(gamePlayFrame.getReinforcementPanel());
        gamePlayModel.addObserver(gamePlayFrame.getReinforcementPanel().getTradeCardsPanel());
        gamePlayModel.addObserver(gamePlayFrame.getAttackingPanel());
        gamePlayModel.addObserver(gamePlayFrame.getAttackingPanel().getAttackPreparePanel());
        gamePlayModel.addObserver(gamePlayFrame.getAttackingPanel().getBattleResultPanel());
        gamePlayModel.addObserver(gamePlayFrame.getFortificationPanel());
        gamePlayModel.addObserver(gamePlayFrame.getPhaseViewPanel());
    }
    
    /**
     * Register the controller to be the listener to all UI component of the views.
     */
    private void registerToBeListener() {
        /* Play button to start the game */
        gamePlayFrame.getGameSetupPanel().addPlayButtonListener(e -> gameStartupPhase());
        
        /* For Startup Panel */
        gamePlayFrame.getStartupPanel().addPlaceArmyButtonListener(e -> placeArmy());
        gamePlayFrame.getStartupPanel().addPlayButtonListener(e -> startTheGame());
        
        /* For Reinforcement Panel */
        gamePlayFrame.getReinforcementPanel().addPlaceArmiesButtonListener(e -> distributeArmies());
        gamePlayFrame.getReinforcementPanel().addGoToFortificationButtonListener(e -> goToAttackingPhase());
        gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().addTradeCardsButtonListener(e -> tradeSelectedCards());
        gamePlayFrame.getReinforcementPanel().addTradeCardsButtonListener(e -> goToTradeCardsPanel());
        gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().addToReinforcementButtonListener(e -> backToReinforcement());
        
        /* For Attacking Panel */
        gamePlayFrame.getAttackingPanel().getAttackPreparePanel().addAttackButtonListener(e -> attackTerritory());
        gamePlayFrame.getAttackingPanel().getAttackPreparePanel().addDoneButtonListener(e -> goToFortificationPhase());
        gamePlayFrame.getAttackingPanel().getAttackPreparePanel().addAttackingTerritoryDropdownListener(e -> updateDefendingTerritoriesAndAttackingDice(
                String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackingTerritoriesDropdown().getSelectedItem())
        ));
        gamePlayFrame.getAttackingPanel().getBattleResultPanel().addDoneButtonListener(e -> goToFortificationPhase());
        gamePlayFrame.getAttackingPanel().getBattleResultPanel().addAnotherAttackButtonListener(e -> prepareAnotherAttack());
        
        /* For Fortification Panel */
        gamePlayFrame.getFortificationPanel().addMoveArmiesButtonListener(e -> moveArmies());
        gamePlayFrame.getFortificationPanel().addSourceTerritoryDropdownListener(e -> updateTargetTerritoriesDropdown(
                String.valueOf(gamePlayFrame.getFortificationPanel().getSourceTerritoryDropdown().getSelectedItem())
        ));
        gamePlayFrame.getFortificationPanel().addNextPlayerButtonListener(e -> changeToNextPlayer());
    }
    
    // region For Setup Phase
    
    /**
     * Called when the number of players for the game is decided, the game then starts.
     */
    private void gameStartupPhase() {
        /* initialize the game */
        try {
            int enteredPlayers = Integer.parseInt(gamePlayFrame.getGameSetupPanel().getPlayerCount().getText());
            if ((enteredPlayers > 1) && (enteredPlayers <= gamePlayModel.getGameMap().getMaxPlayers())) {
                gamePlayModel.initializeNewGame(enteredPlayers);
            } else if (enteredPlayers == 1) {
                gamePlayModel.initializeNewGame(enteredPlayers);
                UIHelper.displayMessage(gamePlayFrame, "Player 1 Wins!");
                UIHelper.closeFrame(gamePlayFrame);
                UIHelper.invokeFrame(callerController.getMainMenuFrame());
            } else {
                UIHelper.displayMessage(gamePlayFrame, "You must enter an amount of players between 1 and " +
                        gamePlayModel.getGameMap().getMaxPlayers());
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
     * This function allows players to place their initial armies into their territories one-by-one.
     */
    private void placeArmy() {
        String selectedTerritoryName = String.valueOf(gamePlayFrame.getStartupPanel().getTerritoryDropdown().getSelectedItem());
        gamePlayModel.placeArmyStartup(selectedTerritoryName);
    }
    
    /**
     * This function change the game state to Play phase
     */
    private void startTheGame() {
        gamePlayModel.startTheGame();
    }
    // endregion
    
    // region For Reinforcement Phase
    
    /**
     * Bring users back to Reinforcement Panel from Trade Cards Panel
     */
    private void backToReinforcement() {
        gamePlayModel.changePhaseOfCurrentPlayer(REINFORCEMENT);
    }
    
    /**
     * Bring users from Reinforcement Panel to Trade Cards Panel
     */
    private void goToTradeCardsPanel() {
        gamePlayModel.changePhaseOfCurrentPlayer(TRADE_CARDS);
    }
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmiesReinforcement in the game_entities.
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
     * Validate player's armies distributing and cards trading, then change the game state to Fortification Phase
     */
    private void goToAttackingPhase() {
        if (gamePlayModel.getCurrentPlayer().getUnallocatedArmies() != 0 || gamePlayModel.getCurrentPlayer().getPlayersHand().size() >= 5) {
            UIHelper.displayMessage(gamePlayFrame, "You have to allocate all of your armies or trade in your cards");
        } else {
            gamePlayModel.changePhaseOfCurrentPlayer(ATTACK_PREPARE);
        }
    }
    
    /**
     * Collect the selected cards from UI and trade them by calling the tradeInCards() from the game_entities.
     */
    private void tradeSelectedCards() {
        int previousUnallocatedArmies = gamePlayModel.getCurrentPlayer().getUnallocatedArmies();
        Vector<String> selectedCards = new Vector<>();
        for (Component component : gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().getCardList().getComponents()) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                selectedCards.add(checkBox.getText());
            }
        }
        String message = gamePlayModel.tradeInCards(selectedCards);
        int gainedArmies = gamePlayModel.getCurrentPlayer().getUnallocatedArmies() - previousUnallocatedArmies;
        gamePlayFrame.getReinforcementPanel().getTradeCardsPanel().setGainedArmiesLabel(gainedArmies);
        UIHelper.displayMessage(gamePlayFrame, message);
    }
    
    // endregion
    
    // region For Attacking Phase
    
    /**
     * Called when players want to prepare another attack
     */
    private void prepareAnotherAttack() {
        /* Check if the defending territory has been conquered */
        if (gamePlayModel.getCurrentBattle().getDefendingTerritory().getArmies() == 0) {
            openMoveArmiesToConqueredTerritoryDialog();
        }
        gamePlayModel.prepareNewAttack();
    }
    
    /**
     * Call appropriate function in GamePlayModel to move a number of armies from attacking territory to the defending one
     *
     * @param conquerDialog The conquer dialog
     * @param owner         The owner frame
     */
    private void moveArmiesToConqueredTerritory(ConquerDialog conquerDialog, JFrame owner) {
        gamePlayModel.moveArmiesToConqueredTerritory((Integer) conquerDialog.getMoveArmiesDropdown().getSelectedItem());
        conquerDialog.getOwner().dispose();
        owner.setVisible(true);
    }
    
    /**
     * Call appropriate function in GamePlayModel to perform an attack from one territory to another
     */
    private void attackTerritory() {
        String attackingPlayer = gamePlayModel.getCurrentPlayer().getPlayerName();
        String attackingTerritory = String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackingTerritoriesDropdown().getSelectedItem());
        String defendingTerritory = String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().getSelectedItem());
        String defendingPlayer = gamePlayModel.getGameMap().getATerritory(defendingTerritory).getOwner().getPlayerName();
        int attackingDice = (int) gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().getSelectedItem();
        
        String situation = String.format("%s attacks from %s to %s's %s using %d dice",
                attackingPlayer,
                attackingTerritory,
                defendingPlayer,
                defendingTerritory,
                attackingDice);
        int maxDefendingDice = gamePlayModel.getMaxDefendingRoll(defendingTerritory);
        gamePlayFrame.setVisible(false);
        
        JFrame frame = new JFrame();
        DefendingDialog defendingDialog = new DefendingDialog(frame, situation, maxDefendingDice);
        defendingDialog.addDoneButtonListener(e -> startTheBattle(defendingDialog, gamePlayFrame));
    }
    
    /**
     * Move to Fortification phase
     */
    private void goToFortificationPhase() {
        if (gamePlayModel.getCurrentPlayer().getGameState() == ATTACK_BATTLE &&
                gamePlayModel.getCurrentBattle().getDefendingTerritory().getArmies() == 0) {
            openMoveArmiesToConqueredTerritoryDialog();
        }
        
        if (gamePlayModel.getCurrentPlayer().hasConqueredTerritories()) {
            gamePlayModel.drawCardForWinner(gamePlayModel.getCurrentPlayer());
            gamePlayModel.getCurrentPlayer().setHasConqueredTerritories(false);
        }
        
        gamePlayModel.changePhaseOfCurrentPlayer(FORTIFICATION);
    }
    
    /**
     * Hide the GamePlayFrame and display a dialog for player
     * to choose how many armies to place on newly conquered territory
     */
    private void openMoveArmiesToConqueredTerritoryDialog() {
        gamePlayFrame.setVisible(false);
        JFrame frame = new JFrame();
        ConquerDialog conquerDialog = new ConquerDialog(frame, gamePlayModel.getCurrentBattle());
        conquerDialog.addMoveArmiesButtonListener(e -> moveArmiesToConqueredTerritory(conquerDialog, gamePlayFrame));
    }
    
    /**
     * Updates the defending territories dropdown and number
     * of attacking dice according to selected attacking territory
     *
     * @param attackingTerritory the selected attacking territory
     */
    private void updateDefendingTerritoriesAndAttackingDice(String attackingTerritory) {
        if (attackingTerritory != null) {
            /* Update defending territories dropdown */
            gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().setModel(
                    new DefaultComboBoxModel<>(gamePlayModel.getNeighborsNotOwnedBySamePlayer(attackingTerritory))
            );
            gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().setSelectedIndex(0);
        
        /* Update attacking dice dropdown */
            int maxRoll = gamePlayModel.getMaxAttackingRoll(attackingTerritory);
            Vector<Integer> rollChoice = new Vector<>();
            for (int i = 1; i <= maxRoll; i++) {
                rollChoice.add(i);
            }
            gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().setModel(
                    new DefaultComboBoxModel<>(rollChoice.toArray(new Integer[rollChoice.size()]))
            );
            gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().setSelectedIndex(
                    gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().getItemCount() - 1);
        }
    }
    
    // region For Attacking Phase
    
    /**
     * Move armies from selected source territory to selected target territory
     * Validate the user's input
     * If move is successful the action is disabled.
     */
    private void moveArmies() {
        String sourceTerritory = String.valueOf(gamePlayFrame.getFortificationPanel().getSourceTerritoryDropdown().getSelectedItem());
        String targetTerritory = String.valueOf(gamePlayFrame.getFortificationPanel().getTargetTerritoryDropdown().getSelectedItem());
        String inputtedArmies = gamePlayFrame.getFortificationPanel().getArmiesToMoveField().getText();
        int quantity;
        try {
            quantity = Integer.parseInt(inputtedArmies);
            
            if ((quantity > 0) && targetTerritory.compareTo("No neighbors owned. Please select another territory") != 0) {
                String message = gamePlayModel.moveArmiesFortification(sourceTerritory, targetTerritory, quantity);
                if (message.contains("Successfully moved")) {
                    gamePlayFrame.getFortificationPanel().getMoveArmiesButton().setEnabled(false);
                    gamePlayFrame.getFortificationPanel().getSourceTerritoryDropdown().setEnabled(false);
                    gamePlayFrame.getFortificationPanel().getTargetTerritoryDropdown().setEnabled(false);
                    gamePlayFrame.getFortificationPanel().getArmiesToMoveField().setEnabled(false);
                }
                UIHelper.displayMessage(gamePlayFrame, message);
            } else {
                UIHelper.displayMessage(gamePlayFrame, "Please validate your selection.");
            }
        } catch (ClassCastException | NumberFormatException nfe) {
            UIHelper.displayMessage(gamePlayFrame, "Invalid entry. Please re-enter a number.");
        }
    }
    
    /**
     * Update the list of target territories according to the players' selected source territory.
     *
     * @param selectedTerritory the selected territory
     */
    private void updateTargetTerritoriesDropdown(String selectedTerritory) {
        Vector<String> targetTerritoriesList = new Vector<>();
        if (selectedTerritory.compareTo(FortificationPanel.getNoValidTerritory()) != 0) {
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
        } else {
            targetTerritoriesList.add("No valid source territory is selected");
        }
    
        DropDownModel targetTerritoriesModel = new DropDownModel(targetTerritoriesList);
        gamePlayFrame.getFortificationPanel().getTargetTerritoryDropdown().setModel(targetTerritoriesModel);
    }
    // endregion
    
    // region For Fortification Phase
    
    /**
     * This function advances the game to next player's turn.
     */
    private void changeToNextPlayer() {
        gamePlayModel.nextPlayerTurn();
    }
    
    /**
     * Call appropriate function in GamePlayModel to perform a battle
     *
     * @param dialog the modal dialog for defender
     * @param owner  the owner of attacked territory
     */
    private void startTheBattle(DefendingDialog dialog, JFrame owner) {
        int defendingDice = (int) dialog.getDefendingDiceDropdown().getSelectedItem();
        dialog.getOwner().dispose();
        owner.setVisible(true);
        String message = gamePlayModel.declareAttack(
                String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackingTerritoriesDropdown().getSelectedItem()),
                String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().getSelectedItem()),
                (Integer) gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().getSelectedItem(),
                defendingDice
        );
        
        // if outcome is victory
        if (gamePlayModel.getCurrentPlayer().getGameState() == VICTORY) {
            UIHelper.displayMessage(gamePlayFrame, message);
            UIHelper.closeFrame(gamePlayFrame);
            UIHelper.invokeFrame(callerController.getMainMenuFrame());
        }
        
    }
    
    // endregion
    
    // endregion
}
