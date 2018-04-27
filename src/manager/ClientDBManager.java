package manager;


import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBCon.ConnectionManagerST;
//import java.util.Vector;
//import java.util.Date;

import dtoIfc.ClientDTOIfc;
import dtos.ClientDTO;
//import dtos.DepositDTO;

public class ClientDBManager {

	private Statement statement = null;
	private ResultSet resultSet = null;

//	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		// STEP 4
//		/*
//		while (resultSet.next()) {
//
//			System.out.println(resultSet.getObject("prop_key") + " - " + resultSet.getObject("prop_value"));
//		}
//		*/
//	
//	}
	public  void updateClient(ClientDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("update  Clients set client_name=?, password=?,"
					+" type=?,address=?,email=?,phone=?,comment=? where client_id=?"); 
			 
			
			
			statement.setString(1, dt1.getClient_name());
			statement.setString(2, dt1.getPassword());
			statement.setString(3, dt1.getType() );
			statement.setString(4, dt1.getAddress() );
			statement.setString(5, dt1.getEmail());
			statement.setString(6, dt1.getPhone() );
			statement.setString(7, dt1.getComment());
			statement.setLong  (8, dt1.getClient_id());
			
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//updateClient(ClientDTO dt1)
	public  int insertClient(ClientDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("insert into Clients (client_name,password,type,address,email,phone,comment)" 
					   + " VALUES (?,?,?,?,?,?,?)");
			
			statement.setString(1, dt1.getClient_name());
			statement.setString(2, dt1.getPassword());
			statement.setString(3, dt1.getType() );
			statement.setString(4, dt1.getAddress() );
			statement.setString(5, dt1.getEmail());
			statement.setString(6, dt1.getPhone() );
			statement.setString(7, dt1.getComment());
			
			
			
			statement.execute();
			
			Statement statement2 = c1.createStatement();
			resultSet = statement2.executeQuery("select IDENT_CURRENT( 'Clients' ) as id");
		    resultSet.next();
		    return resultSet.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}//insertClient(ClientDTO dt1)


	public ClientDTOIfc getClient(long client_id){
		
		ClientDTO dt1 = new ClientDTO();
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			resultSet = statement.executeQuery("SELECT client_id,client_name,password,type,address,email,phone,comment" +
												" FROM Clients" +
												" WHERE client_id =" + client_id);
		    resultSet.next();
		    dt1.setClient_id  (resultSet.getLong("client_id"));
			dt1.setClient_name(resultSet.getString("client_name"));
			dt1.setPassword	  (resultSet.getString("password"));
			dt1.setAddress    (resultSet.getString("address"));
			dt1.setType       (resultSet.getString("type"));
			dt1.setEmail      (resultSet.getString("email"));
			dt1.setPhone      (resultSet.getString("phone"));
			dt1.setComment    (resultSet.getString("comment"));
			
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt1;
	}//getClient()
	
public ClientDTOIfc getClient(String client_name ,String password){
		
		ClientDTOIfc dt1 = new ClientDTO();
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			//statement = c1.createStatement();
			resultSet = statement.executeQuery("SELECT client_id,client_name,password,type,address,email,phone,comment" +
												" FROM Clients" +
												" WHERE client_name ='" + client_name+"' "+
												"AND password ='"+password+"'");
			
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			if(rsCount == 0){
				return null;
			}
			
			resultSet.beforeFirst();
		    resultSet.next();
		    dt1.setClient_id  (resultSet.getLong("client_id"));
			dt1.setClient_name(resultSet.getString("client_name"));
			dt1.setPassword	  (resultSet.getString("password"));
			dt1.setAddress    (resultSet.getString("address"));
			dt1.setType       (resultSet.getString("type"));
			dt1.setEmail      (resultSet.getString("email"));
			dt1.setPhone      (resultSet.getString("phone"));
			dt1.setComment    (resultSet.getString("comment"));
			return dt1;
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}//getClient()
	
	
public ClientDTOIfc[] getAllClients(){
		
		ClientDTO[] clientsDetails = null;
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			
			//statement = c1.createStatement();
			resultSet = statement.executeQuery("SELECT client_id,client_name,password,type,address,email,phone,comment" +
												" FROM Clients");
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			clientsDetails = new ClientDTO[rsCount];
			for(int i=0; i < clientsDetails.length; i++){
				clientsDetails[i] = new ClientDTO();
			}
			
			int i=0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	
		    	clientsDetails[i].setClient_id  (resultSet.getLong("client_id"));
				clientsDetails[i].setClient_name(resultSet.getString("client_name"));
				clientsDetails[i].setPassword	(resultSet.getString("password"));
				clientsDetails[i].setAddress    (resultSet.getString("address"));
				clientsDetails[i].setType       (resultSet.getString("type"));
				clientsDetails[i].setEmail      (resultSet.getString("email"));
				clientsDetails[i].setPhone      (resultSet.getString("phone"));
				clientsDetails[i].setComment    (resultSet.getString("comment"));
				
				i++;
		    }//end while
			
		    
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientsDetails;
	}

public long isThereNameAndPassword(String name , String password){
	
	ClientDTO dt1 = new ClientDTO();
	try {
		Connection c1 = ConnectionManagerST.getInstance().getConnection();
		statement = c1.createStatement();
		resultSet = statement.executeQuery("SELECT count(*) as num" +
											" FROM Clients" +
											" WHERE client_name ='" + name+"' AND password ='"+password+"'");
	    resultSet.next();
	   
		return resultSet.getLong("num");
		
        
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return -1;
	}
	
}//getClient()




	public void deleteClient(long client_id){
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			int isDeleted = statement.executeUpdate("DELETE FROM Clients" +
												" WHERE client_id =" + client_id);
			if(isDeleted == 1){
				   System.out.println("Row is deleted.");
			}
			else{
				 System.out.println("Row is not deleted.");
			}
				 
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//deleteClient(int client_id)
	
}
