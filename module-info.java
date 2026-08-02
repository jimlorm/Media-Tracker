module mediavault {
    // These tell Java that we require the JavaFX libraries
    requires javafx.controls;
    requires javafx.fxml;

    // This grants JavaFX permission to read your FXML and Controller files
    opens mediavault to javafx.fxml;
    exports mediavault;
}