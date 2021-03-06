package facade;

import appServices.*;
import remote.IServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Facade extends UnicastRemoteObject implements IServer {

    private String serverName;

    public Facade() throws RemoteException {
        super();
    }

    public Facade(String serverName) throws RemoteException {
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
        //creamos todos los AppServices
        UsuarioService usuarioService = new UsuarioService();
        OrganizacionService organizacionService = new OrganizacionService();
        RepositorioService repositorioService = new RepositorioService();
        CommitService commitService = new CommitService();
    }

    public static void main(String[] args) {
        //creamos todos los AppServices
        UsuarioService usuarioService = new UsuarioService();
        OrganizacionService organizacionService = new OrganizacionService();
        RepositorioService repositorioService = new RepositorioService();
        CommitService commitService = new CommitService();


        String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

        try
        {
            IServer server = new Facade();
            Registry registry = LocateRegistry.createRegistry((Integer.valueOf(args[1])));
            registry.rebind(name, server);
            System.out.println("* Server '" + name + "' active and waiting...");
        }
        catch (Exception e)
        {
            System.err.println("- Exception running the server: " + e.getMessage());
            e.printStackTrace();
        }

        /*
        //Conexión cliente-servidor
        try {
            Facade server = new Facade();
            IServer stub = (IServer) UnicastRemoteObject.exportObject(server, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
         */
    }
}
