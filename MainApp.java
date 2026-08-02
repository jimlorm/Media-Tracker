import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    private Library myLibrary;
    private User currentUser;

    @Override
    public void init() {
        currentUser = new User("Admin");
        myLibrary = currentUser.getLibrary();
        
        FileManager.loadLibrary(myLibrary);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setLibrary(myLibrary);

        primaryStage.setTitle("MediaVault - " + currentUser.getUsername() + "'s Collection");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() {
        FileManager.saveLibrary(myLibrary);
    }

    public static void main(String[] args) {
        launch(args); 
    }
}