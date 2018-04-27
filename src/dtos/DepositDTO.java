package dtos;

import java.sql.Timestamp;

import dtoIfc.DepositDTOIfc;

//import java.util.Date;

//import java.sql.Date;

public class DepositDTO implements DepositDTOIfc{

	private long deposit_id;
	private long client_id;
	private double balance;
	private String type;
	private Long estimated_balance;
	private Timestamp opening_date;
	private Timestamp closing_date;
	
	
	public long getDeposit_id() {
		return deposit_id;
	}
	public void setDeposit_id(long deposit_id) {
		this.deposit_id = deposit_id;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getEstimated_balance() {
		return estimated_balance;
	}
	public void setEstimated_balance(long estimated_balance) {
		this.estimated_balance = estimated_balance;
	}
	public Timestamp getOpening_date() {
		return opening_date;
	}
	public void setOpening_date(Timestamp opening_date) {
		this.opening_date = opening_date;
	}
	public Timestamp getClosing_date() {
		return closing_date;
	}
	public void setClosing_date(Timestamp closing_date) {
		this.closing_date = closing_date;
	}
	@Override
	public String toString() {
		return "DepositDTO [deposit_id=" + deposit_id + ", client_id="
				+ client_id + ", balance=" + balance + ", type=" + type
				+ ", estimated_balance=" + estimated_balance
				+ ", opening_date=" + opening_date + ", closing_date="
				+ closing_date + "]";
	}

	
	
	
}
