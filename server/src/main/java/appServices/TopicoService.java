package appServices;

import ld.Topico;

import java.util.List;
import java.util.ArrayList;

public class TopicoService {
    private List<Topico> topicos = new ArrayList<>();

    public TopicoService(){
        //acceso DAO
    }

    public synchronized void crearTopico(){
        Topico topico = new Topico();
        //crear variables
        topicos.add(topico);
    }

    public synchronized void borrarTopico(int id){
        for(int i=0; i<topicos.size(); i++){
            if(topicos.get(i).getIdTopico() == id){
                topicos.remove(topicos.get(i));
                break;
            }
        }
    }

    public synchronized Topico devolverTopico(int id){
        Topico topico = null;
        for(int i=0; i<topicos.size(); i++){
            if(topicos.get(i).getIdTopico() == id){
                topico = topicos.get(i);
                break;
            }
        }
        return topico;

    }

}
