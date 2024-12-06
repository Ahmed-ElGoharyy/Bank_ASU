package com.asu_bank.bank_asu.Model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends User {
    private final long client_id;
    protected ArrayList<CurrentAccount> current = new ArrayList<>();
    protected ArrayList<SavingAccount> saving = new ArrayList<>();

    public Client(long id, String firstName, String lastName, String userName, String password, long telephoneNumber) {
        super( firstName, lastName, userName, password, telephoneNumber);
        this.client_id = super.getId();
    }
    public long getClient_id() {return client_id;}

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

        // Check for empty lists
        boolean isCurrentEmpty = current.isEmpty();
        boolean isSavingEmpty = saving.isEmpty();

        if (isCurrentEmpty && isSavingEmpty) {
            throw new EmptyAccountListException("No accounts found. Both current and saving account lists are empty.");
        }

        try {
            // Display current accounts
            if (!isCurrentEmpty) {
                System.out.println("Current Accounts:");
                for (CurrentAccount account : current) {
                    if (account != null) {
                        System.out.println(account);
                    }
                }
            }

            // Display saving accounts
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
            // First, ensure accounts exist
            displayaccountsinfo();

            // Prompt for account number
            System.out.println("Choose The Account You Want: ");
            long Accnum = s.nextLong();

            // Flag to track if account is found
            boolean isfound = false;

            // Search in current accounts
            for (CurrentAccount account : current) {
                if (Accnum == account.getAccountnumber()) {
                    // Check if transaction lists are empty
                    if (account.trasactions.isEmpty() && account.moneytransfer.isEmpty()) {
                        throw new TransactionException("No transactions found for this current account.");
                    }

                    System.out.println("ATM Transactions for Current Account:");
                    for (Object transaction : account.trasactions) {
                        System.out.println(transaction);
                    }

                    System.out.println("Money Transfers for Current Account:");
                    for (Object transaction : account.moneytransfer) {
                        System.out.println(transaction);
                    }

                    isfound = true;
                    break;
                }
            }

            // If not found in current accounts, search in saving accounts
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
            // Check if the provided credit card list is null
            if (card == null) {
                throw new TransactionException("Credit card list is null.");
            }
            // Create a new credit card for the client using their client ID and full name
            CreditCard newcard = new CreditCard(this.client_id, this.firstName + " " + this.lastName);
            // Add the newly created credit card to the list of cards
            card.add(newcard);
            // Print the details of the newly created credit card
            System.out.println(newcard);
        } catch (Exception e) {
            throw new TransactionException("Error requesting credit card: " + e.getMessage());
        }
    }

    public void paywithcreditcard(ArrayList<CreditCard> card) throws TransactionException {
        Scanner s = new Scanner(System.in);

        try {
            // Validate card list
            if (card == null || card.isEmpty()) {
                throw new TransactionException("No credit cards available.");
            }

            // Get payment amount
            System.out.println("Enter The Amount You Want To Pay : ");
            int n = s.nextInt();

            // Show client's cards
            System.out.println("Your Cards : ");
            boolean hasClientCards = false;
            for (CreditCard c : card) {
                if (c.getClient_id() == this.client_id) {
                    System.out.println(c);
                    hasClientCards = true;
                }
            }

            if (!hasClientCards) {
                throw new TransactionException("No credit cards found for this client.");
            }

            // Get card details
            System.out.println("Enter The Card You Want To Pay With : ");
            String cardnum = s.next();
            System.out.println("Enter Cvv : ");
            int cvv = s.nextInt();

            for (CreditCard c : card) {
                if (cardnum.equals(c.getCardNumber()) &&
                        cvv == c.getCvv() &&
                        c.getClient_id() == this.client_id) {
                    if(n<0)
                    {
                        throw new CreditCard.CreditCardException("Invalid Amount,Please Enter Positive One");
                    }
                    c.pay(n);
                    break;
                }
            }

        } catch (InputMismatchException e) {
            throw new TransactionException("Invalid input. Please enter numeric values.");
        } catch (NoSuchElementException e) {
            throw new TransactionException("Incorrect Card Or Cvv");
        } catch (CreditCard.CreditCardException e) {
            throw new TransactionException("Credit card payment failed: " + e.getMessage());
        }
    }

    public void disablecreditcard(ArrayList<CreditCard> card) throws TransactionException {
        Scanner s = new Scanner(System.in);

        try {
            // Validate card list
            if (card == null || card.isEmpty()) {
                throw new TransactionException("No credit cards available.");
            }

            // Show client's cards
            System.out.println("Your Cards : ");
            boolean hasClientCards = false;
            for (CreditCard c : card) {
                if (c.getClient_id() == this.client_id) {
                    System.out.println(c);
                    hasClientCards = true;
                }
            }

            if (!hasClientCards) {
                throw new NoSuchElementException("No credit cards found for this client.");
            }

            // Get card to disable
            System.out.println("Enter The Card You Want To Disable : ");
            String cardnum = s.next();

            boolean iscorrect = false;
            for (CreditCard c : card) {
                if (cardnum.equals(c.getCardNumber()) && c.getClient_id() == this.client_id) {
                    iscorrect = true;
                    c.isActive = false;
                    System.out.println("Card disabled successfully.");
                    break;
                }
            }


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
        // Flag to determine account type (a: unset, C: current, S: saving)
        char x = 'a';
        // Flag to track if an account is found
        boolean isfound = false;
        // Scanner for user input
        Scanner s = new Scanner(System.in);

        // Display available accounts to the user
        System.out.println("Your Accounts :");
        try {
            // Show account information
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
                        saving.get(index).transfermoney(clients);
                    } else if (x == 'C') {
                        current.get(index).transfermoney(clients);
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
}
