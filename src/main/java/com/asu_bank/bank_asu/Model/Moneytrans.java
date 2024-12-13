package com.asu_bank.bank_asu.Model;

import java.util.Date;

public class Moneytrans extends Transaction{
    protected long recieveraccnum;
    protected String made_by;


    public Moneytrans(long senderaccnum, long recieveraccnum, double amount, String type, Date Date, String madeby){
        super(senderaccnum,amount,type,Date);
        this.recieveraccnum=recieveraccnum;
        this.made_by=madeby;
    }

}
