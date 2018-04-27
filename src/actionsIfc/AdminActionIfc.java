package actionsIfc;

import java.rmi.RemoteException;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;


public interface AdminActionIfc extends ActionIfc {
	/**
	 * This function adds a new client to the mbank application.
	 * This operation is done by administrators only. When creating new client, a new account
	 * must be also created for it. Clients with no account are illegal in the system. When creating
	 * new client, it must deposit some amount of money in his new account. That amount of 
	 * money determines the client type. System properties provide information about how much
	 * money is counted as REGULAR client and also for GOLD and PLATINUM.
	 * Each client must use a unique username-password combination
	 * 
	 * @param cdto A client's object which contains the client's details for the client to be added
	 * @param adto An account object for the client to be added
	 * @param firstDeposit The initial amount of money which the client deposits at the account's opening position
	 * @return An account's object with the details of the opened account (with the client id in particular)
	 * @throws RemoteException
	 */
	public AccountDTOIfc addNewClient(ClientDTOIfc cdto , AccountDTOIfc adto , double firstDeposit) throws RemoteException;
	/**
	 * This function removes a client from mbank application.
	 * The client to be removed is the client which is details are in <b><code>cdto</code></b>
	 * Client removal forces the removal of his deposits and account. This operation is done by
	 * administrator. If deposits are being stopped before time (pre-opened), which might be the
	 * case when client with deposits wants to un-register from MBank system, a commission for
	 * deposit pre-opening will be charged for each deposit, before removing it. The commission
	 * rate for pre-opening deposit before its time is one of the system properties-
	 * There are regular_deposit_comission , gold_deposit_comission and platinum_deposit_comission.
	 * 
	 * @param cdto A client's object which contains the client's details for the client to be removed
	 * @return on success returns this string which contains all the amount of all commission and amount of all deposits :
	 * "Action Succeeded - Commission charged on pre open deposits : "+allCommission +" Deposits Amount : "+allAmount.
	 * on failure returns null
	 * @throws RemoteException
	 */
	public String removeClient(ClientDTOIfc cdto) throws RemoteException;
	/**
	 * This function creates a new account for the created new client.
	 * This operation is done by administrators as part of the adding new client process. Each
	 * client has only one account. When creating new account, the amount of money the client 
	 * deposits will set both client and account types. Regular client have special commission rates
	 * and credits, so does Gold and Platinum clients. Each client account has a credit-limit. Client 
	 * cannot get to an account balance lower than credit-limit. The credit-limit of the new account 
	 * will be set according to the system properties for each client type :
	 * regular_credit_limit , gold_credit_limit and platinum_credit_limit.
	 * 
	 * @param cdto A client's object which contains the new client's details
 	 * @param adto An account object for the account to be created
	 * @return true on success or false on failure
 	 * @throws RemoteException
	 */
	public boolean createNewAccount(ClientDTOIfc cdto  , AccountDTOIfc adto) throws RemoteException;
	/**
	 * This function removes an account from the mbank application.
	 * This operation is done by administrators as part of client removal process. This is done after 
	 * removing all existing client deposits (if any). In case of negative balance, which means that
	 * the client owe money to the bank, the money will be charged immediately. Imagine that the 
	 * client must give cash money to pay the bill and close the account. This means that Activities
	 * table is reported due to bank income(as a commission). Therefore, you must insert a new 
	 * row to the activity table with the following data, just before removing the account :
	 * client_id , amount , activity_date , commission ,description.
	 * In case of positive balance account, this part is not required - the client doesn't owe 
	 * anything to the bank so there is no income as part of the removal process and no effect on
	 * Activities table.  
	 * 
	 * @param clientIfc A client's object which contains the client's details for the client which its account needed to be removed
	 * @return true on success or false on failure
	 * @throws RemoteException
	 */
	public boolean removeAccount(ClientDTOIfc clientIfc) throws RemoteException;
	/**
	 * This function returns all the clients' details that are on mbank application.
	 * Admin can get a full client report.
	 * 
	 * @return an array of clients' objects if successful or null otherwise
	 * @throws RemoteException
	 */
	public ClientDTOIfc[] viewAllClientsDetails() throws RemoteException;
	/**
	 * This function returns all accounts' details that are on mbank application.
	 * Admin can get a full account report.
	 * 
	 * @return an array of accounts' objects if successful or null otherwise
	 * @throws RemoteException
	 */
	public AccountDTOIfc[] viewAllAccountsDetails() throws RemoteException;
	/**
	 * This function returns all deposits' details that are on mbank application.
	 * Admin can get a full deposit report.
	 * 
	 * @return an array of deposits' objects if successful or null otherwise
	 * @throws RemoteException
	 */
	public DepositDTOIfc[] viewAllDepositsDetails() throws RemoteException;
	/**
	 * This function returns all activities that are on mbank application.
	 * Admin can get a full activities report.
	 * 
	 * @return an array of activities' objects if successful or null otherwise
	 * @throws RemoteException
	 */
	public ActivityDTOIfc[] viewAllActivitiesDetails() throws RemoteException;
	/**
	 * This function updates any system property.
	 * Admin can update any system property.
	 * 
	 * @param pdto A property object for the property to be updated
	 * @param current_prop_key The current key for the property to be updated
	 * @throws RemoteException
	 */
	public void updateSystemProperty(PropertyDTOIfc pdto , String current_prop_key) throws RemoteException;
}
