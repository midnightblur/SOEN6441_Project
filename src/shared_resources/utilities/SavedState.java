package shared_resources.utilities;

import game_play.model.GamePlayModel;

import java.io.*;

public class SavedState implements Serializable {
    GamePlayModel gamePlayModel;
    
    public SavedState(GamePlayModel gamePlayModel) {
        this.gamePlayModel = gamePlayModel;
    }
    
    public void SaveGame() {
        try {
//            SavedState state = new SavedState(gamePlayModel);
            OutputStream file = new FileOutputStream("savedGame.game");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(this.gamePlayModel);
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
