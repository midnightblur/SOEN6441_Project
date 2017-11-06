package game_play.view.ui_components;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class AttackingPanel extends JPanel implements Observer {
    // region Attributes declaration
    private JPanel cardsPanel;
    // endregion
    
    // region Constructors
    public AttackingPanel() {
        populateUI();
    }
    // endregion
    
    // region Private methods
    private void populateUI() {
        cardsPanel = new JPanel(new CardLayout());
        
        
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
    
    // endregion
    
    // region MVC & Observer pattern methods
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
    
    }
    // endregion
}
