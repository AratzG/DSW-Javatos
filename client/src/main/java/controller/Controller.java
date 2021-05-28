package main.java.controller;

import main.java.lp.VentanaExtraccion;
import serviceLocator.ServiceLocator;

import java.rmi.RemoteException;

public class Controller {
    private ServiceLocator serviceLocator;

    public Controller(String[] args) {
        this.serviceLocator = new ServiceLocator();
        this.serviceLocator.setService(args);
        this.serviceLocator.getService();
        VentanaExtraccion ventanaExtraccion = new VentanaExtraccion(this);
    }

    public void extraerDatos() {

    }

    public static void main(String[] args) {
        Controller controller = new Controller(args);
    }
}
