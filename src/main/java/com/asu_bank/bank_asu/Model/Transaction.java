package com.asu_bank.bank_asu.Model;

import java.util.Date;

public class Transaction {
    protected long accnumber;
    private int transid;
    protected double amount;
    public static int counter=0;
    protected String type;
    protected Date Date;

    public Transaction(long accnumber, double amount, String type, Date Date){
        counter++;
        this.transid=counter;
        this.accnumber=accnumber;
        this.amount=amount;
        this.type=type;
        this.Date =Date;
    }
    public Transaction( double amount, String type, Date Date){
        counter++;
        this.transid=counter;
        this.accnumber=00000;
        this.amount=amount;
        this.type=type;
        this.Date =Date;
    }
}
