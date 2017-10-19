/* 
 * Risk Game Team 2
 * DropDownModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import javax.swing.*;
import java.util.Vector;

/**
 * The Class DropDownModel used as a base for UI dropdown made out of vector.
 */
public class DropDownModel extends DefaultComboBoxModel<String> {
    
    /**
     * Instantiates a new drop down game_entities.
     *
     * @param items the items
     */
    public DropDownModel(Vector<String> items) {
        super(items);
    }

    /* (non-Javadoc)
     * @see javax.swing.DefaultComboBoxModel#getSelectedItem()
     */
    public String getSelectedItem() {
        return String.valueOf(super.getSelectedItem());
    }


}
