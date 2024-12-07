package com.asu_bank.bank_asu.Model;


import java.util.ArrayList;
import java.util.List;


public class Bank {
    protected static Bank instance;
    protected   List<Client> BankClients;
    protected   List<Employee> BankEmployees;



    private Bank() {
        BankClients = new ArrayList<>();
        BankEmployees = new ArrayList<>();
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }
}
