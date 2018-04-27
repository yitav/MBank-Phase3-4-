package actions;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import actionsIfc.ActionIfc;
import manager.AccountDBManager;
import manager.ActivityDBManager;
import manager.ClientDBManager;
import manager.DepositDBManager;
import manager.PropertyDBManager;
import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;


public class Action extends UnicastRemoteObject implements ActionIfc{

	public Action() throws RemoteException {
		super();
		
	}

	public boolean updateClientDetails(ClientDTOIfc cdt){
		
		ClientDBManager cdb = new ClientDBManager();
		ClientDTOIfc clientFromDB = cdb.getClient(cdt.getClient_name(), cdt.getPassword());
		if(clientFromDB == null || clientFromDB.getClient_id() < 0)
			return false;
		cdt.setClient_id(clientFromDB.getClient_id());
		cdb.updateClient(cdt);
		return true;
		
	
	}
	
	public ClientDTOIfc viewClientDetails(String client_name , String password){
	
		ClientDBManager cdb = new ClientDBManager();
		
		return cdb.getClient(client_name, password);
		
	}
	
	public AccountDTOIfc[] viewAccountDetails(String client_name , String password){
		
		ClientDBManager cdb = new ClientDBManager();
		AccountDBManager adb = new AccountDBManager();
		ClientDTOIfc clientFromDB = cdb.getClient(client_name, password);
		if(clientFromDB == null){
			return null;
		}
		return adb.getAccounts( clientFromDB.getClient_id() );
		
	}
	
	public DepositDTOIfc[] viewClientDeposits(String client_name , String password){
		ClientDBManager cdb = new ClientDBManager();
		DepositDBManager ddb = new DepositDBManager();
		
		return ddb.getDeposits( cdb.getClient(client_name, password).getClient_id() );
		
	}
	
	public ActivityDTOIfc[] viewClientActivities(String client_name , String password){
		ClientDBManager cdb = new ClientDBManager();
		ActivityDBManager adb = new ActivityDBManager();
		
		return adb.getActivities( cdb.getClient(client_name, password).getClient_id() );
		
	}
	
	public PropertyDTOIfc viewSystemProperty(String prop_key){
		
		PropertyDBManager pdb = new PropertyDBManager();
		
		return pdb.getProperty(prop_key);
	}
	
}
