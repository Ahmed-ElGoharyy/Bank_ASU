package com.asu_bank.bank_asu.Model;

public class CreditCard {
    private final String cardNumber;
    private final int cvv;
    public boolean isActive;
    protected double loyaltyPoints = 0;
    protected static double limit = 20000;
    protected double spending = 0;
    private long client_id;


    static int  cvvmax=999;
    static int cvvmin=100;
    static int cardcounter=1000 ;

    public CreditCard(long id ){
        this.client_id=id;
        this.isActive=true;
        // Generate Random Card Number
        String part1;
        part1="7894 1547 2355 ";
        this.cardNumber=part1 + cardcounter;
        cardcounter++;
        // Generate Random Cvv

        this.cvv=cvvmin + (int)(Math.random() * ((cvvmax - cvvmin) + 1));
    }


    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        CreditCard.limit = limit;
    }

    public int getCvv() {
        return cvv;
    }
    public double getSpending() {
        return spending;
    }
    public long getClient_id() {
        return client_id;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }
    public boolean isActive() {return isActive;}



    public void setLoyaltyPoints(double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
    public void setSpending(double spending) {
        this.spending = spending;
    }
    public void setActive(boolean active) {isActive = active;}
    public void setClient_id(long client_id) {this.client_id = client_id;}
}
