package dtos;

import dtoIfc.ClientDTOIfc;

public class ClientDTO implements ClientDTOIfc{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8534754457269787207L;
	private long client_id;
	private String client_name;
	private String password;
	private String type;
	private String address;
	private String email;
	private String phone;
	private String comment;
	
	
	public long getClient_id() {
		return client_id;
	}
	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ClientDTO [client_id=" + client_id + ", client_name="
				+ client_name + ", password=" + password + ", type=" + type
				+ ", address=" + address + ", email=" + email + ", phone="
				+ phone + ", comment=" + comment + "]";
	}
	
	
	
}
