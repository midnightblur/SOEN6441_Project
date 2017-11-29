package game_play.model;

import javax.swing.table.DefaultTableModel;
import java.util.Observable;
import java.util.Vector;

/**
 * The model for results table
 */
public class TournamentResultsModel extends Observable {
    private DefaultTableModel resultsModel;
    private Vector<String> columns;
    
    /**
     * Constructor for the table model of the game results
     *
     * @param gameCount the game count to determine columns number
     */
    public TournamentResultsModel(int gameCount) {
        resultsModel = new DefaultTableModel();
        resultsModel.setRowCount(0);
        columns = new Vector<>();
        columns.clear();
        columns.add("Map/Game");
        for (int i = 0; i < gameCount; i++) {
            columns.add("Game " + (i + 1));
        }
        resultsModel.setColumnIdentifiers(columns);
    }
    
    /**
     * Gets the table results model
     *
     * @return the model for the results table
     */
    public DefaultTableModel getResultsModel() {
        return resultsModel;
    }
    
    /**
     * Sets the rows within the results table model
     *
     * @param winners the array of winners across maps from each game
     */
    public void setRows(String[][] winners) {
        for (String[] row : winners) {
            resultsModel.addRow(row);
        }
        setChanged();
        notifyObservers();
    }
}
