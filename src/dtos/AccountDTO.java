package dtos;

import dtoIfc.AccountDTOIfc;

public class AccountDTO implements AccountDTOIfc{

	private long account_id;
	private long client_id;
	private double balance;
	private double credit_limit;
	private String comment;
	
	public long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}
	public long getClient_id() {
		return client_id;
	}
	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(double credit_limit) {
		this.credit_limit = credit_limit;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "AccountDTO [account_id=" + account_id + ", client_id="
				+ client_id + ", balance=" + balance + ", credit_limit="
				+ credit_limit + ", comment=" + comment + "]";
	}
	
	
}
