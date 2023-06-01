package com.greenhouse.bankApp;

import java.text.DecimalFormat;

public class UserAccounts {
	private CurrentAccount currentAccount;
	private SavingsAccount savingsAccount;
	private final DecimalFormat df = new DecimalFormat("0.00");

	public UserAccounts()
	{
	}

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
		object.getTransactions().add(new Transaction(ref, amount, "+"));
		object.addToBalance(amount);
		System.out.println(object.getFirstName() + ": You've received R" + df.format(amount) + " from your Savings Account");
		System.out.println("Balance is now: " + df.format(object.getBalance()));
	}

	public void sendMoneyToSavings(double amount, String ref, CurrentAccount object){
		if(object.getBalance() > amount){
			Transaction ts = new Transaction(ref, amount, "-");
			object.getTransactions().add(ts);
			object.subtractFromBalance(amount);
			receiveMoneyFromCurrent(amount, ref, object);
			System.out.printf(object.getFirstName() + ": You have moved R" + df.format(amount) + " to your Savings Account.");
		}else{
			System.out.println("You don't have enough money to make this transaction!");
		}
	}

	public void sendMoneyToAnotherCA(double amount, String ref, CurrentAccount object, Bank bank, String myAccountNumber) {
		UserAccounts myAccount = bank.findClient(myAccountNumber);
		if(amount < 0){
			System.out.println("Please provide a valid amount to deposit");
			return;
		}else if(amount > myAccount.getCurrentAccount().getBalance()){
			System.out.println("You don't have enough money to make this transaction.");
			return;
		}

		boolean isCAAccount = object.getAccountNumber().startsWith("CA");
		if(!isCAAccount){
			System.out.println("We can't make a transaction to a Savings Account");
		}else{
			myAccount.getCurrentAccount().getTransactions().add(new Transaction(ref, amount, "-"));
			double charge = amount * 0.05;
			myAccount.getCurrentAccount().subtractFromBalance(amount+charge);
			object.addToBalance(amount);
			object.getTransactions().add(new Transaction(ref + ": " + myAccountNumber, amount, "+"));
			System.out.println(myAccount.getCurrentAccount().getFirstName() + ": You've managed to send R" + df.format(amount) + " successfully to: " + object.getAccountNumber());
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

	public UserAccounts findWithCurrentAccount(CurrentAccount object, Bank bank){
		UserAccounts client = bank.getClient(object.getIdNumber());
		if(client == null) {
			System.out.println("Error, client doesn't exist");
			return null;
		}
		return client;
	}

	public void sendMoneyToCurrent(double amount, String ref, SavingsAccount object, Bank bank){
		if(amount < object.getBalance()){
			UserAccounts client = findWithSavingsAccount(object, bank);
			if(client != null){
				String firstName = object.getUserAccounts(bank).getCurrentAccount().getFirstName();
				object.subtractFromBalance(amount);
				receiveMoneyFromSavings(amount, ref, client.getCurrentAccount());
				Transaction ts = new Transaction(ref, amount, "-");
				object.getTransactions().add(ts);
				System.out.println(firstName + ": You've moved R" + df.format(amount) + " successfully from your Current Savings account");
				System.out.println("Your balance is now: " + df.format(object.getBalance()));
			}else
				System.out.println("Client doesn't exist on the system");
		}else{
			System.out.println("You don't have enough money to make this transaction");
		}
	}

	public void receiveMoneyFromCurrent(double amount, String ref, CurrentAccount object){
		Transaction ts = new Transaction(ref, amount, "+");
		object.getTransactions().add(ts);
		amount = amount * 1.05;
		object.addToBalance(amount);
		System.out.println(object.getFirstName() + "You've received " + df.format(amount) + " from your Current Account.");
		System.out.println("Balance is now updated to R" + df.format(amount) + ".");
	}

	public UserAccounts getUserAccounts(){
		CurrentAccount current = getCurrentAccount();
		SavingsAccount savings = getSavingsAccount();
		return new UserAccounts(current, savings);
	}

	public void printAllTransactions(){
		System.out.println("Current Account Transactions for: " + getSavingsAccount().getFirstName() + " " + getSavingsAccount().getLastName());
		this.getCurrentAccount()
				.getTransactions()
				.forEach(System.out::println);
		System.out.println("Balance: " + this.getCurrentAccount()
				.getBalance() + "\n");
		System.out.println("Savings Account Transactions");
		this.getSavingsAccount()
				.getTransactions()
				.forEach(System.out::println);
		System.out.println("Balance: " + this.getSavingsAccount()
											.getBalance());
		System.out.println();
	}

}