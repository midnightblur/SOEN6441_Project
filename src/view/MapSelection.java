package view;

import model.DropDownModel;
import model.GameMapHandler;
import util.Config;

import javax.swing.*;
import java.awt.*;

public class MapSelection extends JPanel {
    private JLabel chooseMapLabel = new JLabel();
    private JComboBox<String> chooseMapDropdown = new JComboBox<>();
    
    public MapSelection() {
        this.setLayout(new FlowLayout());
        
        chooseMapLabel.setText(Config.UI_LABEL_CHOOSE_MAP);
        DropDownModel dropDownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        chooseMapDropdown.setModel(dropDownModel);
        
        this.add(chooseMapLabel);
        this.add(chooseMapDropdown);
    }
}
