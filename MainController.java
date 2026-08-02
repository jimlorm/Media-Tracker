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
}