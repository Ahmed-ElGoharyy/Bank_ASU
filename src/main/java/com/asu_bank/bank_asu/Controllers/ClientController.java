package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Model.Client;
import com.asu_bank.bank_asu.Model.Employee;
import com.asu_bank.bank_asu.Model.User;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ClientController {

    private Client currentUser;

    @FXML
    private Text WelcomeText;








    public void setCurrentUser(User user) {
        if (user instanceof Employee) {
            this.currentUser = (Client) user;
            if (WelcomeText != null) { // Check for null WelcomeText before setting text
                WelcomeText.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLasttName());
            }

        } else {
            // Optional: Handle unexpected user type
            utility.ShowErrorAlert("Invalid user type for Employee view");
        }
    }
}
