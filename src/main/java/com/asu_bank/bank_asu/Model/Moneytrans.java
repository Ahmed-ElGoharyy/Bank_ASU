package com.asu_bank.bank_asu.Model;

public class Moneytrans extends Transaction{
    protected long recieveraccnum;

    public Moneytrans(long senderaccnum, long recieveraccnum, double amount, String type){
        super(senderaccnum,amount,type);
        this.recieveraccnum=recieveraccnum;
    }

}
