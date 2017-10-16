package view.ui_components;

import model.RiskGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * Startup Panel representing the controls for Startup phase of the game
 */
public class GameSetupPanel extends JPanel implements Observer {

    private static final String MAX_PLAYERS_LABEL = "Maximum players you can enter is ";
    private static final String PLAYERS_LABEL = "Enter the desired number of players:";
    private static final String PLAY_BUTTON = "Play";
    
    private int totalPlayers = 0;
    private JLabel playersLabel;
    private JLabel maxPlayersLabel;
    private JTextField playerCount;
    private JButton playButton;

    /* Constructors */
    public GameSetupPanel() {
        playersLabel = new JLabel(PLAYERS_LABEL);
        maxPlayersLabel = new JLabel();
        playersLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        playerCount = new JTextField(totalPlayers);
        playButton = new JButton(PLAY_BUTTON);
        playButton.setForeground(Color.BLUE);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new GridLayout(15, 1));

        /* Add the elements to the panel */
        controlWrapper.add(playersLabel);
        controlWrapper.add(playerCount);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(playButton);
        addVerticalSpacing(controlWrapper);
        addVerticalSpacing(controlWrapper);

        add(controlWrapper);
    }

    /* Getters & Setters */
    public void setMaxPlayersLabel(int playerCount) {
        this.maxPlayersLabel.setText(MAX_PLAYERS_LABEL + Integer.toString(playerCount));
    }
    
    public void setPlayerCount(JTextField playerCount) {
        this.playerCount = playerCount;
    }
    
    /* MVC & Observer pattern methods */
     public void addPlayButtonListener(ActionListener listenerForPlayButton) {
        playButton.addActionListener(listenerForPlayButton);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RiskGame) {
            setMaxPlayersLabel(((RiskGame) o).getGameMap().getMaxPlayers());
        }
    }
}
