package com.greenhouse.bankApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank{

	private List<UserAccounts> bankCustomers = new ArrayList<>();
	private DecimalFormat df = new DecimalFormat("0.00");

	public List<UserAccounts> getBankCustomers()
	{
		return bankCustomers;
	}

	public UserAccounts getClient(int idNumber){
		for(UserAccounts client: getBankCustomers()){
			if(client.getCurrentAccount().getIdNumber() == idNumber){
				return client;
			}
		}
		return null;
	}

	public UserAccounts createNewAccounts(String firstName, String lastName, int idNumber, String phoneNumber, String address){
		SavingsAccount savings = new SavingsAccount(firstName, lastName, idNumber, phoneNumber, address);
		CurrentAccount current = new CurrentAccount(firstName, lastName, idNumber, phoneNumber, address);
		UserAccounts user = new UserAccounts(current,savings);
		getBankCustomers().add(user);
		return user;
	}

	public List<UserAccounts> getAllUsers(){
		return bankCustomers;
	}

	public UserAccounts findClient(String accountNumber){
		if(accountNumber.startsWith("CA")){
			for(UserAccounts e : getBankCustomers()){
				String account = e.getCurrentAccount().getAccountNumber();
				if(accountNumber.equalsIgnoreCase(account))
					return e;
			}
		}
		else if (accountNumber.startsWith("SA")){
			for(UserAccounts e : getBankCustomers()){
				String account = e.getSavingsAccount().getAccountNumber();
				if(accountNumber.equalsIgnoreCase(account))
					return e;
			}
		}else
		{
			System.out.println("Account number is not valid");
			return null;
		}

		System.out.println("Account number doesn't exist on the system");
		return null;
	}

	public void performDebitTransaction(Transaction ts, String accountNumber){
		UserAccounts client = findClient(accountNumber);

		if(client != null){
			if(ts.getAmount() < client.getCurrentAccount().getBalance()){
				client.getCurrentAccount().subtractFromBalance(ts.getAmount());
				String ref = ts.getReference() + ": Debit Order Transaction by Bank Z for " + accountNumber;
				client.getCurrentAccount().getTransactions().add(new Transaction(ref, ts.getAmount()));
				System.out.println("Balance is now updated to: R" + df.format(client.getCurrentAccount().getBalance()));
			}else System.out.println("Debit Order transaction bounced back due to low low balance");
		}else
			System.out.println("Bank Account Number is invalid, please try again");
	}

	public void performDebitTransaction(List<Transaction> transactions, List<String> accountNumbers){
		UserAccounts client;
		for(String accountNumber: accountNumbers){
			client = findClient(accountNumber);
			if(client != null){
				for(Transaction ts: transactions){
					if(accountNumber.startsWith("CA") && ts.getReference().contains(accountNumber)){
						performDebitTransaction(ts, accountNumber);
						return;
					}else if(accountNumber.startsWith("SA"))
						System.out.println("We can't do debit orders from a Savings Accounts");
					else
						System.out.println("Bank Account Number doesn't exist on the system");
				}
				System.out.println("No Transaction was made for this Account Number");
			}else System.out.println("Bank Account Number is invalid");
		}
	}

	public void performCreditTransaction(Transaction ts, String accountNumber){
		UserAccounts client = findClient(accountNumber);
		if(client != null){
			if(client.getCurrentAccount().getBalance() > -5000){
				client.getCurrentAccount().subtractFromBalance(ts.getAmount());
				String ref = ts.getReference() + ": " + " Credit by Bank Z for " + accountNumber;
				client.getCurrentAccount().getTransactions().add(new Transaction(ref, ts.getAmount()));
				System.out.println("Balance is now updated to: R" + df.format(client.getCurrentAccount().getBalance()));
			}else
				System.out.println("You have exceeded your Credit Balance. Transaction failed.");
		}else System.out.println("Bank Account Number is invalid, please try again");
	}

	public void performCreditTransaction(List<Transaction> transactions, List<String> accountNumbers){
		UserAccounts client;
		for(String accountNumber: accountNumbers){
			client = findClient(accountNumber);
			if(client != null){
				for(Transaction ts: transactions){
					if(accountNumber.startsWith("CA") && ts.getReference().contains(accountNumber)){
						performCreditTransaction(ts, accountNumber);
						return;
					}else if (accountNumber.startsWith("SA"))
						System.out.println("We can't make give Credit to a Savings Account");
					else
						System.out.println("Bank Account Number is invalid");
				}
			}else System.out.println("Bank Account Number wasn't found on the system.");
		}
	}

	public void printAllClients(){
		for(UserAccounts user: getAllUsers()){
			System.out.println(user.getCurrentAccount().toString());
		}
	}

	public String generateAccountNumber(String account)
	{
		Random rand = new Random();
		for (int i = 0; i < 10; i++)
		{
			int n = rand.nextInt(10);
			if(i == 0 && n == 0)
				n = rand.nextInt(10);
			account += Integer.toString(n);
		}

		return account;
	}

}
