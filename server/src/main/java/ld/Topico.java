package ld;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable
public class Topico {

    @PrimaryKey
    private int idTopico;

    private String nomTopico;

    @Join
    private List<Repositorio> repos = new ArrayList<>();

    public Topico() {
    }

    public Topico(int idTopico, String nomTopico) {
        this.idTopico = idTopico;
        this.nomTopico = nomTopico;
    }

    public int getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(int idTopico) {
        this.idTopico = idTopico;
    }

    public String getNomTopico() {
        return nomTopico;
    }

    public void setNomTopico(String nomTopico) {
        this.nomTopico = nomTopico;
    }
}
