package com.asu_bank.bank_asu.Model;

import java.util.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract public class Account {

 private Long client_id;
 private  Long accountnumber;
 protected ArrayList<Transaction> trasactions = new ArrayList<>();
 protected ArrayList<Moneytrans> moneytransfer = new ArrayList<>();
 protected String AccountState;
 protected String AccountType;
 private Double balance;
 private static Integer counter = 7;

 public Account(){
                        // For data loading
 }

 public Long getClient_id() {
  return client_id;           // Dont change this
 }

 public String getAccountType() {
  return AccountType;       // dont change
 }

 public String getAccountState() {
  return AccountState;
 }

 public void setAccountState(String accountState) {
  AccountState = accountState;
 }

 public Account(double balance) {
  counter++;
  this.accountnumber = Long.valueOf(counter);
  ;
  this.balance = balance;
  this.AccountState = "Active";
 }

 public Long getAccountnumber() {
  return accountnumber;
 }

 public Double getBalance() {
  return balance;
 }

 public void setBalance(double balance) {
  this.balance = balance;
 }


 public static class NegativeAmountException extends Exception {
  public NegativeAmountException(String message) {
   super(message);
  }
 }

 public static class InsufficientFundsException extends Exception {
  public InsufficientFundsException(String message) {
   super(message);
  }
 }

 public void deposit() throws NegativeAmountException {
  double amount;
  Scanner s = new Scanner(System.in);
  System.out.println("Enter Amount Of Deposit : ");
  amount = s.nextDouble();
  // Check for negative or zero amount
  if (amount <= 0) {
   throw new NegativeAmountException("Invalid deposit amount. Amount must be positive");
  }

  // Perform deposit
  this.setBalance(this.getBalance() + amount);
  Date d = new Date();
  Transaction trans = new Transaction(this.accountnumber, amount, "Deposit",d );
  this.trasactions.add(trans);
  System.out.printf("Deposit successful. Amount: $%.2f. New balance: $%.2f%n", amount, this.getBalance());
 }

 public void withdraw() throws NegativeAmountException, InsufficientFundsException {
  double amount;
  Scanner s = new Scanner(System.in);
  System.out.println("Enter Amount Of Withdraw : ");
  amount = s.nextDouble();
  // Check for negative or zero amount
  if (amount <= 0) {
   throw new NegativeAmountException("Invalid withdrawal amount. Amount must be positive");
  }

  // Check for sufficient funds
  if (amount > this.getBalance()) {
   throw new InsufficientFundsException("Insufficient funds for withdrawal.");
  }

  // Perform withdrawal
  this.setBalance(this.getBalance() - amount);
  Date d = new Date();
  Transaction trans = new Transaction(this.accountnumber, amount, "Withdraw",d);
  this.trasactions.add(trans);
  System.out.printf("Withdrawal successful. Amount: $%.2f. New balance: $%.2f%n", amount, this.getBalance());
 }

 public static class TransferException extends Exception {
  public TransferException(String message) {
   super(message);
  }
 }

 public void transfermoney(ArrayList<Client> clients,String maker) throws TransferException,
         NegativeAmountException, InsufficientFundsException {

  // Validate input parameters
  if (clients == null || clients.isEmpty()) {
   throw new TransferException("No clients available for money transfer.");
  }

  Scanner s = new Scanner(System.in);

  try {
   // Prompt for receiver's account number
   System.out.println("Enter Receiver Account Number: ");
   long recaccnum = s.nextLong();

   // Prompt for transfer amount
   System.out.println("Enter Transfer Amount: ");
   double transamount = s.nextDouble();

   // Validate transfer amount
   if (transamount <= 0) {
    throw new NegativeAmountException(String.format(
            "Invalid transfer amount. Amount must be positive. Attempted transfer: $%.2f",
            transamount
    ));
   }

   // Check for sufficient funds
   if (transamount > this.balance) {
    throw new InsufficientFundsException(String.format(
            "Insufficient funds for transfer. Current balance: $%.2f, Attempted transfer: $%.2f",
            this.balance,
            transamount
    ));
   }

   // Flag to track if account is found
   boolean isfound = false;

   // Search in current accounts
   for (Client clientObj : clients) {
    for (CurrentAccount receiverAccount : clientObj.current) {
     if (receiverAccount.getAccountnumber() == recaccnum) {
      performTransfer(receiverAccount, transamount,maker);
      isfound = true;
      return;
     }
    }
   }

   // Search in saving accounts if not found in current accounts
   for (Client clientObj : clients) {
    for (SavingAccount receiverAccount : clientObj.saving) {
     if (receiverAccount.getAccountnumber() == recaccnum) {
      performTransfer(receiverAccount, transamount,maker);
      isfound = true;
      return;
     }
    }
   }

   // If no account found
   if (!isfound) {
    throw new TransferException("No account found with account number");
   }

  } catch (InputMismatchException e) {
   throw new TransferException("Invalid input. Please enter numeric values.");
  }
 }

 // Helper method to perform the actual transfer
 private void performTransfer(Account receiverAccount, double transamount, String maker) throws TransferException {
  try {
   // Deduct amount from sender's account
   this.balance -= transamount;

   // Add amount to receiver's account
   receiverAccount.setBalance(receiverAccount.getBalance() + transamount);

   Date d = new Date();
   // Create transfer transaction
   Moneytrans trans = new Moneytrans(this.accountnumber, receiverAccount.getAccountnumber(), transamount, "Money Transfer", d,maker);

   // Add transaction for both sender and receiver
   this.moneytransfer.add(trans);
   receiverAccount.moneytransfer.add(trans);

   System.out.printf("Transfer successful. Amount: $%.2f transferred to account %d%n", transamount, receiverAccount.getAccountnumber());

  } catch (Exception e) {
   throw new TransferException("Transfer failed: " + e.getMessage());
  }
 }
}