package appServices;

import ld.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    //hacer que esta lista se llene con los datos de la BD
    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        //rellenar la lista con lo que está en la BD
    }

    public synchronized void crearOrg() {
        Usuario user = new Usuario();
        //añadir variables
        usuarios.add(user);
    }

    public synchronized void borrarUsuario(int id) {
        for(int i=0;i<usuarios.size();i++) {
            if(usuarios.get(i).getIdUsuario() == id) {
                usuarios.remove(usuarios.get(i));
                break;
            }
        }
    }

    public synchronized Usuario devolverUsuario(int id) {
        Usuario user = null;

        for(int i=0;i<usuarios.size();i++) {
            if(usuarios.get(i).getIdUsuario() == id) {
                user = usuarios.get(i);
                break;
            }
        }
        return user;
    }
}
