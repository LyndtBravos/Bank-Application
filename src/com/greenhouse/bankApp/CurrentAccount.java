package com.greenhouse.bankApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrentAccount {

	private String firstName;
	private String lastName;
	private int idNumber;
	private String phoneNumber;
	private String accountNumber;
	private List<Transaction> transactions;
	private double balance;
	private String address;
	private DecimalFormat df = new DecimalFormat("0.00");

	public CurrentAccount()
	{
	}

	public CurrentAccount(String firstName, String lastName, int idNumber, String phoneNumber, String address)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
		this.balance = 0.0;
		this.accountNumber = new Bank().generateAccountNumber("CA"); //CA - Current Account
		this.transactions = new ArrayList<>();
		this.address = address;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public int getIdNumber()
	{
		return idNumber;
	}

	public UserAccounts getUserAccounts(Bank bank){
		UserAccounts user = new UserAccounts().findWithCurrentAccount(this, bank);
		return user;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public List<Transaction> getTransactions()
	{
		return transactions;
	}

	public double getBalance()
	{
		return balance;
	}

	public String getAddress()
	{
		return address;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void addToBalance(double amount){
		this.balance+=amount;
	}

	public void subtractFromBalance(double amount){
		this.balance-=amount;
	}

	public void printTransactionsList(){
		int i = 0;
		System.out.println("For you" + getFirstName() + " " + getLastName());
		for (Transaction ts: getTransactions()){
			i++;
			System.out.println("Transaction " + (i) + " " + ts.toString());
		}
	}

	public void depositMoney(double deposit, String ref, CurrentAccount account){
		account.addToBalance(deposit);
		account.getTransactions().add(new Transaction(ref, deposit, "+"));
		System.out.println(account.getFirstName() + ": You have gained R" + df.format(deposit));
		System.out.println("Balance: " + account.getBalance());
	}

	public void withdrawMoney(double amount, String ref, CurrentAccount account){
		account.subtractFromBalance(amount);
		account.getTransactions().add(new Transaction(ref, amount, "-"));
	}

	@Override
	public String toString()
	{
		return "CurrentAccount{" + "firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + ", idNumber=" + getIdNumber() + ", phoneNumber=" + getPhoneNumber() + ", accountNumber='" + getAccountNumber()
				+ '\'' + ", balance=R" + df.format(getBalance()) + ", address='" + getAddress() + '\'' + '}';
	}
}
