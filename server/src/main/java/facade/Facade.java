package facade;

import appServices.*;
import dao.UsuarioDAO;
import gateway.GithubGateway;
import ld.Organizacion;
import ld.Repositorio;
import ld.Usuario;
import remote.IServer;

import javax.jdo.*;
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
        crearUsuarioService();
        crearOrganizacionService();
        crearRepositorioService();
        crearTopicoService();
        crearCommitService();
        crearEquipoService();
    }

    public static void crearCommitService(){
        CommitService commitService = new CommitService();
    }

    public static void crearEquipoService(){
        EquipoService equipoService = new EquipoService();
    }

    public static void crearOrganizacionService(){
        OrganizacionService organizacionService = new OrganizacionService();
    }

    public static void crearRepositorioService(){
        RepositorioService repositorioService = new RepositorioService();
    }

    public static void crearTopicoService(){
        TopicoService topicoService = new TopicoService();
    }

    public static void crearUsuarioService(){
        UsuarioService usuarioService = new UsuarioService();
    }

    public static void main(String[] args) {
        crearUsuarioService();
        crearOrganizacionService();
        //crearEquipoService();
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
