package Views;

import Models.Adjacency;
import Models.GameMap;
import Models.MapsLoader;
import Models.Territory;
import Utils.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;

public class DrawGraph extends JFrame {
    private String graphName;
    private int width;
    private int height;
    private GameMap gameMap;

    public DrawGraph(GameMap gameMap) {
        this.graphName = gameMap.getMapName();
        this.width = Config.GRAPH_NODE_WIDTH;
        this.height = Config.GRAPH_NODE_HEIGHT;
        this.gameMap = gameMap;
    }

    public void paint(Graphics g) {
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());

        /* draw the edges */
        g.setColor(Config.GRAPH_EDGE_COLOR);
        Map<String, Territory> territoryMap = gameMap.getTerritories();
        for (Iterator<Adjacency> iterator = gameMap.getAdjacencies().iterator(); iterator.hasNext();) {
            Adjacency adjacency = iterator.next();
            Territory t1 = territoryMap.get(adjacency.getTerritory1());
            Territory t2 = territoryMap.get(adjacency.getTerritory2());
            g.drawLine(t1.getX(), t1.getY(), t2.getX(), t2.getY());
        }

        /* draw the nodes */
        for (Map.Entry<String, Territory> entry : territoryMap.entrySet()) {
            Territory territory = entry.getValue();
            int nodeWidth = Math.max(width, f.stringWidth(territory.getName()) + width / 2);
            g.setColor(Config.GRAPH_NODE_COLOR);
            g.fillOval(territory.getX() - nodeWidth / 2, territory.getY() - nodeHeight / 2, nodeWidth, nodeHeight);
            g.setColor(Config.GRAPH_BORDER_COLOR);
            g.drawOval(territory.getX() - nodeWidth / 2, territory.getY() - nodeHeight / 2, nodeWidth, nodeHeight);
            g.drawString(territory.getName(), territory.getX() - f.stringWidth(territory.getName()) / 2, territory.getY() + f.getHeight() / 2);
        }
    }

    public void draw() {
        this.repaint();
    }

    public static void main(String[] args) {
        MapsLoader mapsLoader = new MapsLoader();
        GameMap gameMap = mapsLoader.readMapFile(Config.MAPS_PATH);
        DrawGraph frame = new DrawGraph(gameMap);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.draw();
    }
}
