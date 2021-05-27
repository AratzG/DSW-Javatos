package dao;

import gateway.GithubGateway;
import ld.Commit;
import ld.Usuario;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommitDAO {

    public static void extraerCommits() {
        limpiarBD();

        List<Commit> listaCommits = new ArrayList<>();

        try {
            //en este ejemplo, descargaremos todos los commits de nuestro repositorio
            GithubGateway c1 = new GithubGateway("repos/AratzG/DSW-Javatos/commits");
            Response res = c1.makeGetRequest("");

            JSONArray array = res.readEntity(JSONArray.class);

            HashMap<String, String> hm = (HashMap<String, String>)array.get(0);

            Commit commit = new Commit();

            System.out.println(hm);
            System.out.println("-----------------");

            //ID del commit
            commit.setIdCommit(hm.get("node_id"));
            System.out.println("ID del commit: " + commit.getIdCommit());

            //Nombre del commit
            String[] obtenerMensaje = hm.toString().split("message=");
            String[] mensajeObtenido = obtenerMensaje[1].split(",");
            commit.setNomCommit(mensajeObtenido[0]);
            System.out.println("Nombre del commit: " + commit.getNomCommit());

            //Usuario que hace el commit, para ello habrá que descargar dicho usuario desde API Github
            String[] obtenerUsuario = hm.toString().split("login=");
            String[] usuarioObtenido = obtenerUsuario[1].split(",");
            commit.setUsuario(UsuarioDAO.extraerUnUsuario("users/" + usuarioObtenido[0]));
            System.out.println("Usuario del commit: " + commit.getUsuario().getNomUsuario());

            //Repo del commit
            commit.setRepo(RepositorioDAO.extraerUnRepo("repos/AratzG/DSW-Javatos"));
            System.out.println("Repo del commit: " + commit.getRepo().getNomRepo());

            listaCommits.add(commit);

        } catch (Exception e) {
            System.out.println("Catched exception: " + e.getMessage());
        }

        rellenarBD(listaCommits);
    }

    public static void limpiarBD() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = null;

        Transaction tx = null;

        //Primero limpiamos los usuarios de la BD
        try {
            System.out.println("- Cleaning the DB...");

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            tx.begin();

            Query<Commit> query1 = pm.newQuery(Commit.class);
            System.out.println(" * '" + query1.deletePersistentAll() +
                    "' users and their accounts deleted from the DB.");

            tx.commit();
        } catch (Exception ex) {
            System.err.println(" $ Error cleaning the DB: " + ex.getMessage());
            ex.printStackTrace();
        }

        finally
        {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }
    }

    public static void rellenarBD(List<Commit> listaCommits) {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = null;

        Transaction tx = null;

        //Después, rellenamos la BD
        try
        {
            System.out.println("- Store objects in the DB");

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            tx.begin();

            //Persistimos en la BD cada uno de los usuarios descargados
            for(int i=0;i<listaCommits.size();i++) {
                pm.makePersistent(listaCommits.get(i));
            }

            tx.commit();
        } catch (Exception ex) {
            System.err.println(" $ Error storing objects in the DB: " + ex.getMessage());
            ex.printStackTrace();
        }

        finally
        {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }
    }
}
