package game_play.model;

import javax.swing.table.DefaultTableModel;
import java.util.Observable;
import java.util.Vector;

public class TournamentResultsModel extends Observable {
    private DefaultTableModel resultsModel;
    private Vector<String> columns;
    
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
    
    public DefaultTableModel getResultsModel() {
        return resultsModel;
    }
    
    public void setRows(String[][] winners) {
        for (String[] row : winners) {
            resultsModel.addRow(row);
        }
        setChanged();
        notifyObservers();
    }
}
