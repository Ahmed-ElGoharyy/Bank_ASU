package com.asu_bank.bank_asu.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class utility {


    public static void ShowErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void ShowSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
