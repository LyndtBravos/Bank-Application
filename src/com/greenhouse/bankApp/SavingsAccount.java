package com.greenhouse.bankApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccount {

	private String firstName;
	private String lastName;
	private int idNumber;
	private String phoneNumber;
	private String accountNumber;
	private List<Transaction> transactions;
	private double balance;
	private String address;
	private DecimalFormat df = new DecimalFormat("0.00");

	public UserAccounts getUserAccounts(Bank bank){
		UserAccounts user = new UserAccounts().findWithSavingsAccount(this, bank);
		return user;
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

	public SavingsAccount()
	{
	}

	public SavingsAccount(String firstName, String lastName, int idNumber, String phoneNumber, String address)
	{
		this.firstName = firstName;
		this.transactions = new ArrayList<>();
		System.out.println("Welcome to Bank X, " + firstName + ". You've been credited with R500 as a joining bonus. Enjoy!");
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.balance = 500.00;
		this.accountNumber = new Bank().generateAccountNumber("SA"); // SA for Savings Account
		getTransactions().add(new Transaction("Joining Bonus", 500.00, "+"));
		new CurrentAccount(firstName, lastName, idNumber, phoneNumber, address);
	}

	public void printTransactionsList(){
		int i = 0;

		System.out.println("For you: " + getFirstName() + " " + getLastName());
		for (Transaction ts: getTransactions()){
			i++;
			System.out.println("Transaction " + i + ": " + ts.toString());
		}
	}

	@Override
	public String toString()
	{
		return "SavingsAccount{" + "firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + ", idNumber=" + getIdNumber() + ", phoneNumber=" + getPhoneNumber() + ", accountNumber='" + getAccountNumber()
				+ '\'' + ", balance=R" + df.format(getBalance()) + ", address='" + getAddress() + '\'' + '}';
	}
}