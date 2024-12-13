package com.asu_bank.bank_asu.Model;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;

public class Employee extends User {

    protected String address;
    protected String position;
    protected String graduatedCollege;
    protected Integer yearOfGraduation;
    protected Character totalGrade;
    protected ArrayList<CurrentAccount> current = new ArrayList<>();
    protected ArrayList<SavingAccount> saving = new ArrayList<>();
    protected ArrayList<Client> clients = new ArrayList<>();

    Scanner s=new Scanner(System.in);
    //Constructor
    public Employee ( String firstName, String lastName, String userName, String password, long telephoneNumber,
                     String address,String position,String graduatedCollege,int yearOfGraduation,char totalGrade) {
        super( firstName, lastName, userName, password, telephoneNumber);
        this.address=address;
        this.position=position;
        this.graduatedCollege=graduatedCollege;
        this.yearOfGraduation=yearOfGraduation;
        this.totalGrade=totalGrade;
    }

    public  Employee(){
        super();
    }
    //Getters
    public String getAddress() {return address;}

    public String getGraduatedCollege() {return graduatedCollege;}

    public char getTotalGrade() {return totalGrade;}

    public String getPosition() {return position;}

    public int getYearOfGradutaion() {return yearOfGraduation;}

    public void createClientAccount() throws Client.AccountException
    {
        try {
            //take new client account info

            System.out.println("Enter the required details for the new account: ");

            //Account type
            System.out.println("Account type(Saving/Current): ");
            String accountType=s.next();
            if((!accountType.equalsIgnoreCase("saving"))&&(!accountType.equalsIgnoreCase("current")))
            {
                throw new Client.AccountException("invalid Account type!");
            }

            //Balance
            System.out.println("Balance: ");
            double balance=s.nextDouble();
            if(balance<0)
            {
                throw new Account.NegativeAmountException("balance can't be a negative number..");

            }
            if(accountType.equalsIgnoreCase("current"))
            {
                CurrentAccount newAccount= new CurrentAccount(balance);
                current.add(newAccount);
            } else {
                SavingAccount newAccount = new SavingAccount(balance);
                saving.add(newAccount);
            }
            System.out.println("Account added successfully.");
        } catch(Exception e)
        {
            System.out.println(e.getMessage());

        }
    }

    public void editAccountInfo() throws Exception
    {
        try {
            Account oldAccount = null;
            boolean isfound = false;
            boolean isCurrentAccount=false;

            //search by account number
            System.out.println("Enter Account number you want to edit : ");
            long accountnumber = s.nextLong();
            if (accountnumber <= 0) {
                throw new Exception("invalid account number");
            }


            //search for account number in current accounts
            for (CurrentAccount account : current) {
                if (accountnumber == account.getAccountnumber()) {
                    isfound = true;
                    oldAccount = account;
                    isCurrentAccount=true;
                    break;
                }
            }


            //search for account number in saving accounts
            if(!isfound) {
                for (SavingAccount account : saving) {
                    if (accountnumber == account.getAccountnumber()) {
                        isfound = true;
                        isCurrentAccount = false;
                        oldAccount = account;
                        break;
                    }
                }
            }

            //if account doesn't exist
            if(!isfound)
            {
                throw new Client.EmptyAccountListException("No accounts found!");
            }

            Account updatedAccount=null;
            System.out.print("Enter new Balance: ");
            double newBalance = s.nextDouble();
            if (newBalance < 0) {
                throw new Client.AccountException ("Balance cannot be negative ");
            }



            if (isCurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) oldAccount;
                currentAccount.setBalance(newBalance);
            } else
            {
                SavingAccount savingAccount = (SavingAccount) oldAccount;
                savingAccount.setBalance(newBalance);
            }

            System.out.println("Account updated successfully.");
        } catch( Exception e)
        {
            System.out.println(e.getMessage());
        }

    }



    public void removeAccount() throws Exception {

        try {
            System.out.println("Enter the Account Number you want to remove: ");
            long accountNumber = s.nextLong();
            if (accountNumber <= 0) {
                throw new Client.AccountException("Invalid account number");
            }

            boolean isfound = false;

            // search in current accounts
            for (CurrentAccount account : current) {
                if (account.getAccountnumber() == accountNumber) {
                    current.remove(account);
                    isfound = true;
                    System.out.println("Account removed successfully from Current Accounts.");
                    break;
                }
            }
            //search in saving accounts
            if (!isfound) {
                for (SavingAccount account : saving) {
                    if (account.getAccountnumber() == accountNumber) {
                        saving.remove(account);
                        isfound = true;
                        System.out.println("Account removed successfully from Saving Accounts.");
                        break;
                    }
                }
            }


            if (!isfound) {
                throw new Client.AccountException("Account not found! ");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void searchClient() throws Exception{
        try {
            boolean isfound=false;
            System.out.println("Enter client id: ");
            long client_id = s.nextLong();
            if (client_id <= 0) {
                throw new Exception("Invalid Client id !");
            }

            //search for the client by clientid
            for(Client client : clients )
            {
                if (client_id== client.getClient_id())
                {
                    isfound=true;
                    System.out.println(client);
                }
            }

            if(!isfound)
            {
                throw new Exception("Client id not found! ");
            }

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void editPersonalInfo() throws Client.AccountException
    {
        try {
            // First Name Validation
            System.out.println("Enter Your FirstName : ");
            String newFirstName = validateName(s.next(), "First Name");

            // Last Name Validation
            System.out.println("Enter Your Lastname : ");
            String newLastName = validateName(s.next(), "Last Name");

            // Username Validation
            System.out.println("Enter Your Username : ");
            String newUsername = validateUsername(s.next());

            // Password Validation
            System.out.println("Enter Your Password : ");
            String newPassword = validatePassword(s.next());

            // Telephone Number Validation
            System.out.println("Enter Your Telephone Number : ");
            long newTelephoneNumber = validateTelephoneNumber(s.next());

            //Enter Address
            System.out.println("Enter Your Address : ");
            String newAddress = s.next();

            //Enter Position
            System.out.println("Enter Your Position : ");
            String newPosition = s.next();

            //Enter College
            System.out.println("Enter Your Graduated College Name : ");
            String newGraduatedCollege = s.next();

            //Enter Year Of Graduation
            System.out.println("Enter Your Year Of Graduation : ");
            int newYearOfGraduation = s.nextInt();

            //Enter Total Grade
            System.out.println("Enter Your Total Grade : ");
            char newTotalGrade = s.next().charAt(0);


            // If all validations pass, update the information
            this.firstName = newFirstName;
            this.lastName = newLastName;
            this.setUserName(newUsername);
            this.telephoneNumber = newTelephoneNumber;
            this.address = newAddress;
            this.position = newPosition;
            this.graduatedCollege = newGraduatedCollege;
            this.yearOfGraduation = newYearOfGraduation;
            this.totalGrade = newTotalGrade;

            System.out.println("Personal information updated successfully.");

        } catch (InputMismatchException e) {
            throw new Client.AccountException("Invalid input format. Please enter correct information.");
        } catch (NoSuchElementException e) {
            throw new Client.AccountException("Input error. No information provided.");
        } catch (IllegalArgumentException e) {
            throw new Client.AccountException("Validation Error: " + e.getMessage());
        }
    }
}





