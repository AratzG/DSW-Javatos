package appServices;

import dao.RepositorioDAO;
import ld.Repositorio;
import ld.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioService {

    private List<Repositorio> repos = new ArrayList<>();

    public RepositorioService() {
        //rellenar lista con BD
        RepositorioDAO.extraerRepositorios();
    }

    public synchronized void crearRepo() {
        Repositorio repo = new Repositorio();
        //añadir variables
        repos.add(repo);
    }

    public synchronized void borrarRepo(int id) {
        for(int i=0;i<repos.size();i++) {
            if(repos.get(i).getIdRepo() == id) {
                repos.remove(repos.get(i));
                break;
            }
        }
    }

    public synchronized Repositorio devolverRepo(int id) {
        Repositorio repo = null;

        for(int i=0;i<repos.size();i++) {
            if(repos.get(i).getIdRepo() == id) {
                repo = repos.get(i);
                break;
            }
        }
        return repo;
    }
}
