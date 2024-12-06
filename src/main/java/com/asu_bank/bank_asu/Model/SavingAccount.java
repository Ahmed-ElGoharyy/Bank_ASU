package com.asu_bank.bank_asu.Model;

public class SavingAccount extends Account {
    private final double interestRate = 0.05;

    public SavingAccount(double balance){
        super(balance);
        super.AccountType="Saving";
    }

    public void calculatinterestrate()
    {
        double interest = super.getBalance() + super.getBalance() * interestRate;
        super.setBalance(interest);
        System.out.println("Your yearly interest of 5 % applied.\nYour new balance is: " + super.getBalance());
    }



}
