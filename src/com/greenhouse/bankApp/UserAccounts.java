package com.greenhouse.bankApp;

import java.text.DecimalFormat;

public class UserAccounts {
	private final CurrentAccount currentAccount;
	private final SavingsAccount savingsAccount;
	private final DecimalFormat df = new DecimalFormat("0.00");

	public CurrentAccount getCurrentAccount()
	{
		return currentAccount;
	}

	public SavingsAccount getSavingsAccount()
	{
		return savingsAccount;
	}

	public UserAccounts(CurrentAccount currentAccount, SavingsAccount savingsAccount)
	{
		this.currentAccount = currentAccount;
		this.savingsAccount = savingsAccount;
	}

	public void receiveMoneyFromSavings(double amount, String ref, CurrentAccount object)
	{
		object.getTransactions().add(new Transaction(ref, amount));
		object.addToBalance(amount);
		System.out.println("You've received R" + df.format(amount) + " from your Savings Account");
		System.out.println("Balance is now: " + object.getBalance());
	}

	public void sendMoneyToSavings(double amount, String ref, CurrentAccount object){
		if(object.getBalance() > amount){
			Transaction ts = new Transaction(ref, amount);
			object.getTransactions().add(ts);
			object.subtractFromBalance(amount);
			receiveMoneyFromCurrent(amount, ref, object);
			System.out.printf("You have moved R" + df.format(amount) + " to your Savings Account.");
		}else{
			System.out.println("You don't have enough money to make this transaction!");
		}
	}

	public void sendMoneyToAnotherCA(double amount, String ref, CurrentAccount object, Bank bank, String myAccountNumber) {
		if(object.getAccountNumber().length() != 12){
			System.out.println("Please provide a valid Account Number");
			return;
		}

		UserAccounts myAccount = bank.findClient(myAccountNumber);

		if(amount < 0){
			System.out.println("Please provide a valid amount to deposit");
			return;
		}else if(amount > myAccount.getCurrentAccount().getBalance()){
			System.out.println("You don't have enough money to make this transaction.");
			return;
		}

		boolean isCAAccount = object.getAccountNumber().substring(0,2).equalsIgnoreCase("CA");
		if(!isCAAccount){
			System.out.println("We can't make a transaction to a Savings Account");
		}else{
			object.getTransactions().add(new Transaction(ref, amount));
			double charge = amount * 0.05;
			myAccount.getCurrentAccount().subtractFromBalance(amount+charge);
			object.addToBalance(amount);
			object.getTransactions().add(new Transaction(ref + ": " + myAccountNumber, amount));
			System.out.println("You've managed to send R" + df.format(amount) + " successfully to: " + object.getAccountNumber());
			System.out.println("Your balance is now: " + df.format(myAccount.getCurrentAccount().getBalance()));
		}
	}

	public UserAccounts findWithSavingsAccount(SavingsAccount object, Bank bank){
		UserAccounts client = bank.getClient(object.getIdNumber());
		if(client == null) {
			System.out.println("Error, client doesn't exist");
			return null;
		}
		return client;
	}

	public UserAccounts findWithCurrentAccount(CurrentAccount object){
		UserAccounts client = new Bank().getClient(object.getIdNumber());
		if(client == null) {
			System.out.println("Error, client doesn't exist");
			return null;
		}
		return client;
	}

	public void sendMoneyToCurrent(double amount, String ref, SavingsAccount object, Bank bank){
		if(amount < object.getBalance()){
			object.subtractFromBalance(amount);
			UserAccounts client = findWithSavingsAccount(object, bank);
			if(client != null){
				receiveMoneyFromSavings(amount, ref, client.getCurrentAccount());
				Transaction ts = new Transaction(ref, amount);
				object.getTransactions().add(ts);
				System.out.println("You've moved R" + df.format(amount) + " successfully from your Current Savings account");
				System.out.println("Your balance is now: " + df.format(object.getBalance()));
			}else
				System.out.println("Client doesn't exist on the system");
		}else{
			System.out.println("You don't have enough money to make this transaction");
		}
	}

	public void receiveMoneyFromCurrent(double amount, String ref, CurrentAccount object){
		Transaction ts = new Transaction(ref, amount);
		object.getTransactions().add(ts);
		amount = amount * 1.05;
		object.addToBalance(amount);
		System.out.println("You've received " + df.format(amount) + " from your Current Account.");
		System.out.println("Balance is now updated to R" + df.format(amount) + ".");
	}
}