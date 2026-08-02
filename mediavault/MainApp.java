package mediavault;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main entry point for the Media Vault JavaFX application.
 * <p>Manages the application lifecycle including initialization, UI loading,
 * and shutdown procedures.</p>
 *
 * @author Jimlor
 * @version 1.2
 */
public class MainApp extends Application {
    /** The active library collection loaded during the session. */
    private Library myLibrary;
    /** The active user operating the application. */
    private User currentUser;

    /**
     * Initializes the application before the UI starts.
     * <p>Sets up the default user and loads existing library data from storage.</p>
     */
    @Override
    public void init() {
        currentUser = new User("Admin");
        myLibrary = currentUser.getLibrary();
        
        FileManager.loadLibrary(myLibrary);
    }

    /**
     * Starts the JavaFX application and displays the primary stage.
     *
     * @param primaryStage the primary window of the application
     * @throws Exception if the FXML layout fails to load
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setUser(currentUser);

        primaryStage.setTitle("MediaVault - " + currentUser.getUsername() + "'s Collection");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    /**
     * Executes shutdown procedures when the application is closed.
     * <p>Ensures that all current library data is saved to storage.</p>
     */
    @Override
    public void stop() {
        FileManager.saveLibrary(myLibrary);
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args); 
    }
}