package shared_resources.utilities;

import game_play.model.GamePlayModel;
import game_play.model.MapTableModel;
import game_play.model.PlayerTerritoriesModel;
import shared_resources.game_entities.Battle;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Player;

import java.io.*;
import java.util.LinkedList;
import java.util.Vector;

public class SavedState implements Serializable {
    private LinkedList<Object> objects;
    private GamePlayModel gamePlayModel;
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private Config.GAME_STATES gameState;
    private Player currentPlayer;
    private PlayerTerritoriesModel playerTerritoriesModel;
    private int armyValue;
    private Vector<Card> deck;
    private Vector<Player> players;
    private Battle currentBattle;
    
    public SavedState(GamePlayModel gamePlayModel) {
        objects = new LinkedList<>();
        this.gamePlayModel = gamePlayModel;
        objects.add(gamePlayModel);
    }
    
    
    public void SaveGame() {
        
        try {
            OutputStream file = new FileOutputStream("savedGame.game");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(objects);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void LoadGame(String path) {
        SavedState state;
        
        InputStream file;
        try {
            file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            state = (SavedState) input.readObject();
            this.gamePlayModel = state.gamePlayModel;
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
