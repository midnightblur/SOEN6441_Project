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
 * DropDownModel is responsible for providing the data game_play for JComboBox
 * The input data need to be in type of a Vector of Strings.
 *
 * @author Team 2
 * @version 1.0
 */
public class DropDownModel extends DefaultComboBoxModel<String> {
    
    // region Constructors
    
    /**
     * Instantiates a new DropDownModel from a Vector of Strings.
     *
     * @param items the items
     */
    public DropDownModel(Vector<String> items) {
        super(items);
    }
    // endregion
    
    // region Getters & Setters
    /**
     * @see javax.swing.DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public String getSelectedItem() {
        return String.valueOf(super.getSelectedItem());
    }
    // endregion
    
}
