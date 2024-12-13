package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Main;
import com.asu_bank.bank_asu.Model.Bank;
import com.asu_bank.bank_asu.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private ListView<String> myListView;
    @FXML
    private Button ShowEmps;
    @FXML
    private Button ShowClients;

    @FXML
     private TextField FirstNameText;
    @FXML
    private TextField LastNameText;
    @FXML
    private TextField UsernameText;
    @FXML
    private TextField PasswordText;
    @FXML
    private TextField TeleText;
    @FXML
    private TextField AddressText;
    @FXML
    private TextField PositionText;
    @FXML
    private TextField GradCollegeText;
    @FXML
    private TextField GradYearText;
    @FXML
    private TextField TotalGradeText;


    Bank bank =Bank.getInstance();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myListView.getItems().clear();
    }






    @FXML
    public void showEmpsButtonClicked(ActionEvent event) {
        // Clear any existing items
        myListView.getItems().clear();

        try {
            if (bank == null) {
                System.out.println("Warning: Bank object not injected. Employee data might not be available.");
            } else {
                // Get employee data from Bank object (assuming getter method)
                ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList(bank.getBankEmployees());
                ObservableList<Employee> employees = employeeObservableList;

                // Create an ObservableList of Strings to display in the ListView
                ObservableList<String> employeeListStrings = FXCollections.observableArrayList();
                for (Employee employee : employees) {
                    // Customize employee display format as needed (e.g., name, ID, department)
                    employeeListStrings.add("   ID:  " + employee.getId() + "       FirstName:  " +
                            employee.getFirstName() + "          LastName:  "+employee.getLasttName()+
                            "         Position : " + employee.getPosition()+ "         TotalGrade :  "+
                            employee.getTotalGrade()+"       GraduationYear :  "+employee.getYearOfGradutaion());
                }

                myListView.setItems(employeeListStrings);
            }
        } catch (Exception e) {
            utility.ShowErrorAlert("Error : "+e);
        }
    }





    @FXML
    private void AuthorizeButtonClicked(ActionEvent event) {
        try {
            char totalGrade = TotalGradeText.getText().charAt(0);
            Employee newEmployee = new Employee(

                    FirstNameText.getText(),
                    LastNameText.getText(),
                    UsernameText.getText(),
                    PasswordText.getText(),
                    Long.parseLong(TeleText.getText()),
                    AddressText.getText(),
                    PositionText.getText(),
                    GradCollegeText.getText(),
                    Integer.parseInt(GradYearText.getText()),
                    totalGrade);

            // Add the employee to the bank
            bank.BankEmployees.add(newEmployee);
            utility.ShowSuccessAlert("New Employee added and authorized successfully!");
        } catch (Exception e) {
            utility.ShowErrorAlert("Error : Wrong data type for"+ e);
        }
    }

    @FXML
    public void showClientsButtonClicked(ActionEvent event) {
        // Clear any existing items
        myListView.getItems().clear();

//        try {
//            if (bank == null) {
//                System.out.println("Warning: Bank object not injected. Employee data might not be available.");
//            } else {
//                // Get employee data from Bank object (assuming getter method)
//                ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList(bank.getBankEmployees());
//                ObservableList<Employee> employees = employeeObservableList;
//
//                // Create an ObservableList of Strings to display in the ListView
//                ObservableList<String> employeeListStrings = FXCollections.observableArrayList();
//                for (Employee employee : employees) {
//                    // Customize employee display format as needed (e.g., name, ID, department)
//                    employeeListStrings.add("   ID:  " + employee.getId() + "       FirstName:  " +
//                            employee.getFirstName() + "          LastName:  "+employee.getLasttName()+
//                            "         Position : " + employee.getPosition()+ "         TotalGrade :  "+
//                            employee.getTotalGrade()+"       GraduationYear :  "+employee.getYearOfGradutaion());
//                }
//
//                myListView.setItems(employeeListStrings);
//            }
//        } catch (Exception e) {
//            utility.ShowErrorAlert("Error in loading clients data : "+e);
//        }
    }



    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }


}
