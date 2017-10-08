package view;

import javax.swing.*;

public class MapEditorScreen extends JFrame {
    private GeneralLayout generalLayout;
    
    public MapEditorScreen() {
        generalLayout = new GeneralLayout(GeneralLayout.SCREENS.MAP_EDITOR);
        this.setContentPane(generalLayout);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
