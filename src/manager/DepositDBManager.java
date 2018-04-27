package manager;
import java.sql.Connection;
import java.sql.Date;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Vector;


import DBCon.ConnectionManagerST;


import dtoIfc.DepositDTOIfc;
//import dtos.AccountDTO;
import dtos.DepositDTO;
//import java.util.Date;
public class DepositDBManager {

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
	public  void updateDeposit(DepositDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("update  Deposits set client_id=?,"
					+" balance=?,type=?,estimated_balance=?,opening_date=?,closing_date=? where deposit_id=?"); 
			 
			
			statement.setLong   ( 1, dt1.getClient_id() );
			statement.setDouble( 2, dt1.getBalance() );
			statement.setString( 3, dt1.getType() );
			statement.setLong  ( 4, dt1.getEstimated_balance() );
			statement.setTimestamp  ( 5, dt1.getOpening_date() );
			statement.setTimestamp  ( 6, dt1.getClosing_date() );
			statement.setLong  ( 7, dt1.getDeposit_id() );
			
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//updateDeposit(DepositDTO dt1)
	
	public  void insertDeposit(DepositDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("insert into Deposits (client_id,balance,type,estimated_balance,opening_date,closing_date)" 
					   + " VALUES (?,?,?,?,?,?)");
			 
			
			statement.setLong  ( 1, dt1.getClient_id() );
			statement.setDouble( 2, dt1.getBalance() );
			statement.setString( 3, dt1.getType() );
			statement.setLong  ( 4, dt1.getEstimated_balance() );
			statement.setTimestamp( 5, dt1.getOpening_date() );
			statement.setTimestamp( 6, dt1.getClosing_date() );
			
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//insertDeposit(DepositDTO dt1)

public DepositDTOIfc[] getDeposits(long client_id){
		
		DepositDTO[] depositsArr = null;
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			//statement = c1.createStatement();
			
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery("SELECT deposit_id,client_id,balance,type,estimated_balance,opening_date,closing_date" +
												" FROM Deposits" +
												" WHERE client_id =" + client_id);
			
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			depositsArr = new DepositDTO[rsCount];
			for(int i = 0; i < depositsArr.length; i++){
				depositsArr[i] = new DepositDTO();
			}
			
			int i=0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	
		    	depositsArr[i].setDeposit_id			(resultSet.getLong("deposit_id"));
		    	depositsArr[i].setClient_id 			( resultSet.getLong("client_id") );
				depositsArr[i].setBalance   			( resultSet.getDouble("balance") );
				depositsArr[i].setType      			( resultSet.getString("type") );
				depositsArr[i].setEstimated_balance	( resultSet.getLong("estimated_balance") );
				depositsArr[i].setOpening_date     	( resultSet.getTimestamp("opening_date") );
				depositsArr[i].setClosing_date   	( resultSet.getTimestamp("closing_date") );
				
				i++;
		    }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return depositsArr;
	}//getDeposits(long client_id)
	
public DepositDTOIfc[] getAllDeposits(){
	
	DepositDTO[] depositsDetails = null;
	//DepositDTO dt1 = new DepositDTO();
	try {
		Connection c1 = ConnectionManagerST.getInstance().getConnection();
		//statement = c1.createStatement();
		
		statement = c1.createStatement(
			    ResultSet.TYPE_SCROLL_INSENSITIVE, 
			    ResultSet.CONCUR_READ_ONLY);
		
		resultSet = statement.executeQuery("SELECT deposit_id,client_id,balance,type,estimated_balance,opening_date,closing_date" +
											" FROM Deposits");
		
		int rsCount = 0;
		while(resultSet.next())
		{
		    rsCount = rsCount + 1;
		}//end while
		
		depositsDetails = new DepositDTO[rsCount];
		for(int i=0; i < depositsDetails.length; i++){
			depositsDetails[i] = new DepositDTO();
		}
		
		int i=0;
		resultSet.beforeFirst();
	    while( resultSet.next() ){
	    	
	    	depositsDetails[i].setDeposit_id			( resultSet.getLong("deposit_id") );
		    depositsDetails[i].setClient_id 			( resultSet.getLong("client_id") );
			depositsDetails[i].setBalance   			( resultSet.getDouble("balance") );
			depositsDetails[i].setType      			( resultSet.getString("type") );
			depositsDetails[i].setEstimated_balance		( resultSet.getLong("estimated_balance") );
			depositsDetails[i].setOpening_date     		( resultSet.getTimestamp("opening_date") );
			depositsDetails[i].setClosing_date   		( resultSet.getTimestamp("closing_date") );
			
			i++;
	    }//end while
		
				
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return depositsDetails;
}

	
	
	public void deleteDeposit(long deposit_id){
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			int isDeleted = statement.executeUpdate("DELETE FROM Deposits" +
												" WHERE deposit_id =" + deposit_id);
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
		
	}//deleteDeposit(long deposit_id)
	
}
