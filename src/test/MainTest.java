package test;

//import java.util.Date;
import java.sql.Date;
import java.sql.Timestamp;

import manager.AccountDBManager;
import manager.ActivityDBManager;
import manager.ClientDBManager;
import manager.DepositDBManager;
import manager.PropertyDBManager;
import dtoIfc.AccountDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtos.AccountDTO;
import dtos.ActivityDTO;
import dtos.ClientDTO;
import dtos.DepositDTO;
import dtos.PropertyDTO;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * test ClientDBManager
		 */
		
		ClientDBManager cdb = new ClientDBManager();
		//test isThereNameAndPassword
		
		//System.out.println( cdb.isThereNameAndPassword("Yossi Lev", "0123") );
		
		
		//test insertClient
//		ClientDTO c = new ClientDTO();
//		c.setClient_id(10);
//		c.setClient_name("Yossi Lev");
//		c.setPassword("0123");
//		c.setType("REGULAR");
//		c.setAddress("Tel Aviv Ya Habibi");
//		c.setEmail("yos@gmail.com");
//		c.setPhone("03-1234567");
//		c.setComment("test insert : Yossi Lev|0123|REGULAR"
//					+ "|Tel Aviv Ya Habibi|yos@gmail.com|03-1234567");
//		System.out.println( cdb.insertClient(c) );
		
		//ClientDTO c = new ClientDTO();
		//c.setClient_id(11);
		//c.setClient_name("Yosi Second");
		//c.setPassword("4567");
		//c.setType("GOLD");
		//c.setAddress("Ramat Gan Ya Habibi");
		//c.setEmail("yosSec@gmail.com");
		//c.setPhone("03-7654321");
		//c.setComment("test insert Second : Yosi Second|4567|GOLD"
		//			+ "|Ramat Gan Ya Habibi|yosSec@gmail.com|03-7654321");
		//cdb.insertClient(c);
		
		
		//test getClient
		//ClientDTO cg = cdb.getClient(2);
		//System.out.println(cg.toString());
		
		//test updateClient
		//ClientDTO cu = new ClientDTO();
		//cu.setClient_id(2);
		//cu.setClient_name("Update Yossi Lev");
		//cu.setPassword("Update 0123");
		//cu.setType("Update REGULAR");
		//cu.setAddress("Update Tel Aviv");
		//cu.setEmail("updateYossi@gmail.com");
		//cu.setPhone("Update 03-1234567");
		//cu.setComment("Updated");
		
		//cdb.updateClient(cu);
		
		//test getAllClients
		//ClientDTO cArr[]= null;
		//cArr = cdb.getAllClients();
		//for(ClientDTO client : cArr){
		//	System.out.println(client);
		//}
		
		//test deleteClient
		//cdb.deleteClient(2);
		//ClientDTO cArr[]= null; //see client was deleted
		//cArr = cdb.getAllClients();
		//for(ClientDTO client : cArr){
		//	System.out.println(client);
		//}	
		
		
		/**
		 * test AccountDBManager
		 */
		
		//test insertAccount
		
//		AccountDBManager adb = new AccountDBManager();
//		
//		AccountDTO a = new AccountDTO();
//		a.setAccount_id(10);
//		a.setClient_id(4);
//		a.setBalance(1.99);
//		a.setCredit_limit(3.95);
//		a.setComment("insert Account");
//		
//		adb.insertAccount(a);
		
//		AccountDTO a2 = new AccountDTO();
//		a2.setAccount_id(10);
//		a2.setClient_id(4);
//		a2.setBalance(99.1);
//		a2.setCredit_limit(95.3);
//		a2.setComment("2 insert Account");
//		
//		adb.insertAccount(a2);
		
		//test updateAccount
		
//		AccountDTO a3 = new AccountDTO();
//		a3.setAccount_id(2);
//		a3.setClient_id(4);
//		a3.setBalance(0.001);
//		a3.setCredit_limit(1.01);
//		a3.setComment("update Account");
//		
//		adb.updateAccount(a3);
		
		//test getAccounts
//		AccountDBManager adb = new AccountDBManager();
//		
//		AccountDTOIfc[] aArr = adb.getAccounts(4);
//		for(AccountDTOIfc account : aArr){
//			System.out.println(account);
//		}
		
		//test getAllAcounts
		//AccountDTO[] aArr = adb.getAllAccounts();
		
		//for(AccountDTO account: aArr){
		//	System.out.println(account);
		//}	
		
		
		//test deleteAccount
		
//		adb.deleteAccount(2);
//		
//		AccountDTO[] aArr = adb.getAllAccounts(); //check if account was deleted
//		
//		for(AccountDTO account: aArr){
//			System.out.println(account);
//		}	
		
		
		/**
		 * test DepositsDBManager
		 */
//		DepositDBManager ddb = new DepositDBManager();
		
		//test insertDeposit
		
//		DepositDTO d1 = new DepositDTO();
//		d1.setDeposit_id(10);
//		d1.setClient_id(3);
//		d1.setBalance(33.33);
//		d1.setType("SHORT");
//		d1.setEstimated_balance(100);
//		Timestamp t = new Timestamp(2016, 1, 2, 3, 4, 5, 6);
//		
//		d1.setOpening_date(t);
//		t = new Timestamp(2016, 2, 3, 4, 5, 6, 7);
//		
//		d1.setClosing_date(t);
		
//		DepositDTO d2 = new DepositDTO();
//		d2.setDeposit_id(10);
//		d2.setClient_id(3);
//		d2.setBalance(66.66);
//		d2.setType("LONG");
//		d2.setEstimated_balance(200);
//		t = new Timestamp(2017, 3, 4, 5, 6, 7, 8);
//		
//		d2.setOpening_date(t);
//		t = new Timestamp(2017, 4, 5, 6, 7, 8, 9);
//		
//		d2.setClosing_date(t);
//		
//		DepositDTO d3 = new DepositDTO();
//		d3.setDeposit_id(10);
//		d3.setClient_id(4);
//		d3.setBalance(99.99);
//		d3.setType("SHORT");
//		d3.setEstimated_balance(300);
//		t = new Timestamp(2018, 5, 6, 7, 8, 9, 10);
//		
//		d3.setOpening_date(t);
//		t = new Timestamp(2018, 6, 7, 8, 9, 10, 11);
//		
//		d3.setClosing_date(t);
		
//		ddb.insertDeposit(d1);
//		ddb.insertDeposit(d2);
//		ddb.insertDeposit(d3);
		
		
		
		
		
		
		//test update
		
//		DepositDBManager ddb = new DepositDBManager();
//		
//		DepositDTO d1 = new DepositDTO();
//		d1.setDeposit_id(1);
//		d1.setClient_id(3);
//		d1.setBalance(99.99);
//		d1.setType("LONG");
//		d1.setEstimated_balance(300);
//		Timestamp t = new Timestamp(118,5, 7, 8, 9, 10, 11); 
//		
//		d1.setOpening_date(t);
//		t = new Timestamp(118, 4, 6, 7, 8, 9, 10);
//		
//		d1.setClosing_date(t);
//		
//		
//		DepositDTO d2 = new DepositDTO();
//		d2.setDeposit_id(2);
//		d2.setClient_id(3);
//		d2.setBalance(33.33);
//		d2.setType("SHORT");
//		d2.setEstimated_balance(100);
//		
//		t = new Timestamp(117, 3, 5, 6, 7, 8, 9);
//		d2.setOpening_date(t);
//		
//		t = new Timestamp(117, 2, 4, 5, 6, 7, 8);
//		d2.setClosing_date(t);
//		
//		DepositDTO d3 = new DepositDTO();
//		d3.setDeposit_id(3);
//		d3.setClient_id(4);
//		d3.setBalance(66.66);
//		d3.setType("LONG");
//		d3.setEstimated_balance(200);
//		t = new Timestamp(116, 1, 3, 4, 5, 6, 7);
//		
//		d3.setOpening_date(t);
//		t = new Timestamp(116, 0, 2, 3, 4, 5, 6);
//		
//		d3.setClosing_date(t);
//		
//		ddb.updateDeposit(d1);
//		ddb.updateDeposit(d2);
//		ddb.updateDeposit(d3);
		
		
		//test getDeposits
		
//		DepositDBManager ddb = new DepositDBManager();
//		
//		DepositDTO[] dArr =   ddb.getDeposits(3);
//		
//		for(DepositDTO deposit : dArr){
//			System.out.println(deposit);
//		}
		
		//test getAllDeposits
		
//		DepositDBManager ddb = new DepositDBManager();
//		
//		DepositDTO[] dArr =   ddb.getAllDeposits();
//		
//		for(DepositDTO deposit : dArr){
//			System.out.println(deposit);
//		}
		
		//test deleteDeposit
		
		//DepositDBManager ddb = new DepositDBManager();
		
		//ddb.deleteDeposit(1);
		
		
		/**
		 * test ActivityDBManager
		 */
		
		//test insertActivity
		
//		ActivityDBManager adb = new ActivityDBManager();
//		
//		ActivityDTO a1 = new ActivityDTO();
//		a1.setId(10);
//		a1.setClient_id(3);
//		a1.setAmount(0.99);
//		a1.setActivity_date(new Timestamp(116 , 1 ,2 ,3 ,4 ,5, 6));
//		a1.setCommision(0.05);
//		a1.setDescription("insert 1");
//		
//		ActivityDTO a2 = new ActivityDTO();
//		a2.setId(10);
//		a2.setClient_id(3);
//		a2.setAmount(1.99);
//		a2.setActivity_date(new Timestamp(117 , 2 ,3 ,4 ,5 ,6, 7));
//		a2.setCommision(0.10);
//		a2.setDescription("insert 2");
//
//		ActivityDTO a3 = new ActivityDTO();
//		a3.setId(10);
//		a3.setClient_id(4);
//		a3.setAmount(3.99);
//		a3.setActivity_date(new Timestamp(118 , 3 ,4 ,5 ,6 ,7, 8));
//		a3.setCommision(0.15);
//		a3.setDescription("insert 3");
//		
//		adb.insertActivity(a1);
//		adb.insertActivity(a2);
//		adb.insertActivity(a3);
		
		//test updateActivity
		
//		ActivityDBManager adb = new ActivityDBManager();
//		
//		ActivityDTO a4 = new ActivityDTO();
//		a4.setId(1);
//		a4.setClient_id(3);
//		a4.setAmount(4.99);
//		a4.setActivity_date(new Timestamp(119 , 5 ,5 ,6 ,7 ,8, 9));
//		a4.setCommision(0.20);
//		a4.setDescription("update 1");
//		
//		adb.updateActivity(a4);
		
		//test getActivities
		
		
//		ActivityDBManager adb = new ActivityDBManager();
//		
//		ActivityDTO[] aArr = adb.getActivities(3);
//		for(ActivityDTO activity : aArr){
//			System.out.println(activity);
//		}
		
		
		//test getAllActivities
		
//		ActivityDBManager adb = new ActivityDBManager();
//		
//		ActivityDTO[] aArr = adb.getAllActivities();
//		for(ActivityDTO activity : aArr){
//			System.out.println(activity);
//		}
		
		//test deleteActivity
		
		//ActivityDBManager adb = new ActivityDBManager();
		
		//adb.deleteActivity(1);
		
		/**
		 * test Properties
		 */
		
		//test insert
		
//		PropertyDBManager pdb = new PropertyDBManager();
//		
//		PropertyDTO p1 = new PropertyDTO();
//		p1.setProp_key("reg");
//		p1.setProp_value("1000$");
//		
//		PropertyDTO p2 = new PropertyDTO();
//		p2.setProp_key("gold_deposit_rate");
//		p2.setProp_value("100000$");
//		
//		pdb.insertProperty(p1);
//		pdb.insertProperty(p2);
		
		//test update
		
//		PropertyDBManager pdb = new PropertyDBManager();
//		PropertyDTO p3 = new PropertyDTO();
//		p3.setProp_key("regular_deposit_rate");
//		p3.setProp_value("10000$");
//		
//		pdb.updateProperty(p3 , "reg");
		
		//test getProperty
		
//		PropertyDBManager pdb = new PropertyDBManager();
//		
//		PropertyDTO p4 = pdb.getProperty("gold_deposit_rate");
//		System.out.println(p4);
		
		
		//test getAllProperties
		
//		PropertyDBManager pdb = new PropertyDBManager();
//		PropertyDTO[] pArr = pdb.getAllProperties();
//		for(PropertyDTO prop : pArr){
//			System.out.println(prop);
//		}
		
		//test deleteProperty
		
//		PropertyDBManager pdb = new PropertyDBManager();
//		pdb.deleteProperty("gold_deposit_rate");
		
		
	}

}
