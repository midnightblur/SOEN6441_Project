
import controller.*;
import model.*;
import view.*;
import javax.swing.*;
import java.awt.*;


/**
 * Main driver class.
 */
public class Driver {
    public static void main(String[] args) {
        // views
        PlayUI playUI = new PlayUI();

        // models
        RiskGame riskGame = new RiskGame();

        // controllers
        EntryMenu entryMenu = new EntryMenu(playUI, riskGame);
    }
}
