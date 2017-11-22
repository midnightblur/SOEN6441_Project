/*
 * Risk Game Team 2
 * SavedState.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.utilities;

import game_play.model.GamePlayModel;

import java.io.*;

/**
 * Class providing serialization capability for specific game objects
 * The main model of the game is used to save the state of the game and later restore it
 */
public class SavedState implements Serializable {
    
    // region Public Methods
    
    /**
     * Saving the game to file from the central game model
     *
     * @param gamePlayModel the main game model to be serialized
     * @param path          the destination to write the serialized object to file
     */
    public static void SaveGame(GamePlayModel gamePlayModel, String path) {
        
        try {
            OutputStream file = new FileOutputStream(path);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(gamePlayModel);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Loading the game from provide file path
     *
     * @param path the absolute location of the file
     *
     * @return the loaded GamePlayModel restored from the serialized file
     */
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
            System.out.println(e.getMessage());
        }
        return state;
    }
    // endregion
    
}
