package main;

import ifc.MBankIfc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dtoIfc.ClientDTOIfc;
import actions.AdminAction;
import actions.ClientAction;
import actionsIfc.ActionIfc;
import manager.ClientDBManager;
import manager.PropertyDBManager;




public class MBankST extends UnicastRemoteObject implements MBankIfc{
	
    //private Action action;
        
	private static MBankST mySelf = null;
	
	
	
	private MBankST() throws RemoteException{
		super();
		
	}
	
	public static MBankST getInstance() throws RemoteException{
		
		if( mySelf == null )
			mySelf = new MBankST();
		return mySelf;
			
	}
	
       
	public ActionIfc login( String userName , String password){
		
		//System.out.println("Login works"); //Debug purposes
		
		ActionIfc action = null;
		PropertyDBManager pdbm = new PropertyDBManager();
		
		//System.out.println( pdbm.getProperty("admin_username") );
		//System.out.println( pdbm.getProperty("admin_password") );
		String adminUsername = pdbm.getProperty("admin_username").getProp_value();
		String adminPassword = pdbm.getProperty("admin_password").getProp_value();
		
		//System.out.println("DB admin username : " + adminUsername); //Debug purposes
		//System.out.println("DB admin password : " + adminPassword); //Debug purposes
		
		if( userName.equals( pdbm.getProperty("admin_username").getProp_value() ) 
		   		&& password.equals( pdbm.getProperty("admin_password").getProp_value() ) 
		  )
		{
			try {
				action = new AdminAction();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ClientDBManager cdbm = new ClientDBManager();
			ClientDTOIfc[] cdtoArr = cdbm.getAllClients();
			for(ClientDTOIfc client : cdtoArr){
				if(client.getClient_name().equals(userName) &&
						client.getPassword().equals(password))
				{
					
							try {
								action = new ClientAction();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				
				}
				
			}
			
			
		}
		
		return action;
	}
        
	@Override
    public String helloWorld() throws RemoteException{
    	return "HelloWorld";
    }
	
}
