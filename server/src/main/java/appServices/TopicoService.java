package appServices;

import ld.Equipo;
import ld.Topico;

import java.util.ArrayList;
import java.util.List;

public class TopicoService {

    private List<Topico> topicos = new ArrayList<>();

    public TopicoService() {
        //rellenar lista con BD
    }

    public synchronized void crearTopico() {
        Topico topic = new Topico();
        //añadir variables
        topicos.add(topic);
    }

    public synchronized void borrarTopico(int id) {
        for(int i=0;i<topicos.size();i++) {
            if(topicos.get(i).getIdTopico() == id) {
                topicos.remove(topicos.get(i));
                break;
            }
        }
    }

    public synchronized Topico devolverTopico(int id) {
        Topico topic = null;

        for(int i=0;i<topicos.size();i++) {
            if(topicos.get(i).getIdTopico() == id) {
                topic = topicos.get(i);
                break;
            }
        }
        return topic;
    }
}
