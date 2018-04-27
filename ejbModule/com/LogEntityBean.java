package com;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="Logs")
public class LogEntityBean implements Serializable {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
	private long Id;
	
	@Column(name="Log_date")
	private Timestamp Log_date;
	
	@Column(name="Client_id")
	private long Client_id;
	
	@Column(name="Description")
	private String Description;
	
	public LogEntityBean(){
		
	}
	
   
    public long getId() {
       return Id;
    }
    public void setId(long id) {
		Id = id;
	}
	
	
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
