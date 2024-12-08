package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;


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

    public static void ConfirmAction(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void LogOut(ActionEvent event) {
        try {
            utility.ShowSuccessAlert("You Are Logging Out!");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));  //Return to Login view
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            utility.ShowErrorAlert("Log Out Failed! " + e.getMessage());
        }
    }
}
