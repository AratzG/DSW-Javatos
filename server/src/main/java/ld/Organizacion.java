package ld;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PersistenceCapable
public class Organizacion {

    @PrimaryKey
    private int idOrg;

    private String nomOrg;
    private String descripcion;
    private Date fechaCreacion;

    @Join
    @Persistent(mappedBy="orgsUsuario", dependentElement="true")
    private List<Usuario> miembrosOrg = new ArrayList<>();

    public Organizacion() {
    }

    public Organizacion(int idOrg, String nomOrg, String descripcion, Date fechaCreacion) {
        this.idOrg = idOrg;
        this.nomOrg = nomOrg;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(int idOrg) {
        this.idOrg = idOrg;
    }

    public String getNomOrg() {
        return nomOrg;
    }

    public void setNomOrg(String nomOrg) {
        this.nomOrg = nomOrg;
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
}
