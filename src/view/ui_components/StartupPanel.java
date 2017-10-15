package view.ui_components;

import model.ui_models.FortificationModel;
import model.ui_models.StartupModel;
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

    private static final String PLACE_ARMY_BUTTON = "Place Army";
    private static final String DONE_BUTTON = "Done (next player)";
    private static final String TERRITORY_LABEL = "Place an army on: ";
    private static final String TOTAL_ARMIES_TO_PLACE_LABEL = "Armies to be placed: ";

    private JButton placeArmiesButton;
    private JButton doneButton;
    private JLabel gameState;
    private JLabel playerID;
    private JLabel totalArmiesToPlace;
    private JLabel territoryLabel;
    private JComboBox<String> territoryDropdown;

    /* Constructors */
    public StartupPanel() {
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        totalArmiesToPlace = new JLabel();
        totalArmiesToPlace.setFont(new Font("Sans Serif", Font.BOLD, 16));
        territoryLabel = new JLabel(TERRITORY_LABEL);
        territoryDropdown = new JComboBox<>();
        placeArmiesButton = new JButton(PLACE_ARMY_BUTTON);
        placeArmiesButton.setForeground(Color.BLUE);
        doneButton = new JButton(DONE_BUTTON);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new GridLayout(17, 1));

        /* Add the elements to the panel */
        controlWrapper.add(gameState);
        controlWrapper.add(playerID);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(totalArmiesToPlace);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(territoryLabel);
        controlWrapper.add(territoryDropdown);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(placeArmiesButton);
        addVerticalSpacing(controlWrapper);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(doneButton);
        addVerticalSpacing(controlWrapper);

        add(controlWrapper);
    }

    /* Getters & Setters */
    public void setGameState(Config.GAME_STATES gameState) {
        this.gameState.setText("<html><p style=\"color:blue;\">" + gameState.toString() + "</p></html>");
    }

    public void setTotalArmiesToPlace(int totalArmiesToPlace) {
        this.totalArmiesToPlace.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(totalArmiesToPlace));
    }

    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }

    public JButton getPlaceArmiesButton() {
        return placeArmiesButton;
    }

    public JComboBox getterritoryDropdown() {
        return territoryDropdown;
    }

    public void setTerritoryDropdown(JComboBox territoryDropdown) {
        this.territoryDropdown = territoryDropdown;
    }

    /* MVC & Observer pattern methods */
    public void addPlaceArmiesButtonListener(ActionListener listenerForPlaceArmiesButton) {
        placeArmiesButton.addActionListener(listenerForPlaceArmiesButton);
    }

    public void addTerritoryDropdownListener(ActionListener listenerForTerritoryDropdown) {
        territoryDropdown.addActionListener(listenerForTerritoryDropdown);
    }

    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof StartupModel) {
            territoryDropdown.setModel(((StartupModel) o).getTerritoriesList());
        }
    }
}
