package test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import actions.AdminAction;
import actions.ClientAction;
//import dailyThread.DepositsMaintainer;
import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;
import dtos.AccountDTO;
import dtos.ClientDTO;
import dtos.DepositDTO;
import dtos.PropertyDTO;

public class AdminActionTest {

	AccountDTOIfc adto = null;
	ClientDTOIfc cdto = null;
	AdminAction admin = null;
	ClientAction clientAction = null;
	
	@Before
	public void setUp() throws Exception {
		adto = new AccountDTO();
		String comment = String.format("%.5f", Math.random());
		String password = String.format("%.5f", Math.random());
		String address = String.format("%.5f", Math.random());
		String email = String.format("%.5f", Math.random());
		String phone = String.format("%.5f", Math.random());
		adto.setComment( "junitTest "+ comment );
		
		cdto = new ClientDTO();
		cdto.setClient_name(comment);
		cdto.setPassword(comment+" "+password);
		cdto.setAddress(comment+" "+address);
		cdto.setEmail("testEmail@test.email "+email);
		cdto.setPhone(comment +" "+phone);
		cdto.setComment(comment);
		
		admin = new AdminAction();
		clientAction = new ClientAction();
	}

	@After
	public void tearDown() throws Exception {
		adto = null;
		cdto = null;
		
	}

	@Test
	public void testAddNewClient() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		
		assertTrue(cdto.getType().equals("REGULAR"));
		assertTrue(cdto.getAddress().equals(cdtoInDB.getAddress()));
		assertTrue(cdto.getEmail().equals(cdtoInDB.getEmail()));
		assertTrue(cdto.getPhone().equals(cdtoInDB.getPhone()));
		assertTrue(cdto.getComment().equals(cdtoInDB.getComment()));
		
	}

	@Test
	public void testRemoveClient() {
		
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		AccountDTOIfc[] adtosInDB = admin.viewAccountDetails(cdtoInDB.getClient_name(), cdtoInDB.getPassword());
		assertTrue(adtosInDB.length == 1);
		assertNotNull( admin.removeClient(cdtoInDB) );
		DepositDTOIfc[] deposits = admin.viewClientDeposits(cdtoInDB.getClient_name(), cdtoInDB.getPassword());
		assertTrue(deposits.length == 0);
	}

	@Test
	public void testCreateNewAccount() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		AccountDTOIfc[] adtosInDB = admin.viewAccountDetails(cdto.getClient_name(), cdto.getPassword());
		assertTrue(adtosInDB.length == 1);
		assertTrue( adtosInDB[0].getComment().equals(adto.getComment()) );
		assertTrue( adtosInDB[0].getBalance() == 15000);
		assertTrue( adtosInDB[0].getCredit_limit() == 100000 );
	}

	@Test
	public void testRemoveAccount() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		AccountDTOIfc[] adtosInDB = admin.viewAccountDetails(cdtoInDB.getClient_name(), cdtoInDB.getPassword());
		assertTrue(adtosInDB.length == 1);
		assertNotNull( admin.removeClient(cdtoInDB) );
		assertTrue(admin.removeAccount(cdtoInDB));
		AccountDTOIfc[] accounts = admin.viewAccountDetails(cdtoInDB.getClient_name(), cdtoInDB.getPassword());
		assertTrue((accounts == null) || accounts.length == 0);
		ClientDTOIfc client = admin.viewClientDetails(cdtoInDB.getClient_name(), cdtoInDB.getPassword());
		
		assertNull(client);
		
//		assertNull(client.getClient_id());
//		assertNull(client.getClient_name());
//		assertNull(client.getPassword());
//		assertNull(client.getType());
//		assertNull(client.getAddress());
//		assertNull(client.getEmail());
//		assertNull(client.getPhone());
//		assertNull(client.getComment());
		
	}

	@Test
	public void testViewAllClientsDetails() {
		ClientDTOIfc[] clients = admin.viewAllClientsDetails();
		AccountDTOIfc[][] accounts = new AccountDTO[clients.length][];
		for(int i = 0; i < clients.length; i++){
			 accounts[i] = admin.viewAccountDetails( clients[i].getClient_name(), clients[i].getPassword() );
		}
		for(ClientDTOIfc client : clients){
			try{
				assertNotNull(admin.removeClient(client));
				assertTrue(admin.removeAccount(client));
			}catch(IllegalStateException e){
				System.out.println( e.getMessage() );
				//e.printStackTrace();
			}
			
		}
		ClientDTOIfc[] clientsAfterRemove = admin.viewAllClientsDetails();
		if(clientsAfterRemove.length == 0){
				assertTrue(true);
		}else{
			for(ClientDTOIfc client : clientsAfterRemove){
					ActivityDTOIfc[] activities = admin.viewClientActivities(client.getClient_name(), client.getPassword());
					assertTrue(activities.length > 0);
			}
		}
		
		for(int i = 0; i < clients.length; i++){
				
				if( accounts[i].length > 0){
					AccountDTOIfc accountAns = admin.addNewClient(clients[i], accounts[i][0], accounts[i][0].getBalance());
					assertNotNull(accountAns);
					assertTrue(admin.createNewAccount(clients[i], accountAns));
				}
		}
		
	}

	@Test
	public void testViewAllAccountsDetails() {
		AccountDTOIfc[] allAccounts = admin.viewAllAccountsDetails();
		ClientDTOIfc[] allClients = admin.viewAllClientsDetails();
		assertTrue(allClients.length >= allAccounts.length);
	}

	@Test
	public void testViewAllDepositsDetails() {
		DepositDTOIfc[] allDepositsBefore = admin.viewAllDepositsDetails();
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		
		DepositDTOIfc deposit = new DepositDTO();
		deposit.setBalance(20000);
		deposit.setClosing_date(new Timestamp( new Date().getTime() + (long)(Math.random()*100000) ) );
		assertTrue(clientAction.createNewDeposit(cdto.getClient_name(), cdto.getPassword(), deposit));
		DepositDTOIfc[] allDepositsAfter = admin.viewAllDepositsDetails();
		assertTrue( (allDepositsBefore.length+1 )== (allDepositsAfter.length) );

	}

	@Test
	public void testViewAllActivitiesDetails() {
		ClientDTOIfc[] clients = admin.viewAllClientsDetails();
		AccountDTOIfc[][] accounts = new AccountDTO[clients.length][];
		for(int i = 0; i < clients.length; i++){
			 accounts[i] = admin.viewAccountDetails( clients[i].getClient_name(), clients[i].getPassword() );
		}
		for(ClientDTOIfc client : clients){
			try{
				assertNotNull(admin.removeClient(client));
				assertTrue(admin.removeAccount(client));
			}catch(IllegalStateException e){
				System.out.println( e.getMessage() );
				//e.printStackTrace();
			}
			
		}
		ClientDTOIfc[] clientsAfterRemove = admin.viewAllClientsDetails();
		ActivityDTOIfc[] allActivities = admin.viewAllActivitiesDetails();
		
		assertTrue(allActivities.length >= clientsAfterRemove.length);
		
		for(int i = 0; i < clients.length; i++){
				
				if( accounts[i].length > 0){
					AccountDTOIfc accountAns = admin.addNewClient(clients[i], accounts[i][0], accounts[i][0].getBalance());
					assertNotNull(accountAns);
					assertTrue(admin.createNewAccount(clients[i], accountAns));
				}
		}
	}

	@Test
	public void testUpdateSystemProperty() {
		PropertyDTOIfc newProp = new PropertyDTO();
		newProp.setProp_key("admin_username");
		newProp.setProp_value("newValue");
		admin.updateSystemProperty(newProp , "admin_username");
		String propValFromDB = admin.viewSystemProperty(newProp.getProp_key()).getProp_value();
		assertTrue(propValFromDB.equals(newProp.getProp_value()));
		newProp.setProp_value("system");
		admin.updateSystemProperty(newProp , "admin_username");
		assertTrue( admin.viewSystemProperty("admin_username").getProp_value().equals("system") );
	}

	@Test
	public void testUpdateClientDetails() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		String updateAddress = "update "+String.format("%.5f", Math.random());
		String updateEmail = "update "+String.format("%.5f", Math.random());
		String updatephone = "update "+ String.format("%.5f", Math.random());
		String updateComment = "update "+String.format("%.5f", Math.random());
		
		cdto.setAddress(updateAddress);
		cdto.setEmail(updateEmail);
		cdto.setPhone(updatephone);
		cdto.setComment(updateComment);
		
		assertTrue(admin.updateClientDetails(cdto));
		ClientDTOIfc cdtoUpdated = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertTrue(cdtoUpdated.getAddress().equals(updateAddress));
		assertTrue(cdtoUpdated.getEmail().equals(updateEmail));
		assertTrue(cdtoUpdated.getPhone().equals(updatephone));
		assertTrue(cdtoUpdated.getComment().equals(updateComment));
		assertNotNull(admin.removeClient(cdtoUpdated));
		assertTrue(admin.removeAccount(cdtoUpdated));
	}

	@Test
	public void testViewClientDetails() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		assertTrue(cdto.getType().equals("REGULAR"));
		assertTrue(cdto.getAddress().equals(cdtoInDB.getAddress()));
		assertTrue(cdto.getEmail().equals(cdtoInDB.getEmail()));
		assertTrue(cdto.getPhone().equals(cdtoInDB.getPhone()));
		assertTrue(cdto.getComment().equals(cdtoInDB.getComment()));
	}

	@Test
	public void testViewAccountDetails() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		AccountDTOIfc[] adtosInDB =  admin.viewAccountDetails(cdtoInDB.getClient_name(), cdto.getPassword());
		
		assertTrue(adtosInDB[0].getClient_id() == adtoAns.getClient_id());
		assertTrue(adtosInDB[0].getBalance()== adtoAns.getBalance());
		assertTrue(adtosInDB[0].getComment().equals(adtoAns.getComment()));
		
		
	}

	@Test
	public void testViewClientDeposits() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		
		DepositDTOIfc deposit = new DepositDTO();
		deposit.setBalance(25000);
		Timestamp timestampDate = new Timestamp( new Date().getTime() + (long)(Math.random()*100000)+500000 );
		deposit.setClosing_date(timestampDate);
		assertTrue(clientAction.createNewDeposit(cdto.getClient_name(), cdto.getPassword(), deposit));
		
		DepositDTOIfc[] deposits = admin.viewClientDeposits(cdto.getClient_name(), cdto.getPassword());
		assertTrue( deposits[0].getBalance() == deposit.getBalance() );
		//System.out.println(deposits[0].getClosing_date());
		//System.out.println(deposit.getClosing_date());
		assertTrue( Math.abs( deposits[0].getClosing_date().getTime() - deposit.getClosing_date().getTime() ) < 10 );
		
	}

	@Test
	public void testViewClientActivities() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 15000);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		
		DepositDTOIfc deposit = new DepositDTO();
		deposit.setBalance(25000);
		Timestamp timestampDate = new Timestamp( new Date().getTime() + (long)(Math.random()*100000)+500000 );
		deposit.setClosing_date(timestampDate);
		//when creating a deposit activity table should be reported
		assertTrue(clientAction.createNewDeposit(cdto.getClient_name(), cdto.getPassword(), deposit));
		ActivityDTOIfc[] activities = admin.viewClientActivities(cdto.getClient_name(), cdto.getPassword());
		assertTrue(activities[0].getAmount() == deposit.getBalance());
		assertTrue(activities[0].getClient_id() == adtoAns.getClient_id());
	}

	@Test
	public void testViewSystemProperty() {
		assertTrue( admin.viewSystemProperty("admin_username").getProp_value().equals("system") );
	}

}
