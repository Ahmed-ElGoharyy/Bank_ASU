package com.asu_bank.bank_asu.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.lang.classfile.Label;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    @FXML
    private Label EmployeeChoicebox;
    @FXML
    private ChoiceBox<String> MyEmployeeChoicebox;
    private String[] employeemethods={"Add account","Add bank account","Edit account","Delete account","Search account","Edit personal informations"};
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyEmployeeChoicebox.getItems().addAll(employeemethods);

    }
    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }





}
