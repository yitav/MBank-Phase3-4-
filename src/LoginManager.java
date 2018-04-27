

import ifc.MBankIfc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import actionsIfc.ActionIfc;
import actionsIfc.ClientActionIfc;




public class LoginManager {
	MBankIfc mbankStub = null;
	
	
	private static class SingletonHolder {
        private static LoginManager instance = new LoginManager();
    }
	
	private LoginManager(){
		try {
			mbankStub = (MBankIfc) Naming.lookup("rmi://localhost:8000/mbank");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static LoginManager getInstance() {
        return SingletonHolder.instance;
    }
	public ClientActionIfc tryLogin(String clientName , String password){
		ClientActionIfc ans = null;
		if(mbankStub == null){
			return null;
		}
		ActionIfc actionStub = null;
		try {
			
			actionStub = mbankStub.login(clientName, password);
			if(actionStub == null){
				return null;
			}else if(actionStub instanceof ClientActionIfc ){
				ans = (ClientActionIfc) actionStub; 
				
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ans;
	}
	
	
}
