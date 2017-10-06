package model;

import javax.swing.*;

public class DropDownModel extends DefaultComboBoxModel<String> {
    public DropDownModel(String[] items) {
        super(items);
    }
    
    @Override
    public String getSelectedItem() {
        String selectedJob = (String) super.getSelectedItem();
        return selectedJob;
    }
}
