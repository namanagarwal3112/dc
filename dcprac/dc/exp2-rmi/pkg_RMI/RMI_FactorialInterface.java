package pkg_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_FactorialInterface extends Remote {

    public int getFactorial(int input) throws RemoteException;

}