package model.ui_models;

import javax.swing.*;
import java.util.Vector;

public class DropDownModel extends DefaultComboBoxModel<String> {
    public DropDownModel(Vector<String> items) {
        super(items);
    }
    
    public String getSelectedItem() {
        return String.valueOf(super.getSelectedItem());
    }
}
