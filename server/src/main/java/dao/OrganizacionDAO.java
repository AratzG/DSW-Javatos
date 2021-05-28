package dao;

import gateway.GithubGateway;
import ld.Organizacion;
import ld.Usuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizacionDAO {

    public static void extraerOrgs() {
        limpiarBD();

        List<Organizacion> listaOrgs = new ArrayList<>();

        try {
            GithubGateway g1 = new GithubGateway("organizations");
            Response res = g1.makeGetRequest("");

            JSONArray array = res.readEntity(JSONArray.class);
            System.out.println("Tamanyo del array: " + array.size());

            for(int i=0;i<array.size();i++) {
                HashMap<String, String> hm = (HashMap<String, String>) array.get(i);
                Organizacion org = new Organizacion();
                org.setIdOrg(i);
                org.setNomOrg(hm.get("login"));
                org.setDescripcion(hm.get("description"));

                listaOrgs.add(org);
            }

        }  catch (Exception e) {
            System.out.println("Catched exception: " + e.getMessage());
        }

        rellenarBD(listaOrgs);
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

            Query<Organizacion> query1 = pm.newQuery(Organizacion.class);
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

    public static void rellenarBD(List<Organizacion> listaOrgs) {
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
            for(int i=0;i<listaOrgs.size();i++) {
                pm.makePersistent(listaOrgs.get(i));
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
