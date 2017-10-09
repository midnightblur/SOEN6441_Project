package view.screens;



import javax.swing.*;

public class MainPage extends JFrame {
    private JPanel Play;
    private JButton mapEditorButton;
    private JButton playGameButton;
    private JButton quitGameButton;


    public MainPage() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // this.setContentPane(this.mainArea);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
