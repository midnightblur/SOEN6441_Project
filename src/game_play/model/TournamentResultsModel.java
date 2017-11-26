package game_play.model;

import javax.swing.table.DefaultTableModel;
import java.util.Observable;
import java.util.Vector;

public class TournamentResultsModel extends Observable {
    private DefaultTableModel resultsModel;
    private String[][] rows;
    private Vector<String> columns;
    
    public TournamentResultsModel(Vector<GamePlayModel> gamePlayModels, int gameCount) {
        resultsModel = new DefaultTableModel();
        resultsModel.setRowCount(0);
        columns = new Vector<>();
        columns.clear();
        for (int i = 0; i < gameCount; i++) {
            columns.add("Game " + i);
        }
        
        rows = new String[gamePlayModels.size()][gameCount];
        for (int i = 0; i < gamePlayModels.size(); i++) {
            for (int j = 0; j < gameCount; j++) {
                rows[i][0] = gamePlayModels.elementAt(j).getGameMap().getMapName();
                rows[i][j] = gamePlayModels.elementAt(j).getWinner();
            }
        }
        resultsModel.setColumnIdentifiers(columns);
        for (String[] row : rows) {
            resultsModel.addRow(row);
        }
        
        setChanged();
        notifyObservers();
    }
    
    public DefaultTableModel getResultsModel() {
        return resultsModel;
    }
}
