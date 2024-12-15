package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Model.*;
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
    @FXML
    private Text Displaytext;
    @FXML
    private TextField TransactionButtonText;
    @FXML
    private TextField AccountNumberText;
    @FXML
    private TextField AccountStateText;
    @FXML
    private TextField Client_idText;
    @FXML
    private TextField AccountTypeText;
    @FXML
    private TextField transidText ;
    @FXML
    private TextField amountText;
    @FXML
    private TextField typeText;
    @FXML
    private TextField DateText;
    @FXML
    private TextField accnumText;
    @FXML
    private TextField recieveraccnumText;




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

    @FXML
    private ListView<String> myListView;
    Bank bank = Bank.getInstance();

    @FXML
    public void showaccountinfo(ActionEvent event) {
        // Clear any existing items
        myListView.getItems().clear();

        try {
            if (bank == null) {
                System.out.println("Warning: Account data might not be available.");
            } else {
                ObservableList<CurrentAccount> currentaccountObservableList = FXCollections.observableArrayList(bank.getCurrentAccount());
                ObservableList<CurrentAccount> currentaccount = currentaccountObservableList;
                ObservableList<SavingAccount> savingaccountObservableList = FXCollections.observableArrayList(bank.getSavingAccount());
                ObservableList<SavingAccount> savingaccount = savingaccountObservableList;

                ObservableList<Client> ClientObservableList = FXCollections.observableArrayList(bank.BankClients);
                ObservableList<Client> Clients = ClientObservableList;

                ObservableList<String> accountListStrings = FXCollections.observableArrayList();
                //ObservableList<String> savingaccountListStrings = FXCollections.observableArrayList();

                //for (int i = 0; i < Clients.size(); i++) {
                for (CurrentAccount account : currentaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {

                        // Customize employee display format as needed (e.g., name, ID, department)
                        accountListStrings.add("   ID:  " + account.getClient_id() + "       Account number:  " +
                                account.getAccountnumber() + "          Account State:  " + account.getAccountState() +
                                "         Account type : " + account.getAccountType() + "         Account balance :  " +
                                account.getBalance());

                    }


                }

                for (SavingAccount account : savingaccountObservableList)
                {



                    if (currentUser.getClient_id().equals(account.getClient_id())) {

                        accountListStrings.add("   ID:  " + account.getClient_id() + "       Account number:  " +
                                account.getAccountnumber() + "          Account State:  " + account.getAccountState() +
                                "         Account type : " + account.getAccountType() + "         Account balance :  " +
                                account.getBalance());

                    }

                }
                myListView.setItems(accountListStrings);
                Displaytext.setText("Account Information:");
            }


        } catch (Exception e) {
            utility.ShowErrorAlert("Error : " + e);
        }
    }

   @FXML
    public void showwTransButtonClicked(ActionEvent event) {


        // Clear any existing items
        myListView.getItems().clear();

        try {
            if (bank == null) {
                System.out.println("Transaction unavailable ");
            } else {

                ObservableList<CurrentAccount> currentaccountObservableList = FXCollections.observableArrayList(bank.getCurrentAccount());
                ObservableList<CurrentAccount> currentaccount = currentaccountObservableList;
                ObservableList<SavingAccount> savingaccountObservableList = FXCollections.observableArrayList(bank.getSavingAccount());
                ObservableList<SavingAccount> savingaccount = savingaccountObservableList;

                ObservableList<Transaction> TransactionObservableList = FXCollections.observableArrayList(bank.BankATMTrans);
                ObservableList<Transaction> Transactions = TransactionObservableList;

                ObservableList<Moneytrans> TransferObservableList = FXCollections.observableArrayList(bank.getBankMoneyTransfers());
                ObservableList<Moneytrans> Transfers = TransferObservableList;
                ObservableList<String> accountListStrings = FXCollections.observableArrayList();
                ObservableList<String> TransactionsListStrings = FXCollections.observableArrayList();

                for (SavingAccount account : savingaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {
                        for (Transaction transaction : TransactionObservableList ) {
                            if (account.getAccountnumber().equals(transaction.getAccnumber())) {
                                TransactionsListStrings.add("   Transaction No  :    " + transaction.getTransid() +
                                        "         Transaction Account :" + transaction.getAccnumber() +
                                        "       Type : " + transaction.getType() + "       Amount :  " + transaction.getAmount() +
                                        "     Date  :" + transaction.getDate());
                            }
                        }

                    }
                }
                for (CurrentAccount account : currentaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {
                        for (Transaction transaction :TransactionObservableList  ) {
                            if (account.getAccountnumber().equals(transaction.getAccnumber())) {
                                TransactionsListStrings.add("   Transaction No  :    " + transaction.getTransid() +
                                        "         Transaction Account :" + transaction.getAccnumber() +
                                        "       Type : " + transaction.getType() + "       Amount :  " + transaction.getAmount() +
                                        "Date  :" + transaction.getDate());
                            }
                        }

                    }
                }

                for (CurrentAccount account : currentaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {
                        for (Moneytrans transfer : TransferObservableList) {
                            if (account.getAccountnumber().equals(transfer.getAccnumber())&&(transfer.getRecieveraccnum()!=0))
                            {
                                TransactionsListStrings.add("   Account number  :    " + transfer.getAccnumber()+
                                        "         Transaction id :" + transfer.getTransid() +
                                        "       Amount : " + transfer.getAmount() + "       Type :  " + transfer.getType() +
                                        "     To: " + transfer.getRecieveraccnum() + "     Date  :" + transfer.getDate());
                            }
                        }

                    }
                }


                for (SavingAccount account : savingaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {
                        for (Moneytrans transfer : TransferObservableList) {
                            if (account.getAccountnumber().equals(transfer.getAccnumber())&&(transfer.getRecieveraccnum()!=0))
                            {
                                TransactionsListStrings.add("   Account number  :    " + transfer.getAccnumber()+
                                        "         Transaction id :" + transfer.getTransid() +
                                        "       Amount : " + transfer.getAmount() + "       Type :  " + transfer.getType() +
                                        "     To: " + transfer.getRecieveraccnum() + "     Date  :" + transfer.getDate());
                            }
                        }

                    }
                }

                myListView.setItems(TransactionsListStrings);
                Displaytext.setText("Transactions : ");

            }
        }catch (Exception e) {
            utility.ShowErrorAlert("Error loading Transaction : "+e);
        }
    }



}