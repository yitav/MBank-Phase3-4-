package dtoIfc;

import java.io.Serializable;

public interface AccountDTOIfc extends Serializable {

	public long getAccount_id();
	public void setAccount_id(long account_id);
	
	public long getClient_id();
	public void setClient_id(long client_id);
	
	public double getBalance();
	public void setBalance(double balance);
	
	public double getCredit_limit();
	public void setCredit_limit(double credit_limit);
	
	public String getComment();
	public void setComment(String comment);
	
}
