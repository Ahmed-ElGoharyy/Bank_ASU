package com.asu_bank.bank_asu.Classes;

public class creditCard {
    protected int cardNumber;
    private int cvv;
    public boolean isActive;
    protected double loyaltyPoints = 0;
    public static final double limit = 20000;
    protected double spending = 0;

    // Constructor with validation

    public creditCard(int cardNumber, int cvv, boolean isActive) {
        if (!isValidCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Invalid card number");
        }
        if (!isValidCVV(cvv)) {
            throw new IllegalArgumentException("Invalid CVV");
        }
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.isActive = isActive;
    }

    // Card number validation method
    private boolean isValidCardNumber(int cardNumber) {
        // Basic validation: check length and non-zero
        return cardNumber > 0 ;
    }

    // CVV validation method
    private boolean isValidCVV(int cvv) {
        // CVV should be 3
        return cvv > 99 && cvv < 1000;
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


    // if you want to de activate card

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getCvv() {
        return cvv;
    }


    // Getters for other attributes
    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public double getSpending() {
        return spending;
    }
}
