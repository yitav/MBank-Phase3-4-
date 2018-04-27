package actionsIfc;

import java.rmi.RemoteException;

import dtoIfc.DepositDTOIfc;


public interface ClientActionIfc extends ActionIfc {
	
	/**
	 * This function withdraws <b><code>withdraw</code></b> - an amount of money , from an account 
	 * which belongs to the requesting client.
	 * This action occurs when client wants to withdraw money from his account. Withdraw is
	 * allowed as long as account balance is greater than credit limit. The bank charges for all
	 * withdraw & deposit actions according to the bank commission rate property.
	 * Commission is charged from client account.
	 * 
	 * @param client_name The name of the client which needs to withdraw
	 * @param password The password of the client which needs to withdraw
	 * @param account_index The index of the account to be withdrawn from - should be 0 
	 * because every client has one account exactly
	 * @param withdraw The amount to be withdrawn
	 * @return true in case of success or false otherwise 
	 * @throws Exception
	 * @throws RemoteException
	 */
	public boolean withdrawFromAccount
				(String client_name, String password ,int account_index , double withdraw)
				throws Exception , RemoteException;
	/**
	 * This function deposits <b><code>deposit</code></b> - an amount of money , to requesting client's account.
	 * This action occurs when client wants to deposit money in his account. The bank charges for
	 * all withdraw & deposit actions according to the bank commission rate property. Commission 
	 * is charged from client account.
	 * 
	 * @param client_name The name of the client which needs to deposit
	 * @param password The password of the client which needs to deposit
	 * @param account_index The index of the account to be deposited to - should be 0 
	 * @param deposit The amount to be deposited
	 * @return true in case of success or false otherwise 
	 * @throws RemoteException
	 */
	public boolean depositToAccount(String client_name, String password, int account_index , double deposit) throws RemoteException;
	/**
	 * This function creates new deposit for the requesting client.
	 * Client may create as many deposits as it likes. The deposit interest is calculated according to
	 * the client type daily interest rate system property. Since MBank allows constant interest
	 * deposits only, the estimated amount of the deposit can be calculated by the time it is 
	 * created. There are the 3 daily interest rates system properties :
	 * regular_daily_interest , gold_daily_interest and platinum_daily_interest.
	 * Another important character of deposit - is the deposit type. Deposit type can be :
	 * Short - short term deposit, 1 year maximum. Cannot be pre-opened by clients.
	 * Clients are forced to wait to its closing date in order to get their money.
	 * Long - long term deposit, up to 40 years. Can be pre-opened by clients.
	 * 
	 * @param client_name The name of the client which needs to make a new deposit
	 * @param password The password of the client which needs to make a new deposit
	 * @param ddto a deposit object which contains the details of the needed deposit
	 * @return true in case of success or false otherwise
	 * @throws RemoteException
	 */
	public boolean createNewDeposit(String client_name , String password , DepositDTOIfc ddto) throws RemoteException;
	/**
	 * This function pre-opens a deposit for the requesting client.
	 * Pre-Open means that client can withdraw before deposit closing date. In this case the bank 
	 * will charge a pre-open fee as part of the deposit removal process. pre open fee is also a 
	 * system property : pre_open_fee.
	 * 
	 * @param client_name The name of the client which needs to pre-open the deposit
	 * @param password The password of the client which needs to pre-open the deposit
	 * @param deposit_index The index of the deposit that should be pre-opened
	 * @param account_index The index of the account the deposit should be inserted - should be 0 
	 * @return  true in case of success or false otherwise
	 * @throws RemoteException
	 */
	public boolean preOpenDeposit(String client_name , String password ,int deposit_index , int account_index) throws RemoteException;
	
	
}
