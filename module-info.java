module mediavault {
    requires javafx.controls;
    requires javafx.fxml;

    opens mediavault to javafx.fxml;
    exports mediavault;
}