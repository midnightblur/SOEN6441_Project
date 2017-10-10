package model;

import model.game_entities.GameMap;
import model.helpers.GameMapHandler;
import model.ui_models.MapTableModel;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utilities.Config.MAPS_FOLDER;

public class MapEditorModelTest {
    private static String fileName = "World.map";
    private static GameMap inputMap;
    private static GameMap outputMap;
    private static MapTableModel mapTableModel;
    
    @BeforeClass
    public static void roundTripMapFile() throws Exception {
        inputMap = GameMapHandler.loadGameMap(fileName);
        mapTableModel = new MapTableModel();
        mapTableModel.updateMapTableModel(inputMap);
        GameMapHandler.writeToFile(inputMap, MAPS_FOLDER + "/TEST.map");
        outputMap = GameMapHandler.loadGameMap("TEST.map");
    }
    
    @AfterClass
    public static void removeTestFile() throws IOException {
        Files.deleteIfExists(Paths.get(MAPS_FOLDER + "/TEST.map"));
    }
    
    @Test
    public void sameContinents() throws Exception {
        Assert.assertArrayEquals(inputMap.getContinentsNames().toArray(), outputMap.getContinentsNames().toArray());
    }
    
    @Test
    public void sameTerritories() throws Exception {
        Assert.assertArrayEquals(inputMap.getTerritories().values().toArray(), outputMap.getTerritories().values().toArray());
    }
    
}