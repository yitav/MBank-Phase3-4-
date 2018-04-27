package ifc;

import java.rmi.Remote;
import java.rmi.RemoteException;

import actionsIfc.ActionIfc;

//import actions.Action;
//import javax.ejb.Local;

/**
 *
 * @author yitav.eliyahu
 */

//@Local
public interface MBankIfc extends Remote {
    //public Action login( String userName , String password);
    public String helloWorld() throws RemoteException;
    public ActionIfc login( String userName , String password) throws RemoteException;

}
