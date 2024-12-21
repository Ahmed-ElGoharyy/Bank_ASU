package com.asu_bank.bank_asu.Controllers;

import com.asu_bank.bank_asu.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URL;

import java.util.Date;
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

    @FXML
    private TextField FirstNameclientText;
    @FXML
    private TextField LastNameclientText;
    @FXML
    private TextField UsernameclientText;
    @FXML
    private TextField PasswordclientText;
    @FXML
    private TextField TeleclientText;

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

    @FXML
    private TextField transrec;
    @FXML
    private TextField transamout;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("intialize Start");

        myListView.getItems().clear();
        System.out.println("intialize end");
    }


    @FXML
    private void CreateClientaccButton(ActionEvent event) {

        String adminUsername = "admin";
        int graduationYear;
        Long telephoneNumber;


        if (FirstNameclientText.getText().isEmpty() || LastNameclientText.getText().isEmpty() ||
                UsernameclientText.getText().isEmpty() || PasswordclientText.getText().isEmpty() ||
                TeleclientText.getText().isEmpty()) {
            utility.ShowErrorAlert("Error: All fields must be filled out.");
            return;
        }
        for (Client c : bank.BankClients) {
            if (c.getUserName().equals(UsernameclientText.getText())) {
                utility.ShowErrorAlert("Error: This username is already taken! Please enter another Username.");
                return;
            }
        }
        if (FirstNameclientText.getText().matches(".*\\d.*")) {
            utility.ShowErrorAlert("Error: The Firstname cannot contain numbers!");
            return;
        }
        if (LastNameclientText.getText().matches(".*\\d.*")) {
            utility.ShowErrorAlert("Error: The Lastname cannot contain numbers!");
            return;
        }
        try {
            telephoneNumber = Long.parseLong(TeleclientText.getText());
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
        Client c = new Client(

                FirstNameclientText.getText(),
                LastNameclientText.getText(),
                UsernameclientText.getText(),
                PasswordclientText.getText(),
                Long.parseLong(TeleclientText.getText()));
        bank.BankClients.add(c);
        utility.ShowSuccessAlert("New Client added successfully!: \n "+"ID: "+c.getClient_id()+"\n FirstName: "+c.getFirstName()+"\n LastName: "+c.getLastName()+"\n Username: "+c.getUserName()+"\n Phone Num.: "+c.getTelephoneNumber());
    }
    public void CreateSavingAccButton() {
        try {
            String ClientId = SavingClientID.getText();
            String StartBalance = SavingStartBalance.getText();
            if (ClientId.isEmpty() || StartBalance.isEmpty()) {
                utility.ShowErrorAlert("All fields must be filled out!");
                return;
            }
            if (!ClientId.matches(".*\\d.*")) {
                utility.ShowErrorAlert("Client ID must be numeric!");
                return;
            }


            if (!StartBalance.matches(".*\\d.*")) {
                utility.ShowErrorAlert("Start Balance must be numeric!");
                return;
            }
            Double startBalance = Double.valueOf(SavingStartBalance.getText());
            Long clientId = Long.valueOf(SavingClientID.getText());


            if(startBalance<0){
                utility.ShowErrorAlert("Balance cannot be negative!");
                return;
            }


            SavingAccount newSavingAcc = new SavingAccount(startBalance, clientId);

            // Add to the bank's list of saving accounts
            bank.BankSavingAccounts.add(newSavingAcc);

            // Find the client with the matching client ID and add the account to their list

            boolean clientFound = false;
            for (Client client : bank.BankClients) {
                if (client.getClient_id().equals(clientId)) {
                    client.getSaving().add(newSavingAcc);
                    clientFound = true;
                    break;

                }

            }
            if(!clientFound){
                utility.ShowErrorAlert("Client does not exist...Enter a valid Client ID!");
                return;
            }


            utility.ShowSuccessAlert("Saving Account added successfully for Client: " + clientId + "\n" +
                    "Your Account Number: " + newSavingAcc.getAccountnumber() + "\n" +
                    "Your Account State: " + newSavingAcc.getAccountState() + "\n" +
                    "Your Current Balance: " + newSavingAcc.getBalance() + "\n"+"\n"+
                    "Note : Intrest rate of "+newSavingAcc.getInterestRate()+" Will be Evaluated Yearly\n"+
                    "To Cancel this Refer to our Bank Branch.");
        }catch(NumberFormatException e){
            utility.ShowErrorAlert("Error: " +e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }


    public void CreateCurrentAccButton() {
        try {
            String ClientId = CurrentClientID.getText();
            String StartBalance = CurrentStartBalance.getText();
            if (ClientId.isEmpty() || StartBalance.isEmpty()) {
                utility.ShowErrorAlert("All fields must be filled out!");
                return;
            }
            if (!ClientId.matches(".*\\d.*")) {
                utility.ShowErrorAlert("Client ID must be numeric!");
                return;
            }
            if (!StartBalance.matches(".*\\d.*")) {
                utility.ShowErrorAlert("Start Balance must be numeric!");
                return;
            }

            Double startBalance = Double.valueOf(CurrentStartBalance.getText());
            Long clientId = Long.valueOf(CurrentClientID.getText());

            if(startBalance<0){
                utility.ShowErrorAlert("Balance cannot be negative!");
                return;
            }


            //need t check balance before  !!!!!!!!!!!!!!

            CurrentAccount newCurrentAcc = new CurrentAccount(startBalance, clientId);

            if(startBalance < 3000) {
                if (utility.ConfirmAction("Warning :\nThere will be Fees of 100 EGP since the Balance is less than 3000 EGP \n\n"
                        +"Would you like to continue ?")) {
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

                } else {
                    //do nothing
                }
            }else {


                bank.BankCurrentAccounts.add(newCurrentAcc);

                // Find the client with the matching client ID and add the account to their list

                boolean clientFound = false;
                for (Client client : bank.BankClients) {
                    if (client.getClient_id().equals(clientId)) {
                        client.getCurrent().add(newCurrentAcc);
                        clientFound = true;
                        break;

                    }

                }
                if(!clientFound){
                    utility.ShowErrorAlert("Client does not exist...Enter a valid Client ID!");
                    return;
                }




                utility.ShowSuccessAlert("Current Account added successfully for Client: " + clientId + "\n" +
                        "Your Account Number: " + newCurrentAcc.getAccountnumber() + "\n" +
                        "Your Account State: " + newCurrentAcc.getAccountState() + "\n" +
                        "Your Current Balance: " + newCurrentAcc.getBalance() + "\n");
            }
        }catch(NumberFormatException e){
            utility.ShowErrorAlert("Error: " +e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }



    public void SearchAccButton(){
        try {


            // Check if either radio button is selected
            if (!Accradio.isSelected() && !Nameradio.isSelected()) {
                utility.ShowErrorAlert("Choose Name or Account No.");
                return;
            }

            boolean accfound = false;
            StringBuilder resultsText = new StringBuilder();

            if (Nameradio.isSelected()) {

                String SearchAcc = SearchAccTextField.getText();

                Long idsearch = currentUser.getclientidbyname(SearchAcc,bank); // function we made to get id
                if(SearchAcc.matches(".*\\d.*")){
                    utility.ShowErrorAlert("Full Name cannot be a number!");
                    return;
                }
                if (idsearch !=0) {
                    accfound=true;
                    resultsText.append("Found! Your Client ID is : ").append(idsearch);
                }
                else {
                    utility.ShowErrorAlert("Full  Name is incorrect! Can't find Your Client ID \n " +
                            "Please enter your 'FIRSTNAME LASTNAME' .");
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
            utility.ShowErrorAlert("Invalid Account Number or Name!");
        } catch (Exception e) {
            utility.ShowErrorAlert("Error: " + e.getMessage());
        }
    }






    public void EditPersonalButtonClick(){
        String adminUsername = "admin";
        int graduationYear;
        Long telephoneNumber;
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
        try {
            graduationYear = Integer.parseInt(GradYearText.getText());
            if (graduationYear < 1980 || graduationYear > 2024) {
                utility.ShowErrorAlert("Error: The Graduation Year must be between 1980-2024.");
                return;
            }
        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("The Graduation Year must be a valid number!");
            return;
        }
        String totalGradeInput = TotalGradeText.getText();
        if (totalGradeInput.length() != 1 || !("ABC".contains(totalGradeInput))) {
            utility.ShowErrorAlert(" The Total Grade must be a single character (A, B, or C)!");
            return;
        }
        try {
            String newAdd = AddressText.getText();
            if (UsernameText.getText().equals(adminUsername)) {
                utility.ShowErrorAlert("Error: The Username '" + adminUsername + "' is reserved for the admin only.");
                return;
            }

            if (FirstNameText.getText().isEmpty() || LastNameText.getText().isEmpty() ||
                    UsernameText.getText().isEmpty() || PasswordText.getText().isEmpty() ||
                    TeleText.getText().isEmpty() || AddressText.getText().isEmpty() ||
                    PositionText.getText().isEmpty() || GradCollegeText.getText().isEmpty() ||
                    GradYearText.getText().isEmpty() || TotalGradeText.getText().isEmpty()) {
                utility.ShowErrorAlert("Error: All fields must be filled out.");
                return;
            }
            for (Employee employee : bank.BankEmployees) {
                if (employee.getUserName().equals(UsernameText.getText())) {
                    utility.ShowErrorAlert("Error: This username is already taken! Please enter another Username.");
                    return;
                }
            }
            if (PositionText.getText().matches(".*\\d.*")) {
                utility.ShowErrorAlert("Error: The Position cannot be numbers! Please enter a valid Job Title.");
                return;
            }
            if (GradCollegeText.getText().matches(".*\\d.*")) {
                utility.ShowErrorAlert("Error: The Graduated College cannot be numbers. Please enter a valid College Name.");
                return;
            }
            if (!newAdd.matches(".*[a-zA-Z].*") || !newAdd.matches(".*[0-9].*")) {
                throw new IllegalArgumentException("The Address must contain numbers and names!");
            }
            if (FirstNameText.getText().matches(".*\\d.*")) {
                utility.ShowErrorAlert("Error: The Firstname cannot contain numbers!");
                return;
            }
            if (LastNameText.getText().matches(".*\\d.*")) {
                utility.ShowErrorAlert("Error: The Lastname cannot contain numbers!");
                return;
            }
            currentUser.setUserName(UsernameText.getText());
            currentUser.setFirstName(FirstNameText.getText());
            currentUser.setLastName(LastNameText.getText());
            currentUser.setPassword(PasswordText.getText());
            currentUser.setTelephoneNumber(telephoneNumber);
            currentUser.setAddress(AddressText.getText());
            currentUser.setGraduatedCollege(GradCollegeText.getText());
            currentUser.setTotalGrade(TotalGradeText.getText().charAt(0));
            currentUser.setYearOfGraduation(Integer.parseInt(GradYearText.getText()));
            currentUser.setPosition(PositionText.getText());


            utility.ShowSuccessAlert("Your Info Edited Successfully!");
        } catch (NumberFormatException e) {
            utility.ShowErrorAlert("Error: Please enter valid numeric values for telephone number and graduation year.");
        } catch (IllegalArgumentException e) {
            utility.ShowErrorAlert("Error: " + e.getMessage());
        } catch (Exception e) {
            utility.ShowErrorAlert("Error: " + e.getMessage());
        }
    }



    public void EditAccountButton(){
        try {
            String accountNumberText = AccountNofield.getText();
            String newAccState = EditAccfield.getText();
            if (!accountNumberText.matches("\\d+")) {
                utility.ShowErrorAlert("The Account Number must be numeric!");
                return;
            }
            Long currentAcc = Long.parseLong(AccountNofield.getText());
            boolean accfound = false;
            if (!newAccState.equals("Active") && !newAccState.equals("Inactive") && !newAccState.equals("active") && !newAccState.equals("inactive")){
                utility.ShowErrorAlert("The Account State must be 'Active' or 'Inactive'!");
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
            String accountNumber = AccountNofield.getText();
            if (accountNumber.isEmpty()) {
                utility.ShowErrorAlert("The Account Number cannot be empty! Please enter an Account Number.");
                return;
            }
            if (!accountNumber.matches("\\d+")) {
                throw new IllegalArgumentException("The Account Number must be numeric! Please enter a valid Account Number.");
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
                        utility.ShowSuccessAlert("Account deleted successfully!");
                    }
                }
                for (int i = 0; i < bank.BankCurrentAccounts.size(); i++) {
                    CurrentAccount acc = bank.BankCurrentAccounts.get(i);

                    if (acc.getAccountnumber().equals(currentAcc)) {
                        accfound = true;

                        // Remove the account from the list
                        bank.BankCurrentAccounts.remove(i);
                        AccountNofield.setText("");
                        utility.ShowSuccessAlert("Account deleted successfully!");
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
    public void tranmoneybutton(){
        try {
            String accountNumberText = AccountNofield.getText();
            if (!accountNumberText.matches("\\d+")) {
                utility.ShowErrorAlert("The Account Number must be numeric!");
                return;
            }

            Long accn = Long.parseLong(AccountNofield.getText());
            boolean accfound = false;
            for (SavingAccount acc : bank.BankSavingAccounts) {
                if (acc.getAccountnumber().equals(accn)) {
                    accfound = true;
                    long l = Long.parseLong(transrec.getText());
                    double k = Double.parseDouble(transamout.getText());
                    System.out.println(l+" "+k+" "+accn);
                    trans(l,k,accn);
                    AccountNofield.setText("");
                }
            }


            for (CurrentAccount acc : bank.BankCurrentAccounts) {
                if (acc.getAccountnumber().equals(accn)) {
                    accfound = true;
                    long l = Long.parseLong(transrec.getText());
                    double k = Double.parseDouble(transamout.getText());
                    System.out.println(l+" "+k+" "+accn);
                    trans(l,k,accn);
                    AccountNofield.setText("");
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
    public void trans(Long recaccnum, Double balance, Long sendaccnum) {
        try {
            boolean recaccisfound = false;
            boolean transferCompleted = false;

            // Input validation
            if (recaccnum == null || balance == null || sendaccnum == null) {
                utility.ShowErrorAlert("Account numbers and balance cannot be null");
                return;
            }

            if (balance <= 0) {
                utility.ShowErrorAlert("Transfer amount must be positive");
                return;
            }

            if (recaccnum.equals(sendaccnum)) {
                utility.ShowErrorAlert("Cannot transfer money to the same account");
                return;
            }

            // Check Current Account as sender
            for (CurrentAccount sendc : bank.BankCurrentAccounts) {
                if (sendc.getAccountnumber().equals(sendaccnum)) {
                    // Check Current Account as receiver
                    for (CurrentAccount recc : bank.BankCurrentAccounts) {
                        if (recc.getAccountnumber().equals(recaccnum)) {
                            recaccisfound = true;
                            if (sendc.getBalance() < balance) {
                                utility.ShowErrorAlert("No Enough Balance In Account!");
                                return;
                            }
                            sendc.setBalance(sendc.getBalance() - balance);
                            recc.setBalance(recc.getBalance() + balance);
                            Date d = new Date();
                            Moneytrans t = new Moneytrans(sendaccnum, recaccnum, balance, "Money Transfer", d,
                                    currentUser.getFirstName() + " " + currentUser.getLastName());
                            bank.BankMoneyTransfers.add(t);
                            sendc.moneytransfer.add(t);
                            recc.moneytransfer.add(t);
                            utility.ShowSuccessAlert("Money Transferred Successfully");
                            transferCompleted = true;
                            break;
                        }
                    }

                    // If not found in Current Accounts, check Saving Accounts as receiver
                    if (!transferCompleted) {
                        for (SavingAccount recs : bank.BankSavingAccounts) {
                            if (recs.getAccountnumber().equals(recaccnum)) {
                                recaccisfound = true;
                                if (sendc.getBalance() < balance) {
                                    utility.ShowErrorAlert("No Enough Balance In Account!");
                                    return;
                                }
                                sendc.setBalance(sendc.getBalance() - balance);
                                recs.setBalance(recs.getBalance() + balance);
                                Date d = new Date();
                                Moneytrans t = new Moneytrans(sendaccnum, recaccnum, balance, "Money Transfer", d,
                                        currentUser.getFirstName() + " " + currentUser.getLastName());
                                bank.BankMoneyTransfers.add(t);
                                sendc.moneytransfer.add(t);
                                recs.moneytransfer.add(t);
                                utility.ShowSuccessAlert("Money Transferred Successfully");
                                transferCompleted = true;
                                break;
                            }
                        }
                    }
                    if (transferCompleted) break;
                }
            }

            // If sender not found in Current Accounts, check Saving Accounts as sender
            if (!transferCompleted) {
                for (SavingAccount sends : bank.BankSavingAccounts) {
                    if (sends.getAccountnumber().equals(sendaccnum)) {
                        // Check Current Account as receiver
                        for (CurrentAccount recc : bank.BankCurrentAccounts) {
                            if (recc.getAccountnumber().equals(recaccnum)) {
                                recaccisfound = true;
                                if (sends.getBalance() < balance) {
                                    utility.ShowErrorAlert("No Enough Balance In Account!");
                                    return;
                                }
                                sends.setBalance(sends.getBalance() - balance);
                                recc.setBalance(recc.getBalance() + balance);
                                Date d = new Date();
                                Moneytrans t = new Moneytrans(sendaccnum, recaccnum, balance, "Money Transfer", d,
                                        currentUser.getFirstName() + " " + currentUser.getLastName());
                                bank.BankMoneyTransfers.add(t);
                                sends.moneytransfer.add(t);
                                recc.moneytransfer.add(t);
                                utility.ShowSuccessAlert("Money Transferred Successfully");
                                transferCompleted = true;
                                break;
                            }
                        }

                        // If not found in Current Accounts, check Saving Accounts as receiver
                        if (!transferCompleted) {
                            for (SavingAccount recs : bank.BankSavingAccounts) {
                                if (recs.getAccountnumber().equals(recaccnum)) {
                                    recaccisfound = true;
                                    if (sends.getBalance() < balance) {
                                        utility.ShowErrorAlert("No Enough Balance In Account!");
                                        return;
                                    }
                                    sends.setBalance(sends.getBalance() - balance);
                                    recs.setBalance(recs.getBalance() + balance);
                                    Date d = new Date();
                                    Moneytrans t = new Moneytrans(sendaccnum, recaccnum, balance, "Money Transfer", d,
                                            currentUser.getFirstName() + " " + currentUser.getLastName());
                                    bank.BankMoneyTransfers.add(t);
                                    sends.moneytransfer.add(t);
                                    recs.moneytransfer.add(t);
                                    utility.ShowSuccessAlert("Money Transferred Successfully");
                                    transferCompleted = true;
                                    break;
                                }
                            }
                        }
                        if (transferCompleted) break;
                    }
                }
            }

            if (!recaccisfound) {
                utility.ShowErrorAlert("Receiver Account Not Found");
            }

        } catch (Exception e) {
            utility.ShowErrorAlert("An error occurred during transfer: " + e.getMessage());
        }
    }


    @FXML
    private void logoutButtonClicked(ActionEvent event) {
        utility.LogOut(event);
    }





}