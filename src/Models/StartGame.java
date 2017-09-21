package Models;

import Controllers.MapsLoader;
import Utils.Config;
import Views.DrawGraph;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Initiates the game by first loading the map from MAPS_PATH, and then starts the RiskGame.
 */
public class StartGame {
    public static void main(String[] args) {
        MapsLoader mapsLoader = new MapsLoader();
        GameMap gameMap = mapsLoader.readMapFile(Config.MAPS_PATH);
        DrawGraph frame = new DrawGraph(gameMap);
        frame.setSize(Config.GRAPH_FRAME_WIDTH, Config.GRAPH_FRAME_HEIGHT);
        frame.setVisible(true);
        frame.draw();

        RiskGame game = new RiskGame(gameMap);
    }
}