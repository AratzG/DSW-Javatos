package ld;

import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PersistenceCapable
public class Repositorio {

    @PrimaryKey
    private int idRepo;

    private String nomRepo;
    private String descripcion;
    private Date fechaCreacion;
    private Date ultimaActualizacion;

    @Column(name="Organizacion")
    private Organizacion org;

    @Column(name="Usuario")
    private Usuario usuario;

    @Join
    @Persistent(mappedBy="repos", dependentElement="true")
    private List<Topico> topicos = new ArrayList<>();

    @Join
    @Persistent(mappedBy="reposUsuario", dependentElement="true")
    private List<Usuario> miembrosRepo = new ArrayList<>();

    public Repositorio() {
    }

    public Repositorio(int idRepo, String nomRepo, String descripcion, Date fechaCreacion,
                       Date ultimaActualizacion, Organizacion org, Usuario usuario) {
        this.idRepo = idRepo;
        this.nomRepo = nomRepo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacion = ultimaActualizacion;
        this.org = org;
        this.usuario = usuario;
    }

    public int getIdRepo() {
        return idRepo;
    }

    public void setIdRepo(int idRepo) {
        this.idRepo = idRepo;
    }

    public String getNomRepo() {
        return nomRepo;
    }

    public void setNomRepo(String nomRepo) {
        this.nomRepo = nomRepo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Organizacion getOrg() {
        return org;
    }

    public void setOrg(Organizacion org) {
        this.org = org;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
