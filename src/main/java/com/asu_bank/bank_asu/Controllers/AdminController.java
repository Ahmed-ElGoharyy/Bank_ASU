package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

import java.io.IOException;

public class AdminController {

    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }

}
