package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBCon.ConnectionManagerST;
import dtoIfc.PropertyDTOIfc;
import dtos.PropertyDTO;
//import java.util.Date;


public class PropertyDBManager {
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
	public  void updateProperty(PropertyDTOIfc dt1 , String current_prop_key){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			PreparedStatement statement = 
					c1.prepareStatement("update  properties set prop_key=?,prop_value=?"
					+" where prop_key=?"); 
			 
			statement.setString( 1, dt1.getProp_key() );
			statement.setString( 2, dt1.getProp_value() );
			statement.setString( 3, current_prop_key );
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//updateProperty(PropertyDTO dt1)
	public  void insertProperty(PropertyDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			PreparedStatement statement = 
					c1.prepareStatement("insert into properties (prop_key,prop_value)" 
					   + " VALUES (?,?)");
			 
			statement.setString( 1, dt1.getProp_key() );
			statement.setString( 2, dt1.getProp_value() );
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//insertProperty(PropertyDTO dt1


	public PropertyDTOIfc getProperty(String prop_key){
		
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			resultSet = statement.executeQuery("SELECT prop_key,prop_value" +
												" FROM properties" +
												" WHERE prop_key ='" +prop_key+"'");
		    resultSet.next();
		    PropertyDTO dt1 = new PropertyDTO();
		    
			dt1.setProp_key   ( resultSet.getString("prop_key") );
			dt1.setProp_value ( resultSet.getString("prop_value") );
			return dt1;
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}//getProperty(String prop_key)
	
public PropertyDTOIfc[] getAllProperties(){
		
		PropertyDTO[] propertiesArr = null;
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			//statement = c1.createStatement();
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery("SELECT *" +
												" FROM properties");
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			propertiesArr = new PropertyDTO[rsCount];
			for(int i = 0; i < propertiesArr.length; i++){
				propertiesArr[i] = new PropertyDTO();
			}
			
			int i = 0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	propertiesArr[i].setProp_key   ( resultSet.getString("prop_key") );
		    	propertiesArr[i].setProp_value ( resultSet.getString("prop_value") );
			
		    	i++;
		    }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertiesArr;
	}//getProperty(String prop_key)
	
	
	
	
	public void deleteProperty(String prop_key){
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			int isDeleted = statement.executeUpdate("DELETE FROM properties" +
												" WHERE prop_key ='" + prop_key +"'");
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
