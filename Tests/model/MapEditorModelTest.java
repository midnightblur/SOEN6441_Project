/* 
 * Risk Game Team 2
 * MapEditorModelTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package model;

import model.game_entities.GameMap;
import model.helpers.GameMapHelper;
import model.ui_models.MapTableModel;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utilities.Config.MAPS_FOLDER;

/**
 * The Class MapEditorModelTest.
 */
public class MapEditorModelTest {
    
    /** The file name. */
    private static String fileName = "World.map";
    
    /** The input map. */
    private static GameMap inputMap;
    
    /** The output map. */
    private static GameMap outputMap;
    
    /** The map table model. */
    private static MapTableModel mapTableModel;
    
    /**
     * Prepare the test:
     * <ul>
     * <li>Load a specific map file into a input map object
     * <li>Make a tableModel from the map
     * <li>Save to file the tableModel
     * <li>Load the file that was just saved into a output map object
     * <li>
     * </ul>.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void roundTripMapFile() throws Exception {
        inputMap = GameMapHelper.loadGameMap(fileName);
        mapTableModel = new MapTableModel();
        mapTableModel.updateMapTableModel(inputMap, Config.GAME_STATES.MAP_EDITOR);
        GameMapHelper.writeToFile(inputMap, MAPS_FOLDER + "/TEST.map");
        outputMap = GameMapHelper.loadGameMap("TEST.map");
    }
    
    /**
     * Remove the newly created map test file.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @AfterClass
    public static void removeTestFile() throws IOException {
        Files.deleteIfExists(Paths.get(MAPS_FOLDER + "/TEST.map"));
    }
    
    /**
     * Assert if the input map has the same continents as the output map.
     *
     * @throws Exception the exception
     */
    @Test
    public void sameContinents() throws Exception {
        Assert.assertArrayEquals(inputMap.getContinentsNames().toArray(), outputMap.getContinentsNames().toArray());
    }
    
    /**
     * Assert if the input map has the same territories as the output map.
     *
     * @throws Exception the exception
     */
    @Test
    public void sameTerritories() throws Exception {
        Assert.assertArrayEquals(inputMap.getTerritories().values().toArray(), outputMap.getTerritories().values().toArray());
    }
    
}