package controller;

import view.PlayUI;

/**
 * Controller to read and set map filepath to the model, and update
 * the view to display the map.
 */
public class StartupPhase {
    private PlayUI playUI;

    public StartupPhase(PlayUI playUI) {
        this.playUI = playUI;
    }
}
