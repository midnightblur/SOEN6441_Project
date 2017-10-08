package view;

import util.Config;

import javax.swing.*;

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
}
