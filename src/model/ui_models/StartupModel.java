package model.ui_models;

import model.game_entities.Player;
import model.game_entities.Territory;

import javax.swing.*;
import java.util.Observable;
import java.util.Vector;

import static model.ui_models.GamePlayModel.getInstance;

public class StartupModel extends Observable {
    private Vector<String> territoriesList;
    private GamePlayModel gamePlayModel;
    private Player currentPlayer;

    public StartupModel() {
        gamePlayModel = getInstance();
        currentPlayer = gamePlayModel.getCurrentPlayer();
        territoriesList = new Vector<>();

        /* collect player's territories */
        for (Territory t : gamePlayModel.getGameMap().getTerritoriesOfPlayer(currentPlayer).values()) {
            territoriesList.add(t.getName());
        }
        broadcastGamePlayChanges();
    }

    /* Getters & setters */

    /**
     * Accessor for the territories list model
     *
     * @return the model
     */
    public ComboBoxModel getTerritoriesList() {
        return new DropDownModel(territoriesList);
    }

    /* Private methods */

    /**
     * Method to update the GamePlayModel and notify the Observer.
     */
    private void broadcastGamePlayChanges() {
        setChanged();
        notifyObservers();
    }
}
