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



    public void AccinUseButton() {
        Long accnum =Long.valueOf(AccountNofield.getText());
        boolean accfound =false;

        for (CurrentAccount Acc : bank.BankCurrentAccounts){
           if( currentUser.getClient_id().equals(Acc.getClient_id()) ){
               if(Acc.getAccountnumber().equals(accnum)){
                    accfound = true;
                    currentAccount = Acc;
                    Accountnotext.setText(""+currentAccount.getAccountnumber()+"");
                }else{
                }

            }

        }
        for (SavingAccount Acc : bank.BankSavingAccounts){
            if( currentUser.getClient_id().equals(Acc.getClient_id()) ){
                if(Acc.getAccountnumber().equals(accnum)){
                    accfound = true;
                    currentAccount = Acc;
                    Accountnotext.setText(""+currentAccount.getAccountnumber()+"");

                }else{
                }

            }

        }
        if(!accfound){
            utility.ShowErrorAlert("unable to access this Account !");
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
                                        "       Transaction number  :   " + transfer.getAccnumber()+
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
                                        "       Transaction number  :    " + transfer.getAccnumber() +
                                        "       Type :  " + transfer.getType()+"       Amount :  " + transfer.getAmount()
                                        + "     Date  :" + transfer.getDate() + "         Reciever :   " + transfer.getRecieveraccnum());
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
    @FXML
    private TextField AmountField;

    @FXML
    public void Deposit(ActionEvent event) {
        double amoount=Double.valueOf(AmountField.getText());

        currentAccount.setBalance(currentAccount.getBalance()+amoount);

        Transaction transaction= new Transaction(currentAccount.getAccountnumber(),
                amoount,"Deposit", new Date());


        bank.BankATMTrans.add(transaction);

        utility.ShowSuccessAlert("Deposit of : "+amoount+" Successful.");
}


    @FXML
    private TextField AmountField2;
    @FXML
    public void WithDraw(ActionEvent event) {

        double amoount=Double.valueOf(AmountField2.getText());

        currentAccount.setBalance(currentAccount.getBalance()-amoount);

        Transaction transaction= new Transaction(currentAccount.getAccountnumber(),
                amoount,"Withdraw", new Date());


        bank.BankATMTrans.add(transaction);

        utility.ShowSuccessAlert("Withdraw of : "+amoount+" Successful.");


    }





    @FXML
    private TextField To;
    @FXML
    private TextField Amount;

   @FXML
     public void Transfer(ActionEvent event){
        try{
            Long Reciever = Long.valueOf(To.getText());
            double Amount4 = Double.valueOf(Amount.getText());
            boolean recieverfound =false;


            for(CurrentAccount Rec: bank.BankCurrentAccounts){
                if(Reciever.equals(Rec.getAccountnumber())){
                    recieverfound = true ;
                    currentAccount.setBalance(currentAccount.getBalance()-Amount4);
                    Rec.setBalance(Rec.getBalance()+ Amount4 );
                    Moneytrans newtrans = new Moneytrans(currentAccount.getAccountnumber(),
                    Rec.getAccountnumber(), Amount4,"Transfer" , new Date());
                    bank.BankMoneyTransfers.add(newtrans);
                    utility.ShowSuccessAlert(Amount4 +" EGP is  Transfered from Account: "+currentAccount.getAccountnumber()
                            +"  to Account: "+Rec.getAccountnumber());
                }
            }
            for(SavingAccount Rec: bank.BankSavingAccounts){
                if(Reciever.equals(Rec.getAccountnumber())){
                    recieverfound = true ;
                    currentAccount.setBalance(currentAccount.getBalance()-Amount4);
                    Rec.setBalance(Rec.getBalance()+ Amount4 );

                    Moneytrans newtrans = new Moneytrans(currentAccount.getAccountnumber(),
                            Rec.getAccountnumber(), Amount4,"Transfer" , new Date());
                    utility.ShowSuccessAlert(Amount4 +" EGP is Transfered from Account: "+currentAccount.getAccountnumber()
                    +"  to Account: "+Rec.getAccountnumber());
                    bank.BankMoneyTransfers.add(newtrans);

                }
            }

            if(!recieverfound){
                utility.ShowErrorAlert("Couldn't find Reciever Account , Check Acc No Again.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    private TextField TelText;
    public void EditPersonalInfo(){

        try {
            Long newTel = Long.valueOf(TelText.getText());

            currentUser.setTelephone(newTel);

            utility.ShowSuccessAlert("Telephone Number Changed to : "+currentUser.getTelephoneNumber()
            +" Successfully. ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}



