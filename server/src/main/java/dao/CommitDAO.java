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

            for(int i=0;i<array.size();i++) {
                HashMap<String, String> hm = (HashMap<String, String>)array.get(i);

                Commit commit = new Commit();

                //ID del commit
                //commit.setIdCommit(hm.get("node_id"));
                commit.setIdCommit(i);

                //Nombre del commit
                String[] obtenerMensaje = hm.toString().split("message=");
                String[] mensajeObtenido = obtenerMensaje[1].split(",");
                commit.setNomCommit(mensajeObtenido[0]);

                listaCommits.add(commit);
            }
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
                    "' commits deleted from the DB.");

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
            System.out.println("- Store commits in the DB");

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            tx.begin();

            //Persistimos en la BD cada uno de los usuarios descargados
            for(int i=0;i<listaCommits.size();i++) {
                pm.makePersistent(listaCommits.get(i));
            }

            tx.commit();
        } catch (Exception ex) {
            System.err.println(" $ Error storing commits in the DB: " + ex.getMessage());
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
