package game_play.view.ui_components;

import game_play.model.GamePlayModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * PhaseViewPanel is responsible for displaying all information related to current phase of the game.
 *
 * @author Team 2
 * @version 2.0
 */
public class PhaseViewPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 100;
    private GridLayout contentGrid;
    private JLabel gameStateLabel;
    private JLabel playerInfoLabel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate a new World domination panel
     */
    public PhaseViewPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        
        setLayout(new GridLayout(1,2));
        gameStateLabel = new JLabel();
        gameStateLabel.setForeground(Color.BLUE);
        gameStateLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        
        playerInfoLabel = new JLabel();
    
        
        add(gameStateLabel);
        add(playerInfoLabel);
    
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

            /* Refresh the UI elements */
            gameStateLabel.setText(gamePlayModel.getGameState().toString());
            if (gamePlayModel.getGameState().getValue() >= 3) {
                playerInfoLabel.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerInfoLabel.setText(gamePlayModel.getCurrentPlayer().playerInfo(gamePlayModel));
            }
            
            /* Draw the view based on current state of world domination */
            repaint();
        }
    }
    // endregion
    
}
