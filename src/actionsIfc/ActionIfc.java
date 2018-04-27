package actionsIfc;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;


public interface ActionIfc extends Remote {
	/**
	 * This function updates a client details by the details given in <b><code>cdt</code></b>
	 * 
	 * 
	 * @param cdt The client object which contains the details to be updated
	 * @return true if the update was successful, false otherwise
	 * @throws RemoteException
	 */
	public boolean updateClientDetails(ClientDTOIfc cdt) throws RemoteException;
	/**
	 * This function returns a client object with details of the requested client 
	 * according to its name and password by <b><code>client_name</code></b> and <b><code>password</code></b>
	 * 
	 * @param client_name The name of the client whose details needed to be viewed
	 * @param password The password of the client whose details needed to be viewed
	 * @return The details of the requested client if successful or null otherwise
	 * @throws RemoteException
	 */
	public ClientDTOIfc viewClientDetails(String client_name , String password) throws RemoteException;
	/**
	 * This function returns an account object with details of the account 
	 * for the requested client according to its name and password 
	 * by <b><code>client_name</code></b> and <b><code>password</code></b>
	 * 
	 * @param client_name  The name of the client whose account needed to be viewed
	 * @param password The password of the client whose account needed to be viewed
	 * @return The details of the account for the requested client if successful or null otherwise
	 * @throws RemoteException
	 */
	public AccountDTOIfc[] viewAccountDetails(String client_name , String password) throws RemoteException;
	/**
	 * This function returns an array of deposits objects with details of each deposit 
	 * for the requested client according to its name and password 
	 * by <b><code>client_name</code></b> and <b><code>password</code></b>
	 * 
	 * @param client_name The name of the client whose deposits needed to be viewed
	 * @param password The password of the client whose deposits needed to be viewed
	 * @return The details of the deposits for the requested client if successful or null otherwise
	 * @throws RemoteException
	 */
	public DepositDTOIfc[] viewClientDeposits(String client_name , String password) throws RemoteException;
	/**
	 * This function returns an array of activities objects with details of each activity which was documented
	 * for the requested client according to its name and password 
	 * by <b><code>client_name</code></b> and <b><code>password</code></b>
	 * 
	 * @param client_name The name of the client whose activities needed to be viewed
	 * @param password The password of the client whose activities needed to be viewed
	 * @return The details of the activities that the requested client committed if successful or null otherwise
	 * @throws RemoteException
	 */
	public ActivityDTOIfc[] viewClientActivities(String client_name , String password) throws RemoteException;
	/**
	 * This function returns a property object for the key <b><code>prop_key</code></b> which was requested
	 * 
	 * @param prop_key The key for the property which is needed to be viewed
	 * @return A property object if successful or null otherwise
	 * @throws RemoteException
	 */
	public PropertyDTOIfc viewSystemProperty(String prop_key) throws RemoteException;
	
}
