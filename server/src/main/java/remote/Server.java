package remote;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Server extends UnicastRemoteObject implements IServer {

    private String serverName;

    public Server(String serverName) throws RemoteException {
        super();
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Se ha establecido la conexión";
    }

    @Override
    public void extraerDatos() throws RemoteException {

    }
}
