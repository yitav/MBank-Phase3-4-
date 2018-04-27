package DBCon;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Date;

public class ConnectionManagerST {

	private Connection connect = null;
    private static ConnectionManagerST mySelf=null;

    
	private  ConnectionManagerST()
	{
		try
		{
			// This will load the MySQL driver, each DB has its own driver
			// STEP 1
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// Setup the connection with the DB
			// STEP 2
			
			//connect = DriverManager
			//		.getConnection("jdbc:sqlserver://localhost/ProjectJB?"
			//				+ "user=root&password=1234");
			
			//connect = DriverManager
			//		.getConnection("jdbc:sqlserver://localhost/ProjectJB?"
			//				,"root","1234");
			
			//connect = DriverManager
			//		.getConnection("jdbc:sqlserver://localhost/ProjectJB"
			//				,"root","1234");
			
			
			
			//connect = DriverManager
			//		.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ProjectJB"
			//,"root","1234");
			
			connect = DriverManager
					.getConnection("jdbc:sqlserver://localhost;databaseName=ProjectJB"
							,"root","1234");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static ConnectionManagerST getInstance(){
		if (mySelf == null)
			mySelf = new ConnectionManagerST();
		
		return mySelf;
	}
	
	public Connection getConnection(){
		return this.connect;
	}
	public void Close()
	{
		try 
		{
			// STEP 5
			connect.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
