package game_play.view.ui_components;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BattleResultPanel extends JPanel implements Observer {
    // region Attributes declaration
    // endregion
    
    // region Constructors
    public BattleResultPanel() {
        setLayout(new GridLayout(0, 1));
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
    
    }
    // endregion
}
