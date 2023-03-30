package com.greenhouse.bankApp;

public class Transaction
{
	private String reference;
	private double amount;

	public Transaction(String reference, double amount)
	{
		this.reference = reference;
		this.amount = amount;
	}

	public String getReference()
	{
		return reference;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	@Override
	public String toString()
	{
		return "Transaction: " + "reference='" + reference + '\'' + ", amount=" + amount;
	}
}
