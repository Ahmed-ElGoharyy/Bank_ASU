package com.asu_bank.bank_asu.Model;

import java.util.Date;

public class Transaction {
    protected Long accnumber;
    protected Integer transid;
    protected Double amount;
    public static int counter=0;
    protected String type;
    protected Date Date;


    public Transaction(){}




    public Long getAccnumber() {
        return accnumber;
    }

    public Integer getTransid() {
        return transid;
    }

    public Double getAmount() {
        return amount;
    }

    public static int getCounter() {
        return counter;
    }

    public String getType() {
        return type;
    }

    public java.util.Date getDate() {
        return Date;
    }

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
      //  this.accnumber= 00000;
        this.amount=amount;
        this.type=type;
        this.Date =Date;
    }
}
