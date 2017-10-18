/* 
 * Risk Game Team 2
 * DropDownModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package model.ui_models;

import javax.swing.*;
import java.util.Vector;

/**
 * The Class DropDownModel.
 */
public class DropDownModel extends DefaultComboBoxModel<String> {
    
    /**
     * Instantiates a new drop down model.
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
