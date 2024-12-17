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
import javafx.scene.text.Text;

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
    private Text Displaytext;

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


    Bank bank = Bank.getInstance();


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
                            employee.getFirstName() + "          LastName:  " + employee.getLasttName() +
                            "         Position : " + employee.getPosition() +  "         Address :"+employee.getAddress()+
                            "         TotalGrade :  " +
                            employee.getTotalGrade() + "       GraduationYear :  " + employee.getYearOfGradutaion());
                }

                myListView.setItems(employeeListStrings);
                Displaytext.setText("Bank Employees : ");
            }
        } catch (Exception e) {
            utility.ShowErrorAlert("Error : " + e);
        }
    }


    @FXML
    private void AuthorizeButtonClicked(ActionEvent event) {

        String adminUsername = "admin";
        int graduationYear;
        Long telephoneNumber;
        try {
            telephoneNumber = Long.valueOf(TeleText.getText());


            if (FirstNameText.getText().isEmpty() || LastNameText.getText().isEmpty() ||
                    UsernameText.getText().isEmpty() || PasswordText.getText().isEmpty() ||
                    TeleText.getText().isEmpty() || AddressText.getText().isEmpty() ||
                    PositionText.getText().isEmpty() || GradCollegeText.getText().isEmpty() ||
                    GradYearText.getText().isEmpty() || TotalGradeText.getText().isEmpty()) {
                utility.ShowErrorAlert("Error: All fields must be filled out.");
                return;
            }
            if (String.valueOf(telephoneNumber).length() <6) {
                utility.ShowErrorAlert("Error: Telephone number must be at least 7 digits.");
                return;
            }
            if (String.valueOf(telephoneNumber).length() >10) {
                utility.ShowErrorAlert("Error: Telephone number must be max 11 digits.");
                return;
            }
            if (telephoneNumber < 0) {
                utility.ShowErrorAlert("Error: Telephone number cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Error: Some Fields are Empty.");
            return;
        }
        try {
            graduationYear = Integer.parseInt(GradYearText.getText());
            if (graduationYear < 1980 || graduationYear > 2024) {
                utility.ShowErrorAlert("Error: Graduation year must be between 1980 and 2024.");
                return;
            }
        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Error: Graduation year must be a valid number.");
            return;
        }
        String totalGradeInput = TotalGradeText.getText();
        if (totalGradeInput.length() != 1 || !("ABC".contains(totalGradeInput))) {
            utility.ShowErrorAlert("Error: Total grade must be a single character (A, B, or C).");
            return;
        }
            try {
                char totalGrade = TotalGradeText.getText().charAt(0);
                String newAdd = AddressText.getText();
                if (UsernameText.getText().equals(adminUsername)) {
                    utility.ShowErrorAlert("Error: Username '" + adminUsername + "' is reserved for the admin.");
                    return;
                }

                for (Employee employee : bank.BankEmployees) {
                    if (employee.getUserName().equals(UsernameText.getText())) {
                        utility.ShowErrorAlert("Error: This username is already taken.");
                        return;
                    }
                }
                if (PositionText.getText().matches(".*\\d.*")) {
                    utility.ShowErrorAlert("Error: Position cannot be numbers. Please enter a valid job title.");
                    return;
                }
                if(GradCollegeText.getText().matches(".*\\d.*")){
                    utility.ShowErrorAlert("Error: Graduated College cannot be numbers. Please enter a valid College name.");
                return;
                }
                if(!newAdd.matches(".*[a-zA-Z].*")||!newAdd.matches(".*[0-9].*")){
                    throw new IllegalArgumentException("Address must contain numbers and names");
                }
                if (FirstNameText.getText().matches(".*\\d.*")) {
                    utility.ShowErrorAlert("Error: First name cannot contain numbers.");
                    return;
                }
                if (LastNameText.getText().matches(".*\\d.*")) {
                    utility.ShowErrorAlert("Error: Last name cannot contain numbers.");
                    return;
                }
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
            } catch (NumberFormatException e) {
                utility.ShowErrorAlert("Error: Please enter valid numeric values for telephone number and graduation year.");
            } catch (IllegalArgumentException e) {
                utility.ShowErrorAlert("Error: " + e.getMessage());
            } catch (Exception e) {
                utility.ShowErrorAlert("Error: " + e.getMessage());
            }
    }




    @FXML
    public void showClientsButtonClicked(ActionEvent event) {

        // Clear any existing items
        myListView.getItems().clear();

        try {
           if (bank == null) {
               System.out.println("Warning: Bank object not injected. Employee data might not be available.");
            } else {
                // Get employee data from Bank object (assuming getter method)
               ObservableList<Client> ClientObservableList = FXCollections.observableArrayList(bank.BankClients);
                ObservableList<Client> Clients = ClientObservableList;

                // Create an ObservableList of Strings to display in the ListView
                ObservableList<String> ClientsListStrings = FXCollections.observableArrayList();
                for (Client client : Clients) {
                  // Customize employee display format as needed (e.g., name, ID, department)
                    ClientsListStrings.add("      ID :  " + client.getClient_id() + "          FirstName :  " +
                            client.getFirstName() + "             LastName :  "+client.getLasttName()+
                            "           Telephone No :"+client.getTelephoneNumber()
                         );
               }

                myListView.setItems(ClientsListStrings);
                Displaytext.setText("Bank Clients : ");

           }
        } catch (Exception e) {
            utility.ShowErrorAlert("Error in loading clients data : "+e);
       }
    }




    @FXML
    public void showTransButtonClicked(ActionEvent event) {
        // Clear any existing items
        myListView.getItems().clear();

        try {
            if (bank == null) {
                System.out.println("Warning: Bank object not injected. Employee data might not be available.");
            } else {
                // Get employee data from Bank object (assuming getter method)
                ObservableList<Transaction> TransactionObservableList = FXCollections.observableArrayList(bank.BankATMTrans);
                ObservableList<Transaction> Transactions = TransactionObservableList;

                ObservableList<Moneytrans> TransferObservableList = FXCollections.observableArrayList(bank.BankMoneyTransfers);
                ObservableList<Moneytrans> Transfers = TransferObservableList;

                // Create an ObservableList of Strings to display in the ListView
                ObservableList<String> TransactionsListStrings = FXCollections.observableArrayList();
                for (Transaction transaction : Transactions) {
                    // Customize employee display format as needed (e.g., name, ID, department)
                    TransactionsListStrings.add("   Transaction No  :    "+ transaction.getTransid() +
                            "         Transaction Account :" +transaction.getAccnumber()+
                            "       Type : "+transaction.getType()+ "       Amount :  "+transaction.getAmount()+
                            "     To: "+transaction.getAccnumber()+"     Date  :"+transaction.getDate());

                }
                for(Moneytrans transfer : Transfers){
                    TransactionsListStrings.add("   Transaction No :"+transfer.getTransid()+
                    "         Transaction Account :" +transfer.getAccnumber()+
                            "       Type : "+transfer.getType()+ "       Amount :  "+transfer.getAmount()+
                            "     From : "+transfer.getAccnumber()+"      Date  :" +transfer.getDate() +
                            "      Reciever :" +transfer.getRecieveraccnum() );



                }

                myListView.setItems(TransactionsListStrings);
                Displaytext.setText("Transactions : ");

            }
        } catch (Exception e) {
            utility.ShowErrorAlert("Error loading Transaction : "+e);
        }
    }






    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }


}
