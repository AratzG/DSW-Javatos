package serviceLocator;

import remote.IServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServiceLocator {

    IServer stubServer = null;

    public void setService(String[] args) {

        try
        {
            Registry registry = LocateRegistry.getRegistry(((Integer.valueOf(args[1]))));
            String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
            //stubServer = (IServer) java.rmi.Naming.lookup(name);
            stubServer = (IServer) registry.lookup(name);
            System.out.println("* Message coming from the server: '" + stubServer.sayHello() + "'");

        }
        catch (Exception e)
        {
            System.err.println("- Exception running the client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public IServer getService(){
       return stubServer;
    }
}
