package dao;

import gateway.GithubGateway;
import ld.Organizacion;
import ld.Usuario;
import org.json.simple.JSONObject;

import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class OrganizacionDAO {

    public static void extraerOrgs() {
        limpiarBD();

        List<Organizacion> listaOrgs = new ArrayList<>();

        int aux = 0;

        //obtenemos un determinado número de usuarios desde github
        for(int i=1;i<82;i++) {
            aux = i;
            try {
                GithubGateway c1 = new GithubGateway("organizations/" + aux);
                Response res1 = c1.makeGetRequest("");

                //obtenemos la respuesta como objeto JSON
                JSONObject o = res1.readEntity(JSONObject.class);

                //creamos un usuario para cada objeto JSON que obtenemos
                Organizacion org = new Organizacion();

                if(o.get("id") != null) org.setIdOrg(aux);
                if(o.get("login") != null) org.setNomOrg(o.get("login").toString());
                if(o.get("description") != null) org.setDescripcion(o.get("company").toString());

                listaOrgs.add(org);

            } catch (Exception e) {
                System.out.println("Catched exception: " + e.getMessage());
                e.printStackTrace();
            }
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
