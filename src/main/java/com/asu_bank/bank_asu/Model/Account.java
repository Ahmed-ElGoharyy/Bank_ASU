package com.asu_bank.bank_asu.Model;

import java.util.*;

abstract public class Account
{

 protected Long client_id;
 protected Long accountnumber;
 public List<Transaction> trasactions = new ArrayList<>();
 public List<Moneytrans> moneytransfer = new ArrayList<>();
 protected String AccountState;
 protected String AccountType;
 protected Double balance;
 public static Integer counter = 8020;

 public Account(){
  counter++;
  this.accountnumber = Long.valueOf(counter);
  this.AccountState = "InActive";// For data loading
 }

 public Long getClient_id() {
  return client_id;           // Dont change this
 }

 public List<Moneytrans> getMoneytransfer() {
  return moneytransfer;
 }

 public List<Transaction> getTrasactions() {
  return trasactions;
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

  this.balance = balance;
  this.AccountState = "InActive";
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

 public void deposit()  {
  double amount;
  Scanner s = new Scanner(System.in);
  System.out.println("Enter Amount Of Deposit : ");
  amount = s.nextDouble();


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

   // flag 3ashan y search 3ala receiver acc number
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

   // Search in saving accounts law mal2ahoush in current accounts
   for (Client clientObj : clients) {
    for (SavingAccount receiverAccount : clientObj.saving) {
     if (receiverAccount.getAccountnumber() == recaccnum) {
      performTransfer(receiverAccount, transamount,maker);
      isfound = true;
      return;
     }
    }
   }

   // law mal2ash acc number
   if (!isfound) {
    throw new TransferException("No account found with account number");
   }

  } catch (InputMismatchException e) {
   throw new TransferException("Invalid input. Please enter numeric values.");
  }
 }

 //  perform the actual transfer
 private void performTransfer(Account receiverAccount, double transamount, String maker) throws TransferException {
  try {
   this.balance -= transamount;

   receiverAccount.setBalance(receiverAccount.getBalance() + transamount);

   Date d = new Date();
   // Create transfer transaction
   Moneytrans trans = new Moneytrans(this.accountnumber, receiverAccount.getAccountnumber(), transamount, "Money Transfer",new Date());

   // Add transaction for both sender and receiver
   this.moneytransfer.add(trans);
   receiverAccount.moneytransfer.add(trans);

   System.out.printf("Transfer successful. Amount: $%.2f transferred to account %d%n", transamount, receiverAccount.getAccountnumber());

  } catch (Exception e) {
   throw new TransferException("Transfer failed: " + e.getMessage());
  }
 }
 public String getclientnamebyid( Long clientid,Bank bank){
  String name=new String();
  boolean isfound=false;
  for(Client client : bank.BankClients){
   if(clientid==client.getClient_id()){
    name= client.firstName+" "+client.lastName;
    isfound=true;
    break;
   }
  }
  if(!isfound) {
   name=null;
  }
  //lw raga3 null yebaa mala2aho4
  return name;
 }
}