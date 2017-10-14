package model.ui_models;

import model.RiskGame;
import model.game_entities.Player;
import model.game_entities.Territory;

import javax.swing.*;
import java.util.Objects;
import java.util.Observable;
import java.util.Vector;

import static model.RiskGame.getInstance;

public class FortificationModel extends Observable {
    private Vector<String> sourceTerritoriesList;
    private Vector<String> targetTerritoriesList;
    private RiskGame riskGame;
    private Player currentPlayer;
    
    public FortificationModel() {
        riskGame = getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        sourceTerritoriesList = new Vector<>();
        targetTerritoriesList = new Vector<>();
        
        /* collect player's territories */
        for (Territory t : riskGame.getGameMap().getTerritoriesOfPlayer(currentPlayer).values()) {
            sourceTerritoriesList.add(t.getName());
        }
        broadcastGamePlayChanges();
    }
    
    /* Getters & setters */
    
    /**
     * Accessor for the source territories model
     *
     * @return the model
     */
    public ComboBoxModel getSourceTerritoriesList() {
        return new DropDownModel(sourceTerritoriesList);
    }
    
    /**
     * Sets target territory dropdown based on source territory dropdown
     * Only adjacent territories belonging to same player are shown
     *
     * @param selectedTerritory selected value in source territory dropdown
     */
    public void setTargetTerritoriesList(String selectedTerritory) {
        targetTerritoriesList.clear();
        Vector<String> neighbours = riskGame.getGameMap().getATerritory(selectedTerritory).getNeighbors();
        for (String n : neighbours) {  // if neighbour is owned by current player, add it to the lost
            if (riskGame.getGameMap().getATerritory(n).isOwnedBy(currentPlayer.getPlayerID())
                    && !Objects.equals(n, selectedTerritory)) {
                targetTerritoriesList.add(n);
            }
        }
        if (targetTerritoriesList.size() == 0) {
            targetTerritoriesList.add("No neighbours owned. Please select another territory");
        }
        broadcastGamePlayChanges();
    }
    
    /**
     * Accessor for the target territories model
     *
     * @return the model
     */
    public ComboBoxModel getTargetTerritoriesList() {
        return new DropDownModel(targetTerritoriesList);
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
