package controller;

import model.game_entities.Card;
import model.game_entities.Player;
import model.ui_models.GamePlayModel;
import view.screens.GamePlayFrame;
import view.ui_components.TradeCardsPanel;

import javax.swing.*;

import static view.helpers.UIHelper.setDivider;

/**
 * The Trade Cards Controller responsible of capturing user interaction from the trade cards panel
 * and converting them to changes in the risk game model
 * Once user is done trading cards, it returns focus to the ReinforcementController
 *
 * @author
 * @version 1.0
 */
public class TradeCardsController {
    private TradeCardsPanel tradeCardsPanel;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    private Player currentPlayer;
    private int armiesBeforeTrading;
    
    /* Constructors */
    
    /**
     * Constructor for the Trade Cards Controller is
     * responsible for exchanging cards for armies
     *
     * @param gamePlayFrame the main frame
     */
    public TradeCardsController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        tradeCardsPanel = new TradeCardsPanel();
        
        gamePlayFrame.getContentPane().setRightComponent(tradeCardsPanel);
        setDivider(gamePlayFrame.getContentPane());
        gamePlayModel = GamePlayModel.getInstance();
        currentPlayer = gamePlayModel.getCurrentPlayer();
        
//        gamePlayModel.setGameState(TRADE_IN);
        
        /* Set control panel */
        populateTradeCardsPanel();
        
        // collect the armies before trading to later calculate the gained armies
        armiesBeforeTrading = currentPlayer.getUnallocatedArmies();

        /* Register Observer to Observable */
        
//        currentPlayer.addObserver(tradeCardsPanel);
        
        /* Register to be ActionListeners */
        
    }
    
    /* Private methods */
    
    /**
     * Populate the trade cards control panel with updated model data
     */
    private void populateTradeCardsPanel() {

        /* set the phase label */
//        tradeCardsPanel.setGameState(gamePlayModel.getGameState());
        
        /* set the player ID label */
        tradeCardsPanel.setPlayerID(currentPlayer.getPlayerID());

        /* the cards list for this particular player */
        listCards();
        
        /* set the army value label */
        tradeCardsPanel.setArmyValueLabel(gamePlayModel.getArmyValue());
    }
    
    
    
    /**
     * This method is used to list the cards that a player has.
     */
    public void listCards() {
    
    }
}
