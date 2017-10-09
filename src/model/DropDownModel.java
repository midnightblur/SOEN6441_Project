package model;

import javax.swing.*;
import java.util.Vector;

public class DropDownModel extends DefaultComboBoxModel<String> {
    public DropDownModel(Vector<String> items) {
        super(items);
    }
    
    @Override
    public String getSelectedItem() {
        return (String) super.getSelectedItem();
    }

}
