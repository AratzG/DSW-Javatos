package ld;

import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PersistenceCapable
public class Equipo {

    @PrimaryKey
    private int idEquipo;

    private String nomEquipo;
    private String descripcion;
    private Date fechaCreacion;

    @Column(name="Organizacion")
    private Organizacion org;

    @Join
    @Persistent(mappedBy="equiposUsuario", dependentElement="true")
    private List<Usuario> miembrosEquipo = new ArrayList<>();

    public Equipo() {
    }

    public Equipo(int idEquipo, String nomEquipo, String descripcion, Date fechaCreacion,
                  Organizacion org) {
        this.idEquipo = idEquipo;
        this.nomEquipo = nomEquipo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.org = org;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNomEquipo() {
        return nomEquipo;
    }

    public void setNomEquipo(String nomEquipo) {
        this.nomEquipo = nomEquipo;
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

    public Organizacion getOrg() {
        return org;
    }

    public void setOrg(Organizacion org) {
        this.org = org;
    }
}
