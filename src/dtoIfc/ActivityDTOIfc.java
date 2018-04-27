package dtoIfc;

import java.io.Serializable;
import java.sql.Timestamp;

public interface ActivityDTOIfc extends Serializable {
	
	public long getId();
	public void setId(long id);
	
	public long getClient_id();
	public void setClient_id(long client_id);
	
	public double getAmount();
	public void setAmount(double amount);
	
	public Timestamp getActivity_date();
	public void setActivity_date(Timestamp activity_date);
	
	public double getCommision();
	public void setCommision(double comission);
	
	public String getDescription();
	public void setDescription(String description);
	
}
