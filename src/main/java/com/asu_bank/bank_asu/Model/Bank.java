package com.asu_bank.bank_asu.Model;

import java.util.ArrayList;
import java.util.List;


public class Bank {


    public final String BankName = "ASU Bank";
    protected static Bank instance;
    public List<Client> BankClients;
    public List<Employee> BankEmployees;
    public List<Transaction>  BankATMTrans;
    public List<Moneytrans>  BankMoneyTransfers;
    public List<SavingAccount>  BankSavingAccounts;
    public List<CurrentAccount> BankCurrentAccounts;



    
    public List<Employee> getBankEmployees() {
        return BankEmployees;
    }
    public List<Transaction> getBankATMTrans() {return BankATMTrans;}
    public List<Moneytrans> getBankMoneyTransfers() {return BankMoneyTransfers;}


    // Constructor Private to not make any objects except one "below" (only by getInstance() )

    private Bank() {
        BankClients = new ArrayList<>();
        BankEmployees = new ArrayList<>();



    }

    //       Singleton Design Pattern (DO NOT CHANGE THIS YA DAHY YA ASFAR HAAA)

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }


}
