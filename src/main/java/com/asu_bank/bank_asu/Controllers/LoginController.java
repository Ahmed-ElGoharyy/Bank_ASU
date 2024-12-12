package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Main;
import com.asu_bank.bank_asu.Model.*;
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
    protected User CurrentUser;

    @FXML
    private void handleLogin(ActionEvent event) {
        Bank bank = Bank.getInstance();
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText();

        // Empty field check
        if (username.isEmpty() || password.isEmpty()) {
            utility.ShowErrorAlert("Username or Password can't be empty!");
            usernameTextField.clear();
            passwordField.clear();
            return;  // Important: exit method if fields are empty
        }




        // Admin check
        boolean AdminFound=false;
        if (username.equals("admin") && password.equals("admin")) {
            Switch(event, "Admin");
            AdminFound=true;
        }




        // Employee Check


        boolean employeeFound = false;
        for (Employee employee : bank.BankEmployees) {
            if (employee.getUserName().equals(username)) {
                employeeFound = true;
                if (employee.getPassword().equals(password)) {
                    CurrentUser = employee;
                    Switch(event, "Employee");
                    return;
                } else {
                    utility.ShowErrorAlert("Incorrect password!");
                    usernameTextField.clear();
                    passwordField.clear();
                    return;
                }
            }
        }





//        // Client Check (uncommented and fixed)
//        boolean clientFound = false;
//        for (Client client : bank.BankClients) {
//            if (client.getUserName().equals(username)) {
//                clientFound = true;
//                if (client.getPassword().equals(password)) {
//                    CurrentUser = client;
//                    Switch(event, "Client");
//                    return;
//                } else {
//                    utility.ShowErrorAlert("Incorrect password!");
//                    usernameTextField.clear();
//                    passwordField.clear();
//                    return;
//                }
//            }
//        }

      if(!AdminFound && !employeeFound )  // If no user found
        utility.ShowErrorAlert("User is not found!");
        usernameTextField.clear();
        passwordField.clear();
    }





    public void Switch(ActionEvent event,String View)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(View+".fxml"));
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