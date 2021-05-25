package ld;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable
public class Usuario {

    @PrimaryKey
    private int idUsuario;

    private String nomUsuario;
    private String empresa;
    private String localizacion;
    private String email;

    @Join
    private List<Organizacion> orgsUsuario = new ArrayList<>();
    @Join
    private List<Repositorio> reposUsuario = new ArrayList<>();
    @Join
    private List<Equipo> equiposUsuario = new ArrayList<>();
    private Organizacion org;


    public Usuario() {
    }

    public Usuario(int idUsuario, String nomUsuario, String empresa, String localizacion,
                   String email) {
        this.idUsuario = idUsuario;
        this.nomUsuario = nomUsuario;
        this.empresa = empresa;
        this.localizacion = localizacion;
        this.email = email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
