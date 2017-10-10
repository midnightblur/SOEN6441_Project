package controller;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;
import model.ui_models.DropDownModel;
import model.ui_models.MapEditorModel;
import utilities.Config;
import view.helpers.SaveDialog;
import view.screens.MapEditorFrame;
import view.ui_components.EditContinentPanel;
import view.ui_components.EditTerritoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

public class MapEditorController {
    private static final String NONE_RADIO_BUTTON = "NONE";
    private static final String CONTINENT_NAME_GENERATOR = "continent_";
    private static final String TERRITORY_NAME_GENERATOR = "territory_";
    private static final int DEFAULT_CONTROL_VALUE = 1;
    
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    private MainGameController callerController;
    private int newContinentID; // helps generate new continent name => faster demo
    private int newTerritoryID; // helps generate new territory name => faster demo
    
    /* Constructors */
    public MapEditorController(MainGameController mainGameController) {
        this.callerController = mainGameController;
        newContinentID = 0;
        newTerritoryID = 0;
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
    
        /* Display list of maps to load to edit */
        DropDownModel mapDropdownModel = new DropDownModel(GameMapHelper.getMapsInFolder(Config.MAPS_FOLDER));
        this.mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().setModel(mapDropdownModel);
        
        /* Register Observer to Observable */
        this.mapEditorModel.addObserver(this.mapEditorFrame.getMapTable());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapControlPanel().addLoadMapButtonListener(e -> loadMap());
        this.mapEditorFrame.getEditMapControlPanel().addBackButtonListener(e -> backToMainMenu());
        this.mapEditorFrame.getEditMapControlPanel().addNewMapButtonListener(e -> initiateNewGameMap());
        this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().addContinentsListDropdownListener(e -> prepareContinentEditArea());
        this.mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().addTerritoryListDropdownListener(e -> prepareTerritoryEditArea());
        this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().addSaveContinentButtonListener(e -> saveContinentInfo());
        this.mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().addSaveTerritoryButtonListener(e -> saveTerritoryInfo());
        this.mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().addRemoveContinentButtonListener(e -> removeContinentInfo());
        this.mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().addRemoveTerritoryButtonListener(e -> removeTerritoryInfo());
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
     * Prepare the content for Continent Editing area
     */
    private void prepareContinentEditArea() {
        String selectedContinents = String.valueOf(mapEditorModel.getContinentsDropdownModel().getSelectedItem());
        mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().removeAll();
        if (selectedContinents.compareTo(MapEditorModel.getCreateNewContinentItem()) == 0) {
            String newContinentName = CONTINENT_NAME_GENERATOR + newContinentID;
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentNameText().setText(newContinentName);
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContientControlValueText().setText(String.valueOf(DEFAULT_CONTROL_VALUE));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
            
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentNameText().setText(CONTINENT_NAME_GENERATOR + newContinentID++);
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getSaveContinentButton().setText(EditContinentPanel.getAddButtonLabel());
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getRemoveContinentButton().setEnabled(false);
        } else {
            Continent continent = mapEditorModel.getGameMap().getAContinent(selectedContinents);
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentNameText().setText(continent.getName());
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContientControlValueText().setText(String.valueOf(continent.getControlValue()));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                if (continent.isContain(territory.getName())) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
                mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getSaveContinentButton().setText(EditContinentPanel.getSaveButtonLabel());
            mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getRemoveContinentButton().setEnabled(true);
        }
        
        mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().revalidate();
        mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().repaint();
    }
    
    /**
     * Prepare the content for Territory Editing area
     */
    private void prepareTerritoryEditArea() {
        String selectedTerritories = String.valueOf(mapEditorModel.getTerritoriesDropdownModel().getSelectedItem());
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().removeAll();
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().removeAll();
        ButtonGroup buttonGroup = new ButtonGroup();
        if (selectedTerritories.compareTo(MapEditorModel.getCreateNewTerritoryItem()) == 0) {
            String newTerritoryName = TERRITORY_NAME_GENERATOR + newTerritoryID;
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoryNameText().setText(newTerritoryName);
            
            // None radio button to choose no continent
            JRadioButton noneRadioBtn = new JRadioButton(NONE_RADIO_BUTTON);
            buttonGroup.add(noneRadioBtn);
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(noneRadioBtn);
            for (Continent continent : mapEditorModel.getGameMap().getContinents().values()) {
                JRadioButton radioButton = new JRadioButton(continent.getName());
                radioButton.setSelected(false);
                buttonGroup.add(radioButton);
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(radioButton);
            }
            noneRadioBtn.setSelected(true);
            
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
            
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoryNameText().setText(TERRITORY_NAME_GENERATOR + newTerritoryID++);
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getSaveTerritoryButton().setText(EditTerritoryPanel.getAddButtonLabel());
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRemoveTerritoryButton().setEnabled(false);
        } else {
            Territory currentTerritory = mapEditorModel.getGameMap().getATerritory(selectedTerritories);
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoryNameText().setText(currentTerritory.getName());
            
            // None radio button to choose no continent
            JRadioButton noneRadioBtn = new JRadioButton(NONE_RADIO_BUTTON);
            buttonGroup.add(noneRadioBtn);
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(noneRadioBtn);
            for (Continent continent : mapEditorModel.getGameMap().getContinents().values()) {
                JRadioButton radioButton = new JRadioButton(continent.getName());
                if (currentTerritory.belongToContinent(continent.getName())) {
                    radioButton.setSelected(true);
                } else {
                    radioButton.setSelected(false);
                }
                buttonGroup.add(radioButton);
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(radioButton);
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
                mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getSaveTerritoryButton().setText(EditTerritoryPanel.getSaveButtonLabel());
            mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRemoveTerritoryButton().setEnabled(true);
        }
        
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().revalidate();
        mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().repaint();
    }
    
    /**
     * Get information from Continent Editing area and save to GameMap object
     */
    private void saveContinentInfo() {
        try {
            String newContinentName = mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentNameText().getText();
            int controlValue = Integer.parseInt(mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContientControlValueText().getText());
            Continent newContinent = new Continent(newContinentName, controlValue);
            
            Vector<String> territoryVector = new Vector<>();
            for (Component component : mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getCheckBoxPanel().getComponents()) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    territoryVector.add(checkBox.getText());
                }
            }
            newContinent.setTerritories(territoryVector);
            
            if (mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getSaveContinentButton().getText().compareTo(EditContinentPanel.getAddButtonLabel()) == 0) {
                /* Adding new continent */
                String result = mapEditorModel.addNewContinent(newContinent);
                if (result.compareTo(String.format(GameMap.getMsgContinentAddSuccess(), newContinentName)) != 0) {
                    mapEditorFrame.displayErrorMessage(result);
                }
            } else {
                /* Update existing continent */
                String oldContinentName = String.valueOf(mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentsListDropdown().getSelectedItem());
                String result = mapEditorModel.updateContinent(oldContinentName, newContinent);
                if (result.compareTo(String.format(GameMap.getMsgContinentEditSuccess(), newContinentName)) != 0) {
                    mapEditorFrame.displayErrorMessage(result);
                }
            }
        } catch (NumberFormatException e) {
            mapEditorFrame.displayErrorMessage(e.getMessage());
        }
    }
    
    private void removeContinentInfo() {
        String continentName = String.valueOf(mapEditorFrame.getEditMapControlPanel().getEditContinentPanel().getContinentsListDropdown().getSelectedItem());
        String result = mapEditorModel.removeContinent(continentName);
        if (result.compareTo(String.format(GameMap.getMsgContinentRemoveSuccess(), continentName)) != 0) {
            mapEditorFrame.displayErrorMessage(result);
        }
    }
    
    /**
     * Get information from Territory Editing area and save to GameMap object
     */
    private void saveTerritoryInfo() {
        String newTerritoryName = mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoryNameText().getText();
        String chosenContinent = "";
        for (Component component : mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getRadioButtonsPanel().getComponents()) {
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
        Vector<String> neighbourVector = new Vector<>();
        for (Component component : mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getCheckBoxPanel().getComponents()) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                neighbourVector.add(checkBox.getText());
            }
        }
        newTerritory.setNeighbors(neighbourVector);
        
        if (mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getSaveTerritoryButton().getText().compareTo(EditTerritoryPanel.getAddButtonLabel()) == 0) {
            /* Adding new territory */
            String result = mapEditorModel.addNewTerritory(newTerritory);
            if (result.compareTo(String.format(GameMap.getMsgTerritoryAddSuccess(), newTerritoryName)) != 0) {
                mapEditorFrame.displayErrorMessage(result);
            }
        } else {
            /* Update existing territory */
            String oldTerritoryName = String.valueOf(mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoriesListDropdown().getSelectedItem());
            String result = mapEditorModel.updateTerritory(oldTerritoryName, newTerritory);
            if (result.compareTo(String.format(GameMap.getMsgTerritoryEditSuccess(), newTerritoryName)) != 0) {
                mapEditorFrame.displayErrorMessage(result);
            }
        }
    }
    
    private void removeTerritoryInfo() {
        String territoryName = String.valueOf(mapEditorFrame.getEditMapControlPanel().getEditTerritoryPanel().getTerritoriesListDropdown().getSelectedItem());
        String result = mapEditorModel.removeTerritory(territoryName);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryRemoveSuccess(), territoryName)) != 0) {
            mapEditorFrame.displayErrorMessage(result);
        }
    }
    
    private void saveMap() {
        String validateMessage = GameMapHelper.validateMap(mapEditorModel.getGameMap());
        if (validateMessage.compareTo(Config.MSG_MAPFILE_VALID) != 0) {
            mapEditorFrame.displayErrorMessage(validateMessage);
        } else {
            SaveDialog fileChooser = new SaveDialog();
            int selection = fileChooser.showSaveDialog(fileChooser.getParent());
            if (selection == JFileChooser.APPROVE_OPTION) {
                File mapFileToSave = fileChooser.getSelectedFile();
                try {
                    GameMapHelper.writeToFile(mapEditorModel.getGameMap(), mapFileToSave.getAbsolutePath());
                    mapEditorFrame.displayErrorMessage("The map file was saved at \n" + mapFileToSave.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    mapEditorFrame.displayErrorMessage(e.toString());
                }
            }
        }
    }
}