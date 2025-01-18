package org.example.frontend;

import javafx.scene.control.Alert;

public class AlertManager {

    public static void showInfo(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}