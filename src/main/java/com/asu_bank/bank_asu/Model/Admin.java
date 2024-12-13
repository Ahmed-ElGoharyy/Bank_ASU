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




    public void displayAllClients() {
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
            if (clients == null || clients.isEmpty()) {
                throw new IllegalArgumentException("No clients available for transaction search.");
            }

            char c;
            Scanner s = new Scanner(System.in);
            System.out.println("Press (C:Search By Client,E:Search By Employee) :");
            c = s.next().charAt(0);

            if (c == 'C' || c == 'c') {
                long id;
                System.out.println("Enter Client ID: ");
                id = s.nextLong();
                boolean clientFound = false;

                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).id == id) {
                        clientFound = true;

                        if (clients.get(i).current == null || clients.get(i).saving == null || clients.get(i).credittrans == null) {
                            throw new NullPointerException("Client account information is incomplete.");
                        }

                        for (int j = 0; j < clients.get(i).current.size(); j++) {
                            if (clients.get(i).current.get(j) != null && clients.get(i).current.get(j).trasactions != null) {
                                System.out.println(clients.get(i).current.get(j).trasactions);
                            }
                        }
                        for (int j = 0; j < clients.get(i).saving.size(); j++) {
                            if (clients.get(i).saving.get(j) != null && clients.get(i).saving.get(j).trasactions != null) {
                                System.out.println(clients.get(i).saving.get(j).trasactions);
                            }
                        }
                        for (int j = 0; j < clients.get(i).credittrans.size(); j++) {
                            if (clients.get(i).credittrans.get(j) != null) {
                                System.out.println(clients.get(i).credittrans);
                            }
                        }
                    }
                    break;
                }

                if (!clientFound) {
                    throw new NoSuchElementException("No client found with the given ID: " + id);
                }
            }
            else if (c == 'E' || c == 'e') {
                String empsearch;
                System.out.println("Enter Employee Name :");
                empsearch = s.next();
                boolean transactionFound = false;

                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).current != null) {
                        for (int j = 0; j < clients.get(i).current.size(); j++) {
                            if (clients.get(i).current.get(j) != null && clients.get(i).current.get(j).moneytransfer != null) {
                                for (int k = 0; k < clients.get(i).current.get(j).moneytransfer.size(); k++) {
                                    if (clients.get(i).current.get(j).moneytransfer.get(k).made_by.equals(empsearch)) {
                                        System.out.println(clients.get(i).current.get(j).moneytransfer.get(k));
                                        transactionFound = true;
                                    }
                                }
                            }

                            if (j < clients.get(i).saving.size() &&
                                    clients.get(i).saving.get(j) != null &&
                                    clients.get(i).saving.get(j).moneytransfer != null) {
                                for (int k = 0; k < clients.get(i).saving.get(j).moneytransfer.size(); k++) {
                                    if (clients.get(i).saving.get(j).moneytransfer.get(k).made_by.equals(empsearch)) {
                                        System.out.println(clients.get(i).saving.get(j).moneytransfer.get(k));
                                        transactionFound = true;
                                    }
                                }
                            }
                        }
                    }
                }

                if (!transactionFound) {
                    throw new NoSuchElementException("No transactions found for employee: " + empsearch);
                }
            }
            else {
                throw new IllegalArgumentException("Invalid search option. Use 'C' or 'E'.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct data type.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Null object encountered: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error accessing account or transaction information.");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
//    public void showTransactions() {
//        try {
//            System.out.println("List of Transactions:");
//            if (bank.BankClients.isEmpty()) {
//                throw new NullPointerException("Client not found.");
//            }
//            for (Transaction transaction : transactions) {
//                System.out.println(transaction);
//            }
//        } catch (Exception e) {
//            System.err.println("An error occurred while showing transactions: " + e.getMessage());
//        }
//
//    }
}

