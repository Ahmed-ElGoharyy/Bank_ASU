package com.asu_bank.bank_asu.Model;

public class SavingAccount extends Account {

    private final double interestRate = 0.05;





    public  SavingAccount( ){
        //for data loading
    }


    public double getInterestRate() {
        return interestRate;
    }


    public SavingAccount(double balance, Long ClientID){
        super(balance);
        super.AccountType="Saving";
        super.client_id = ClientID;
    }

    public void calculatinterestrate()
    {
        double interest = super.getBalance() + super.getBalance() * interestRate;
        super.setBalance(interest);
        System.out.println("Your yearly interest of 5 % applied.\nYour new balance is: " + super.getBalance());
    }



}
