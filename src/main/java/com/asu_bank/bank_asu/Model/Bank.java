package com.asu_bank.bank_asu.Model;

import java.util.ArrayList;
import java.util.List;


public class Bank {

    public final String BankName = "ASU Bank";
    protected static Bank instance;
    protected   List<Client> BankClients;
    protected   List<Employee> BankEmployees;



           // Constructor Private to not make any objects (only by getInstace() below )

    private Bank() {
        BankClients = new ArrayList<>();
        BankEmployees = new ArrayList<>();
    }

    //       Singleton Design Pattern (DO NOT CHANGE THIS YA DAHY HAAA)

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }
}
