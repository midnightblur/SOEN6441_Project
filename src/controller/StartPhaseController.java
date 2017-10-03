package controller;

import view.PlayUI;

/**
 * Controller to read and set map filepath to the model, and update
 * the view to display the map.
 */
public class StartPhaseController {
    private PlayUI playUI;

    public StartPhaseController(PlayUI playUI) {
        this.playUI = playUI;
    }
}
