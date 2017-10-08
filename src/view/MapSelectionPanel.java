package view;

import model.DropDownModel;
import model.GameMapHandler;
import util.Config;

import javax.swing.*;
import java.awt.*;

public class MapSelectionPanel extends JPanel {
    private JLabel chooseMapLabel;
    private JComboBox<String> chooseMapDropdown;
    
    /* Constructor */
    public MapSelectionPanel() {
        this.setLayout(new FlowLayout());
        
        chooseMapLabel = new JLabel();
        chooseMapLabel.setText(Config.UI_LABEL_CHOOSE_MAP);
        
        chooseMapDropdown = new JComboBox<>();
        DropDownModel dropDownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        chooseMapDropdown.setModel(dropDownModel);
        
        this.add(chooseMapLabel);
        this.add(chooseMapDropdown);
    }
}
