package com.asu_bank.bank_asu.Model;

import java.util.*;

public class Client extends User {
    protected Long client_id ;
    protected  int counterr = 5 ;
    protected CreditCard creditCard;
    protected List<CurrentAccount> current = new ArrayList<>() ;
    protected List<SavingAccount> saving = new ArrayList<>();
    protected List<Transaction> credittrans = new ArrayList<>();


    public List<Transaction> getCredittrans() {
        return credittrans;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard credit) {
        this.creditCard = credit;
    }

    public List<SavingAccount> getSaving() {
        return saving;
    }

    public void setSaving(ArrayList<SavingAccount> saving) {
        this.saving = saving;
    }


    public List<CurrentAccount> getCurrent() {
        return current;
    }

    public void setCurrent(ArrayList<CurrentAccount> current) {
        this.current = current;
    }


    public Client( String firstName, String lastName, String userName, String password, long telephoneNumber) {
        super( firstName, lastName, userName, password, telephoneNumber);
        counterr++;
        this.client_id = Long.valueOf(counterr);
    }


    public Client(){
        counterr++;
        this.client_id = Long.valueOf(counterr);
    }


    public Long getClient_id() {return client_id;}
    public Long getTelephone() {return super.telephoneNumber;}
    public void setTelephone(Long Tel) {
        this.telephoneNumber = Tel;
    }

    // Custom exception for empty list
    public static class EmptyAccountListException extends Exception {
        public EmptyAccountListException(String message) {
            super(message);
        }

    }

    // Custom exception for transaction-related errors
    public static class TransactionException extends Exception {
        public TransactionException(String message) {
            super(message);
        }
    }

    // Custom exception to account-related errors
    public static class AccountException extends Exception {
        public AccountException(String message) {
            super(message);
        }
    }

    public void displayaccountsinfo() throws AccountException, EmptyAccountListException {

        boolean isCurrentEmpty = current.isEmpty();
        boolean isSavingEmpty = saving.isEmpty();

        if (isCurrentEmpty && isSavingEmpty) {
            throw new EmptyAccountListException("No accounts found. Both current and saving account lists are empty.");
        }

        try {
            if (!isCurrentEmpty) {
                System.out.println("Current Accounts:");
                for (CurrentAccount account : current) {
                    if (account != null) {
                        System.out.println(account);
                    }
                }
            }

            if (!isSavingEmpty) {
                System.out.println("Saving Accounts:");
                for (SavingAccount account : saving) {
                    if (account != null) {
                        System.out.println(account);
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new AccountException("Error accessing account information: Null account detected " + e.getMessage());
        }
    }

    public void showtransactionhistory() throws AccountException, EmptyAccountListException, TransactionException {
        Scanner s = new Scanner(System.in);

        try {
            displayaccountsinfo();

            System.out.println("Choose The Account You Want: ");
            long Accnum = s.nextLong();

            boolean isfound = false;

            // Search in current accounts
            for (CurrentAccount account : current) {
                if (Accnum == account.getAccountnumber()) {
                    if (account.trasactions.isEmpty() && account.moneytransfer.isEmpty()) {
                        throw new TransactionException("No transactions found for this current account.");
                    }

                    System.out.println("ATM Transactions for Current Account:");
                    for (Transaction transaction : account.trasactions) {
                        System.out.println(transaction);
                    }

                    System.out.println("Money Transfers for Current Account:");
                    for (Moneytrans transaction : account.moneytransfer) {
                        System.out.println(transaction);
                    }

                    isfound = true;
                    break;
                }
            }

            if (!isfound) {
                for (SavingAccount account : saving) {
                    if (Accnum == account.getAccountnumber()) {
                        // Check if transaction lists are empty
                        if (account.trasactions.isEmpty() && account.moneytransfer.isEmpty()) {
                            throw new TransactionException("No transactions found for this saving account.");
                        }

                        System.out.println("ATM Transactions for Saving Account:");
                        for (Object transaction : account.trasactions) {
                            System.out.println(transaction);
                        }

                        System.out.println("Money Transfers for Saving Account:");
                        for (Object transaction : account.moneytransfer) {
                            System.out.println(transaction);
                        }

                        isfound = true;
                        break;
                    }
                }
            }

            // If account number is not found in either list
            if (!isfound) {
                throw new AccountException("Incorrect Account Number. No matching account found.");
            }

        } catch (InputMismatchException e) {
            throw new TransactionException("Invalid input. Please enter a valid account number.");
        } catch (NoSuchElementException e) {
            throw new TransactionException("Input error. No account number provided.");
        }
    }

    public void requestcreditcard(ArrayList<CreditCard> card) throws TransactionException {
        try {
            if (card == null) {

                // Create a new credit card for the client using their client ID and full name
                CreditCard newcard = new CreditCard(this.client_id);
                // Add the newly created credit card to the list of cards
                card.add(newcard);
                // Print the details of the newly created credit card
                System.out.println(newcard);
            }
            else
                System.out.println("Already Have One");
        } catch (Exception e) {
            throw new TransactionException("Error requesting credit card: " + e.getMessage());
        }
    }




    public void disablecreditcard() throws TransactionException {
        Scanner s = new Scanner(System.in);

        try {
            if (this.creditCard == null ) {
                throw new TransactionException("No credit cards available.");
            }


                    this.creditCard.isActive = false;
                    System.out.println("Card disabled successfully.");


        } catch (InputMismatchException e) {
            throw new TransactionException("Invalid input. Please enter numeric values.");
        } catch (NoSuchElementException e) {
            throw new TransactionException("Incorrect Card Or Cvv");
        }
    }

    public void Accfuncs(ArrayList<Client> clients) throws TransactionException {
        // Account number to be used
        long accnum;
        // Index of the selected account
        int index = 0;
        // Acc Type
        char x = 'a';
        // Acc Foundd Or Not
        boolean isfound = false;
        Scanner s = new Scanner(System.in);

        System.out.println("Your Accounts :");
        try {

            this.displayaccountsinfo();

            // Prompt user to select an account
            System.out.println("Enter The Account You Want To Use : ");
            accnum = s.nextLong();

            // Search for account in current accounts list
            for (int i = 0; i < current.size(); i++) {
                if (current.get(i).getAccountnumber() == accnum) {
                    isfound = true;
                    index = i;
                    x = 'C'; // Mark as current account
                }
            }

            // If not found in current accounts, search in savings accounts
            if (!isfound) {
                for (int i = 0; i < saving.size(); i++) {
                    if (saving.get(i).getAccountnumber() == accnum) {
                        isfound = true;
                        index = i;
                        x = 'S'; // Mark as savings account
                    }
                }
            }

            // Throw exception if no matching account is found
            if (!isfound) {
                throw new AccountException("No account found with number " + accnum);
            }

            // If account is found, proceed with account operations
            if (isfound) {
                // Variable to store user's chosen action
                char c;
                // Prompt user for desired action
                System.out.println("Press(M:Money Transfer , D:Deposit , W:Withdraw ) : ");
                c = s.next().charAt(0);

                // Handle money transfer
                if (c == 'M' || c == 'm') {
                    if (x == 'S') {
                        saving.get(index).transfermoney(clients,"Client");
                    } else if (x == 'C') {
                        current.get(index).transfermoney(clients,"Client");
                    }
                }
                // Handle withdrawal
                else if (c == 'W' || c == 'w') {
                    if (x == 'S') {
                        saving.get(index).withdraw();
                    } else if (x == 'C') {
                        current.get(index).withdraw();
                    }
                }
                // Handle deposit
                else if (c == 'D' || c == 'd') {
                    if (x == 'S') {
                        saving.get(index).deposit();
                    } else if (x == 'C') {
                        current.get(index).deposit();
                    }
                }
                // Throw exception for invalid action
                else {
                    throw new AccountException("Invalid Action selected. Choose M, D, or W.");
                }
            }
        }
        catch (InputMismatchException e) {
            throw new TransactionException("Invalid input. Please enter a valid account number or action.");
        } catch (NoSuchElementException e) {
            throw new TransactionException("Input error. No account number or action provided.");
        } catch (EmptyAccountListException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AccountException | Account.TransferException | Account.NegativeAmountException | Account.InsufficientFundsException e) {
            System.out.println("Account Operation Error: " + e.getMessage());
        }
    }

    public void editPersonalInfo() throws AccountException {
        Scanner s = new Scanner(System.in);

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

            // If all validations pass, update the information
            this.firstName = newFirstName;
            this.lastName = newLastName;
            this.setUserName(newUsername);
            this.setPassword(newPassword);
            this.telephoneNumber = newTelephoneNumber;

            System.out.println("Personal information updated successfully.");

        } catch (InputMismatchException e) {
            throw new AccountException("Invalid input format. Please enter correct information.");
        } catch (NoSuchElementException e) {
            throw new AccountException("Input error. No information provided.");
        } catch (IllegalArgumentException e) {
            throw new AccountException("Validation Error: " + e.getMessage());
        }
    }

    public void showclientinfo() {
        try {
            // Validate critical fields before printing
            if (this.id == null) {
                throw new IllegalStateException("Client ID cannot be null");
            }

            if (this.firstName == null || this.firstName.trim().isEmpty()) {
                throw new IllegalArgumentException("First Name is required");
            }

            if (this.lastName == null || this.lastName.trim().isEmpty()) {
                throw new IllegalArgumentException("Last Name is required");
            }

            // Print client information with null checks
            System.out.println("ID : " + this.id);
            System.out.println("FirstName : " + this.firstName);
            System.out.println("LastName : " + this.lastName);

            // Add null check for username
            String username = this.getUserName();
            System.out.println("Username : " + (username != null ? username : "N/A"));

            // Avoid printing passwords directly - this is a security risk
            System.out.println("Password : [PROTECTED]");

            // Add null check for telephone number
            System.out.println("TelephoneNumber : " +
                    (this.telephoneNumber != null ? this.telephoneNumber : "Not Provided"));
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Log the error or handle it appropriately
            System.err.println("Error displaying client information: " + e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected exceptions
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }
}
