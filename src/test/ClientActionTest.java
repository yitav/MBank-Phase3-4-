package test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtos.AccountDTO;
import dtos.ClientDTO;
import dtos.DepositDTO;
import actions.AdminAction;
import actions.ClientAction;

public class ClientActionTest {

	ClientAction clientAction = null;
	AdminAction admin = null;
	
	AccountDTOIfc adto = null;
	ClientDTOIfc cdto = null;
	int i = 0;
	
	
	@Before
	public void setUp() throws Exception {
			
			clientAction = new ClientAction();
			admin = new AdminAction();
			
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
			
	}

	@After
	public void tearDown() throws Exception {
		clientAction = null;
		admin = null;
		adto = null;
		cdto = null;
	}

	@Test
	public void testWithdrawFromAccount() {
		double firstDeposit = 30000;
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, firstDeposit);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		
		try {
			double amountToWithdraw = 5000;
			assertTrue(clientAction.withdrawFromAccount(cdto.getClient_name(), cdto.getPassword(), 0, amountToWithdraw));
			AccountDTOIfc[] accounts = clientAction.viewAccountDetails(cdto.getClient_name(), cdto.getPassword());
			//because there is a commission the balance is less or equal
			assertTrue(accounts[0].getBalance() <= (firstDeposit - amountToWithdraw));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		
		
	}

	@Test
	public void testDepositToAccount() {
		double firstDeposit = 30000;
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, firstDeposit);
		ClientDTOIfc cdtoInDB = admin.viewClientDetails(cdto.getClient_name(), cdto.getPassword());
		assertNotNull(cdtoInDB);
		assertTrue(admin.createNewAccount(cdtoInDB, adtoAns));
		
		double amountToDeposit = 10000;
		assertTrue(clientAction.depositToAccount(cdto.getClient_name(), cdto.getPassword(), 0, amountToDeposit));
		AccountDTOIfc[] accounts = clientAction.viewAccountDetails(cdto.getClient_name(), cdto.getPassword());
		assertTrue((accounts[0].getBalance()> firstDeposit));
		assertTrue(accounts[0].getBalance() <= (firstDeposit +amountToDeposit));
					
	}

	@Test
	public void testCreateNewDeposit() {
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, 50000);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		
		DepositDTOIfc deposit = new DepositDTO();
		deposit.setBalance(35000);
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
	public void testPreOpenDeposit() {
		double firstDeposit = 100000;
		AccountDTOIfc adtoAns = admin.addNewClient(cdto, adto, firstDeposit);
		assertNotNull(adtoAns);
		assertTrue( admin.createNewAccount(cdto, adtoAns) );
		
		DepositDTOIfc deposit = new DepositDTO();
		double depositAmount = 25000;
		deposit.setBalance(depositAmount);
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.YEAR , 2);
		
		Timestamp timestampDate = new Timestamp( cal.getTimeInMillis() );
		deposit.setClosing_date(timestampDate);
		assertTrue(clientAction.createNewDeposit(cdto.getClient_name(), cdto.getPassword(), deposit));
		
		assertTrue(clientAction.preOpenDeposit(cdto.getClient_name(), cdto.getPassword(), 0, 0));
		
		AccountDTOIfc[] accounts = clientAction.viewAccountDetails(cdto.getClient_name(), cdto.getPassword());
		assertTrue(accounts[0].getBalance() > firstDeposit);
		assertTrue(accounts[0].getBalance() <= firstDeposit + depositAmount);
		
		DepositDTOIfc[] deposits = clientAction.viewClientDeposits(cdto.getClient_name(), cdto.getPassword());
		assertTrue(deposits.length == 0);
		
	}

}
