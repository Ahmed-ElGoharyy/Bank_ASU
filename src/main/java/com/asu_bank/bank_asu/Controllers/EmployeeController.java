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

import javax.security.auth.login.AccountNotFoundException;
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
    @FXML
    private TextField PositionTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField AccountNofield;
    @FXML
    private TextField EditAccfield;
    @FXML
    private TextField SearchAccTextField;
    @FXML
    private RadioButton Accradio;
    @FXML
    private  RadioButton Nameradio;
    @FXML
    private  Text Results;


    @FXML
    private TextField SavingClientID;
    @FXML
    private TextField SavingStartBalance;
    @FXML
    private TextField CurrentClientID;
    @FXML
    private TextField CurrentStartBalance;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("intialize Start");

        myListView.getItems().clear();
        System.out.println("intialize end");
    }



        public void CreateSavingAccButton() {
            try {
                Double startBalance = Double.valueOf(SavingStartBalance.getText());
                Long clientId = Long.valueOf(SavingClientID.getText());

                SavingAccount newSavingAcc = new SavingAccount(startBalance, clientId);

                // Add to the bank's list of saving accounts
                bank.BankSavingAccounts.add(newSavingAcc);

                // Find the client with the matching client ID and add the account to their list

                for (Client client : bank.BankClients) {
                    if (client.getClient_id().equals(clientId)) {
                        client.getSaving().add(newSavingAcc);
                        break;
                    }
                }

                utility.ShowSuccessAlert("Saving Account added successfully for Client: " + clientId + "\n" +
                        "Your Account Number: " + newSavingAcc.getAccountnumber() + "\n" +
                        "Your Account State: " + newSavingAcc.getAccountState() + "\n" +
                        "Your Current Balance: " + newSavingAcc.getBalance() + "\n");
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }


    public void CreateCurrentAccButton() {
        try {
            Double startBalance = Double.valueOf(CurrentStartBalance.getText());
            Long clientId = Long.valueOf(CurrentClientID.getText());


            //need t check balance before!!!!!!!!!!!!!!

            CurrentAccount newCurrentAcc = new CurrentAccount(startBalance, clientId);

            // Add to the bank's list of saving accounts
            bank.BankCurrentAccounts.add(newCurrentAcc);

            // Find the client with the matching client ID and add the account to their list

            for (Client client : bank.BankClients) {
                if (client.getClient_id().equals(clientId)) {
                    client.getCurrent().add(newCurrentAcc);
                    break;
                }
            }

            utility.ShowSuccessAlert("Current Account added successfully for Client: " + clientId + "\n" +
                    "Your Account Number: " + newCurrentAcc.getAccountnumber() + "\n" +
                    "Your Account State: " + newCurrentAcc.getAccountState() + "\n" +
                    "Your Current Balance: " + newCurrentAcc.getBalance() + "\n");
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }



    public void SearchAccButton(){
        try {


            // Check if either radio button is selected
            if (!Accradio.isSelected() && !Nameradio.isSelected()) {
                utility.ShowErrorAlert("Choose Client ID or Account No.");
                return;
            }

            boolean accfound = false;
            StringBuilder resultsText = new StringBuilder();

            if (Nameradio.isSelected()) {

                String SearchAcc = SearchAccTextField.getText();

                    Long idsearch = currentUser.getclientidbyname(SearchAcc); // functin we made to get id

                    if (idsearch !=null) {
                        accfound=true;
                        resultsText.append("Found! Your Client ID is : ").append(idsearch);
                    }
                    else {
                         utility.ShowErrorAlert("Name is incorrect! Can't find Your Client ID");
                }


            } else if (Accradio.isSelected()) {

                Long SearchAcc = Long.valueOf(SearchAccTextField.getText());
                // Search for accounts by Account Number
                for (SavingAccount acc : bank.BankSavingAccounts) {
                    if (acc.getAccountnumber().equals(SearchAcc)) {
                        if (accfound) {
                            resultsText.append(" & ");
                        } else {
                            accfound = true;
                        }
                        resultsText.append("Found! Your Client ID is : ").append(acc.getClient_id());
                    }
                }

                for (CurrentAccount acc : bank.BankCurrentAccounts) {
                    if (acc.getAccountnumber().equals(SearchAcc)) {
                        if (accfound) {
                            resultsText.append(" & ");
                        } else {
                            accfound = true;
                        }
                        resultsText.append("Found! Your Client ID is : ").append(acc.getClient_id());
                    }
                }

                if (!accfound) {
                    utility.ShowErrorAlert("Account No is incorrect! Can't find Your Account.");
                }
            }

            if (accfound) {
                Results.setText(resultsText.toString());
            }

        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Invalid Account Number or Client ID format.");
        } catch (Exception e) {
            utility.ShowErrorAlert("Error: " + e.getMessage());
        }
    }






    public void EditPersonalButtonClick(){
        String newAdd = addressTextField.getText();
        String newPos = PositionTextField.getText();
        try {
            // Check if either address or position is null or empty
            if (newAdd == null || newAdd.trim().isEmpty() || newPos == null || newPos.trim().isEmpty()) {
                utility.ShowErrorAlert("Fill all fields please.");
            }
            if (PositionTextField.getText().matches(".*\\d.*")) {
                utility.ShowErrorAlert("Error: Position cannot be numbers. Please enter a valid job title.");
                return;
            }
            if(!newAdd.matches(".*[a-zA-Z].*")||!newAdd.matches(".*[0-9].*")){
                throw new IllegalArgumentException("Address must contain numbers and names");
            } else {
                // Update the user information
                currentUser.setAddress(newAdd);
                currentUser.setPosition(newPos);

                // Show success alert
                utility.ShowSuccessAlert("Your Address Changed to: " + currentUser.getAddress() +
                        "\n"+ "Your Position Changed to: " + currentUser.getPosition());
            }
        }catch (Exception e){
            utility.ShowErrorAlert("Error changing Your info :"+e.getMessage());
        }
    }


    public void EditAccountButton(){
        try {
            String accountNumberText = AccountNofield.getText();
            String newAccState = EditAccfield.getText();
            if (!accountNumberText.matches("\\d+")) {
                utility.ShowErrorAlert("Account Number must be numeric.");
                return;
            }
            Long currentAcc = Long.parseLong(AccountNofield.getText());
            boolean accfound = false;
            if (!newAccState.equals("Active") && !newAccState.equals("Inactive") && !newAccState.equals("active") && !newAccState.equals("inactive")){
                utility.ShowErrorAlert("Account State must be 'Active' or 'Inactive'.");
                return;
            }
            for (SavingAccount acc : bank.BankSavingAccounts) {
                if (acc.getAccountnumber().equals(currentAcc)) {
                    accfound = true;
                    acc.setAccountState(newAccState);
                    AccountNofield.setText("");
                    utility.ShowSuccessAlert("Account State updated to :  " + newAccState);
                }
            }


            for (CurrentAccount acc : bank.BankCurrentAccounts) {
                if (acc.getAccountnumber().equals(currentAcc)) {
                    accfound = true;
                    acc.setAccountState(newAccState);
                    AccountNofield.setText("");
                    utility.ShowSuccessAlert("Account State updated to :  " + newAccState);
                }
            }
            if (!accfound) {
                utility.ShowErrorAlert("Account Number is incorrect!");

            }
        }catch(NumberFormatException e){
            utility.ShowErrorAlert(" Error : "+e.getMessage());
        }catch(IllegalArgumentException e){
            utility.ShowErrorAlert(" Error : "+e.getMessage());
        } catch (Exception e) {
            utility.ShowErrorAlert(" Error :  "+e.getMessage());
        }
    }


    public void DeleteAcc() {

        try {
            String accountNumberInput = AccountNofield.getText();
            if (accountNumberInput.isEmpty()) {
                utility.ShowErrorAlert("Account number cannot be empty. Please enter an account number.");
                return;
            }
            if (!accountNumberInput.matches("\\d+")) {
                throw new IllegalArgumentException("Account number must be numeric. Please enter an account number.");
            }
            Long currentAcc = Long.parseLong(AccountNofield.getText());
            if (utility.ConfirmAction("Are you sure You want to delete Acc No :" + currentAcc)) {

                boolean accfound = false;
                for (int i = 0; i < bank.BankSavingAccounts.size(); i++) {
                    SavingAccount acc = bank.BankSavingAccounts.get(i);

                    if (acc.getAccountnumber().equals(currentAcc)) {
                        accfound = true;

                        // Remove the account from the list
                        bank.BankSavingAccounts.remove(i);
                        AccountNofield.setText("");
                        utility.ShowSuccessAlert("Account deleted successfully.");
                    }
                }
                for (int i = 0; i < bank.BankCurrentAccounts.size(); i++) {
                    CurrentAccount acc = bank.BankCurrentAccounts.get(i);

                    if (acc.getAccountnumber().equals(currentAcc)) {
                        accfound = true;

                        // Remove the account from the list
                        bank.BankCurrentAccounts.remove(i);
                        AccountNofield.setText("");
                        utility.ShowSuccessAlert("Account deleted successfully.");
                    }
                }
                if (!accfound) {
                    throw new AccountNotFoundException("Account Number is incorrect!");
                }
            } else {
            }
        }catch (NumberFormatException e){
            utility.ShowErrorAlert(" Error:  " + e.getMessage());
        } catch (IllegalArgumentException e){
            utility.ShowErrorAlert(" Error:  " + e.getMessage());
        } catch (AccountNotFoundException e){
            utility.ShowErrorAlert(" Error:  " + e.getMessage());
        }catch (Exception e) {
            utility.ShowErrorAlert(" Error Deleting Account! :  "+e.getMessage());
        }



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
                    AccListStrings.add("   Account No  :   "+ saving.getAccountnumber() +
                            "         Account owner's ID :    "+saving.getClient_id()+
                            "         Account Type :   " +saving.getAccountType()+
                            "         Account State: "+saving.getAccountState()+ "       Balance :  "+saving.getBalance());


                }
                for(CurrentAccount current : currents){
                    AccListStrings.add("    Account No :   "+current.getAccountnumber()+
                            "         Account owner's ID :    "+current.getClient_id()+
                            "        Account type:  "+current.getAccountType()+
                            "         Account state :   "+current.getAccountState()+"      Balance :  "+current.getBalance());



                }

                myListView.setItems(AccListStrings);

            }
        } catch (Exception e) {
            utility.ShowErrorAlert("Error loading Transaction : "+e);
        }
        System.out.println("Dispacc end");

    }






// TO set the current user to the user logged in


     public void setCurrentUser(User user) {

        System.out.println("setuser start");

        if (user instanceof Employee) {
            this.currentUser = (Employee) user;

            if (WelcomeText != null) { // Check for null WelcomeText before setting text
                WelcomeText.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLasttName());
            }
        } else {
            // Optional: Handle unexpected user type
            utility.ShowErrorAlert("Invalid user type for Employee view");
        }
        System.out.println("setuser end");

    }



    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }





}
