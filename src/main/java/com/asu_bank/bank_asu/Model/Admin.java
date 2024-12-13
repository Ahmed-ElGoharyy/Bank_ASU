package com.asu_bank.bank_asu.Model;
import java.util.ArrayList;
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

