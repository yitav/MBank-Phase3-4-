package dtos;

import java.sql.Timestamp;

import dtoIfc.ActivityDTOIfc;



public class ActivityDTO implements ActivityDTOIfc{
	
	private long id;
	private long client_id;
	private double amount;
	private Timestamp activity_date;
	private double commision;
	private String description;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getClient_id() {
		return client_id;
	}
	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getActivity_date() {
		return activity_date;
	}
	public void setActivity_date(Timestamp activity_date) {
		this.activity_date = activity_date;
	}
	public double getCommision() {
		return commision;
	}
	public void setCommision(double comission) {
		this.commision = comission;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ActivityDTO [id=" + id + ", client_id=" + client_id
				+ ", amount=" + amount + ", activity_date=" + activity_date
				+ ", comission=" + commision + ", description=" + description
				+ "]";
	}
	
	

}
