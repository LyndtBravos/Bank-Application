package com.greenhouse.bankApp;

public class Main
{
	public static void main(String[] args)	{
		System.out.println("==============================================================================="
							+ "\nBrian's Bank Project"
							+ "\n============================================================================");
		Bank bank = new Bank();
		bank.createNewAccounts("Brian", "Mthembu", 981017, "0672570115", "47 Kent Avenue, Randburg, Johannesburg");

		UserAccounts brian = bank.getClient(981017);
		System.out.println();
		System.out.println(brian.getCurrentAccount().toString());
		System.out.println(brian.getSavingsAccount().toString());

		brian.sendMoneyToSavings(750, "Yours Truly", brian.getCurrentAccount());

		brian.sendMoneyToCurrent(400, "Retry now", brian.getSavingsAccount(), bank);
		brian.getCurrentAccount().depositMoney(1000, "Salary", brian.getCurrentAccount());
		brian.sendMoneyToCurrent(350, "Yours Truly", brian.getSavingsAccount(), bank);

		bank.createNewAccounts("Xolani", "Ndlovu", 981220, "0832928195", "Live Easy Highlands North, Rouxville, Johannesburg");
		UserAccounts xolani = bank.getClient(981220);
		brian.sendMoneyToAnotherCA(350, "Blesser", xolani.getCurrentAccount(), bank, brian.getCurrentAccount().getAccountNumber());

		xolani = bank.getClient(981225); // Account doesn't exist

		if(xolani != null)
			brian.sendMoneyToAnotherCA(350, "Blesser", xolani.getCurrentAccount(), bank, brian.getCurrentAccount().getAccountNumber());

		bank.printAllClients();
		bank.getBankCustomers()
				.forEach(UserAccounts::printAllTransactions);

	}
}