package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Model.Bank;
import com.asu_bank.bank_asu.Model.Client;
import com.asu_bank.bank_asu.Model.Employee;
import com.asu_bank.bank_asu.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientController {

    private Client currentUser;

    @FXML
    private Text WelcomeText;








    public void setCurrentUser(User user) {
        if (user instanceof Client) {
            this.currentUser = (Client) user;
            if (WelcomeText != null) { // Check for null WelcomeText before setting text
                WelcomeText.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLasttName());
            }

        } else {
            // Optional: Handle unexpected user type
            utility.ShowErrorAlert("Invalid user type for Client view");
        }
    }


    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }
}


