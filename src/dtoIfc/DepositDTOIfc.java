package dtoIfc;

import java.io.Serializable;
import java.sql.Timestamp;

public interface DepositDTOIfc extends Serializable {

	public long getDeposit_id();
	public void setDeposit_id(long deposit_id);
	
	public long getClient_id();
	public void setClient_id(long client_id);
	
	public double getBalance();
	public void setBalance(double balance);
	
	public String getType();
	public void setType(String type);
	
	public long getEstimated_balance();
	public void setEstimated_balance(long estimated_balance);
	
	public Timestamp getOpening_date();
	public void setOpening_date(Timestamp opening_date);
	
	public Timestamp getClosing_date();
	public void setClosing_date(Timestamp closing_date);
	
	
	
}
