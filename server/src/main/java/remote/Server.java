package remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Server extends UnicastRemoteObject implements IServer {

    private String serverName;

    public Server() throws RemoteException {
        super();
    }

    public Server(String serverName) throws RemoteException {
        super();
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hola, mundo";
    }

    @Override
    public void extraerDatos() throws RemoteException {

    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            IServer stub = (IServer) UnicastRemoteObject.exportObject(server, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
