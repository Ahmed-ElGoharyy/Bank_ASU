package com.asu_bank.bank_asu.Model;

import java.util.Date;

public class Moneytrans extends Transaction{
    protected Long recieveraccnum ;




    public Moneytrans(){}
    public Long getRecieveraccnum() {
        return recieveraccnum;
    }
    public String getMade_by() {
        return made_by;
    }
    public Long getAccnumber() {
        return accnumber;
    }
    public Integer getTransid() {return transid;}
    public Double getAmount() {
        return this.amount;
    }
    public static int getCounter() {
        return counter;
    }
    public String getType() {
        return this.type;
    }
    public java.util.Date getDate() {
        return this.Date;
    }




    public Moneytrans(long senderaccnum, long recieveraccnum, double amount, String type, Date Date){
        super(senderaccnum,amount,type,Date);
        this.recieveraccnum=recieveraccnum;

    }
    public Moneytrans(long senderaccnum, long recieveraccnum, double amount, String type, Date Date,String madeby){
        super(senderaccnum,amount,type,Date);
        this.recieveraccnum=recieveraccnum;
        this.made_by=madeby;

    }

}
