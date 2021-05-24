package remote;

import appServices.*;
import ld.Repositorio;

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

    public void crearCommitService(){
        CommitService commitService = new CommitService();
        //commitService.crearCommit();
    }

    public void crearEquipoService(){
        EquipoService equipoService = new EquipoService();
        //equipoService.crearEquipo();
    }

    public void crearOrganizacionService(){
        OrganizacionService organizacionService = new OrganizacionService();
        //organizacionService.crearOrganizacion();
    }

    public void crearRepositorioService(){
        RepositorioService repositorioService = new RepositorioService();
        //repositorioService.crearRepositorio();
    }

    public void crearTopicoService(){
        TopicoService topicoService = new TopicoService();
        //topicoService.crearTopico();
    }

    public void crearUsuarioService(){
        UsuarioService usuarioService = new UsuarioService();
        //
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
