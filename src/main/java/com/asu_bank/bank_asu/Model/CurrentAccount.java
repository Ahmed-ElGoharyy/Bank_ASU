package com.asu_bank.bank_asu.Model;

public class CurrentAccount extends Account {
    private final double minimumBalance = 3000;
    public CurrentAccount(double balance){
        super(balance);
        super.AccountType="Current";
        checkminimumbalance();
    }


    public void checkminimumbalance() {
        if (super.getBalance() <= minimumBalance) {
            super.setBalance(super.getBalance() - 100);
            System.out.println("Balance Less Than Minimum Balance So Fees Will Be Applied. \n Your Current Balance is: " + super.getBalance());
        }
    }
}
