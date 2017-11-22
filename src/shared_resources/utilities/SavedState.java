package shared_resources.utilities;

import game_play.model.GamePlayModel;

import java.io.*;

public class SavedState implements Serializable {
    
    public static void SaveGame(GamePlayModel gamePlayModel) {
        
        try {
            OutputStream file = new FileOutputStream("savedGame.game");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(gamePlayModel);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static GamePlayModel LoadGame(String path) {
        GamePlayModel state = null;
        InputStream file;
        try {
            file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            state = (GamePlayModel) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("e.getMessage() = " + e.getMessage());;
        }
        return state;
    }
}
