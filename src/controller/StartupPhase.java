package controller;

import model.RiskGame;
import util.Config;
import view.PlayUI;

/**
 * Controller to read and set map filepath to the model, and dispatchToController
 * the view to display the map.
 */
public class StartupPhase {
    private PlayUI playUI;
    private RiskGame riskGame;

    public StartupPhase(PlayUI playUI, RiskGame riskGame) {
        this.playUI = playUI;
        this.riskGame = riskGame;

        // TODO: the following method calls should be moved to be part of StartupListener class which implements the ActionListener class
        /*
        initStartUp should only take 1 param like 'new StartupListener()'.
        Config.MAPS_PATH should be retrieved from the view's filepath value,
        6 (number of players) should be retrieved from the view's number of players value
         */
        riskGame.initStartup(Config.MAPS_PATH, 6);

    }
}
