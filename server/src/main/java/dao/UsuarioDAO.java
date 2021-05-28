package dao;

import gateway.GithubGateway;
import ld.Usuario;
import org.json.simple.JSONObject;

import javax.jdo.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static void extraerUsuarios() {
        limpiarBD();

        List<Usuario> listaUsuarios = new ArrayList<>();
        int aux = 0;

        //Vamos a obtener los 20 primeros usuarios (20 primeros IDs) de GitHub
        //algunos IDs ahora no existen, así que descargaremos menos de 20 usuarios
        for(int i=1;i<20;i++) {
            aux = i;
            try {
                GithubGateway c1 = new GithubGateway("user/" + aux);
                Response res1 = c1.makeGetRequest("");

                //obtenemos la respuesta como objeto JSON
                JSONObject u = res1.readEntity(JSONObject.class);

                //creamos un usuario para cada objeto JSON que obtenemos
                Usuario user = new Usuario();

                if(u.get("id") != null) user.setIdUsuario(aux);
                if(u.get("login") != null) user.setNomUsuario(u.get("login").toString());
                if(u.get("company") != null) user.setEmpresa(u.get("company").toString());
                if(u.get("location") != null) user.setLocalizacion(u.get("location").toString());
                if(u.get("email") != null) user.setEmail(u.get("email").toString());

                listaUsuarios.add(user);

            } catch (Exception e) {
                System.out.println("Catched exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        rellenarBD(listaUsuarios);
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

            Query<Usuario> query1 = pm.newQuery(Usuario.class);
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

    public static void rellenarBD(List<Usuario> listaUsuarios) {
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
            for(int i=0;i<listaUsuarios.size();i++) {
                pm.makePersistent(listaUsuarios.get(i));
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
