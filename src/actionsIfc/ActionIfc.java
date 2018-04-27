package actionsIfc;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;


public interface ActionIfc extends Remote {

	public boolean updateClientDetails(ClientDTOIfc cdt) throws RemoteException;
	
	public ClientDTOIfc viewClientDetails(String client_name , String password) throws RemoteException;
	
	public AccountDTOIfc[] viewAccountDetails(String client_name , String password) throws RemoteException;
	
	public DepositDTOIfc[] viewClientDeposits(String client_name , String password) throws RemoteException;
	
	public ActivityDTOIfc[] viewClientActivities(String client_name , String password) throws RemoteException;
	
	public PropertyDTOIfc viewSystemProperty(String prop_key) throws RemoteException;
	
}
