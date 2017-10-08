package view.Screens;

import util.Config;
import view.Panels.GeneralLayoutPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MapEditorFrame extends JFrame {
    private GeneralLayoutPanel generalLayoutPanel;
    
    private static final String TITLE = "Map Editor";
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 900;
    
    /* Constructors */
    public MapEditorFrame() {
        generalLayoutPanel = new GeneralLayoutPanel(Config.SCREEN_NAME.MAP_EDITOR);
        this.setTitle(TITLE);
        this.setContentPane(generalLayoutPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    /* Getters & Setters */
    public GeneralLayoutPanel getGeneralLayoutPanel() {
        return generalLayoutPanel;
    }
    
    /* Public methods */
    public void addLoadMapBtnListener(ActionListener listenForLoadMapButton) {
        this.generalLayoutPanel.getMapEditorControlPanel().getLoadMapButton().addActionListener(listenForLoadMapButton);
    }
    
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
