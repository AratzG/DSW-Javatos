package remote;

import java.rmi.RemoteException;

public interface IServer {
    public void extraerDatos() throws RemoteException;
    String sayHello() throws RemoteException;
}
