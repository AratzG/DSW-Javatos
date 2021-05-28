package ld;

import javax.jdo.annotations.*;
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

    @Persistent(mappedBy="usuario")
    private List<Commit> listaCommits = new ArrayList<>();

    @Join
    private List<Organizacion> orgsUsuario = new ArrayList<>();
    @Join
    private List<Repositorio> reposUsuario = new ArrayList<>();

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
