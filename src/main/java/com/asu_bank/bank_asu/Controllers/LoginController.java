package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Main;
import com.asu_bank.bank_asu.Model.Admin;
import com.asu_bank.bank_asu.Model.Client;
import com.asu_bank.bank_asu.Model.Employee;
import com.asu_bank.bank_asu.Model.User;
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
    private void handleLogin(ActionEvent event) {                   //method to execute when login button presses

        String username = usernameTextField.getText().trim();
        String password = passwordField.getText();



        if (username.isEmpty() || password.isEmpty()) {                          //to check both are typed
            utility.ShowErrorAlert("Username or Password can't be empty!");
            usernameTextField.clear();      // Clear Text Boxes After trying
            passwordField.clear();
        }


        //      ##########  ADMIN CHECK #############


        else if (username.equals("admin") && password.equals("admin")) {            //admin check

            // there is only one admin Acc so it doesn't matter to create an object for it
            Switch(event, "Admin");

        }
//
//            // #########  CLIENT CHECK ############
//
//          else if(check if user exists   ){                                      //client Check
//                if(checkif password is right ){
//                    CurrentUser = new Client();                    //    to set the current user
//                    CurrentUser = (Client)CurrentUser;            //Downcasting to make the current user a client
//                    Switch(event, "Client");                // switch to client scene
//
//                }
//                else{
//                    utility.ShowErrorAlert("Password is Wrong!");
//                        usernameTextField.clear();      // Clear Text Boxes After trying
//                        passwordField.clear();
//                }
//            }
//        }
//
//
//        //   ######### EMPLOYEE CHECK #####################
//
//
//        else if(check if user exists   ){                                      //emp Check
//            if(checkif password is right ){
//                CurrentUser = new Employee();                    //    to set the current user
//                CurrentUser = (Client)CurrentUser;            //Downcasting to make the current user an emp
//                Switch(event, "Employee");                // switch to client scene
//
//            }
//                else{
//                utility.ShowErrorAlert("Password is Wrong!");
//                    usernameTextField.clear();      // Clear Text Boxes After trying
//                    passwordField.clear();
//            //            }
//        }
//    }
//




        else {
            utility.ShowErrorAlert("User is not found!");

            usernameTextField.clear();      // Clear Text Boxes After trying
            passwordField.clear();
        }
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