package dtoIfc;

import java.io.Serializable;

public interface ClientDTOIfc extends Serializable {
	
		public long getClient_id() ;
		public void setClient_id(long client_id);
		
		public String getClient_name();
		public void setClient_name(String client_name);
		
		public String getPassword();
		public void setPassword(String password);
		
		public String getType();
		public void setType(String type);
		
		public String getAddress();
		public void setAddress(String address);
		
		public String getEmail();
		public void setEmail(String email);
		
		public String getPhone();
		public void setPhone(String phone);
		
		public String getComment();
		public void setComment(String comment);
			
	
	
}
