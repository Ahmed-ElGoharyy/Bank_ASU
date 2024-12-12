package com.asu_bank.bank_asu.Model;

import java.util.Scanner;

abstract public class User {

    protected  Long id;
    protected String firstName;
    protected String lastName;
    private String userName;
    private String password;
    protected Long telephoneNumber;
    private static int counter=0;
    User employeesArr[] = new User[10];
    User clientsArr[] = new User[10];
    User adminsArr[] = new User[10];

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User() {
                // DONT CHANGE THIS !!!!!!!!!!! (needed for data loading)
    }
    public User( String firstName, String lastName, String userName, String password, long telephoneNumber) {
        counter++;
        this.id = Long.valueOf(counter);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.telephoneNumber = telephoneNumber;

    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLasttName() {
        return lastName;
    }



    public String getPassword() {
        return password;
    }

    public long getTelephoneNumber() {
        return telephoneNumber;
    }


    public abstract void editPersonalInfo() throws Client.AccountException;

    // Validate Name
    protected String validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }

        // Check if name contains only letters and spaces
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new IllegalArgumentException(fieldName + " should contain only letters.");
        }

        // Trim and capitalize first letter
        return name.trim().substring(0, 1).toUpperCase() + name.trim().substring(1).toLowerCase();
    }

    // Validate Username
    protected String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        // Username must be at least 3 characters long and contain only letters, numbers, and underscores
        if (!username.matches("^[a-zA-Z0-9_]{3,}$")) {
            throw new IllegalArgumentException("Username must be at least 3 characters long and contain only letters, numbers, and underscores.");
        }

        return username.trim();
    }

    // Validate Password
    protected String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        // Password must be at least 8 characters long, contain at least one uppercase, one lowercase, and one number
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
        }

        return password.trim();
    }

    // Validate Telephone Number
    protected long validateTelephoneNumber(String telephoneNumberStr) {
        // Remove any non-digit characters
        String cleanedNumber = telephoneNumberStr.replaceAll("[^\\d]", "");

        if (cleanedNumber.isEmpty()) {
            throw new IllegalArgumentException("Telephone number cannot be empty.");
        }

        try {
            long telephoneNumber = Long.parseLong(cleanedNumber);

            // Basic validation for telephone number length (adjust as needed)
            if (cleanedNumber.length() < 10 || cleanedNumber.length() > 15) {
                throw new IllegalArgumentException("Invalid telephone number length.");
            }

            return telephoneNumber;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid telephone number format.");
        }
    }

    public void login() {

        Scanner input = new Scanner(System.in);
        boolean authenticator = false;

        while (!authenticator) {
            System.out.print("Enter Username: ");
            String username = input.nextLine();
            System.out.print("Enter Password: ");
            String Password = input.nextLine();

            for (int i = 0; i < employeesArr.length; i++) {

                if (employeesArr[i].userName.equals(username) && employeesArr[i].password.equals(Password)) {

                    authenticator = true;
                    System.out.println("You are successfully logged in!");
                    break;
                } else if (clientsArr[i].userName.equals(username) && clientsArr[i].password.equals(Password)) {
                    authenticator = true;
                    System.out.println("You are successfully logged in!");
                    break;
                } else if (adminsArr[i].userName.equals(username) && adminsArr[i].password.equals(Password)) {
                    authenticator = true;
                    System.out.println("You are successfully logged in!");
                    break;
                } else {
                    System.out.println("Invalid username or password, please try again");

                }


            }


        }


    }

}
