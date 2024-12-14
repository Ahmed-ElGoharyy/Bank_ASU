package com.asu_bank.bank_asu.Controllers;
import com.asu_bank.bank_asu.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;

import java.util.ResourceBundle;

public class EmployeeController implements Initializable {


    private Employee currentUser;

    Bank bank = Bank.getInstance();

    @FXML
    private ListView<String> myListView;
    @FXML
    private Button displayAcc;
    @FXML
    private Text selectedAcctext;
    @FXML
    private Text WelcomeText;
    @FXML
    private Button NameSearchButton;




    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }



    public void DisplayAccount(ActionEvent event) {
        // Clear any existing items
        myListView.getItems().clear();

        try {
            if (bank == null) {
                System.out.println("Warning: Bank object not injected. Employee data might not be available.");
            } else {
                // Get employee data from Bank object (assuming getter method)
                ObservableList<SavingAccount> SavingObservableList = FXCollections.observableArrayList(bank.BankSavingAccounts);
                ObservableList<SavingAccount> savings = SavingObservableList;

                ObservableList<CurrentAccount> CurrentObservableList = FXCollections.observableArrayList(bank.BankCurrentAccounts);
                ObservableList<CurrentAccount> currents = CurrentObservableList;

                // Create an ObservableList of Strings to display in the ListView
                ObservableList<String> AccListStrings = FXCollections.observableArrayList();
                for (SavingAccount saving : savings) {
                    // Customize employee display format as needed (e.g., name, ID, department)
                    AccListStrings.add("   Account No  :    "+ saving.getAccountnumber() +
                            "         Account Type :" +saving.getAccountType()+
                            "       Account State: "+saving.getAccountState()+ "       Balance :"+saving.getBalance());


                }
                for(CurrentAccount current : currents){
                    AccListStrings.add("   Account No :"+current.getAccountnumber()
                    +"      Account type: "+current.getAccountType()+
                            "         Account state :"+current.getAccountState()+"     Balance :"+current.getBalance());



                }

                myListView.setItems(AccListStrings);
                myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        selectedAcctext.setText("Selected Account : "+myListView.getSelectionModel().getSelectedItem());
                    }
                });
            }
        } catch (Exception e) {
            utility.ShowErrorAlert("Error loading Transaction : "+e);
        }
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
