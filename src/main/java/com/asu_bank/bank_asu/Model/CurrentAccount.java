package com.asu_bank.bank_asu.Model;

public class CurrentAccount extends Account {


    private final double minimumBalance = 3000;
    private final double Fees = 100;


    public double getFees() {
        return Fees;
    }


    public double getMinimumBalance() {
        return minimumBalance;
    }


    public CurrentAccount(double balance, Long ClientID){
        super(balance);
        super.AccountType="Current";
        super.client_id = ClientID;

        checkminimumbalance();
    }

    public  CurrentAccount(){
        // For data loading
    }


    public void checkminimumbalance() {
        if (super.getBalance() < minimumBalance) {
            super.setBalance(super.getBalance() - Fees);
            System.out.println("Balance Less Than Minimum Balance So Fees Will Be Applied. \n Your Current Balance is: " + super.getBalance());
        }
    }
}
