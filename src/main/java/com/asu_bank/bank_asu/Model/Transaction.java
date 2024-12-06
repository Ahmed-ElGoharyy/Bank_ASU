package com.asu_bank.bank_asu.Model;

public class Transaction {
    protected long accnumber;
    private int transid;
    protected double amount;
    public static int counter=0;
    protected String type;

    public Transaction(long accnumber, double amount, String type){
        counter++;
        this.transid=counter;
        this.accnumber=accnumber;
        this.amount=amount;
        this.type=type;
    }
}
