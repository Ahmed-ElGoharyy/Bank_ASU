package com.asu_bank.bank_asu.Model;

import java.util.Date;

public class Moneytrans extends Transaction{
    protected long recieveraccnum;

    public Moneytrans(long senderaccnum, long recieveraccnum, double amount, String type, Date Date){
        super(senderaccnum,amount,type,Date);
        this.recieveraccnum=recieveraccnum;
    }

}
