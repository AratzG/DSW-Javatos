package dao;
import gateway.GithubGateway;
import ld.Repositorio;
import ld.Usuario;
import org.json.simple.JSONObject;
import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDAO {

    public static void extraerRepositorios() {
        limpiarBD();

        List<Repositorio> listaRepos = new ArrayList<>();

        int aux = 0;

        //obtenemos un determinado número de repositorios desde github
        for(int i=1;i<82;i++) {
            aux = i;
            try {
                GithubGateway c1 = new GithubGateway("repos/" + aux);
                Response res1 = c1.makeGetRequest("");

                //obtenemos la respuesta como objeto JSON
                JSONObject o = res1.readEntity(JSONObject.class);

                //creamos un usuario para cada objeto JSON que obtenemos
                Repositorio rep = new Repositorio();

                if(o.get("id") != null) rep.setIdRepo(aux);
                if(o.get("name") != null) rep.setNomRepo(o.get("name").toString());
                //(no he visto el atributo en la API)if(o.get("description") != null) rep.setDescripcion(o.get("description").toString());

                listaRepos.add(rep);

            } catch (Exception e) {
                System.out.println("Catched exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        rellenarBD(listaRepos);
    }

    public static Repositorio extraerUnRepo(String accessPoint) {
        //en este método descargamos un repositorio a partir del accessPoint

        List<Repositorio> listaRepos = new ArrayList<>();
        Repositorio repo = new Repositorio();

        try {
            GithubGateway c1 = new GithubGateway(accessPoint);
            Response res1 = c1.makeGetRequest("");

            //obtenemos la respuesta como objeto JSON
            JSONObject o = res1.readEntity(JSONObject.class);

            if(o.get("id") != null) repo.setIdRepo((int) o.get("id"));
            if(o.get("name") != null) repo.setNomRepo(o.get("name").toString());

            listaRepos.add(repo);

        } catch (Exception e) {
            System.out.println("Catched exception: " + e.getMessage());
            e.printStackTrace();
        }
        rellenarBD(listaRepos);
        return repo;
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
                    "' repositories  deleted from the DB.");

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
            System.out.println("- Store objects in the DB");

            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            tx.begin();

            //Persistimos en la BD cada uno de los usuarios descargados
            for(int i=0;i<listaRepos.size();i++) {
                pm.makePersistent(listaRepos.get(i));
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
