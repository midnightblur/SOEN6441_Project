package view;

import controller.GameMapHandler;
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
    }

    public void paint(Graphics g) {
        mapDisplay.setSize(mapPanel.getPreferredSize());
        mapDisplay.add(mapPanel);
        mainArea.setSize(new Dimension(mapDisplay.getWidth() + controlArea.getWidth() + 20, Math.max(mapDisplay.getHeight(), controlArea.getHeight()) + 40));
        this.setSize(mainArea.getSize());
    }

    public static void main(String[] args) {
        PlayUI playUI = new PlayUI();
        playUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playUI.setContentPane(playUI.mainArea);
        playUI.pack();
        playUI.setResizable(false);
        playUI.setVisible(true);

        RiskGame game = new RiskGame(playUI.gameMapHandler.getGameMap());
    }
}
