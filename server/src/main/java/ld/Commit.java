package ld;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Commit
{

    @PrimaryKey
    private int idCommit;
    private String nomCommit;

    @Column(name="Usuario")
    private Usuario usuario;

    @Column(name="Repositorio")
    private Repositorio repo;

    public Commit() {
    }

    public Commit(int idCommit, String nomCommit, Usuario usuario, Repositorio repo) {
        this.idCommit = idCommit;
        this.nomCommit = nomCommit;
        this.usuario = usuario;
        this.repo = repo;
    }

    public int getIdCommit() {
        return idCommit;
    }

    public void setIdCommit(int idCommit) {
        this.idCommit = idCommit;
    }

    public String getNomCommit() {
        return nomCommit;
    }

    public void setNomCommit(String nomCommit) {
        this.nomCommit = nomCommit;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Repositorio getRepo() {
        return repo;
    }

    public void setRepo(Repositorio repo) {
        this.repo = repo;
    }
}
