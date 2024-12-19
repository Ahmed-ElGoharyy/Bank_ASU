package com.asu_bank.bank_asu.Model;

public class CreditCard {
    private final String cardNumber;
    private final int cvv;
    public boolean isActive;
    protected double loyaltyPoints = 0;
    protected static final double limit = 20000;
    protected double spending = 0;
    private long client_id;



// Constructor with validation

    public CreditCard(long id ){
        this.client_id=id;
        this.isActive=true;
        // Generate Random Card Number
        String part1;
        part1="1111 1111 1111 ";
        int cardmax=9999,cardmin=1000;
        this.cardNumber=part1 +cardmin;
        cardmin++;

        // Generate Random Cvv
        int cvvmax=999,cvvmin=100;
        this.cvv=cvvmin + (int)(Math.random() * ((cvvmax - cvvmin) + 1));
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
