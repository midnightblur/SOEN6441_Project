package view.ui_components;

import javax.swing.*;

public class AddCountry extends JPanel {
    private JLabel title = new JLabel("Adding a new country");
    private JLabel addToContinentLabel = new JLabel("Select a continent");
    private JLabel selectNeigborsLabel = new JLabel("Select the neighbors");
    private JTextField name = new JTextField("Enter a name");
    private JComboBox<String> addToContinentDropdown = new JComboBox<>();
    private JList<String> selectNeighbors = new JList<>();
    private JButton addCountry = new JButton("ADD");
    
    /* Constructors */
    public AddCountry() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(title);
        add(name);
        add(addToContinentLabel);
        add(addToContinentDropdown);
        add(selectNeigborsLabel);
        add(selectNeighbors);
        add(addCountry);
    }
    
    
}
