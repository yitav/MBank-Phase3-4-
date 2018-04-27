package main;

import ifc.MBankIfc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Timer;
//
//import dailyThread.DepositsMantainer;


public class Main {

	//cmd >> start rmiregistry
	public static void main(String[] args) {
		
//		Calendar cal = Calendar.getInstance();
//		Date date = new Date(new Date().getTime());
//		cal.setTime(date);
//		cal.add(Calendar.DATE, 1);
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		
//		Timer timer = new Timer();
//		timer.schedule(new DepositsMantainer(), cal.getTime());
		
		try {
			LocateRegistry.createRegistry(8000);
			System.setProperty("java.rmi.server.hostname","127.0.0.1");
			
			MBankIfc mbank = MBankST.getInstance();
			
			Naming.rebind("rmi://127.0.0.1:8000/mbank", mbank);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server is up");
		
	}

}
