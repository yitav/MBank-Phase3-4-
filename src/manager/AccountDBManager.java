package manager;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBCon.ConnectionManagerST;
//import java.util.Date;

import dtoIfc.AccountDTOIfc;
import dtos.AccountDTO;
//import dtos.ClientDTO;


public class AccountDBManager {
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
	public  void updateAccount(AccountDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("update  Accounts set client_id=?,"
					+" balance=?,credit_limit=?,comment=? where account_id=?"); 
			 
			
			statement.setLong   ( 1, dt1.getClient_id() );
			statement.setDouble( 2, dt1.getBalance() );
			statement.setDouble( 3, dt1.getCredit_limit() );
			statement.setString( 4, dt1.getComment() );
			statement.setLong  ( 5, dt1.getAccount_id());
			
			
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//updateAccount(AccountDTO dt1)
	public  boolean insertAccount(AccountDTOIfc dt1){
		
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			PreparedStatement statement = c1.prepareStatement("insert into Accounts (client_id,balance,credit_limit,comment)" 
					   + " VALUES (?,?,?,?)");
			 
			
			statement.setLong   ( 1, dt1.getClient_id() );
			statement.setDouble( 2, dt1.getBalance() );
			statement.setDouble( 3, dt1.getCredit_limit() );
			statement.setString( 4, dt1.getComment() );
			
			
			statement.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//insertAccount(AccountDTO dt1)


	public AccountDTOIfc[] getAccounts(long client_id){
		
		AccountDTO[] accountsDetails = null;
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery("SELECT account_id,client_id,balance,credit_limit,comment" +
												" FROM Accounts" +
												" WHERE client_id =" + client_id);
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			accountsDetails = new AccountDTO[rsCount];
			for(int i=0; i < accountsDetails.length; i++){
				accountsDetails[i] = new AccountDTO();
			}
			
			int i=0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	
		    	accountsDetails[i].setAccount_id  ( resultSet.getLong("account_id") );
				accountsDetails[i].setClient_id   ( resultSet.getLong("client_id") 	);
				accountsDetails[i].setBalance     ( resultSet.getDouble("balance")  );
				accountsDetails[i].setCredit_limit( resultSet.getDouble("credit_limit") );
				accountsDetails[i].setComment     ( resultSet.getString("comment") 	);
				
				i++;
		    }//end while
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountsDetails;
	}//getAccount(long account_id)
	
public AccountDTOIfc[] getAllAccounts(){
		
		AccountDTO[] accountsDetails = null; 
		//AccountDTO dt1 = new AccountDTO();
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			//statement = c1.createStatement();
			statement = c1.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery("SELECT account_id,client_id,balance,credit_limit,comment" +
												" FROM Accounts");
			int rsCount = 0;
			while(resultSet.next())
			{
			    rsCount = rsCount + 1;
			}//end while
			
			accountsDetails = new AccountDTO[rsCount];
			for(int i=0; i < accountsDetails.length; i++){
				accountsDetails[i] = new AccountDTO();
			}
			
			int i=0;
			resultSet.beforeFirst();
		    while( resultSet.next() ){
		    	
		    	accountsDetails[i].setAccount_id  ( resultSet.getLong("account_id") );
				accountsDetails[i].setClient_id   ( resultSet.getLong("client_id") 	);
				accountsDetails[i].setBalance     ( resultSet.getDouble("balance")  );
				accountsDetails[i].setCredit_limit( resultSet.getDouble("credit_limit") );
				accountsDetails[i].setComment     ( resultSet.getString("comment") 	);
				
				i++;
		    }//end while
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountsDetails;
	}
	
	public void deleteAccount(long account_id){
		try {
			Connection c1 = ConnectionManagerST.getInstance().getConnection();
			statement = c1.createStatement();
			int isDeleted = statement.executeUpdate("DELETE FROM Accounts" +
												" WHERE account_id =" + account_id);
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
		
	}//deleteAccount(long account_id)
	
}
