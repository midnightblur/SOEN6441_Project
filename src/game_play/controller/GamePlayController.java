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
import game_play.view.screens.StrategyDialog;
import game_play.view.ui_components.FortificationPanel;
import shared_resources.game_entities.Battle;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.helper.UIHelper;
import shared_resources.utilities.MapFilter;
import shared_resources.utilities.SaveOpenDialog;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.GAME_EXTENSION;
import static shared_resources.utilities.Config.GAME_STATES.*;
import static shared_resources.utilities.SavedState.loadGame;
import static shared_resources.utilities.SavedState.saveGame;

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
    private StrategyDialog strategyDialog;
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
        strategyDialog = new StrategyDialog(gamePlayFrame);
        gamePlayFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        registerObserversToObservable();
        registerToBeListener();
        gamePlayModel.setGameMap(gameMap);
        gamePlayModel.setGameState(SETUP);
        gamePlayFrame.getGameMapTable().setModel(gamePlayModel.getMapTableModel().getModel());
    }
    
    /**
     * Limited constructor to be used when loading a game before starting to play
     *
     * @param callerController The controller who calls this controller (to help go back to previous screen
     */
    public GamePlayController(MainMenuController callerController) {
        this.callerController = callerController;
        gamePlayModel = new GamePlayModel();
        gamePlayFrame = new GamePlayFrame(callerController);
        strategyDialog = new StrategyDialog(gamePlayFrame);
        gamePlayFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        registerObserversToObservable();
        registerToBeListener();
    }
    
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
    // endregion
    
    // region Private methods
    
    /**
     * Register the controller to be the listener to all UI component of the views.
     */
    private void registerToBeListener() {
        /* Menu listeners */
        
        gamePlayFrame.addSaveMenuListener(e -> saveGameState());
        gamePlayFrame.addLoadMenuListener(e -> loadSavedGame());
        gamePlayFrame.addStrategyMenuListener(e -> showStrategyOptions(gamePlayModel.getPlayers()));
        gamePlayFrame.addOpenDefendingDialogButtonListener(e -> openDefendingDialog());
        gamePlayFrame.addPopupVictoryDialogButtonListener(e -> announceVictoryIfPossible(
                gamePlayModel.getCurrentPlayer().getPlayerName() + " is the winner"));
        gamePlayFrame.addTurnCounterReachedMaxButtonListener(e -> askUserToContinue());
        gamePlayFrame.addAttackCounterReachedMaxButtonListener(e -> askUserInteraction());
        
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
        
        /* For Strategy Panel */
        strategyDialog.addSubmitButtonListener(e -> setStrategy());
    }
    
    /**
     * Save the game state to file
     */
    private void saveGameState() {
        File gameFileToSave;
        SaveOpenDialog fileChooser = new SaveOpenDialog(new MapFilter(GAME_EXTENSION), "Save game");
        int selection = fileChooser.showDialog();
        if (selection == JFileChooser.APPROVE_OPTION) {
            gameFileToSave = fileChooser.getSelectedFile();
            // add file extension if user does not enters it
            if (!gameFileToSave.getAbsolutePath().toLowerCase().endsWith(GAME_EXTENSION)) {
                gameFileToSave = new File(gameFileToSave.getAbsolutePath() + GAME_EXTENSION);
            }
            try {
                saveGame(gamePlayModel, gameFileToSave.getAbsolutePath());
                UIHelper.displayMessage(gamePlayFrame, "The game was saved at \n" + gameFileToSave.getAbsolutePath());
                
            } catch (Exception e) {
                e.printStackTrace(System.err);
                UIHelper.displayMessage(gamePlayFrame, e.getMessage());
            }
        }
    }
    
    /**
     * Loads a previously saved game
     */
    void loadSavedGame() {
        File gameFileLoader;
        SaveOpenDialog fileChooser = new SaveOpenDialog(new MapFilter(GAME_EXTENSION), "Load game");
        int selection = fileChooser.showDialog();
        if (selection == JFileChooser.APPROVE_OPTION) {
            gameFileLoader = fileChooser.getSelectedFile();
            try {
                gamePlayModel.setGamePlayModel(loadGame(gameFileLoader.getAbsolutePath()));
                UIHelper.displayMessage(gamePlayFrame, "The game was loaded from \n" + gameFileLoader.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                UIHelper.displayMessage(gamePlayFrame, e.getMessage());
            }
        }
        
    }
    
    /**
     * Once the maximum turns is reached we ask the user if they wish to continue playing
     * If we continue, the turnCounter is reset
     */
    private void askUserToContinue() {
        Object[] options = { "Continue", "Exit" };
        int result = JOptionPane.showOptionDialog(null, "Game reached maximum turns allotted (" + gamePlayModel.getMaxTurns() + ")\n" +
                        "Do you want to continue playing another " + gamePlayModel.getMaxTurns() + " turns?",
                "Max Turns Reached",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, null);
        
        
        
        /* Yes, continue playing */
        if (result == JOptionPane.YES_OPTION) {
            gamePlayModel.setMaxTurns(gamePlayModel.getMaxTurns() + gamePlayModel.getTurnCounter());
//            gamePlayModel.addReinforcementForCurrPlayer();
            gamePlayModel.letBotsPlay();
            // TODO: what happens if we play with humans, what function do we call here?
        }
        
        /* Yes, continue playing */
        else if (result == JOptionPane.NO_OPTION) {
            UIHelper.invokeFrame(callerController.getMainMenuFrame());
            UIHelper.disableFrame(gamePlayFrame);
        }
    }
    
    /**
     * Asks user for a decision when maximum turns are reached
     */
    private void askUserInteraction() {
        Object[] options = { "Continue", "Exit" };
        int result = JOptionPane.showOptionDialog(null, "Game reached maximum attacks allotted (" + gamePlayModel.getMaxAttackTurn() + ")\n" +
                        "Do you want to continue attacking?",
                "Max Turns Reached",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, null);
        
        /* Yes, continue playing */
        if (result == JOptionPane.YES_OPTION) {
            gamePlayModel.setAttackCounter(0);
            gamePlayModel.botsAttack();
            // TODO: what happens if we play with humans, what function do we call here?
        }
        
        /* Yes, continue playing */
        else if (result == JOptionPane.NO_OPTION) {
            UIHelper.invokeFrame(callerController.getMainMenuFrame());
            UIHelper.disableFrame(gamePlayFrame);
        }
    }
    //endregion
    
    
    // region For Setup Phase
    
    /**
     * Setting the player's strategy
     *
     * @param players the players to populate the strategy dialog
     */
    private void showStrategyOptions(Vector<Player> players) {
//TODO: clear the dialog each time is shown
        strategyDialog.populateOptions(players, false);
        strategyDialog.revalidate();
        strategyDialog.repaint();
        strategyDialog.setVisible(true);
    }
    
    /**
     * Called when the number of players for the game is decided, the game then starts.
     */
    private void gameStartupPhase() {
        /* initialize the game */
        try {
            int enteredPlayers = Integer.parseInt(gamePlayFrame.getGameSetupPanel().getPlayerCount().getText());
            if ((enteredPlayers > 1) && (enteredPlayers <= gamePlayModel.getGameMap().getMaxPlayers())) {
                gamePlayModel.initializeNewGame(enteredPlayers);
                showStrategyOptions(gamePlayModel.getPlayers());
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
    
    // region For Startup Phase
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmiesReinforcement in the game_entities.
     */
    private void distributeArmies() {
        TableModel armiesData = gamePlayFrame.getReinforcementPanel().getPlayerTerritoryTable().getModel();
        String territoryName;
        int armies;
        int runningSum = 0;
        
        /* Prepare the list of armies to distribute */
        Map<Territory, Integer> armiesToPlace = new HashMap<>();
        for (int r = 0; r < armiesData.getRowCount(); r++) {
            armies = Integer.parseInt(armiesData.getValueAt(r, 1).toString());
            if (armies > 0) {   // only add entries that have more than 0 armies to be placed
                runningSum += armies;
                territoryName = armiesData.getValueAt(r, 0).toString();
                armiesToPlace.put(gamePlayModel.getGameMap().getATerritory(territoryName), armies);
            }
        }
        
        /* Distribute the armies*/
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
    // endregion
    
    // region For Reinforcement Phase
    
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
    
    /**
     * Bring users from Reinforcement Panel to Trade Cards Panel
     */
    private void goToTradeCardsPanel() {
        gamePlayModel.changePhaseOfCurrentPlayer(TRADE_CARDS);
    }
    
    /**
     * Bring users back to Reinforcement Panel from Trade Cards Panel
     */
    private void backToReinforcement() {
        gamePlayModel.changePhaseOfCurrentPlayer(REINFORCEMENT);
    }
    
    /**
     * Call appropriate function in GamePlayModel to perform an attack from one territory to another
     */
    private void attackTerritory() {
        setupTheBattle();
        Player defender = gamePlayModel.getCurrentBattle().getDefender();
        
        if (defender.isHuman()) {
            openDefendingDialog();
        } else {
            int maxDefendingDice = gamePlayModel.getCurrentBattle().getMaxDefendingRoll();
            int defendingDice = defender.botChooseDefendingDice(maxDefendingDice);
            startTheBattle(defendingDice);
        }
    }
    
    /**
     * Sets up the battle resources
     */
    private void setupTheBattle() {
        /* Prepare ingredients for the battle */
        String defendingTerritoryName = String.valueOf(
                gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().getSelectedItem());
        String attackingTerritoryName = String.valueOf(
                gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackingTerritoriesDropdown().getSelectedItem());
        
        Player attacker = gamePlayModel.getCurrentPlayer();
        Player defender = gamePlayModel.getGameMap().getATerritory(defendingTerritoryName).getOwner();
        
        Territory attackingTerritory = gamePlayModel.getGameMap().getATerritory(attackingTerritoryName);
        Territory defendingTerritory = gamePlayModel.getGameMap().getATerritory(defendingTerritoryName);
        
        int attackingDice = (int) gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().getSelectedItem();
        gamePlayModel.setCurrentBattle(new Battle(attacker, attackingTerritory, attackingDice,
                defender, defendingTerritory, 0));
    }
    
    /**
     * Opens the modal dialog for defender to roll dices when is attacked
     */
    private void openDefendingDialog() {
        Battle currentBattle = gamePlayModel.getCurrentBattle();
        String situation = String.format("%s attacks from %s to %s's %s using %d dice",
                currentBattle.getAttacker().getPlayerName(),
                currentBattle.getAttackingTerritory().getName(),
                currentBattle.getDefender().getPlayerName(),
                currentBattle.getDefendingTerritory().getName(),
                currentBattle.getAttackerDice().getRollsCount());
        int maxDefendingDice = gamePlayModel.getCurrentBattle().getMaxDefendingRoll();
        
        /* Let the defender choose number of dice to defence */
        gamePlayFrame.setVisible(false);
        JFrame frame = new JFrame();
        DefendingDialog defendingDialog = new DefendingDialog(frame, situation, maxDefendingDice);
        defendingDialog.addDoneButtonListener(e -> startTheBattle(defendingDialog, gamePlayFrame));
    }
    
    /**
     * Move to Fortification phase
     */
    private void goToFortificationPhase() {
        if (gamePlayModel.getCurrentPlayer().hasConqueredTerritories()) {
            gamePlayModel.drawCardForWinner(gamePlayModel.getCurrentPlayer());
            gamePlayModel.getCurrentPlayer().setHasConqueredTerritories(false);
        }
        
        gamePlayModel.setCurrentBattle(null);
        
        gamePlayModel.changePhaseOfCurrentPlayer(FORTIFICATION);
    }
    
    // endregion
    
    // region For Attacking Phase
    
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
    
    /**
     * Called when players want to prepare another attack
     */
    private void prepareAnotherAttack() {
        gamePlayModel.prepareNewAttack();
    }
    
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
        /* Prepare the model for target dropdown */
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
        
        /* Set the model for the dropdown */
        DropDownModel targetTerritoriesModel = new DropDownModel(targetTerritoriesList);
        gamePlayFrame.getFortificationPanel().getTargetTerritoryDropdown().setModel(targetTerritoriesModel);
    }
    
    /**
     * This function advances the game to next player's turn.
     */
    private void changeToNextPlayer() {
        gamePlayModel.nextPlayerTurn();
    }
    
    /**
     * Call appropriate function in GamePlayModel to perform a battle
     * When defender is a human
     *
     * @param dialog the modal dialog for defender
     * @param owner  the owner of attacked territory
     */
    private void startTheBattle(DefendingDialog dialog, JFrame owner) {
        /* Perform the battle */
        int defendingDice = (int) dialog.getDefendingDiceDropdown().getSelectedItem();
        dialog.getOwner().dispose();
        owner.setVisible(true);
        if (gamePlayModel.getCurrentPlayer().isHuman()) {  // if human attacker
            String message = gamePlayModel.declareAttack(
                    String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackingTerritoriesDropdown().getSelectedItem()),
                    String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().getSelectedItem()),
                    (Integer) gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().getSelectedItem(),
                    defendingDice
            );
            
            announceVictoryIfPossible(message);
            moveArmiesToConqueredTerritoryIfPossible();
        } else {  // if bot attacker
            gamePlayModel.getCurrentBattle().setDefendingDice(defendingDice);
            gamePlayModel.botsFortification(true);
        }
    }
    
    /**
     * Call appropriate function in GamePlayModel to perform a battle
     * When defender is a bot
     *
     * @param defendingDice the number of defender's dice
     */
    private void startTheBattle(int defendingDice) {
        /* Perform the battle */
        String message = gamePlayModel.declareAttack(
                String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackingTerritoriesDropdown().getSelectedItem()),
                String.valueOf(gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getDefendingTerritoriesDropdown().getSelectedItem()),
                (Integer) gamePlayFrame.getAttackingPanel().getAttackPreparePanel().getAttackerNoOfDice().getSelectedItem(),
                defendingDice
        );
        
        announceVictoryIfPossible(message);
        moveArmiesToConqueredTerritoryIfPossible();
    }
    
    /**
     * Pop out the dialog announcing the winner
     *
     * @param message the message
     */
    private void announceVictoryIfPossible(String message) {
        // If outcome is victory
        if (gamePlayModel.getCurrentPlayer().getGameState() == VICTORY) {
            gamePlayModel.setGameState(SETUP);
            UIHelper.displayMessage(gamePlayFrame, message);
            UIHelper.invokeFrame(callerController.getMainMenuFrame());
            UIHelper.disableFrame(gamePlayFrame);
        }
        
        /* Declare draw if turns are used-up*/
        else if (gamePlayModel.getTurnCounter() >= gamePlayModel.getMaxTurns()) {
            message = "Maximum turns reached. No winner. Game is a draw";
            UIHelper.displayMessage(gamePlayFrame, message);
            UIHelper.closeFrame(gamePlayFrame);
            UIHelper.invokeFrame(callerController.getMainMenuFrame());
        }
    }
    
    /**
     * Moves armies to conquered territories by asking user to decide where to place them
     */
    private void moveArmiesToConqueredTerritoryIfPossible() {
        /* Display dialog to let the winning attacker to move armies to conquered territory */
        if (gamePlayModel.getCurrentBattle().getDefendingTerritory().getArmies() == 0) {
            openMoveArmiesToConqueredTerritoryDialog();
        }
    }
    
    // endregion
    // region For Fortification Phase
    
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
     * Sets the player strategy as selected in StrategyDialog
     */
    private void setStrategy() {
        StrategyDialog.BehaviourOptions[] opts = strategyDialog.getPlayersOptions();
        strategyDialog.dispose();
        gamePlayModel.setPlayersType(opts);
    }
    
    // endregion
}

