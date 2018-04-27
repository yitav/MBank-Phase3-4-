package actionsIfc;

import java.rmi.RemoteException;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;


public interface AdminActionIfc extends ActionIfc {

	public AccountDTOIfc addNewClient(ClientDTOIfc cdto , AccountDTOIfc adto , double firstDeposit) throws RemoteException;
	
	public String removeClient(ClientDTOIfc cdt1) throws RemoteException;
	
	public boolean createNewAccount(ClientDTOIfc cdto  , AccountDTOIfc adto) throws RemoteException;
	
	public boolean removeAccount(ClientDTOIfc clientIfc) throws RemoteException;
	
	public ClientDTOIfc[] viewAllClientsDetails() throws RemoteException;
	
	public AccountDTOIfc[] viewAllAccountsDetails() throws RemoteException;
	
	public DepositDTOIfc[] viewAllDepositsDetails() throws RemoteException;
	
	public ActivityDTOIfc[] viewAllActivitiesDetails() throws RemoteException;
	
	public void updateSystemProperty(PropertyDTOIfc pdt1 , String current_prop_key) throws RemoteException;
}
