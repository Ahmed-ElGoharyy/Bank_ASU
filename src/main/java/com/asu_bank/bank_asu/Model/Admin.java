package com.asu_bank.bank_asu.Model;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Admin extends Employee {

    protected static Admin instance;
    Bank bank = Bank.getInstance();

    //singleton design pattern (because there is only 1 admin)

    private Admin( String firstName, String lastName,
                   String userName, String password, long telephoneNumber,
                   String address, String position, String graduatedCollege,
                   int yearOfGraduation, char totalGrade) {

        super( firstName, lastName, userName, password, telephoneNumber,
                address, position, graduatedCollege, yearOfGraduation, totalGrade);
    }

    //       Singleton Design Pattern (DO NOT CHANGE THIS YA DAHY YA ASFAR HAAA)

    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin( "Amr","Ahmed","admin","admin"
                    ,01111122454, "10 Wow st","System Admin","Cairo",
                    2010,'C');
        }
        return instance;
    }


    public void authorizeEmployeeAccounts(Employee employee) {
        try {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null.");
            }
            if (bank.BankEmployees.contains(employee)) {
                throw new IllegalStateException("Employee account is already authorized: " + employee.getFirstName() + " " + employee.getLasttName());
            }
            bank.BankEmployees.add(employee);
            System.out.println("Employee account authorized for: " + employee.getFirstName() + " " + employee.getLasttName());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error authorizing employee account: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    public void displayAllEmployees() {
        try {
            System.out.println("List of Employees:");
            if (bank.BankEmployees.isEmpty()) {
                throw new NullPointerException("Employee not found.");
            }
            //Prints all employees in the ArrayList
            for (Employee employee : bank.BankEmployees) {
                System.out.println( "Name :" +employee.firstName+ "Position : " +employee.position);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying employees: " + e.getMessage());
        }
    }

    public void displayAllClients(ArrayList<Client> clients) {
        try {
            System.out.println("List of Clients:");
            if (clients.isEmpty()) {
                throw new NullPointerException("Client not found.");
            }
            //Prints all client in the ArrayList
            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (Exception e) {
            System.err.println("An error occurred while displaying clients: " + e.getMessage());
        }
    }

    public void showTransactions(ArrayList<Client> clients) {
        try {

            Scanner s = new Scanner(System.in);
            System.out.println("Press (C:Search By Client, E:Search By Employee):");

            // Validate search type input
            char c;
            try {
                c = s.next().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid input. Please enter a valid search option.");
            }

            // Client search
            if (c == 'C' || c == 'c') {
                // Validate and retrieve client ID
                long id;
                try {
                    System.out.println("Enter Client ID: ");
                    id = s.nextLong();
                } catch (InputMismatchException e) {
                    s.nextLine(); // Clear invalid input
                    throw new IllegalArgumentException("Invalid Client ID. Please enter a numeric value.");
                }

                boolean clientFound = false;

                // Enhanced client search with comprehensive error checking
                for (Client client : clients) {
                    if (client.id == id) {
                        clientFound = true;

                        if (client.current == null || client.saving == null || client.credittrans == null) {
                            throw new NullPointerException("Client account information is incomplete.");
                        }

                        // Print transactions with null checks
                        printClientTransactions(client);
                        break;
                    }
                }

                // Check if client was found
                if (!clientFound) {
                    throw new NoSuchElementException("No client found with the given ID: " + id);
                }
            }
            // Employee search
            else if (c == 'E' || c == 'e') {
                // Input employee name
                String empFirstName, empLastName, empSearch;
                try {
                    System.out.println("Enter Employee First Name:");
                    empFirstName = s.next();

                    System.out.println("Enter Employee Last Name:");
                    empLastName = s.next();

                    empSearch = empFirstName + " " + empLastName;
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid employee name input.");
                }

                boolean transactionFound = false;

                // Search through clients and accounts
                for (Client client : clients) {
                    // Null checks for current accounts
                    if (client.current != null) {
                        for (CurrentAccount currentAccount : client.current) {
                            if (currentAccount != null && currentAccount.moneytransfer != null) {
                                for (Moneytrans transfer : currentAccount.moneytransfer) {
                                    if (transfer != null && empSearch.equals(transfer.made_by)) {
                                        System.out.println(transfer);
                                        transactionFound = true;
                                    }
                                }
                            }
                        }
                    }

                    // Null checks for saving accounts
                    if (client.saving != null) {
                        for (SavingAccount savingAccount : client.saving) {
                            if (savingAccount != null && savingAccount.moneytransfer != null) {
                                for (Moneytrans transfer : savingAccount.moneytransfer) {
                                    if (transfer != null && empSearch.equals(transfer.made_by)) {
                                        System.out.println(transfer);
                                        transactionFound = true;
                                    }
                                }
                            }
                        }
                    }
                }

                // Check if any transaction was found
                if (!transactionFound) {
                    throw new NoSuchElementException("No transactions found for employee: " + empSearch);
                }
            }
            // Invalid search option
            else {
                throw new IllegalArgumentException("Invalid search option. Use 'C' or 'E'.");
            }
        }
        // Comprehensive exception handling
        catch (InputMismatchException e) {
            System.err.println("Input error: Please enter the correct data type.");
        } catch (IllegalArgumentException e) {
            System.err.println("Input validation error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null object error: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index error: Unable to access account or transaction information.");
        } catch (NoSuchElementException e) {
            System.err.println("Search error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    private void printClientTransactions(Client client) {
        // Print current account transactions
        for (Account currentAccount : client.current) {
            if (currentAccount != null && currentAccount.trasactions != null) {
                System.out.println(currentAccount.trasactions);
            }
        }


        // Print saving account transactions
        for (Account savingAccount : client.saving) {
            if (savingAccount != null && savingAccount.trasactions != null) {
                System.out.println(savingAccount.trasactions);
            }
        }

        // Print credit transactions
        for (Object creditTransaction : client.credittrans) {
            if (creditTransaction != null) {
                System.out.println(creditTransaction);
            }
        }
    }
}


