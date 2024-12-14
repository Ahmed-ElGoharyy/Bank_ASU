package com.asu_bank.bank_asu.Controllers;
import com.asu_bank.bank_asu.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;

import java.util.ResourceBundle;

public class EmployeeController implements Initializable {


    private Employee currentUser;


    @FXML
    private Text WelcomeText;
    @FXML
    private Button NameSearchButton;




    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }






// TO set the current user to the user logged in


    public void setCurrentUser(User user) {
        if (user instanceof Employee) {
            this.currentUser = (Employee) user;

            if (WelcomeText != null) { // Check for null WelcomeText before setting text
                WelcomeText.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLasttName());
            }
        } else {
            // Optional: Handle unexpected user type
            utility.ShowErrorAlert("Invalid user type for Employee view");
        }
    }

    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }





}
