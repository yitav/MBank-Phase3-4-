package actionsIfc;

import java.rmi.RemoteException;

import dtoIfc.DepositDTOIfc;


public interface ClientActionIfc extends ActionIfc {
	
	
	public boolean withdrawFromAccount
				(String client_name, String password ,int account_index , double withdraw)
				throws Exception , RemoteException;
	
	public boolean depositToAccount(String client_name, String password, int account_index , double deposit) throws RemoteException;
	
	public boolean createNewDeposit(String client_name , String password , DepositDTOIfc ddt1) throws RemoteException;
	
	public boolean preOpenDeposit(String client_name , String password ,int deposit_index , int account_index) throws RemoteException;
	
	
}
