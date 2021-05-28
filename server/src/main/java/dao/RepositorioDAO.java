package dao;
import gateway.GithubGateway;
import ld.Repositorio;
import ld.Usuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioDAO {

    public static void extraerRepositorios() {
        limpiarBD();

        List<Repositorio> listaRepos = new ArrayList<>();

        try {
            //a modo de ejemplo, descargaremos todos los repos de RiotGames
            GithubGateway g1 = new GithubGateway("orgs/riotgames/repos");
            Response res = g1.makeGetRequest("");

            JSONArray array = res.readEntity(JSONArray.class);

            for(int i=0;i<array.size();i++) {
                HashMap<String, String> hm = (HashMap<String, String>) array.get(i);
                Repositorio repo = new Repositorio();
                repo.setIdRepo(i);
                repo.setNomRepo(hm.get("name"));
                repo.setDescripcion(hm.get("description"));

                listaRepos.add(repo);
            }

        } catch (Exception e) {
            System.out.println("Catched exception: " + e.getMessage());
        }

        rellenarBD(listaRepos);
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

            Query<Repositorio> query1 = pm.newQuery(Repositorio.class);
            System.out.println(" * '" + query1.deletePersistentAll() +
                    "' repositories deleted from the DB.");

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

    public static void rellenarBD(List<Repositorio> listaRepos) {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = null;

        Transaction tx = null;

        //Después, rellenamos la BD
        try
        {
            System.out.println("- Store repositories in the DB");

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            tx.begin();

            //Persistimos en la BD cada uno de los usuarios descargados
            for(int i=0;i<listaRepos.size();i++) {
                pm.makePersistent(listaRepos.get(i));
            }

            tx.commit();
        } catch (Exception ex) {
            System.err.println(" $ Error storing repositories in the DB: " + ex.getMessage());
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