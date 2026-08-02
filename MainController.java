import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML private ListView<String> mediaListView;
    @FXML private TextArea detailsTextArea;
    @FXML private Button btnAdd, btnUpdate, btnRate, btnDelete;

    private Library library;

    @FXML
    public void initialize() {
        mediaListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = newValue.intValue();

            if (selectedIndex >= 0 && library != null) {
                MediaEntry selectedMedia = library.getEntry(selectedIndex);
                if (selectedMedia != null) {
                    detailsTextArea.setText(selectedMedia.getDetails());
                }
            }
        });

        btnDelete.setOnAction(event -> handleDeleteEntry());
    }

    public void setLibrary(Library library) {
        this.library = library;
        refreshList();
    }

    private void refreshList() {
        mediaListView.getItems().clear();

        if (library != null) {
            for (MediaEntry m : library.getAllMedia()) {
                mediaListView.getItems().add(m.getTitle() + " (" + m.getCurrentStatus() + ")");
            }
        }
    }

    private void handleDeleteEntry() {
        // Get the currently selected item's index
        int selectedIndex = mediaListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            // Confirm deletion with a pop-up
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this entry?");

            // If the user clicks ok, delete it from the Model and refresh the View
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    library.deleteEntry(selectedIndex); // Delete from backend
                    refreshList(); // Update the visual list
                    detailsTextArea.setText("Select an item to see details..."); // Clear the text area
                }
            });
        } else {
            // If they clicked the button without selecting an item first
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select an entry to delete.");
            errorAlert.showAndWait();
        }
    }
}