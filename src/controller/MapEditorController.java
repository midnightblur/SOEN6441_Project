package controller;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;
import model.ui_models.MapEditorModel;
import utilities.Config;
import view.helpers.SaveDialog;
import view.helpers.UIHelper;
import view.screens.MapEditorFrame;
import view.ui_components.EditContinentPanel;
import view.ui_components.EditTerritoryPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

/**
 * This is the Map editor controller used to control the
 * actions of map editor
 *
 * @author
 * @version 1.0
 */
public class MapEditorController {
    //region Attributes declaration
    private static final String NONE_RADIO_BUTTON = "NONE";
    private static final String CONTINENT_NAME_GENERATOR = "continent_";
    private static final String TERRITORY_NAME_GENERATOR = "territory_";
    private static final int DEFAULT_CONTROL_VALUE = 1;
    
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    private MainMenuController callerController;
    private int newContinentID; // helps generate new continent name => faster demo
    private int newTerritoryID; // helps generate new territory name => faster demo
    //endregion
    
    //region Constructors
    public MapEditorController(MainMenuController mainMenuController) {
        this.callerController = mainMenuController;
        newContinentID = 0;
        newTerritoryID = 0;
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
        
        /* update the model with a list of map files and set the model to view */
        this.mapEditorModel.updateListOfMaps();
        this.mapEditorFrame.getEditMapPanel().getMapLoadPanel().getChooseMapDropdown().setModel(mapEditorModel.getMapDropdownModel());
        
        /* Register Observer to Observable */
        this.mapEditorModel.addObserver(this.mapEditorFrame.getGameMapTable());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel().getMapLoadPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel().getEditContinentPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel().getEditTerritoryPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapPanel().getMapLoadPanel().addLoadMapButtonListener(e -> loadMap());
        this.mapEditorFrame.getEditMapPanel().addBackButtonListener(e -> backToMainMenu());
        this.mapEditorFrame.getEditMapPanel().getMapLoadPanel().addNewMapButtonListener(e -> initiateNewGameMap());
        this.mapEditorFrame.getEditMapPanel().getEditContinentPanel().addContinentsListDropdownListener(e -> prepareContinentEditArea());
        this.mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().addTerritoryListDropdownListener(e -> prepareTerritoryEditArea());
        this.mapEditorFrame.getEditMapPanel().getEditContinentPanel().addSaveContinentButtonListener(e -> saveContinentInfo());
        this.mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().addSaveTerritoryButtonListener(e -> saveTerritoryInfo());
        this.mapEditorFrame.getEditMapPanel().getEditContinentPanel().addRemoveContinentButtonListener(e -> removeContinentInfo());
        this.mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().addRemoveTerritoryButtonListener(e -> removeTerritoryInfo());
        this.mapEditorFrame.getEditMapPanel().addSaveMapButtonListener(e -> saveMap());
    }
    //endregion
    
    //region Private methods
    /**
     * Update the GameMap object from the selected items from DropdownList
     */
    private void loadMap() {
        try {
            String mapName = String.valueOf(mapEditorFrame.getEditMapPanel().getMapLoadPanel().getChooseMapDropdown().getSelectedItem());
            mapEditorModel.loadNewGameMap(mapName);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            UIHelper.displayMessage(mapEditorFrame, e.getMessage());
        }
    }
    
    /**
     * Close the current MapEditor screen and navigate back to the MainMenu screen
     */
    private void backToMainMenu() {
        UIHelper.closeFrame(mapEditorFrame);
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    
    /**
     * Create a new GameMap object for users to make their own map from scratch
     */
    private void initiateNewGameMap() {
        mapEditorModel.initNewMap();
    }
    
    /**
     * Prepare the content for Continent Editing area
     */
    private void prepareContinentEditArea() {
        String selectedContinents = String.valueOf(mapEditorModel.getContinentsDropdownModel().getSelectedItem());
        mapEditorFrame.getEditMapPanel().getEditContinentPanel().getCheckBoxPanel().removeAll();
        if (selectedContinents.compareTo(MapEditorModel.getCreateNewContinentItem()) == 0) {
            String newContinentName = CONTINENT_NAME_GENERATOR + newContinentID;
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().setText(newContinentName);
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContientControlValueText().setText(String.valueOf(DEFAULT_CONTROL_VALUE));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
            
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().setText(CONTINENT_NAME_GENERATOR + newContinentID++);
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getSaveContinentButton().setText(EditContinentPanel.getAddButtonLabel());
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getRemoveContinentButton().setEnabled(false);
        } else {
            Continent continent = mapEditorModel.getGameMap().getAContinent(selectedContinents);
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().setText(continent.getName());
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContientControlValueText().setText(String.valueOf(continent.getControlValue()));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                if (continent.isContain(territory.getName())) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
                mapEditorFrame.getEditMapPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getSaveContinentButton().setText(EditContinentPanel.getSaveButtonLabel());
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getRemoveContinentButton().setEnabled(true);
        }
        
        mapEditorFrame.getEditMapPanel().getEditContinentPanel().revalidate();
        mapEditorFrame.getEditMapPanel().getEditContinentPanel().repaint();
    }
    
    /**
     * Prepare the content for Territory Editing area
     */
    private void prepareTerritoryEditArea() {
        String selectedTerritories = String.valueOf(mapEditorModel.getTerritoriesDropdownModel().getSelectedItem());
        mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().removeAll();
        mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().removeAll();
        ButtonGroup buttonGroup = new ButtonGroup();
        if (selectedTerritories.compareTo(MapEditorModel.getCreateNewTerritoryItem()) == 0) {
            String newTerritoryName = TERRITORY_NAME_GENERATOR + newTerritoryID;
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().setText(newTerritoryName);
            
            // None radio button to choose no continent
            JRadioButton noneRadioBtn = new JRadioButton(NONE_RADIO_BUTTON);
            buttonGroup.add(noneRadioBtn);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(noneRadioBtn);
            for (Continent continent : mapEditorModel.getGameMap().getContinents().values()) {
                JRadioButton radioButton = new JRadioButton(continent.getName());
                radioButton.setSelected(false);
                buttonGroup.add(radioButton);
                mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(radioButton);
            }
            noneRadioBtn.setSelected(true);
            
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
            
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().setText(TERRITORY_NAME_GENERATOR + newTerritoryID++);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getSaveTerritoryButton().setText(EditTerritoryPanel.getAddButtonLabel());
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRemoveTerritoryButton().setEnabled(false);
        } else {
            Territory currentTerritory = mapEditorModel.getGameMap().getATerritory(selectedTerritories);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().setText(currentTerritory.getName());
            
            // None radio button to choose no continent
            JRadioButton noneRadioBtn = new JRadioButton(NONE_RADIO_BUTTON);
            buttonGroup.add(noneRadioBtn);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(noneRadioBtn);
            for (Continent continent : mapEditorModel.getGameMap().getContinents().values()) {
                JRadioButton radioButton = new JRadioButton(continent.getName());
                if (currentTerritory.belongToContinent(continent.getName())) {
                    radioButton.setSelected(true);
                } else {
                    radioButton.setSelected(false);
                }
                buttonGroup.add(radioButton);
                mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(radioButton);
            }
            if (currentTerritory.getContinent().compareTo("") == 0) {
                noneRadioBtn.setSelected(true);
            }
            
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                if (currentTerritory.isNeighbor(territory.getName())) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
                mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getSaveTerritoryButton().setText(EditTerritoryPanel.getSaveButtonLabel());
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRemoveTerritoryButton().setEnabled(true);
        }
        
        mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().revalidate();
        mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().repaint();
    }
    
    /**
     * Get information from Continent Editing area and save to GameMap object
     */
    private void saveContinentInfo() {
        try {
            String newContinentName = mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().getText();
            int controlValue = Integer.parseInt(mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContientControlValueText().getText());
            Continent newContinent = new Continent(newContinentName, controlValue);
            
            Vector<String> territoryVector = new Vector<>();
            for (Component component : mapEditorFrame.getEditMapPanel().getEditContinentPanel().getCheckBoxPanel().getComponents()) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    territoryVector.add(checkBox.getText());
                }
            }
            newContinent.setTerritories(territoryVector);
            
            if (mapEditorFrame.getEditMapPanel().getEditContinentPanel().getSaveContinentButton().getText().compareTo(EditContinentPanel.getAddButtonLabel()) == 0) {
                /* Adding new continent */
                String result = mapEditorModel.addNewContinent(newContinent);
                if (result.compareTo(String.format(GameMap.getMsgContinentAddSuccess(), newContinentName)) != 0) {
                    UIHelper.displayMessage(mapEditorFrame, result);
                }
            } else {
                /* Update existing continent */
                String oldContinentName = String.valueOf(mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentsListDropdown().getSelectedItem());
                String result = mapEditorModel.updateContinent(oldContinentName, newContinent);
                if (result.compareTo(String.format(GameMap.getMsgContinentEditSuccess(), newContinentName)) != 0) {
                    UIHelper.displayMessage(mapEditorFrame, result);
                }
            }
        } catch (NumberFormatException e) {
            UIHelper.displayMessage(mapEditorFrame, e.getMessage());
        }
    }

    /**
     * This method is used to remove the continent
     */
    private void removeContinentInfo() {
        String continentName = String.valueOf(mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentsListDropdown().getSelectedItem());
        String result = mapEditorModel.removeContinent(continentName);
        if (result.compareTo(String.format(GameMap.getMsgContinentRemoveSuccess(), continentName)) != 0) {
            UIHelper.displayMessage(mapEditorFrame, result);
        }
    }
    
    /**
     * Get information from Territory Editing area and save to GameMap object
     */
    private void saveTerritoryInfo() {
        String newTerritoryName = mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().getText();
        String chosenContinent = "";
        for (Component component : mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().getComponents()) {
            JRadioButton radioButton = (JRadioButton) component;
            if (radioButton.isSelected()) {
                chosenContinent = radioButton.getText();
                break;
            }
        }
        if (chosenContinent.compareTo(NONE_RADIO_BUTTON) == 0) {
            chosenContinent = "";
        }
        Territory newTerritory = new Territory(newTerritoryName, chosenContinent);
        Vector<String> neighborVector = new Vector<>();
        for (Component component : mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().getComponents()) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                neighborVector.add(checkBox.getText());
            }
        }
        newTerritory.setNeighbors(neighborVector);
        
        if (mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getSaveTerritoryButton().getText().compareTo(EditTerritoryPanel.getAddButtonLabel()) == 0) {
            /* Adding new territory */
            String result = mapEditorModel.addNewTerritory(newTerritory);
            if (result.compareTo(String.format(GameMap.getMsgTerritoryAddSuccess(), newTerritoryName)) != 0) {
                UIHelper.displayMessage(mapEditorFrame, result);
            }
        } else {
            /* Update existing territory */
            String oldTerritoryName = String.valueOf(mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoriesListDropdown().getSelectedItem());
            String result = mapEditorModel.updateTerritory(oldTerritoryName, newTerritory);
            if (result.compareTo(String.format(GameMap.getMsgTerritoryEditSuccess(), newTerritoryName)) != 0) {
                UIHelper.displayMessage(mapEditorFrame, result);
            }
        }
    }

    /**
     * This method is used when territory has to be removed.
     */
    private void removeTerritoryInfo() {
        String territoryName = String.valueOf(mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoriesListDropdown().getSelectedItem());
        String result = mapEditorModel.removeTerritory(territoryName);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryRemoveSuccess(), territoryName)) != 0) {
            UIHelper.displayMessage(mapEditorFrame, result);
        }
    }

    /**
     * This method is used to save the map.
     */
    private void saveMap() {
        File mapFileToSave = null;
        String validateMessage = GameMapHelper.validateMap(mapEditorModel.getGameMap());
        if (validateMessage.compareTo(Config.MSG_MAPFILE_VALID) != 0) {
            UIHelper.displayMessage(mapEditorFrame, validateMessage);
        } else {
            SaveDialog fileChooser = new SaveDialog();
            int selection = fileChooser.showSaveDialog(fileChooser.getParent());
            if (selection == JFileChooser.APPROVE_OPTION) {
                mapFileToSave = fileChooser.getSelectedFile();
                // add file extension if user does not enters it
                if (!mapFileToSave.getAbsolutePath().toLowerCase().endsWith(".map")) {
                    mapFileToSave = new File(mapFileToSave.getAbsolutePath() + ".map");
                }
                try {
                    GameMapHelper.writeToFile(mapEditorModel.getGameMap(), mapFileToSave.getAbsolutePath());
                    UIHelper.displayMessage(mapEditorFrame, "The map file was saved at \n" + mapFileToSave.getAbsolutePath());
                
                    /* reload the map dropdown list and make the saved map the current one */
                    mapEditorModel.updateListOfMaps();
                    mapEditorFrame.getEditMapPanel().getMapLoadPanel().getChooseMapDropdown().setSelectedItem(mapFileToSave.getName());
                    loadMap();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    UIHelper.displayMessage(mapEditorFrame, e.getMessage());
                }
                
            }
            
        }
    }
    //endregion
}
