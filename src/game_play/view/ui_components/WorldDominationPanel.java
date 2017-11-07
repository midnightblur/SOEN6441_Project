package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * WorldDominationPanel is responsible for representing the ui components for player's domination ratio.
 */
public class WorldDominationPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final int WIDTH = 850;
    private static final int HEIGHT = 20;
    private int totalTerritoriesCount;
    private Map<Integer, Player> playersMap;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate a new World domination panel
     */
    public WorldDominationPanel() {
        playersMap = new TreeMap<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }
    // endregion
    
    // region MVC & Observer pattern methods
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            
            /* Refresh the ratio of players' world domination */
            playersMap.clear();
            totalTerritoriesCount = gamePlayModel.getGameMap().getTerritoriesCount();
            for (Player player : gamePlayModel.getPlayers()) {
                playersMap.put(player.getPlayerID(), player);
            }
            
            /* Draw the view based on current state of world domination */
            repaint();
        }
    }
    // endregion
    
    // region Private/Protected methods
    
    /**
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        /* Draw the initial blank bar */
        g.setColor(Color.darkGray);
        g.fillRect(0,0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        FontMetrics f = g.getFontMetrics();
        String ratioStr = "Players World Domination View";
        int xStr = WIDTH / 2 - f.stringWidth(ratioStr) / 2;
        int yStr = HEIGHT / 2 + 4;
        g.drawString(ratioStr, xStr, yStr);
    
        int startPoint;
        int endPoint = -1;
        for (Player player : playersMap.values()) {
            int playerTerritoriesCount = player.getTerritories().size();
            float playerDominationRatio = (float) playerTerritoriesCount / totalTerritoriesCount;
            startPoint = endPoint + 1;
            endPoint = (int) (startPoint + (WIDTH * playerDominationRatio));
            
            drawPlayerDomination(g, startPoint, endPoint, player.getColor(), playerDominationRatio);
        }
    }
    
    /**
     * Draw a rectangle representing a player's world domination ratio, filled with that player's color
     *
     * @param g          The graphics object
     * @param startPoint The starting point in X axis
     * @param endPoint   The ending point in Y axis
     * @param color      The player's color
     * @param ratio      The player's domination ratio
     */
    private void drawPlayerDomination(Graphics g, int startPoint, int endPoint, Color color, float ratio) {
        // Draw the colored rectangle
        g.setColor(color);
        g.fillRect(startPoint, 0, endPoint - startPoint + 1, HEIGHT);
    
        // Only draw ratio string if there's enough space
        if (ratio > 0.1) {
            FontMetrics f = g.getFontMetrics();
            
            g.setColor(Color.WHITE);
            String ratioStr = String.format("%.1f", ratio * 100).concat("%");
            int xStr = startPoint + (endPoint - startPoint) / 2 - f.stringWidth(ratioStr) / 2;
            int yStr = HEIGHT / 2 + 4;
            g.drawString(ratioStr, xStr, yStr);
        }
    }
    // endregion
}
