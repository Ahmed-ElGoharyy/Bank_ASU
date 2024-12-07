package com.asu_bank.bank_asu.Model;
import java.util.ArrayList;
public class Admin extends Employee {
    protected ArrayList<Employee> employees = new ArrayList<>();
    protected ArrayList<Client> clients = new ArrayList<>();
    protected ArrayList<Transaction> transactions = new ArrayList<>();

    public Admin(long id, String firstName, String lastName, String userName, String password, long telephoneNumber, String address, String position, String graduatedCollege, int yearOfGraduation, char totalGrade) {
        super(id, firstName, lastName, userName, password, telephoneNumber, address, position, graduatedCollege, yearOfGraduation, totalGrade);
    }

    public void authorizeEmployeeAccounts(Employee employee) {
        try {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null.");
            }
            if (employees.contains(employee)) {
                throw new IllegalStateException("Employee account is already authorized: " + employee.getFirstName() + " " + employee.getLasttName());
            }
            employees.add(employee);
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
            if (employees.isEmpty()) {
                throw new NullPointerException("Employee not found.");
            }
            //Prints all employees in the ArrayList
            for (Employee employee : employees) {
                System.out.println(employee);
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

    public void showTransactions() {
        try {
            System.out.println("List of Transactions:");
            if (transactions.isEmpty()) {
                throw new NullPointerException("Client not found.");
            }
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } catch (Exception e) {
            System.err.println("An error occurred while showing transactions: " + e.getMessage());
        }

    }
}

