package dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

public class DataAccessObject {
    // Load Persistence Manager Factory - referencing the Persistence Unit defined in persistence.xml
    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

    // Persistence Manager
    PersistenceManager pm = null;

    //Transaction to group DB operations
    Transaction tx = null;

    public DataAccessObject(){ }

    public void guardarDatos(){


        try
        {
            System.out.println("- Store objects in the DB");

            //Get the Persistence Manager
            pm = pmf.getPersistenceManager();

            //Obtain the current transaction
            tx = pm.currentTransaction();

            //Start the transaction
            tx.begin();

            //Descomentar para ejemplo de creacion de usuarios
            /*pm.makePersistent(usuario1);
            pm.makePersistent(usuario2);*/

            //End the transaction
            tx.commit();
        }

        catch (Exception ex)
        {
            System.err.println(" $ Error storing objects in the DB: " + ex.getMessage());
            ex.printStackTrace();
        }

        finally
        {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            if (pm != null && !pm.isClosed())
            {
                pm.close();
                // ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
            }
        }

    }

    public void buscarDatos(int id){

    }

    public void borrarDatos(){
      try
      {
         System.out.println("- Cleaning the DB...");
         //Get the Persistence Manager
         pm = pmf.getPersistenceManager();
         //Obtain the current transaction
         tx = pm.currentTransaction();
         //Start the transaction
         tx.begin();

         //Delete users from DB
         /*Query<Usuario> query1 = pm.newQuery(Usuario.class);
         System.out.println(" * '" + query1.deletePersistentAll() +
               "' users and their accounts deleted from the DB.");*/

         //End the transaction
         tx.commit();
      }

      catch (Exception ex)
      {
         System.err.println(" $ Error cleaning the DB: " + ex.getMessage());
         ex.printStackTrace();
      }

      finally
      {
         if (tx != null && tx.isActive())
         {
            tx.rollback();
         }

         if (pm != null && !pm.isClosed())
         {
            pm.close();
         }
      }

    }
}
