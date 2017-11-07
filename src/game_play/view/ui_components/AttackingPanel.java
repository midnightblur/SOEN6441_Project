/*
 * Risk Game Team 2
 * AttackingPanel.java
 * Version 1.0
 * Nov 6, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * AttackingPanel is responsible for displaying UI letting player attack other players' territories
 */
public class AttackingPanel extends JPanel implements Observer {
    // region Attributes declaration
    private JPanel cardsPanel;
    private JLabel gameState;
    private JLabel playerName;
    private AttackPreparePanel attackPreparePanel;
    private BattleResultPanel battleResultPanel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate new AttackingPanel
     */
    public AttackingPanel() {
        populateUI();
    }
    // endregion
    
    // region Private methods
    
    /**
     * Populate all the necessary UI components for the panel
     */
    private void populateUI() {
        /* Setup layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        cardsPanel = new JPanel(new CardLayout());
    
        /* Populate header including Phase label & Player label */
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setAlignmentX(CENTER_ALIGNMENT);
        add(gameState);
        playerName = new JLabel();
        playerName.setFont(new Font("Sans Serif", Font.BOLD, 20));
        playerName.setAlignmentX(CENTER_ALIGNMENT);
        add(playerName);
        addVerticalSpacing(this);
        
        /* Setup all child panel */
        attackPreparePanel = new AttackPreparePanel();
        cardsPanel.add(attackPreparePanel, AttackPreparePanel.class.getName());
        battleResultPanel = new BattleResultPanel();
        cardsPanel.add(battleResultPanel, BattleResultPanel.class.getName());
        
        add(cardsPanel);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the card layout panel
     *
     * @return the card layout panel
     */
    public JPanel getCardsPanel() {
        return cardsPanel;
    }
    
    /**
     * Gets the attack prepare panel
     *
     * @return the attack prepare panel
     */
    public AttackPreparePanel getAttackPreparePanel() {
        return attackPreparePanel;
    }
    
    /**
     * Gets the battle result panel
     *
     * @return the battle result panel
     */
    public BattleResultPanel getBattleResultPanel() {
        return battleResultPanel;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.PLAY) {
                playerName.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerName.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                gameState.setText(gamePlayModel.getCurrentPlayer().getGameState().name());
                CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
                if (gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.PLAYER_ATTACK_PREPARE) {
                    cardLayout.show(cardsPanel, AttackPreparePanel.class.getName());
                } else if (gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.PLAYER_ATTACK_BATTLE) {
                    cardLayout.show(cardsPanel, BattleResultPanel.class.getName());
                }
            }
        }
    }
    
    
    // endregion
}
