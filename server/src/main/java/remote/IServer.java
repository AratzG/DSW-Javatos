package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    public void extraerDatos() throws RemoteException;
    String sayHello() throws RemoteException;
}
