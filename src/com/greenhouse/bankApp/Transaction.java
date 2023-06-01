package com.greenhouse.bankApp;

public class Transaction
{
	private final String reference;
	private final double amount;
	private final String status;

	public Transaction(String reference, double amount, String status)
	{
		this.reference = reference;
		this.amount = amount;
		this.status = status;
	}

	public String getReference()
	{
		return reference;
	}

	public double getAmount()
	{
		return amount;
	}

	@Override
	public String toString()
	{
		return "Transaction: " + "reference='" + reference + '\'' + ", amount=" + status + amount;
	}
}
