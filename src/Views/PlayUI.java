package Views;

import Controllers.GameMapHandler;
import Models.RiskGame;
import Utils.Config;

import javax.swing.*;
import java.awt.*;

public class PlayUI extends JFrame {
    private JPanel mapDisplay;
    private JPanel mainArea;
    private JPanel controlArea;

    private GameMapHandler gameMapHandler;
    private DrawGraph drawGraph;

    public PlayUI() {
        gameMapHandler = new GameMapHandler(Config.MAPS_PATH);
        drawGraph = new DrawGraph(gameMapHandler.getGameMap());
        if (gameMapHandler.getValidateMsg() != Config.MSG_MAPFILE_VALID)
            JOptionPane.showMessageDialog(this, gameMapHandler.getValidateMsg(), Config.MSG_MAPFILE_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    public void paint(Graphics g) {
        mapDisplay.setSize(drawGraph.getPreferredSize());
        mapDisplay.add(drawGraph);
        mainArea.setSize(new Dimension(mapDisplay.getWidth() + controlArea.getWidth() + 20, Math.max(mapDisplay.getHeight(), controlArea.getHeight()) + 40));
        this.setSize(mainArea.getSize());
    }

    public static void main(String[] args) {
        PlayUI playUI = new PlayUI();
        playUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playUI.setContentPane(playUI.mainArea);
        playUI.setVisible(true);

        RiskGame game = new RiskGame(playUI.gameMapHandler.getGameMap());
    }
}
