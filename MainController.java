import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML private ListView<String> mediaListView;
    @FXML private TextArea detailsTextArea;
    @FXML private Button btnAdd, btnUpdate, btnRate, btnDelete;

    private Library library;

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