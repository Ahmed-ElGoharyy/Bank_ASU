package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    protected TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            utility.ShowErrorAlert("Username or Password can't be empty!");
            return;
        }

        if (username.equals("admin") && password.equals("admin")) {
            switchToAdmin(event);


        } else {
            utility.ShowErrorAlert("Username or Password is incorrect");
        }
    }

    public void switchToAdmin(ActionEvent event)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Admin.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            utility.ShowErrorAlert("Error switching to admin view: " + e.getMessage());
        }
    }




}