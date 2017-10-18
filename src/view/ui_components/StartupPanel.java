package view.ui_components;

import model.ui_models.DropDownModel;
import model.ui_models.GamePlayModel;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * Startup Panel representing the controls for Startup phase of the game
 */
public class StartupPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String PLACE_ARMY_BUTTON = "Place Army";
    private static final String TERRITORY_LABEL = "Choose territory to place army on";
    private static final String TOTAL_ARMIES_TO_PLACE_LABEL = "Armies to be placed: ";
    
    private JButton placeArmyButton;
    private JLabel playerNameLabel;
    private JLabel totalArmiesToPlaceLabel;
    private JComboBox<String> territoryDropdown;
    // endregion
    
    // region Constructors
    public StartupPanel() {
        JLabel gameStateLabel = new JLabel();
        gameStateLabel.setForeground(Color.BLUE);
        gameStateLabel.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameStateLabel.setText(Config.GAME_STATES.STARTUP.name());
        playerNameLabel = new JLabel();
        playerNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        totalArmiesToPlaceLabel = new JLabel();
        totalArmiesToPlaceLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        JLabel territoryLabel = new JLabel(TERRITORY_LABEL);
        territoryDropdown = new JComboBox<>();
        placeArmyButton = new JButton(PLACE_ARMY_BUTTON);
        placeArmyButton.setForeground(Color.BLUE);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new GridLayout(17, 1));

        /* Add the elements to the panel */
        controlWrapper.add(gameStateLabel);
        controlWrapper.add(playerNameLabel);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(totalArmiesToPlaceLabel);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(territoryLabel);
        controlWrapper.add(territoryDropdown);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(placeArmyButton);
        addVerticalSpacing(controlWrapper);
        
        add(controlWrapper);
    }
    // endregion
    
    // region Getters & Setters
    public JComboBox getTerritoryDropdown() {
        return territoryDropdown;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    public void addPlaceArmyButtonListener(ActionListener listenerForPlaceArmiesButton) {
        placeArmyButton.addActionListener(listenerForPlaceArmiesButton);
    }
    // endregion
    
    // region Public methods
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.STARTUP) {
                territoryDropdown.setModel(new DropDownModel(gamePlayModel.getCurrentPlayerTerritories()));
                playerNameLabel.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerNameLabel.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                totalArmiesToPlaceLabel.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(gamePlayModel.getCurrentPlayer().getUnallocatedArmies()));
            }
        }
    }
    // endregion
}
