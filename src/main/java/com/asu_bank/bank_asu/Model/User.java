package com.asu_bank.bank_asu.Model;

import java.util.Scanner;

abstract public class User {

    protected long id;
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String password;
    protected long telephoneNumber;
    User employeesArr[] = new User[10];
    User clientsArr[] = new User[10];
    User adminsArr[] = new User[10];


    public User(long id, String firstName, String lastName,String userName, String password, long telephoneNumber) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.telephoneNumber = telephoneNumber;

    }


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLasttName() {
        return lastName;
    }

    public String getUsertName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public long getTelephoneNumber() {
        return telephoneNumber;
    }


    public abstract void editPersonalInfo();

    public abstract void transferMoney();

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
