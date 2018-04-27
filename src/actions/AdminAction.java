package actions;


import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import actionsIfc.AdminActionIfc;
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
import dtos.ActivityDTO;


public class AdminAction extends Action implements AdminActionIfc{

	public AdminAction() throws RemoteException {
		super();
		
	}

	public AccountDTOIfc addNewClient(ClientDTOIfc cdto , AccountDTOIfc adto , double firstDeposit){
		
		ClientDBManager cdb1 = new ClientDBManager();
		PropertyDBManager pdb1 = new PropertyDBManager();
		
		boolean isClientInDB = cdb1.isThereNameAndPassword( cdto.getClient_name(), cdto.getPassword() ) != 0;
		if(isClientInDB){
			cdto = cdb1.getClient(cdto.getClient_name(), cdto.getPassword());
		}
		
		String prop_val_regular = pdb1.getProperty("regular_deposit_rate").getProp_value();
		String prop_val_gold = pdb1.getProperty("gold_deposit_rate").getProp_value();
		String prop_val_platinum =	pdb1.getProperty("platinum_deposit_rate").getProp_value();	
		
		
		
		if(firstDeposit >= Double.parseDouble( prop_val_platinum.substring(0, prop_val_platinum.length()-1) )){//trimming the dolar sign
			
				cdto.setType("PLATINUM");
			
		}else if( firstDeposit >= Double.parseDouble( prop_val_gold.substring(0, prop_val_gold.length()-1) ) ){//trimming the dolar sign
			
			cdto.setType("GOLD");
		}
		else if( firstDeposit >= Double.parseDouble( prop_val_regular.substring(0, prop_val_regular.length()-1) ) ){ //trimming the dolar sign
			
			cdto.setType("REGULAR");
			
		}
		
		if(isClientInDB){
			cdb1.updateClient(cdto);
			adto.setClient_id(cdto.getClient_id());
			adto.setBalance(firstDeposit);
			return adto;
		}
		
		int client_id = cdb1.insertClient(cdto);
		if(client_id > 0){
			adto.setClient_id(client_id);
			adto.setBalance(firstDeposit);
			return adto;
		}
		
		return null;
		
	}
	
	
	
	
	
	
	public String removeClient(ClientDTOIfc cdto){
		double[] amount;
		double[] commission;
		double allCommission = 0;
		double allAmount = 0;
		
		ClientDBManager cdb = new ClientDBManager();
		
		ClientDTOIfc clientFromDB = cdb.getClient( cdto.getClient_name(), cdto.getPassword() );
		if(clientFromDB == null)
			return null; //"Action Failed - Client is not registered";
		
		AccountDBManager adb1 = new AccountDBManager();
		DepositDBManager ddb1 = new DepositDBManager();
		ActivityDBManager actdbm = new ActivityDBManager();
		
		DepositDTOIfc[] dt1 = ddb1.getDeposits(clientFromDB.getClient_id());
		AccountDTOIfc[] adt2 = adb1.getAccounts(clientFromDB.getClient_id());
		ActivityDTOIfc[] actdtos = actdbm.getActivities(clientFromDB.getClient_id());
		
//		if(adt2.length == 0){
//			return null;//"Action Failed - Client has no accounts";
//		}
		
		if((adt2.length == 0) && (dt1.length == 0)&& (actdtos.length == 0) ){
			cdb.deleteClient(clientFromDB.getClient_id());
			throw new IllegalStateException("client has no account nor deposits");
			
		}else if ( (adt2.length == 0) && (dt1.length == 0) ){
			throw new IllegalStateException("client has no account but has deposits or activities"); //illegal state
		}
		
		
		amount = new double[dt1.length];
		commission = new double[dt1.length];
		
		PropertyDBManager pdb1 = new PropertyDBManager();
		
		String regular_commission = pdb1.getProperty("regular_deposit_commission").getProp_value();
		String gold_commission = pdb1.getProperty("gold_deposit_commission").getProp_value();
		String platinum_commission = pdb1.getProperty("platinum_deposit_commission").getProp_value();		
		
		
			for(int i =0; i <  dt1.length; i++){
				
				amount[i] = dt1[i].getBalance();
				
				if(dt1[i].getType().equalsIgnoreCase("REGULAR")){
					commission[i] = Double.parseDouble( regular_commission.substring(0, regular_commission.length()-1) ) 
							* amount[i];
				}
				if(dt1[i].getType().equalsIgnoreCase("GOLD")){
					commission [i]= Double.parseDouble( gold_commission.substring(0, gold_commission.length()-1) )
							* amount[i];
				}
				if(dt1[i].getType().equalsIgnoreCase("PLATINUM")){
					commission[i] = Double.parseDouble(  platinum_commission.substring(0, platinum_commission.length()-1) )
							* amount[i];
				}	
			
			}
		
		
		
		for(int i = 0; i < dt1.length; i++){
			ddb1.deleteDeposit(dt1[i].getDeposit_id());
		}	
		for(int i = 0; i <dt1.length; i++ ){
				
				allCommission = allCommission + commission[i];
				allAmount = allAmount + amount[i];
		}
		if(adt2.length > 0){
			adt2[0].setBalance(adt2[0].getBalance() - allCommission);
			adt2[0].setBalance(adt2[0].getBalance() + allAmount);
			adb1.updateAccount(adt2[0]);
		}else{
			ActivityDTOIfc adto = new ActivityDTO();
			adto.setClient_id(clientFromDB.getClient_id());
			adto.setActivity_date(new Timestamp(new Date().getTime()));
			adto.setAmount(allAmount);
			adto.setCommision(allCommission);
			adto.setDescription("Action Succeeded - Commission charged on pre open deposits and Deposits Amount");
			actdbm.insertActivity(adto);
		}
		
		
		return "Action Succeeded - Commission charged on pre open deposits : "+allCommission +" Deposits Amount : "+allAmount;
	}
	
public boolean createNewAccount(ClientDTOIfc cdto  , AccountDTOIfc adto){
		
		AccountDBManager adbm = new AccountDBManager();
		PropertyDBManager pdbm = new PropertyDBManager();
		
		ClientDBManager cdbm = new ClientDBManager();
		ClientDTOIfc clientFromDB = cdbm.getClient(adto.getClient_id());
		//if client details in db are different than what was given to method
		if( !clientFromDB.getClient_name().equals(cdto.getClient_name()) ||   
			!clientFromDB.getPassword().equals(cdto.getPassword()) 		){
			return false;
		}
		if(clientFromDB.getClient_id() != adto.getClient_id()){
			return false;
		}
		 
//		if(clientFromDB.getType().equals("REGULAR")){
//			adto.setCredit_limit(100000);
//		}else if(clientFromDB.getType().equals("GOLD")){
//			adto.setCredit_limit(1000000);
//		}else if(clientFromDB.getType().equals("PLATINUM")){
//			adto.setCredit_limit(Double.MAX_VALUE);	
//		}
		String regular_credit_limit = pdbm.getProperty("regular_credit_limit").getProp_value();
		double reg_cre_lim_double = Double.parseDouble( regular_credit_limit.substring(0, regular_credit_limit.length()-1) );
		String gold_credit_limit = pdbm.getProperty("gold_credit_limit").getProp_value();
		double gold_cre_lim_double = Double.parseDouble( gold_credit_limit.substring(0, gold_credit_limit.length()-1) );
		String platinum_credit_limit = pdbm.getProperty("platinum_credit_limit").getProp_value();
		double plat_cre_lim_double;
		if(platinum_credit_limit.equals("unlimited")){
			plat_cre_lim_double = Double.MAX_VALUE; 
		}else{
			return false; //error in property
		}
		
		if(clientFromDB.getType().equals("REGULAR")){
			adto.setCredit_limit(reg_cre_lim_double);
		}else if(clientFromDB.getType().equals("GOLD")){
			adto.setCredit_limit(gold_cre_lim_double);
		}else if(clientFromDB.getType().equals("PLATINUM")){
			adto.setCredit_limit(plat_cre_lim_double);	
		}
		boolean ans = adbm.insertAccount(adto);
		return ans;
		
	}//public void createNewAccount(ClientDTO dt1 , AccountDTO adt2){	
	
	public boolean removeAccount(ClientDTOIfc clientIfc ){
		
		ClientDBManager cdb = new ClientDBManager();
		//DepositDBManager ddb = new DepositDBManager();
		//DepositDBManager ddb1 = new DepositDBManager();
		AccountDBManager adb1 = new AccountDBManager();
		
		ClientDTOIfc clientFromDB = cdb.getClient(clientIfc.getClient_name(), clientIfc.getPassword());
		
		if(clientFromDB == null){
			return false;
		}
		//DepositDTOIfc[] dt1 = ddb.getDeposits(clientFromDB.getClient_id());
		AccountDTOIfc[] adt2 = adb1.getAccounts(clientFromDB.getClient_id());
		
		
		//for(int i = 0; i < dt1.length; i++){
		//	adt2.setBalance( adt2.getBalance() + dt1[i].getBalance() );
		//}
		//adb1.updateAccount(adt2);
		
		
		
		//for(int i=0; i < dt1.length;i++){
		//	ddb1.deleteDeposit(dt1[i].getDeposit_id());
		//}
		double allBalance = 0;
		for(int i = 0; i < adt2.length ; i++){
				allBalance = allBalance +adt2[i].getBalance(); 
		}
		ActivityDBManager acdbm = new ActivityDBManager();
		if( allBalance < 0 ){
			
			ActivityDTO actdto = new ActivityDTO();
			
			
			
			actdto.setClient_id(clientFromDB.getClient_id());
			actdto.setAmount(allBalance);
			actdto.setActivity_date( new Timestamp( new Date().getTime() ) );
			actdto.setCommision(allBalance);//it is not written in the project description what is the commission on negative balance
			actdto.setDescription("Commission charged due to negative balance account on client removal");
			
			acdbm.insertActivity(actdto);
			
		}
		for(int i = 0; i < adt2.length; i++){
			adb1.deleteAccount(adt2[i].getAccount_id());
		}
		if( acdbm.getActivities(clientFromDB.getClient_id()).length == 0 ){
			cdb.deleteClient(clientFromDB.getClient_id());
		}
		return true;
	}
	
	public ClientDTOIfc[] viewAllClientsDetails(){
		
		ClientDBManager cdb1 = new ClientDBManager();
		
		return cdb1.getAllClients();
		
	}
	
	public AccountDTOIfc[] viewAllAccountsDetails(){
		
		AccountDBManager adb1 = new AccountDBManager();
		
		return adb1.getAllAccounts();
		
	}
	
	public DepositDTOIfc[] viewAllDepositsDetails(){
		
		DepositDBManager ddb2 = new DepositDBManager();
		
		return ddb2.getAllDeposits();
		
	}
	
	public ActivityDTOIfc[] viewAllActivitiesDetails(){
		
		ActivityDBManager adb1 = new ActivityDBManager();
		
		return adb1.getAllActivities();
		
	}
	
	public void updateSystemProperty(PropertyDTOIfc pdto , String current_prop_key){
		
		PropertyDBManager pdb2 = new PropertyDBManager();
		pdb2.updateProperty(pdto , current_prop_key);
		
	}
	
}
