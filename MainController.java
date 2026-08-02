import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MainController {

    @FXML private ListView<String> mediaListView;
    @FXML private TextArea detailsTextArea;
    @FXML private Button btnAdd, btnUpdate, btnRate, btnDelete, btnFilter;

    private Library library;
    private List<MediaEntry> currentDisplayedMedia = new ArrayList<>();

    @FXML
    public void initialize() {
        mediaListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = newValue.intValue();

            if (selectedIndex >= 0 && currentDisplayedMedia != null) {
                MediaEntry selectedMedia = currentDisplayedMedia.get(selectedIndex);
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

        // Listen for filter button
        btnFilter.setOnAction(event -> handleFilter());
    }

    public void setLibrary(Library library) {
        this.library = library;
        refreshList(library.getAllMedia());
    }

    private void refreshList(List<MediaEntry> listToDisplay) {
        mediaListView.getItems().clear();
        currentDisplayedMedia = listToDisplay;

        if (currentDisplayedMedia != null) {
            for (MediaEntry m : currentDisplayedMedia) {
                mediaListView.getItems().add(m.getTitle() + " (" + m.getCurrentStatus() + ")");
            }
        }
    }

    private void handleFilter() {
        if (library == null) return;

        // Create options for the user to pick from
        List<String> filterOptions = Arrays.asList(
                "Show All",
                "Status: Planned", "Status: In Progress", "Status: Completed",
                "Type: Book", "Type: Movie", "Type: TV Series"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Show All", filterOptions);
        dialog.setTitle("Filter Library");
        dialog.setHeaderText("Filter your media entries");
        dialog.setContentText("Select a filter option:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            if (choice.equals("Show All")) {
                refreshList(library.getAllMedia());
            } else if (choice.equals("Status: Planned")) {
                refreshList(library.getMediaByStatus(Status.PLANNED));
            } else if (choice.equals("Status: In Progress")) {
                refreshList(library.getMediaByStatus(Status.IN_PROGRESS));
            } else if (choice.equals("Status: Completed")) {
                refreshList(library.getMediaByStatus(Status.COMPLETED));
            } else if (choice.equals("Type: Book")) {
                refreshList(library.getMediaByType(Book.class));
            } else if (choice.equals("Type: Movie")) {
                refreshList(library.getMediaByType(Movie.class));
            } else if (choice.equals("Type: TV Series")) {
                refreshList(library.getMediaByType(TVSeries.class));
            }

            // Clear the text area since the list just changed
            detailsTextArea.setText("Select an item to see details...");
        });
    }

    private void handleDeleteEntry() {
        // Get the currently selected item's index
        int selectedIndex = mediaListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            MediaEntry selectedMedia = currentDisplayedMedia.get(selectedIndex);

            // Confirm deletion with a pop-up
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this entry?");

            // If the user clicks ok, delete it from the Model and refresh the View
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    int trueIndex = library.getAllMedia().indexOf(selectedMedia);
                    library.deleteEntry(trueIndex);

                    refreshList(library.getAllMedia());
                    detailsTextArea.setText("Select an item to see details...");
                }
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an entry to delete.").showAndWait();
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
                    TVSeries newSeries = new TVSeries(title, genre, Status.PLANNED, episodes);

                    for (int i = 1; i <= episodes; i++) {
                        TextInputDialog epDialog = new TextInputDialog("Episode " + i);
                        epDialog.setTitle("Add Episode Details");
                        epDialog.setHeaderText("Enter title for Episode " + i + " of " + title);

                        Optional<String> epTitle = epDialog.showAndWait();
                        newSeries.addEpisode(new Episode(epTitle.orElse("Episode " + i), i));
                    }
                    library.addEntry(newSeries);
                }

                refreshList(library.getAllMedia()); // Reset list after adding
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
            MediaEntry selectedMedia = currentDisplayedMedia.get(selectedIndex);

            ChoiceDialog<Status> dialog = new ChoiceDialog<>(selectedMedia.getCurrentStatus(), Status.PLANNED, Status.IN_PROGRESS, Status.COMPLETED);
            dialog.setTitle("Update Status");
            dialog.setHeaderText("Change status for: " + selectedMedia.getTitle());
            dialog.setContentText("Select new status:");

            Optional<Status> result = dialog.showAndWait();
            result.ifPresent(newStatus -> {
                library.updateProgress(selectedMedia, newStatus);
                refreshList(library.getAllMedia()); // Reset to show all so the user sees the update
                updateDetailsArea(selectedMedia);
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an entry to update.").showAndWait();
        }
    }

    private void updateDetailsArea(MediaEntry media) {
        if (media == null) {
            detailsTextArea.setText("Select an item to see details...");
        } else {
            StringBuilder details = new StringBuilder(media.getDetails());

            if (media.getCurrentStatus() == Status.COMPLETED) {
                if (media.getReview() == null) {
                    details.append("\n\nYour Rating: (Not yet rated)");
                } else {
                    details.append("\n\nYour Rating: ").append(media.getRating()).append("/10");
                    details.append("\nReview: ").append(media.getReview());
                }
            } else {
                details.append("\n\n(Finish this media to rate and review it!)");
            }

            if (media instanceof TVSeries) {
                details.append("\n\n--- Episode List ---");
                for (Episode ep : ((TVSeries) media).getEpisodes()) {
                    details.append("\n").append(ep.getDetails());
                }
            }

            detailsTextArea.setText(details.toString());
        }
    }

    private void handleRateReview() {
        int selectedIndex = mediaListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            MediaEntry selectedMedia = currentDisplayedMedia.get(selectedIndex);

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

                    library.rateEntry(selectedMedia, rating, review);

                    updateDetailsArea(selectedMedia);
                    rateWindow.close();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Rating must be a number!").showAndWait();
                } catch (Exception ex) {
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