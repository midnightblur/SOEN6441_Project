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
import java.util.HashMap;
import java.util.Map;

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
            Map<String, Object> jsonArgs = new HashMap<>();
            jsonArgs.put(JsonWriter.TYPE, false);
            jsonArgs.put(JsonWriter.PRETTY_PRINT, true);
            JsonWriter jw = new JsonWriter(outputStream, jsonArgs);
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
            Map<String, Object> jsonArgs = new HashMap<>();
            jsonArgs.put(JsonReader.FAIL_ON_UNKNOWN_TYPE, false);
            jsonArgs.put(JsonReader.FAIL_ON_UNKNOWN_TYPE, false);
            jsonArgs.put(JsonReader.TYPE_NAME_MAP, true);
            state = (GamePlayModel) JsonReader.jsonToJava(file, jsonArgs);
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return state;
    }
    // endregion
    
}
