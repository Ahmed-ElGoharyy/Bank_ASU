package com.asu_bank.bank_asu.Model;

public class CreditCard {
    private final String cardNumber;
    private final int cvv;
    public boolean isActive;
    protected double loyaltyPoints = 0;
    public static final double limit = 20000;
    protected double spending = 0;
    private final long client_id;
    public String cardholdername;


// Constructor with validation

    public CreditCard(long id, String holdername){
        this.client_id=id;
        this.cardholdername=holdername;
        this.isActive=true;
        // Generate Random Card Number
        String part1;
        part1="2879 4687 3279 ";
        int cardmax=9999,cardmin=1000;
        int part2=cardmin + (int)(Math.random() * ((cardmax - cardmin) + 1));
        this.cardNumber=part1 + part2;
        // Generate Random Cvv
        int cvvmax=999,cvvmin=100;
        this.cvv=cvvmin + (int)(Math.random() * ((cvvmax - cvvmin) + 1));
    }

    // Custom exception class
    public static class CreditCardException extends Exception {
        public CreditCardException(String message) {
            super(message);
        }
    }



    // Payment method with enhanced error handling
    public void pay(double amount) throws CreditCardException {
        // Check if card is active
        if (!isActive) {
            throw new CreditCardException("Card is not active");
        }

        // Validate payment amount
        if (amount <= 0) {
            throw new CreditCardException("Invalid payment amount");
        }

        // Check credit limit
        if (spending + amount > limit) {
            throw new CreditCardException("Credit card limit exceeded");
        }

        // Process payment
        spending += amount;
        loyaltyPoints += spending * 0.1;
        System.out.println("Payment successful!");
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
}
