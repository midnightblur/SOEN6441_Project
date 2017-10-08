package view.Panels;

import model.MapSelectionModel;
import util.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MapSelectionPanel extends JPanel implements Observer {
    private JLabel chooseMapLabel;
    private JComboBox<String> chooseMapDropdown;
    
    /* Constructor */
    public MapSelectionPanel() {
        this.setLayout(new FlowLayout());
        
        chooseMapLabel = new JLabel();
        chooseMapLabel.setText(Config.UI_LABEL_CHOOSE_MAP);
        
        chooseMapDropdown = new JComboBox<>();
        
        this.add(chooseMapLabel);
        this.add(chooseMapDropdown);
    }
    
    /* Getters & Setters */
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    
    /* Public methods */
    @Override
    public void update(Observable o, Object arg) {
        chooseMapDropdown.setModel(((MapSelectionModel) o).getDropDownModel());
        chooseMapDropdown.setSelectedIndex(0);
    }
}
