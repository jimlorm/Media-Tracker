import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceDialog;
import java.util.Optional;

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
                    updateDetailsArea(selectedMedia);
                }
            }
        });

        // Listen for delete entry button
        btnDelete.setOnAction(event -> handleDeleteEntry());

        // Listen for add entry button
        btnAdd.setOnAction(event -> handleAddEntry());

        // Listen for update status button
        btnUpdate.setOnAction(event -> handleUpdateStatus());

        // Listen for rate entry button
        btnRate.setOnAction(event -> handleRateReview());
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

    private void handleAddEntry() {
        Stage addWindow = new Stage();
        addWindow.initModality(Modality.APPLICATION_MODAL);
        addWindow.setTitle("Add New Media");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Book", "Movie", "TV Series");
        typeBox.setValue("Book");

        TextField titleField = new TextField();
        titleField.setPromptText("Enter Title");

        TextField genreField = new TextField();
        genreField.setPromptText("Enter Genre");

        TextField extra1Field = new TextField();
        extra1Field.setPromptText("Author (Book) / Director (Movie) / Episodes (TV)");

        TextField extra2Field = new TextField();
        extra2Field.setPromptText("Pages (Book) / Runtime (Movie) / Leave empty for TV");

        Button submitButton = new Button("Save Entry");

        submitButton.setOnAction(e -> {
            try {
                String type = typeBox.getValue();
                String title = titleField.getText();
                String genre = genreField.getText();

                if (type.equals("Book")) {
                    int pages = Integer.parseInt(extra2Field.getText());
                    library.addEntry(new Book(title, genre, Status.PLANNED, extra1Field.getText(), pages));
                } else if (type.equals("Movie")) {
                    int runtime = Integer.parseInt(extra2Field.getText());
                    library.addEntry(new Movie(title, genre, Status.PLANNED, extra1Field.getText(), runtime));
                } else if (type.equals("TV Series")) {
                    int episodes = Integer.parseInt(extra1Field.getText());
                    library.addEntry(new TVSeries(title, genre, Status.PLANNED, episodes));
                }

                refreshList();
                addWindow.close();

            } catch (NumberFormatException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for Pages/Runtime/Episodes!");
                error.showAndWait();
            }
        });

        layout.getChildren().addAll(
                new Label("Select Media Type:"), typeBox,
                titleField, genreField, extra1Field, extra2Field,
                submitButton
        );

        addWindow.setScene(new Scene(layout, 300, 250));
        addWindow.showAndWait();
    }

    private void handleUpdateStatus() {
        int selectedIndex = mediaListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            MediaEntry selectedMedia = library.getEntry(selectedIndex);

            ChoiceDialog<Status> dialog = new ChoiceDialog<>(selectedMedia.getCurrentStatus(), Status.PLANNED, Status.IN_PROGRESS, Status.COMPLETED);
            dialog.setTitle("Update Status");
            dialog.setHeaderText("Change status for: " + selectedMedia.getTitle());
            dialog.setContentText("Select new status:");

            Optional<Status> result = dialog.showAndWait();
            result.ifPresent(newStatus -> {
                // Call the model to update progress
                library.updateProgress(selectedMedia, newStatus);
                refreshList();
                updateDetailsArea(selectedMedia);
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an entry to update.").showAndWait();
        }
    }

    private void updateDetailsArea(MediaEntry media) {
        if (media == null) {
            detailsTextArea.setText("Select an item to see details...");
        }
        else {
            StringBuilder details = new StringBuilder(media.getDetails());

            // If it's completed, append the rating and review
            if (media.getCurrentStatus() == Status.COMPLETED) {
                // Check if it has actually been reviewed/rated
                if (media.getReview() == null) {
                    details.append("\n\nYour Rating: (Not yet rated)");
                } else {
                    details.append("\n\nYour Rating: ").append(media.getRating()).append("/10");
                    details.append("\nReview: ").append(media.getReview());
                }
            } else {
                details.append("\n\n(Finish this media to rate and review it!)");
            }

            detailsTextArea.setText(details.toString());
        }
    }

    private void handleRateReview() {
        int selectedIndex = mediaListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            MediaEntry selectedMedia = library.getEntry(selectedIndex);

            Stage rateWindow = new Stage();
            rateWindow.initModality(Modality.APPLICATION_MODAL);
            rateWindow.setTitle("Rate & Review");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(15));

            TextField ratingField = new TextField();
            ratingField.setPromptText("Rating (0-10)");

            TextField reviewField = new TextField();
            reviewField.setPromptText("Write a short review...");

            Button submitBtn = new Button("Submit");

            submitBtn.setOnAction(e -> {
                try {
                    int rating = Integer.parseInt(ratingField.getText());
                    String review = reviewField.getText();

                    // THIS IS WHERE YOUR MODEL ENFORCES THE RULES!
                    library.rateEntry(selectedMedia, rating, review);

                    updateDetailsArea(selectedMedia);
                    rateWindow.close();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Rating must be a number!").showAndWait();
                } catch (Exception ex) {
                    // This catches the IllegalStateException from Library if the status isn't COMPLETED
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                }
            });

            layout.getChildren().addAll(new Label("Rate: " + selectedMedia.getTitle()), ratingField, reviewField, submitBtn);
            rateWindow.setScene(new Scene(layout, 250, 150));
            rateWindow.showAndWait();

        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an entry to rate.").showAndWait();
        }
    }
}