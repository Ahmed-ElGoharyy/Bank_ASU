package com.asu_bank.bank_asu.Model;

import java.util.Date;

public class Transaction {
    protected Long accnumber;
    protected Integer transid;
    protected Double amount;
    public static int counter=0;
    protected String type;
    protected Date Date;
    protected String made_by;


    public Transaction(){
        counter++;
        transid=counter;
    }
    public Transaction(long accn,Double balance,String type,Date d,String madeby){
        counter++;
        transid=counter;
        this.accnumber=accn;
        this.amount=balance;
        this.type=type;
        this.Date=d;
        this.made_by=madeby;
    }




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

    public String getMade_by() { return made_by;}

    public Transaction(Long accnumber, double amount, String type, Date Date){
        counter++;
        this.transid=counter;
        this.accnumber=accnumber;
        this.amount=amount;
        this.type=type;
        this.Date =Date;
    }




    public Transaction( double amount, String type, Date Date){   // For credit card ( because no account is linked with it)
        counter++;
        this.transid=counter;
        this.amount=amount;
        this.type=type;
        this.Date =Date;
    }
}