package appServices;

import dao.OrganizacionDAO;
import ld.Organizacion;
import ld.Usuario;

import java.util.ArrayList;
import java.util.List;

public class OrganizacionService {

    private List<Organizacion> orgs = new ArrayList<>();

    public OrganizacionService() {
        OrganizacionDAO.extraerOrgs();
        //ahora, una vez rellena la BD, rellenar la lista con la info de la BD
    }

    public synchronized void crearOrg() {
        Organizacion org = new Organizacion();
        //añadir variables
        orgs.add(org);
    }

    public synchronized void borrarOrg(int id) {
        for(int i=0;i<orgs.size();i++) {
            if(orgs.get(i).getIdOrg() == id) {
                orgs.remove(orgs.get(i));
                break;
            }
        }
    }

    public synchronized Organizacion devolverOrg(int id) {
        Organizacion org = null;

        for(int i=0;i<orgs.size();i++) {
            if(orgs.get(i).getIdOrg() == id) {
                org = orgs.get(i);
                break;
            }
        }
        return org;
    }
}
