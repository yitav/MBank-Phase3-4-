package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBCon.ConnectionManagerST;
import dtoIfc.ActivityDTOIfc;
import dtos.ActivityDTO;



public class ActivityDBManager {

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
	public  void updateActivity(ActivityDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("update  activity set client_id=?,"
					+" amount=?,activity_date=?,commision=?,description=? where id=?"); 
			 
			
			statement.setLong     ( 1, dt1.getClient_id() );
			statement.setDouble	  ( 2, dt1.getAmount() );
			statement.setTimestamp( 3, dt1.getActivity_date() );
			statement.setDouble	  ( 4, dt1.getCommision() );
			statement.setString	  ( 5, dt1.getDescription() );
			statement.setLong  	  ( 6, dt1.getId() );
			
			
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//updateActivity(ActivityDTO dt1)
	public  void insertActivity(ActivityDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("insert into activity (client_id,amount,activity_date,commision,description)" 
					   + " VALUES (?,?,?,?,?)");
			 
			
			statement.setLong     ( 1, dt1.getClient_id() );
			statement.setDouble	  ( 2, dt1.getAmount() );
			statement.setTimestamp( 3, dt1.getActivity_date() );
			statement.setDouble	  ( 4, dt1.getCommision() );
			statement.setString	  ( 5, dt1.getDescription() );
			
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//insertActivity(ActivityDTO dt1)


public ActivityDTOIfc[] getActivities(long client_id){
		
		ActivityDTO[] activitiesArr = null;
		//ActivityDTO dt1 = new ActivityDTO();
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			//statement = c1.createStatement();
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery("SELECT id,client_id,amount,activity_date,commision,description" +
												" FROM activity" +
												" WHERE client_id =" + client_id);
			
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			activitiesArr = new ActivityDTO[rsCount];
			for(int i = 0; i < activitiesArr.length; i++){
				activitiesArr[i] = new ActivityDTO();
			}
			
			int i = 0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	
		    	activitiesArr[i].setId			  ( resultSet.getLong("id") );
				activitiesArr[i].setClient_id     ( resultSet.getLong("client_id") );
				activitiesArr[i].setAmount        ( resultSet.getDouble("amount") );
				activitiesArr[i].setActivity_date ( resultSet.getTimestamp("activity_date") );
				activitiesArr[i].setCommision     ( resultSet.getDouble("commision") );
				activitiesArr[i].setDescription   ( resultSet.getString("description") );
				
				i++;
		    }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activitiesArr;
	}
	
public ActivityDTOIfc[] getAllActivities(){
		
		ActivityDTO[] activitiesDetails = null;
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			//statement = c1.createStatement();
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery("SELECT id,client_id,amount,activity_date,commision,description" +
												" FROM activity");
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			activitiesDetails = new ActivityDTO[rsCount];
			for(int i = 0; i < activitiesDetails.length; i++){
				activitiesDetails[i] = new ActivityDTO();
			}
			
			int i=0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	
		    	activitiesDetails[i].setId			 ( resultSet.getLong("id") );
				activitiesDetails[i].setClient_id     ( resultSet.getLong("client_id") );
				activitiesDetails[i].setAmount        ( resultSet.getDouble("amount") );
				activitiesDetails[i].setActivity_date ( resultSet.getTimestamp("activity_date") );
				activitiesDetails[i].setCommision     ( resultSet.getDouble("commision") );
				activitiesDetails[i].setDescription   ( resultSet.getString("description") );
				
				i++;
		    }//end while
			
		    
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activitiesDetails;
	}
	
	
	
	public void deleteActivity(long id){
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			int isDeleted = statement.executeUpdate("DELETE FROM activity" +
												" WHERE id =" + id);
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
		
	}//deleteActivity(long id)
public int getMaxId(){
	
		int maxId = 0;
		//ActivityDTO dt1 = new ActivityDTO();
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			resultSet = statement.executeQuery("SELECT max(id)" +
												" FROM activity" );
		    resultSet.next();
		    
			maxId =  resultSet.getInt("client_id") ;
			/*dt1.setAmount        ( resultSet.getDouble("amount") );
			dt1.setActivity_date ( resultSet.getDate("activity_date") );
			dt1.setComission     ( resultSet.getDouble("comission") );
			dt1.setDescription   ( resultSet.getString("description") );*/
			
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxId;
	}
	
}