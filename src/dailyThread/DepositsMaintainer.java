package dailyThread;

import java.util.Date;
import java.util.TimerTask;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import manager.AccountDBManager;
import manager.ClientDBManager;
import manager.DepositDBManager;
import manager.PropertyDBManager;

public class DepositsMaintainer extends TimerTask {

	@Override
	public void run() {
		
		ClientDBManager cdbm = new ClientDBManager();
		DepositDBManager ddbm = new DepositDBManager();
		PropertyDBManager pdbm = new PropertyDBManager();
		AccountDBManager adbm = new AccountDBManager();
		double dailyInterest;
		
		while(true){
			Date dateNow = new Date();
			long timeStarted = dateNow.getTime();
			
			DepositDTOIfc[] allDeposits = ddbm.getAllDeposits();
			
			for(DepositDTOIfc deposit : allDeposits){
				
				ClientDTOIfc client = cdbm.getClient( deposit.getClient_id() );
				
				String regular_interest = pdbm.getProperty("regular_daily_interest").getProp_value();
				String gold_interest = pdbm.getProperty("gold_daily_interest").getProp_value();
				String platinum_interest = pdbm.getProperty("platinum_daily_interest").getProp_value();
				
				if( client.getType().equalsIgnoreCase("REGULAR") ){
					dailyInterest = Double.parseDouble( regular_interest.substring(0, regular_interest.indexOf("/")) )//format of interest is */*
									/Double.parseDouble( regular_interest.substring(regular_interest.indexOf("/"), regular_interest.length()) );
				}
				else if( client.getType().equalsIgnoreCase("GOLD") ){
						dailyInterest = Double.parseDouble( gold_interest.substring(0, gold_interest.indexOf("/")) )
								/Double.parseDouble( gold_interest.substring(gold_interest.indexOf("/"), gold_interest.length()) );
				}
				else {//if(clientFromDB.getType().equalsIgnoreCase("PLATINUM")){
					dailyInterest = Double.parseDouble( platinum_interest.substring(0, platinum_interest.indexOf("/")) )
							/Double.parseDouble( platinum_interest.substring(platinum_interest.indexOf("/"), platinum_interest.length()) );
				}
				
				double currBalance = deposit.getBalance();
				deposit.setBalance(currBalance + currBalance* dailyInterest / 100);
				
				ddbm.updateDeposit(deposit);
//				Date closingDate = deposit.getClosing_date();
//				if(closingDate.getYear() == dateNow.getYear()
//					&&closingDate.getMonth() == dateNow.getMonth()
//					&&closingDate.getDay() == dateNow.getDay()){
//				}
				long closingDate = deposit.getClosing_date().getTime();
				if( timeStarted - closingDate < 1000*60*60*24 ){
					
					ddbm.deleteDeposit( deposit.getDeposit_id() );
					AccountDTOIfc[] accounts = adbm.getAccounts(deposit.getClient_id());
					accounts[0].setBalance( accounts[0].getBalance()+ deposit.getBalance() );
					adbm.updateAccount(accounts[0]);
					
				}
		
			
				try {
					long timeEnded = new Date().getTime();
					//sleep for 24 hours minus the time which the thread was awake
					Thread.sleep( 1000*60*60*24 - (timeEnded-timeStarted) );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		

	}

}
