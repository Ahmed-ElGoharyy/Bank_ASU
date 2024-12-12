package com.asu_bank.bank_asu.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EmployeeController {


    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }




}
