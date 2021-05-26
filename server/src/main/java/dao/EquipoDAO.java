package dao;
import gateway.GithubGateway;
import ld.Equipo;
import org.json.simple.JSONObject;
import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public static void extraerEquipos() {
        limpiarBD();

        List<Equipo> listaEquipos = new ArrayList<>();

        int aux = 0;

        //obtenemos un determinado número de equipos desde github
        for(int i=1;i<82;i++) {
            aux = i;
            try {
                GithubGateway c1 = new GithubGateway("teams/" + aux);
                Response res1 = c1.makeGetRequest("");

                //obtenemos la respuesta como objeto JSON
                JSONObject o = res1.readEntity(JSONObject.class);

                //creamos un usuario para cada objeto JSON que obtenemos
                Equipo eq = new Equipo();

                if(o.get("id") != null) eq.setIdEquipo(aux);
                if(o.get("name") != null) eq.setNomEquipo(o.get("name").toString());
                if(o.get("description") != null) eq.setDescripcion(o.get("description").toString());

                listaEquipos.add(eq);

            } catch (Exception e) {
                System.out.println("Catched exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        rellenarBD(listaEquipos);
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

            Query<Equipo> query1 = pm.newQuery(Equipo.class);
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

    public static void rellenarBD(List<Equipo> listaEquipos) {
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
            for(int i=0;i<listaEquipos.size();i++) {
                pm.makePersistent(listaEquipos.get(i));
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
