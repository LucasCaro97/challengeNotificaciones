package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombreUsuario;
    private String contraseña;
    private List<Tema> tema = new ArrayList<>();


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<Tema> getTema() {
        return tema;
    }

    public void setTema(List<Tema> tema) {
        this.tema = tema;
    }

    public Usuario() {
    }


    @Override
    public String toString() {
        return nombreUsuario;
    }
}
