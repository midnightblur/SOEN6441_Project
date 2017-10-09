package model;

import javax.swing.*;
import java.util.Observable;
import java.util.Vector;

public class DropDownModel extends Observable {
    private DefaultComboBoxModel<String> comboBoxModel;
    
    public DropDownModel(Vector<String> items) {
        this.comboBoxModel = new DefaultComboBoxModel<>(items);
    }
    
    public String getSelectedItem() {
        return String.valueOf(comboBoxModel.getSelectedItem());
    }
    
    public DefaultComboBoxModel<String> getComboBoxModel() {
        return comboBoxModel;
    }
}
