import controller.MainMenuController;

/**
 * Main driver class is responsible for starting the application
 */
public class Driver {
    
    /**
     * Start the application by making a Main Game Controller
     *
     * @param args CLI
     */
    public static void main(String[] args) throws Exception {
        new MainMenuController();
    }
}
