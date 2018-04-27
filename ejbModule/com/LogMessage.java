package com;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogMessage implements Serializable {
	
	private Timestamp Log_date;
	private long Client_id;
	private String Description;
	
	public Timestamp getLog_date() {
		return Log_date;
	}
	public void setLog_date(Timestamp log_date) {
		Log_date = log_date;
	}
	public long getClient_id() {
		return Client_id;
	}
	public void setClient_id(long client_id) {
		Client_id = client_id;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}

}
