package actions;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import actionsIfc.ClientActionIfc;
import manager.AccountDBManager;
import manager.ActivityDBManager;
import manager.ClientDBManager;
import manager.DepositDBManager;
import manager.PropertyDBManager;
import dtoIfc.AccountDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtos.ActivityDTO;
import exceptions.WithdrawException;



public class ClientAction extends Action implements ClientActionIfc{
	
		
	public ClientAction() throws RemoteException {
		super();

	}

	public boolean withdrawFromAccount
				(String client_name, String password ,int account_index , double withdraw)
				throws Exception
		{
		double commission;
		ClientDBManager cdbm = new ClientDBManager();
		long client_id = cdbm.getClient( client_name, password ).getClient_id();
		
		
		PropertyDBManager pdb1 = new PropertyDBManager();
		String commission_rateStr = pdb1.getProperty("commission_rate").getProp_value();
		commission = Double.parseDouble( commission_rateStr.substring(0, commission_rateStr.length()-1) );
		
		AccountDBManager adb1 = new AccountDBManager();
		AccountDTOIfc adt1 = adb1.getAccounts(client_id)[account_index];
		
		
		double balance = adt1.getBalance();
		double creditLimit = adt1.getCredit_limit();
		
		if( (balance - withdraw - commission) >= (0 - creditLimit)){
			adt1.setBalance(adt1.getBalance() - commission);
			adt1.setBalance(adt1.getBalance() - withdraw);
			adb1.updateAccount(adt1);
			
			ActivityDTO actdt1 = new ActivityDTO();
			ActivityDBManager acdb1 = new ActivityDBManager();
			
			actdt1.setClient_id(client_id);
			actdt1.setAmount(withdraw);
			actdt1.setActivity_date( new Timestamp( new Date().getTime() ) );
			actdt1.setCommision(commission);
			actdt1.setDescription("Withdraw from account");
			
			acdb1.insertActivity(actdt1);
			return true;
		}
		throw new WithdrawException("balance will get lower than credit limit");
	}
	
	public boolean depositToAccount(String client_name, String password, int account_index , double deposit){
		double commission;
		long client_id;
		ClientDBManager cdbm = new ClientDBManager();
		AccountDBManager adbm = new AccountDBManager();
		client_id = cdbm.getClient(client_name, password).getClient_id();
		AccountDTOIfc adto = adbm.getAccounts(client_id)[account_index];
		
		PropertyDBManager pdb = new PropertyDBManager();
		String commission_rateStr = pdb.getProperty("commission_rate").getProp_value();
		commission = Double.parseDouble( commission_rateStr.substring(0, commission_rateStr.length()-1) );
		
		AccountDBManager adb = new AccountDBManager();
		
		adto.setBalance(adto.getBalance() - commission);
		adto.setBalance(adto.getBalance() + deposit);
		adb.updateAccount(adto);
		
		ActivityDTO actdt = new ActivityDTO();
		ActivityDBManager acdb = new ActivityDBManager();
		
		actdt.setClient_id(client_id);
		actdt.setAmount(deposit);
		actdt.setActivity_date( new Timestamp( new Date().getTime() ) );
		actdt.setCommision(commission);
		actdt.setDescription("Deposit to account");
		
		acdb.insertActivity(actdt);
		return true;
	}
	
	public boolean createNewDeposit(String client_name , String password , DepositDTOIfc ddto){
		double estimatedAmount = 0;
		double dailyInterest;
		double deposit = ddto.getBalance();
				
		ClientDBManager cdbm = new ClientDBManager();
		ClientDTOIfc clientFromDB = cdbm.getClient(client_name, password);
		
		DepositDBManager ddbm = new DepositDBManager();
		PropertyDBManager pdb1 = new PropertyDBManager();
		
		String regular_interest = pdb1.getProperty("regular_daily_interest").getProp_value();
		String gold_interest = pdb1.getProperty("gold_daily_interest").getProp_value();
		String platinum_interest = pdb1.getProperty("platinum_daily_interest").getProp_value();
		
		
		if(clientFromDB.getType().equalsIgnoreCase("PLATINUM")){
			dailyInterest = Double.parseDouble( platinum_interest.substring(0, platinum_interest.indexOf("/")) )
					/Double.parseDouble( platinum_interest.substring(platinum_interest.indexOf("/")+1, platinum_interest.length()) );
		}
		else if( clientFromDB.getType().equalsIgnoreCase("GOLD") ){
			dailyInterest = Double.parseDouble( gold_interest.substring(0, gold_interest.indexOf("/")) )
					/Double.parseDouble( gold_interest.substring(gold_interest.indexOf("/")+1, gold_interest.length()) );
		}
		else{//( clientFromDB.getType().equalsIgnoreCase("REGULAR") ){
			dailyInterest = Double.parseDouble( regular_interest.substring(0, regular_interest.indexOf("/")) )//format of interest is */*
							/Double.parseDouble( regular_interest.substring(regular_interest.indexOf("/")+1, regular_interest.length()) );
		}
		
		long dateNow = new Date().getTime();
		double diffInDays = ( (ddto.getClosing_date().getTime() - dateNow)  / (1000 * 60 * 60 * 24) );
		double diffInYears = diffInDays/365;
		if(diffInYears <= 1){
			ddto.setType("SHORT");
		}else if(diffInYears <= 40){
			ddto.setType("LONG");
		}else{
			return false;
		}
		
		for(int i = 1; i < diffInDays; i++){
			estimatedAmount = estimatedAmount + (estimatedAmount * dailyInterest / 100);
		}
		
		
		ddto.setEstimated_balance( (long)estimatedAmount );
		ddto.setClient_id( clientFromDB.getClient_id() );
		ddto.setOpening_date( new Timestamp(dateNow) );
		
		ddbm.insertDeposit(ddto);
		
		
		ActivityDTO actdt1 = new ActivityDTO();
		ActivityDBManager acdb1 = new ActivityDBManager();
		
		//actdt1.setId( acdb1.getMaxId() + 1);
		actdt1.setClient_id(clientFromDB.getClient_id());
		actdt1.setAmount(deposit);
		actdt1.setActivity_date( new Timestamp( new Date().getTime() ) );
		actdt1.setCommision(0);
		actdt1.setDescription("Deposit to deposits");
		
		acdb1.insertActivity(actdt1);
		
		return true;
	}
	public boolean preOpenDeposit(String client_name , String password ,int deposit_index , int account_index){
		
		
		DepositDBManager ddb1 = new DepositDBManager();
		AccountDBManager adb2 = new AccountDBManager();
		PropertyDBManager pdb1 = new PropertyDBManager();
		ClientDBManager cdbm = new ClientDBManager();
		long client_id = cdbm.getClient(client_name, password).getClient_id();
		
		AccountDTOIfc adt2 = adb2.getAccounts(client_id)[account_index];
		DepositDTOIfc ddt1 = ddb1.getDeposits(client_id)[deposit_index];
		
		if(ddt1.getType().equalsIgnoreCase("LONG")){
			String feeStr = pdb1.getProperty("pre_open_fee").getProp_value();
			double deposit_balance = ddt1.getBalance();
			double preOpenFee = Double.parseDouble( feeStr.substring(0, feeStr.length()-1) );
			double commission = deposit_balance*preOpenFee/100;
			double amount = deposit_balance - commission;
			double newBalance = adt2.getBalance() + deposit_balance - commission;
			adt2.setBalance( newBalance );
			ddt1.setBalance(0);
			
			ddb1.deleteDeposit(ddt1.getDeposit_id());
			adb2.updateAccount(adt2);
			
			ActivityDTO actdt1 = new ActivityDTO();
			ActivityDBManager acdb1 = new ActivityDBManager();
			
			actdt1.setClient_id(client_id);
			actdt1.setAmount( amount );
			actdt1.setActivity_date( new Timestamp( new Date().getTime() ) );
			actdt1.setCommision(commission);
			actdt1.setDescription("Pre-Open deposit");
			
			acdb1.insertActivity(actdt1);
			return true;
		}
		return false;
	}
	
}
