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

import java.util.*;

public class ClientController {

    private Client currentUser;
    private Account currentAccount;

    Bank bank= Bank.getInstance();



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
    @FXML
    private TextField AccountNofield;
    @FXML
    private Text Accountnotext;

    @FXML
    private TextField UsernameText;
    @FXML
    private TextField PasswordText;
    @FXML
    private TextField TeleText;


    boolean accfound = false;

    public void AccinUseButton() {

        if (AccountNofield.getText().trim().isEmpty()) {         // Check if the text field is empty
            utility.ShowErrorAlert("Please enter an Account Number!   Field can't be empty");
            return;
        }

        try {
            Long accnum = Long.valueOf(AccountNofield.getText());
            boolean accfound = false;  // Added missing declaration

            for (CurrentAccount Acc : bank.BankCurrentAccounts) {
                if (currentUser.getClient_id().equals(Acc.getClient_id())) {
                    if (Acc.getAccountnumber().equals(accnum)) {
                        accfound = true;
                        currentAccount = Acc;
                        Accountnotext.setText("Account in use : " + currentAccount.getAccountnumber());
                    }
                }
            }

            for (SavingAccount Acc : bank.BankSavingAccounts) {
                if (currentUser.getClient_id().equals(Acc.getClient_id())) {
                    if (Acc.getAccountnumber().equals(accnum)) {
                        accfound = true;
                        currentAccount = Acc;
                        Accountnotext.setText("Account in use : " + currentAccount.getAccountnumber());
                    }
                }
            }

            if (!accfound) {
                utility.ShowErrorAlert("Unable to access this Account!");
            }
        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Please enter a valid Account Number!");
        }
    }





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





    public void RequestCreditCard(){
        if(currentUser.getCreditCard() == null) {
            CreditCard credit = new CreditCard(currentUser.getClient_id());
            currentUser.setCreditCard(credit);
            utility.ShowSuccessAlert("Credit Card is made successfully\n\n"+
                    " Client ID : "+credit.getClient_id()+"\n"+
                    " Credit Card Number : "+ credit.getCardNumber()+"\n"+
                    " Credit Card CVV : "+credit.getCvv()+"\n"+
                    " Credit Card Active  : "+credit.isActive()+"\n"+
                    "  Loyalty points : "+credit.getLoyaltyPoints());
        }
        else{
            utility.ShowErrorAlert("You already have a Credit Card.");
        }

    }

    public  void ActivateCard(){
        if(currentUser.getCreditCard() != null) {
            if (!currentUser.getCreditCard().isActive) {
                currentUser.getCreditCard().setActive(true);
                utility.ShowSuccessAlert("Credit Card Status is Active " );
            } else {
                utility.ShowErrorAlert("Credit Card is already active.");
            }
        }else {
            utility.ShowErrorAlert("Cant find your Credit Card!  Request for one.");
        }

    }

    public void DisableCard(){
        if(currentUser.getCreditCard() != null) {
            if (currentUser.getCreditCard().isActive) {
                currentUser.getCreditCard().setActive(false);
                utility.ShowSuccessAlert("Credit Card Status is Disabled " );
            } else {
                utility.ShowErrorAlert("Credit Card is already Disabled");
            }

        }else {
            utility.ShowErrorAlert("Cant find your Credit Card!  Request for one.");
        }

    }

    @FXML
    private TextField creditamountpaid;


    public void paywithcreditcard() {

        if (currentUser.getCreditCard() == null) {
            utility.ShowErrorAlert("Cant find your Credit Card! Request for one.");
            return;
        }

        // Check if card is active
        if (!currentUser.getCreditCard().isActive()) {
            utility.ShowErrorAlert("Card Credit is Disabled, Activate it to make a credit card transaction");
            return;
        }

        String amountText = creditamountpaid.getText().trim();
        if (amountText.isEmpty()) {
            utility.ShowErrorAlert("Amount can't be empty");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount < 0) {
                utility.ShowErrorAlert("Amount can't be negative");
                return;
            }

            double currentSpending = currentUser.getCreditCard().getSpending();
            double cardLimit = currentUser.getCreditCard().getLimit();

            // Check if transaction would exceed limit
            if (amount + currentSpending > cardLimit) {
                utility.ShowErrorAlert("Transaction failed as you exceeded your limit\n\nTry a smaller amount");
                return;
            }

            // Process the transaction
            double loyaltyPoints = amount * 0.1;
            currentUser.getCreditCard().setSpending(currentSpending + amount);
            currentUser.getCreditCard().setLoyaltyPoints(
                    currentUser.getCreditCard().getLoyaltyPoints() + loyaltyPoints
            );

            // Create and add transaction record
            Transaction newTran = new Transaction(amount, "Credit Card transaction", new Date());
            currentUser.getCredittrans().add(newTran);

            // Show success message
            utility.ShowSuccessAlert(String.format(
                    "Credit card transaction is done successfully\n\n" +
                            "Your new loyalty points after update: %.2f\n" +
                            "Your current spending: %.2f",
                    currentUser.getCreditCard().getLoyaltyPoints(),
                    currentUser.getCreditCard().getSpending()
            ));

        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Please enter a valid amount");
        } catch (Exception e) {
            utility.ShowErrorAlert("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    private Text Loyaltpointstext;
    public void ShowloyaltyPoints(){
        if(currentUser.getCreditCard() != null) {
            if(currentUser.getCreditCard().isActive()) {
                String ss = String.valueOf(currentUser.getCreditCard().getLoyaltyPoints());
                Loyaltpointstext.setText(ss);
            }else{
                utility.ShowErrorAlert("Card Credit is Disabled, Activate it to see your Loyalty Points.");

            }
        }else{
            utility.ShowErrorAlert("Cant find your Credit Card!  Request for one.");
        }

    }







    @FXML
    private ListView<String> myListView;

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
                Displaytext.setText("Accounts Information:");
            }


        } catch (Exception e) {
            utility.ShowErrorAlert("Error : " + e);
        }
    }



   @FXML
    public void showwTransButtonClicked() {


        // Clear any existing items
        myListView.getItems().clear();

        try {
            if(currentAccount == null){
                utility.ShowErrorAlert("You must choose an Account first.");
                return;

            }
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
                         if(account.getAccountnumber().equals(currentAccount.getAccountnumber()))
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
                        if(account.getAccountnumber().equals(currentAccount.getAccountnumber()))
                        for (Transaction transaction :TransactionObservableList  ) {
                            if (account.getAccountnumber().equals(transaction.getAccnumber())) {
                                TransactionsListStrings.add("   Transaction No  :    " + transaction.getTransid() +
                                        "         Transaction Account :  " + transaction.getAccnumber() +
                                        "       Type :  " + transaction.getType() + "       Amount :  " + transaction.getAmount() +
                                        "    Date  :  " + transaction.getDate());
                            }
                        }

                    }
                }

                for (CurrentAccount account : currentaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {
                        if(account.getAccountnumber().equals(currentAccount.getAccountnumber()))
                        for (Moneytrans transfer : TransferObservableList) {
                            if (account.getAccountnumber().equals(transfer.getAccnumber())&&(transfer.getRecieveraccnum()!=0))
                            {
                                TransactionsListStrings.add("   Transaction No  :  " + transfer.getTransid() +
                                        "       Transaction Acc number  :   " + transfer.getAccnumber()+
                                         "       Type :  " + transfer.getType()+"       Amount :  " + transfer.getAmount()
                                        + "      Date  :  " + transfer.getDate() + "          Reciever :  " + transfer.getRecieveraccnum());
                            }
                        }

                    }
                }


                for (SavingAccount account : savingaccountObservableList) {
                    if (currentUser.getClient_id().equals(account.getClient_id())) {
                        if(account.getAccountnumber().equals(currentAccount.getAccountnumber()))
                        for (Moneytrans transfer : TransferObservableList) {
                            if (account.getAccountnumber().equals(transfer.getAccnumber())&&(transfer.getRecieveraccnum()!=0))
                            {
                                TransactionsListStrings.add("   Transaction No  :   " + transfer.getTransid() +
                                        "       Transaction Acc number  :    " + transfer.getAccnumber() +
                                        "       Type :  " + transfer.getType()+"       Amount :  " + transfer.getAmount()
                                        + "     Date  :" + transfer.getDate() + "         Reciever :   " + transfer.getRecieveraccnum());
                            }
                        }

                    }
                }
                for(Transaction tr : currentUser.getCredittrans()){

                    TransactionsListStrings.add("   Transaction No  :   " + tr.getTransid() +
                            "       Client ID  :    " + currentUser.getClient_id() +
                            "       Type :  " + tr.getType()+"       Amount :  " + tr.getAmount()
                            + "     Date  :  " + tr.getDate() );

                }

                myListView.setItems(TransactionsListStrings);
                Displaytext.setText("Transactions : ");

            }
        }catch (Exception e) {
            utility.ShowErrorAlert("Error loading Transaction : "+e);
        }
    }
    @FXML
    private TextField AmountField;

    @FXML
    public void Deposit() {
        try {
            // Check for account selection first
            if (currentAccount == null) {
                utility.ShowErrorAlert("You must choose an Account first.");
                return;
            }

            // Check for empty field
            if (AmountField.getText().trim().isEmpty()) {
                utility.ShowErrorAlert("Amount field can't be empty.");
                return;
            }

            // Parse amount after validation
            double amount = Double.valueOf(AmountField.getText().trim());

            // Validate amount
            if (amount < 0) {
                utility.ShowErrorAlert("Deposit amount cannot be negative!");
                return;
            }

            // Process deposit
            currentAccount.setBalance(currentAccount.getBalance() + amount);

            // Create and save transaction record
            Transaction transaction = new Transaction(
                    currentAccount.getAccountnumber(),
                    amount,
                    "Deposit",
                    new Date(),currentUser.getFirstName()+" "+currentUser.getLastName()
            );

            bank.BankATMTrans.add(transaction);

            // Show success message
            utility.ShowSuccessAlert(String.format("Deposit of %.2f EGP successful.", amount));

        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Please enter a valid number for the amount.");
        } catch (Exception e) {
            utility.ShowErrorAlert("An unexpected error occurred: " + e.getMessage());
        }
    }


    @FXML
    private TextField AmountField2;
    @FXML
    public void WithDraw() {
        try {
            // Check for account selection first
            if (currentAccount == null) {
                utility.ShowErrorAlert("You must choose an Account first.");
                return;
            }

            // Check for empty field
            if (AmountField2.getText().trim().isEmpty()) {
                utility.ShowErrorAlert("Amount field can't be empty.");
                return;
            }

            // Parse amount after validation
            double amount = Double.valueOf(AmountField2.getText().trim());

            // Validate amount
            if (amount < 0) {
                utility.ShowErrorAlert("Withdraw amount cannot be negative!");
                return;
            }

            if (amount > currentAccount.getBalance()) {
                utility.ShowErrorAlert("The withdrawal amount cannot be greater than current account balance!");
                return;
            }

            // Process withdrawal
            currentAccount.setBalance(currentAccount.getBalance() - amount);

            // Create and save transaction record
            Transaction transaction = new Transaction(
                    currentAccount.getAccountnumber(),
                    amount,
                    "Withdraw",
                    new Date(),currentUser.getFirstName()+" "+currentUser.getLastName()
            );
            bank.BankATMTrans.add(transaction);

            // Show success message
            utility.ShowSuccessAlert(String.format("Withdrawal of %.2f EGP successful.", amount));

        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Please enter a valid number for the amount.");
        } catch (Exception e) {
            utility.ShowErrorAlert("An unexpected error occurred: " + e.getMessage());
        }
    }





    @FXML
    private TextField To;
    @FXML
    private TextField Amount;

   @FXML
   public void Transfer() {
       try {
           // Check for empty fields first before parsing
           if (To.getText().trim().isEmpty() || Amount.getText().trim().isEmpty()) {
               utility.ShowErrorAlert("Fields can't be empty.");
               return;
           }

           if (currentAccount == null) {
               utility.ShowErrorAlert("You must choose an Account first.");
               return;
           }

           try {
               Long Receiver = Long.valueOf(To.getText().trim());
               double Amount4 = Double.valueOf(Amount.getText().trim());
               boolean receiverfound = false;

               if (Amount4 < 0) {
                   utility.ShowErrorAlert("Transfer cannot be negative!");
                   return;
               }
               if (Amount4 > currentAccount.getBalance()) {
                   utility.ShowErrorAlert("Transfer cannot be greater than current account balance!");
                   return;
               }

               for (CurrentAccount Rec : bank.BankCurrentAccounts) {
                   if (Receiver.equals(Rec.getAccountnumber())) {
                       receiverfound = true;
                       currentAccount.setBalance(currentAccount.getBalance() - Amount4);
                       Rec.setBalance(Rec.getBalance() + Amount4);
                       Moneytrans newtrans = new Moneytrans(currentAccount.getAccountnumber(),
                               Rec.getAccountnumber(), Amount4, "Money Transfer", new Date(),currentAccount.getclientnamebyid(currentAccount.getClient_id(),bank));
                       bank.BankMoneyTransfers.add(newtrans);
                       Rec.moneytransfer.add(newtrans);
                       currentAccount.moneytransfer.add(newtrans);
                       utility.ShowSuccessAlert(Amount4 + " EGP is Transfered from Account: " + currentAccount.getAccountnumber()
                               + "  to Account: " + Rec.getAccountnumber());
                       break; // Exit loop once found
                   }
               }

               if (!receiverfound) {
                   for (SavingAccount Rec : bank.BankSavingAccounts) {
                       if (Receiver.equals(Rec.getAccountnumber())) {
                           receiverfound = true;
                           currentAccount.setBalance(currentAccount.getBalance() - Amount4);
                           Rec.setBalance(Rec.getBalance() + Amount4);

                           Moneytrans newtrans = new Moneytrans(currentAccount.getAccountnumber(),
                                   Rec.getAccountnumber(), Amount4, "Transfer", new Date());
                           utility.ShowSuccessAlert(Amount4 + " EGP is Transfered from Account: " + currentAccount.getAccountnumber()
                                   + "  to Account: " + Rec.getAccountnumber());
                           bank.BankMoneyTransfers.add(newtrans);
                           break; // Exit loop once found
                       }
                   }
               }

               if (!receiverfound) {
                   utility.ShowErrorAlert("Couldn't find Receiver Account, Check Acc No Again.");
               }

           } catch (NumberFormatException e) {
               utility.ShowErrorAlert("Please enter valid numbers for amount and account number.");
           }

       } catch (Exception e) {
           // Show error alert instead of throwing RuntimeException
           utility.ShowErrorAlert("An unexpected error occurred: " + e.getMessage());
       }
   }


    @FXML
    private TextField TelText;
    public void EditPersonalInfo() {

        String adminUsername = "admin";
        Long telephoneNumber;


        if (UsernameText.getText().isEmpty() || PasswordText.getText().isEmpty() ||
                TeleText.getText().isEmpty()) {
            utility.ShowErrorAlert("Error: All fields must be filled out.");
            return;
        }
        for (Client c : bank.BankClients) {
            if (c.getUserName().equals(UsernameText.getText()) ||UsernameText.getText().equals(adminUsername) ) {
                utility.ShowErrorAlert("Error: This username is already taken! Please enter another Username.");
                return;
            }
        }
        try {
            telephoneNumber = Long.parseLong(TeleText.getText());
            if (String.valueOf(telephoneNumber).length() < 6 || String.valueOf(telephoneNumber).length() > 10) {
                utility.ShowErrorAlert("Error: The Telephone Number must be 7-11 digits.");
                return;
            }
            if (telephoneNumber < 0) {
                utility.ShowErrorAlert("Error: The Telephone Number cannot be negative!");
                return;
            }
        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Error: The Telephone Number must be a valid number!");
            return;
        }
        currentUser.setUserName(UsernameText.getText());
        currentUser.setTelephoneNumber(telephoneNumber);
        currentUser.setPassword(PasswordText.getText());
        utility.ShowSuccessAlert("Your Info Edited Successfully!");

    }
}









