/*
 * Risk Game Team 2
 * SavedState.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.utilities;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import game_play.model.GamePlayModel;

import java.io.*;

/**
 * Class providing serialization capability for specific game objects
 * The main model of the game is used to save the state of the game and later restore it
 */
public class SavedJSONState {
    
    // region Public Methods
    
    /**
     * Saving the game to a JSON file from the central game model
     *
     * @param gamePlayModel the main game model to be saved
     * @param path          the destination to write the JSON file
     */
    public static void saveGame(GamePlayModel gamePlayModel, String path) {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            JsonWriter jw = new JsonWriter(outputStream);
            jw.write(gamePlayModel);
            jw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loading the game from provide file path
     *
     * @param path the absolute location of the file
     *
     * @return the loaded GamePlayModel restored from the JSON file
     */
    public static GamePlayModel loadGame(String path) {
        GamePlayModel state = null;
        InputStream file;
        try {
            file = new FileInputStream(path);
            String json = file.toString();
            state = (GamePlayModel) JsonReader.jsonToJava(json);
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return state;
    }
    // endregion
    
}
