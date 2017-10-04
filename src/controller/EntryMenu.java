package controller;

import model.GameState;
import model.RiskGame;
import view.PlayUI;

/**
 * Controller class holding methods used in initial Entry Menu of the game
 */
public class EntryMenu {
    private PlayUI playUI;
    private RiskGame riskGame;

    public EntryMenu(PlayUI playUI, RiskGame riskGame) {
        this.playUI = playUI;
        this.riskGame = riskGame;

        riskGame.setGameState(GameState.MAP_EDITOR);
    }


}
