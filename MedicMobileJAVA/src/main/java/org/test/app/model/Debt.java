package org.test.app.model;

/**
 *
 * @author WensanSiegfred
 * Model attributes for Debt
 *
 */
public class Debt implements Comparable<Debt>{
	private String debtor;
	private String creditor;
	private double amount;

	public Debt(String debtor, String creditor, double amount) {
		this.debtor = debtor;
		this.creditor = creditor;
		this.amount = amount;
	}

	public String getDebtor() {
		return debtor;
	}

	public void setDebtor(String debtor) {
		this.debtor = debtor;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int compareTo(Debt debt) {
		return this.debtor.compareTo(debt.getDebtor());
	}
}
