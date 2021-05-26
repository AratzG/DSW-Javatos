package appServices;

import dao.EquipoDAO;
import ld.Commit;
import ld.Equipo;

import java.util.ArrayList;
import java.util.List;

public class EquipoService {

    private List<Equipo> equipos = new ArrayList<>();

    public EquipoService() {
        //rellenar lista con BD
        EquipoDAO.extraerEquipos();
    }

    public synchronized void crearEquipo() {
        Equipo team = new Equipo();
        //añadir variables
        equipos.add(team);
    }

    public synchronized void borrarEquipo(int id) {
        for(int i=0;i<equipos.size();i++) {
            if(equipos.get(i).getIdEquipo() == id) {
                equipos.remove(equipos.get(i));
                break;
            }
        }
    }

    public synchronized Equipo devolverEquipo(int id) {
        Equipo team = null;

        for(int i=0;i<equipos.size();i++) {
            if(equipos.get(i).getIdEquipo() == id) {
                team = equipos.get(i);
                break;
            }
        }
        return team;
    }
}
