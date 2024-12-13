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

    public void createClientBankAccount(ArrayList<Client> clients) {
        try {
            // Validate input parameters
            if (clients == null || clients.isEmpty()) {
                System.out.println("No clients available to create an account.");
                return;
            }

            // Input client ID
            long clientid;
            try {
                System.out.println("Enter Client ID:");
                clientid = s.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid client ID.");
                s.nextLine(); // Clear the invalid input
                return;
            }

            // Find client
            boolean clientFound = false;
            for (Client client : clients) {
                if (client.id == clientid) {
                    clientFound = true;

                    // Validate account details
                    try {
                        System.out.println("Enter the required details for the new account:");
                        System.out.println("Enter Account Type (C:Current, S:Saving):");

                        char c;
                        try {
                            c = s.next().charAt(0);
                        } catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Invalid account type. Please enter C or S.");
                            return;
                        }

                        System.out.println("Enter Account Balance:");
                        int bal;
                        try {
                            bal = s.nextInt();
                            if (bal < 0) {
                                throw new IllegalArgumentException("Initial balance cannot be negative.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid balance. Please enter a valid number.");
                            s.nextLine(); // Clear the invalid input
                            return;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            return;
                        }

                        // Create account based on type
                        if (c == 'c' || c == 'C') {
                            CurrentAccount curr = new CurrentAccount(bal);
                            client.current.add(curr);
                            System.out.println("Current Account Added Successfully");
                        } else if (c == 'S' || c == 's') {
                            SavingAccount sav = new SavingAccount(bal);
                            client.saving.add(sav);
                            System.out.println("Saving Account Added Successfully");
                        } else {
                            System.out.println("Invalid account type. Please choose C or S.");
                        }
                        break;

                    } catch (Exception e) {
                        System.out.println("Error creating account: " + e.getMessage());
                        return;
                    }
                }
            }

            // Handle case where client is not found
            if (!clientFound) {
                System.out.println("Client with ID " + clientid + " not found.");
            }

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void editAccountInfo(ArrayList<Client> clients) {
        try {
            // Validate input parameters
            if (clients == null || clients.isEmpty()) {
                System.out.println("No clients available to edit account information.");
                return;
            }

            // Input client ID
            long clientid;
            try {
                System.out.println("Enter Client ID:");
                clientid = s.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid client ID.");
                s.nextLine(); // Clear the invalid input
                return;
            }

            // Find client
            boolean clientFound = false;
            for (Client client : clients) {
                if (client.id == clientid) {
                    clientFound = true;

                    // Display current and saving accounts
                    System.out.println("Current Accounts:");
                    if (client.current == null || client.current.isEmpty()) {
                        System.out.println("No current accounts found.");
                    } else {
                        for (CurrentAccount curr : client.current) {
                            System.out.println(curr);
                        }
                    }

                    System.out.println("Saving Accounts:");
                    if (client.saving == null || client.saving.isEmpty()) {
                        System.out.println("No saving accounts found.");
                    } else {
                        for (SavingAccount sav : client.saving) {
                            System.out.println(sav);
                        }
                    }

                    // Input account number to modify
                    long accnum;
                    try {
                        System.out.println("Enter the Account Number You Want to Modify:");
                        accnum = s.nextLong();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid account number.");
                        s.nextLine(); // Clear the invalid input
                        return;
                    }

                    // Flag to track if account was modified
                    boolean accountModified = false;

                    // Check and modify current accounts
                    if (client.current != null) {
                        for (CurrentAccount curr : client.current) {
                            if (curr.getAccountnumber() == accnum) {
                                try {
                                    System.out.println("Enter New Balance:");
                                    int bal = s.nextInt();

                                    // Validate balance
                                    if (bal < 0) {
                                        throw new IllegalArgumentException("Balance cannot be negative.");
                                    }

                                    curr.setBalance(bal);
                                    System.out.println("Current Account Balance Updated Successfully");
                                    accountModified = true;
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a valid balance.");
                                    s.nextLine(); // Clear the invalid input
                                    return;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    return;
                                }
                            }
                        }
                    }

                    // If not modified in current accounts, try saving accounts
                    if (!accountModified && client.saving != null) {
                        for (SavingAccount sav : client.saving) {
                            if (sav.getAccountnumber() == accnum) {
                                try {
                                    System.out.println("Enter New Balance:");
                                    int bal = s.nextInt();

                                    // Validate balance
                                    if (bal < 0) {
                                        throw new IllegalArgumentException("Balance cannot be negative.");
                                    }

                                    sav.setBalance(bal);
                                    System.out.println("Saving Account Balance Updated Successfully");
                                    accountModified = true;
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a valid balance.");
                                    s.nextLine(); // Clear the invalid input
                                    return;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    return;
                                }
                            }
                        }
                    }

                    // Provide feedback if no matching account found
                    if (!accountModified) {
                        System.out.println("No account found with number: " + accnum);
                    }

                    break;
                }
            }

            // Handle case where client is not found
            if (!clientFound) {
                System.out.println("Client with ID " + clientid + " not found.");
            }

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void removeAccount(ArrayList<Client> clients) {

        try {
            System.out.println("Enter the Account Number you want to remove: ");
            long accountNumber = s.nextLong();
            if (accountNumber <= 0) {
                throw new Client.AccountException("Invalid account number");
            }

            boolean isfound = false;

            // search in current accounts
            for(int i=0;i<clients.size();i++) {
                for (CurrentAccount account : clients.get(i).current) {
                    if (account.getAccountnumber() == accountNumber) {
                        clients.get(i).current.remove(account);
                        isfound = true;
                        System.out.println("Account removed successfully from Current Accounts.");
                        break;
                    }
                }
            }
            //search in saving accounts
            if (!isfound) {
                for(int i=0;i<clients.size();i++) {
                    for (SavingAccount account : clients.get(i).saving) {
                        if (account.getAccountnumber() == accountNumber) {
                            clients.get(i).saving.remove(account);
                            isfound = true;
                            System.out.println("Account removed successfully from Saving Accounts.");
                            break;
                        }
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

    public void searchClient(ArrayList<Client> clients) throws Exception {
        Scanner s = new Scanner(System.in);
        try {
            boolean isFound = false;
            System.out.println("Search by:\n1. Client ID\n2. Full Name (First Name and Last Name)\n3. Username");
            int choice = s.nextInt();
            s.nextLine(); // Consume the newline character

            if (choice == 1) {
                System.out.println("Enter client ID: ");
                long client_id = s.nextLong();

                if (client_id <= 0) {
                    throw new Exception("Invalid Client ID!");
                }

                for (Client client : clients) {
                    if (client_id == client.getClient_id()) {
                        isFound = true;
                        System.out.println(client);
                    }
                }

                if (!isFound) {
                    throw new Exception("Client ID not found!");
                }

            } else if (choice == 2) {
                System.out.println("Enter first name: ");
                String firstName = s.nextLine();

                System.out.println("Enter last name: ");
                String lastName = s.nextLine();

                for (Client client : clients) {
                    if (client.getFirstName().equalsIgnoreCase(firstName) && client.getLastName().equalsIgnoreCase(lastName)) {
                        isFound = true;
                        System.out.println(client);
                    }
                }

                if (!isFound) {
                    throw new Exception("No client found with the provided first and last name!");
                }

            } else if (choice == 3) {
                System.out.println("Enter username: ");
                String userName = s.nextLine();

                for (Client client : clients) {
                    if (client.getUserName().equalsIgnoreCase(userName)) {
                        isFound = true;
                        System.out.println(client);
                    }
                }

                if (!isFound) {
                    throw new Exception("Username not found!");
                }

            } else {
                throw new Exception("Invalid choice! Please select 1, 2, or 3.");
            }

        } catch (Exception e) {
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

    public void maketrans(ArrayList<Client> clients) throws  Account.TransferException {
        try {
            System.out.println("Enter Sender Acc Num : ");
            long sendaccnum = s.nextLong();

            boolean isfound = false;

            // Check if the account number is valid (positive)
            if (sendaccnum <= 0) {
                throw new IllegalArgumentException("Invalid account number. Account number must be a positive number.");
            }

            // Search in current accounts
            for (Client sender : clients) {
                for (CurrentAccount curr : sender.current) {
                    if (curr.getAccountnumber() == sendaccnum) {
                            curr.transfermoney(clients, this.firstName + this.lastName);
                            isfound = true;
                            return; // Exit method after successful transfer
                    }
                }

                // If not found in current accounts, search in saving accounts
                if (!isfound) {
                    for (SavingAccount sav : sender.saving) {
                        if (sav.getAccountnumber() == sendaccnum) {
                                sav.transfermoney(clients, this.firstName +" "+ this.lastName);
                                isfound = true;
                                return; // Exit method after successful transfer
                        }
                    }
                }
            }

            // If no account is found
            if (!isfound) {
                throw new IllegalArgumentException("No account found with the given account number: " + sendaccnum);
            }
        } catch (InputMismatchException e) {
            // Handle invalid input (non-numeric input)
            System.err.println("Invalid input. Please enter a valid account number.");
            s.nextLine(); // Clear the invalid input
            throw new IllegalArgumentException("Invalid account number input", e);
        } catch (Exception e) {
            // Catch any unexpected exceptions
            System.err.println("An unexpected error occurred during transfer: " + e.getMessage());
            throw new Account.TransferException("Transfer failed due to an unexpected error");
        }
    }

}





