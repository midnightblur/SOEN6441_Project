/* 
 * Risk Game Team 2
 * MapEditorController.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.controller;

import game_play.controller.MainMenuController;
import map_editor.model.MapEditorModel;
import map_editor.view.EditContinentPanel;
import map_editor.view.EditTerritoryPanel;
import map_editor.view.MapEditorFrame;
import shared_resources.game_entities.Continent;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;
import shared_resources.helper.UIHelper;
import shared_resources.utilities.MapFilter;
import shared_resources.utilities.SaveOpenDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

import static shared_resources.utilities.Config.MAPS_EXTENSION;
import static shared_resources.utilities.Config.MSG_MAPFILE_VALID;

/**
 * The MapEditorController is responsible for creating and editing map files:
 * <ul>
 * <li> Loading maps from Maps folder
 * <li> Creating new maps
 * <li> Creating needed controls for editing territories or continents
 * <li> Saving the map
 * <li> Navigating back to main game menu
 * </ul>
 *
 * @author Team 2
 * @version 1.0
 */
public class MapEditorController {
    // region Attributes declaration
    private static final String NONE_RADIO_BUTTON = "NONE";
    private static final String CONTINENT_NAME_GENERATOR = "continent_";
    private static final String TERRITORY_NAME_GENERATOR = "territory_";
    private static final int DEFAULT_CONTROL_VALUE = 1;
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    private MainMenuController callerController;
    private int newContinentID; // helps generate new continent name => faster demo
    private int newTerritoryID; // helps generate new territory name => faster demo
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map editor controller.
     * <ul>
     * <li> Creates the view and game_play objects
     * <li> Subscribes the views to models
     * <li> Register itself as listener to user actions from view
     * </ul>
     *
     * @param mainMenuController the main menu controller
     */
    public MapEditorController(MainMenuController mainMenuController) {
        this.callerController = mainMenuController;
        newContinentID = 0;
        newTerritoryID = 0;
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
        
        /* update the game_entities with a list of map files and set the game_entities to view */
        this.mapEditorModel.updateListOfMaps();
        this.mapEditorFrame.getEditMapPanel().getMapLoadPanel().getChooseMapDropdown().setModel(mapEditorModel.getMapDropdownModel());
        
        /* Register Observer to Observable */
        this.mapEditorModel.addObserver(this.mapEditorFrame);
        this.mapEditorModel.addObserver(this.mapEditorFrame.getGameMapTable());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel().getMapLoadPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel().getEditContinentPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel().getEditTerritoryPanel());
        this.mapEditorModel.addObserver(this.mapEditorFrame.getEditMapPanel());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapPanel().getMapLoadPanel().addLoadMapButtonListener(e -> loadMap(String.valueOf(mapEditorFrame.getEditMapPanel().getMapLoadPanel().getChooseMapDropdown().getSelectedItem())));
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
    // endregion
    
    // region Methods to handle events from UI
    
    /**
     * Update the GameMap object from the selected items from DropdownList.
     *
     * @param mapName the name of the map to be loaded
     */
    private void loadMap(String mapName) {
        try {
            // load the map
            mapEditorModel.loadNewGameMap(mapName);
            // select the loaded map in dropdown
            mapEditorFrame.getEditMapPanel().getMapLoadPanel().getChooseMapDropdown().setSelectedItem(mapName);
        } catch (Exception e) {
            UIHelper.displayMessage(mapEditorFrame, e.getMessage());
        }
    }
    
    /**
     * This method Closes the current MapEditor screen and navigates back to the MainMenu screen.
     */
    private void backToMainMenu() {
        mapEditorFrame.dispose();
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    
    /**
     * Create a new GameMap object for users to make their own map from scratch.
     */
    private void initiateNewGameMap() {
        mapEditorModel.initNewMap();
    }
    
    /**
     * Prepare the content for Continent Editing UI area.
     */
    private void prepareContinentEditArea() {
        String selectedContinents = String.valueOf(mapEditorModel.getContinentsDropdownModel().getSelectedItem());
        // Clearing the selection
        mapEditorFrame.getEditMapPanel().getEditContinentPanel().getCheckBoxPanel().removeAll();
        /* Setting the text for territories labels on check boxes */
        // When selected continent is not yet created but rather is a proposed name
        if (selectedContinents.compareTo(MapEditorModel.getCreateNewContinentItem()) == 0) {
            String newContinentName = CONTINENT_NAME_GENERATOR + newContinentID;
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().setText(newContinentName);
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentControlValueText().setText(String.valueOf(DEFAULT_CONTROL_VALUE));
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapPanel().getEditContinentPanel().getCheckBoxPanel().add(checkBox);
            }
            
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().setText(CONTINENT_NAME_GENERATOR + newContinentID++);
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getSaveContinentButton().setText(EditContinentPanel.getAddButtonLabel());
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getRemoveContinentButton().setEnabled(false);
        } else { // When selected continent exists
            Continent continent = mapEditorModel.getGameMap().getAContinent(selectedContinents);
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().setText(continent.getName());
            mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentControlValueText().setText(String.valueOf(continent.getControlValue()));
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
        // Redraw the panel
        mapEditorFrame.getEditMapPanel().getEditContinentPanel().revalidate();
        mapEditorFrame.getEditMapPanel().getEditContinentPanel().repaint();
    }
    
    /**
     * Prepare the content for Territory Editing area.
     */
    private void prepareTerritoryEditArea() {
        String selectedTerritories = String.valueOf(mapEditorModel.getTerritoriesDropdownModel().getSelectedItem());
        // Clears the selection
        mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().removeAll();
        mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().removeAll();
        ButtonGroup buttonGroup = new ButtonGroup();
        /* Setting the text for continents labels on check boxes */
        // When selected territory is not yet created but rather is a proposed name
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
            
            // Adding a checkbox for each territory
            for (Territory territory : mapEditorModel.getGameMap().getTerritories().values()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(territory.getName());
                checkBox.setSelected(false);
                mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().add(checkBox);
            }
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().setText(TERRITORY_NAME_GENERATOR + newTerritoryID++);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getSaveTerritoryButton().setText(EditTerritoryPanel.getAddButtonLabel());
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRemoveTerritoryButton().setEnabled(false);
        } else { // When selected territory exists
            Territory currentTerritory = mapEditorModel.getGameMap().getATerritory(selectedTerritories);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().setText(currentTerritory.getName());
            
            // None radio button to choose no continent
            JRadioButton noneRadioBtn = new JRadioButton(NONE_RADIO_BUTTON);
            buttonGroup.add(noneRadioBtn);
            mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getRadioButtonsPanel().add(noneRadioBtn);
            
            /* Select the parent continent radio button for each territory */
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
            
            /* Select the children territory checkboxes for each continent */
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
     * Get information from Continent Editing area and save to GameMap object.
     *
     * It handles both adding and editing/updating a continent.
     */
    private void saveContinentInfo() {
        try {
            String newContinentName = mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentNameText().getText();
            int controlValue = Integer.parseInt(mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentControlValueText().getText());
            Continent newContinent = new Continent(newContinentName, controlValue);
            
            /* Adding selected territories to the new continent */
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
     * Get information from Territory Editing area and save to GameMap object.
     *
     * It handles both adding and editing/updating a territory.
     */
    private void saveTerritoryInfo() {
        String newTerritoryName = mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getTerritoryNameText().getText();
        String chosenContinent = "";
        /* Gets the chosen continent */
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
        
        /* Adding selected neighbors to the new territory */
        Territory newTerritory = new Territory(newTerritoryName, chosenContinent);
        Vector<String> neighborVector = new Vector<>();
        for (Component component : mapEditorFrame.getEditMapPanel().getEditTerritoryPanel().getCheckBoxPanel().getComponents()) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected() && checkBox.getText().compareTo(newTerritoryName) != 0) {
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
     * This method is used to remove the continent.
     */
    private void removeContinentInfo() {
        String continentName = String.valueOf(mapEditorFrame.getEditMapPanel().getEditContinentPanel().getContinentsListDropdown().getSelectedItem());
        String result = mapEditorModel.removeContinent(continentName);
        if (result.compareTo(String.format(GameMap.getMsgContinentRemoveSuccess(), continentName)) != 0) {
            UIHelper.displayMessage(mapEditorFrame, result);
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
     * <ul>
     * <li> It detects if user uses correct extension and ads it if not present.
     * <li> Provides confirmation for saving action
     * <li> Reloads the dropdown making available the newly saved map
     * <li> Displays errors/exceptions to user if any
     * </ul>
     */
    private void saveMap() {
        File mapFileToSave;
        String validateMessage = GameMapHelper.validateMap(mapEditorModel.getGameMap());
        /* Check to see if the map was valid before attempting to save it */
        if (validateMessage.compareTo(MSG_MAPFILE_VALID) != 0) {
            UIHelper.displayMessage(mapEditorFrame, validateMessage);
        } else { // When the map is valid, proceed to save
            SaveOpenDialog fileChooser = new SaveOpenDialog(new MapFilter(MAPS_EXTENSION), "Save Map");
            int selection = fileChooser.showDialog();
            if (selection == JFileChooser.APPROVE_OPTION) {
                mapFileToSave = fileChooser.getSelectedFile();
                // add file extension if user does not enters it
                if (!mapFileToSave.getAbsolutePath().toLowerCase().endsWith(MAPS_EXTENSION)) {
                    mapFileToSave = new File(mapFileToSave.getAbsolutePath() + MAPS_EXTENSION);
                }
                try {
                    GameMapHelper.writeToFile(mapEditorModel.getGameMap(), mapFileToSave.getAbsolutePath());
                    UIHelper.displayMessage(mapEditorFrame, "The map file was saved at \n" + mapFileToSave.getAbsolutePath());
                
                    /* reload the map dropdown list and make the saved map the current one */
                    mapEditorModel.updateListOfMaps();
                    loadMap(mapFileToSave.getName());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    UIHelper.displayMessage(mapEditorFrame, e.getMessage());
                }
            }
        }
    }
    // endregion
}
