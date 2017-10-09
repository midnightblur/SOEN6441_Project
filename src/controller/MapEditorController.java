package controller;

import model.game_entities.Continent;
import model.game_entities.Territory;
import model.helpers.GameMapHandler;
import model.ui_models.DropDownModel;
import model.ui_models.MapEditorModel;
import utilities.Config;
import view.helpers.SaveDialog;
import view.screens.MapEditorFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.File;

public class MapEditorController {
    private static final String CONTINENT_NAME_GENERATOR = "continent_";
    private static final String TERRITORY_NAME_GENERATOR = "territory_";
    private static final int DEFALUT_CONTROL_VALUE = 1;
    
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    private MainGameController callerController;
    private int newContinentID; // helps generate new continent name => faster demo
    private int newTerritoryID; // helps generate new territory name => faster demo
    
    /* Constructors */
    public MapEditorController(MainGameController mainGameController) {
        this.callerController = mainGameController;
        newContinentID = 1;
        newTerritoryID = 1;
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
    
        /* Display list of maps to load to edit */
        DropDownModel mapDropdownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        this.mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().setModel(mapDropdownModel);
        
        /* Register Observer to Observable */
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapTable());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapControlPanel().addLoadMapButtonListener(e -> loadMap());
        this.mapEditorFrame.getEditMapControlPanel().addBackButtonListener(e -> backToMainMenu());
        this.mapEditorFrame.getEditMapControlPanel().addNewMapButtonListener(e -> initiateNewGameMap());
        this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().addContinentsListDropdownListener(e -> prepareContinentEditArea());
        this.mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().addTerritoryListDropdownListener(e -> prepareTerritoryEditArea());
        this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().addSaveButtonListener(e -> saveContientInfo());
        this.mapEditorFrame.getEditMapControlPanel().addSaveMapButtonListener(e -> saveMap());
    }
    
    /* Private methods */
    /**
     * Update the GameMap object from the selected items from DropdownList
     */
    private void loadMap() {
        try {
            String mapName = String.valueOf(mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().getSelectedItem());
            mapEditorModel.loadNewGameMap(mapName);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            mapEditorFrame.displayErrorMessage(e.getMessage());
        }
    }
    
    /**
     * Close the current MapEditor screen and navigate back to the MainMenu screen
     */
    private void backToMainMenu() {
        callerController.invokeFrame();
        mapEditorFrame.dispatchEvent(new WindowEvent(mapEditorFrame, WindowEvent.WINDOW_CLOSING));
    }
    
    /**
     * Create a new GameMap object for users to make their own map from scratch
     */
    private void initiateNewGameMap() {
        // TODO: implement method
    }
    
    /**
     *  Prepare the content for Continent Editing area
     */
    private void prepareContinentEditArea() {
        String selectedContinents = String.valueOf(mapEditorModel.getContinentsDropdownModel().getSelectedItem());
        mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().removeAll();
        if (selectedContinents.compareTo(MapEditorModel.CREATE_NEW_CONTINENT_ITEM) == 0) {
            String newContinentName = CONTINENT_NAME_GENERATOR + newContinentID;
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentNameText().setText(newContinentName);
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContientControlValueText().setText(String.valueOf(DEFALUT_CONTROL_VALUE));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
        } else {
            Continent continent = mapEditorModel.getGameMap().getAContinent(selectedContinents);
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentNameText().setText(continent.getName());
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContientControlValueText().setText(String.valueOf(continent.getControlValue()));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                if (continent.isContain(territory)) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
                mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
        }
        mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().revalidate();
        mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().repaint();
    }
    
    private void prepareTerritoryEditArea() {
        String selectedTerritories = String.valueOf(mapEditorModel.getTerritoriesDropdownModel().getSelectedItem());
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().removeAll();
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().removeAll();
        if (selectedTerritories.compareTo(MapEditorModel.CREATE_NEW_TERRITORY_ITEM) == 0) {
            String newTerritoryName = TERRITORY_NAME_GENERATOR + newTerritoryID;
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoryNameText().setText(newTerritoryName);
            
            for (Continent continent : mapEditorModel.getGameMap().getContinents().values()) {
                JRadioButton radioButton = new JRadioButton(continent.getName());
                radioButton.setSelected(false);
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(radioButton);
            }
            
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
        } else {
            Territory currentTerritory = mapEditorModel.getGameMap().getATerritory(selectedTerritories);
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoryNameText().setText(currentTerritory.getName());
    
            for (Continent continent : mapEditorModel.getGameMap().getContinents().values()) {
                JRadioButton radioButton = new JRadioButton(continent.getName());
                if (currentTerritory.belongToContinent(continent)) {
                    radioButton.setSelected(true);
                } else {
                    radioButton.setSelected(false);
                }
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(radioButton);
            }
    
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                if (currentTerritory.isNeighbor(territory.getName())) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
        }
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().revalidate();
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().repaint();
    }
    
    /**
     *  Get information from Continent Editing area and save to GameMap object
     */
    private void saveContientInfo() {
    
    }
    
    private void saveMap() {
        SaveDialog fileChooser = new SaveDialog();
        int selection = fileChooser.showSaveDialog(fileChooser.getParent());
        if (selection == JFileChooser.APPROVE_OPTION) {
            File mapFileToSave = fileChooser.getSelectedFile();
            try {
                GameMapHandler.writeToFile(this.mapEditorModel.getGameMap(), mapFileToSave.getAbsolutePath());
                mapEditorFrame.displayErrorMessage("The map file was saved at \n" + mapFileToSave.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                mapEditorFrame.displayErrorMessage(e.toString());
            }
        }
    }
}