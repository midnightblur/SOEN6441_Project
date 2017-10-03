package view;

import model.GameMapHandler;
import model.MapPanel;
import model.RiskGame;
import util.Config;

import javax.swing.*;
import java.awt.*;

public class PlayUI extends JFrame {
    private JPanel mapDisplay;
    private JPanel mainArea;
    private JPanel controlArea;

    private GameMapHandler gameMapHandler;
    private MapPanel mapPanel;

    public PlayUI() {
        gameMapHandler = new GameMapHandler(Config.MAPS_PATH);
        mapPanel = new MapPanel(gameMapHandler.getGameMap());
        if (gameMapHandler.getValidateMsg().compareTo(Config.MSG_MAPFILE_VALID) != 0)
            JOptionPane.showMessageDialog(this, gameMapHandler.getValidateMsg(), Config.MSG_MAPFILE_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(this.mainArea);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);

        RiskGame game = new RiskGame(this.gameMapHandler.getGameMap());
    }

    public void paint(Graphics g) {
        mapDisplay.setSize(mapPanel.getPreferredSize());
        mapDisplay.add(mapPanel);
        mainArea.setSize(new Dimension(mapDisplay.getWidth() + controlArea.getWidth() + 20, Math.max(mapDisplay.getHeight(), controlArea.getHeight()) + 40));
        this.setSize(mainArea.getSize());
    }
}
