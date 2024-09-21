package modelo;

import Enums.Alcance;
import Enums.TipoAlerta;

import java.time.LocalDateTime;

public class Alerta {

    private Long id;
    private Tema tema;
    private String descripcion;
    private LocalDateTime fechaYHoraExpiracion;
    private TipoAlerta tipoAlerta;
    private Alcance alcance;
    private boolean leida;

    private Usuario usuario;

    @Override
    public String toString() {
        return "Alerta{" +
                "id=" + id +
                ", tema=" + tema +
                ", descripcion='" + descripcion + '\'' +
                ", fechaYHoraExpiracion=" + fechaYHoraExpiracion +
                ", tipoAlerta=" + tipoAlerta +
                ", alcance=" + alcance +
                ", leida=" + leida +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaYHoraExpiracion() {
        return fechaYHoraExpiracion;
    }

    public void setFechaYHoraExpiracion(LocalDateTime fechaYHoraExpiracion) {
        this.fechaYHoraExpiracion = fechaYHoraExpiracion;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public Alcance getAlcance() {
        return alcance;
    }

    public void setAlcance(Alcance alcance) {
        this.alcance = alcance;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public Alerta(Tema tema, String descripcion, LocalDateTime fechaYHoraExpiracion, TipoAlerta tipoAlerta, Alcance alcance, Usuario usuario) {
        this.tema = tema;
        this.descripcion = descripcion;
        this.fechaYHoraExpiracion = fechaYHoraExpiracion;
        this.tipoAlerta = tipoAlerta;
        this.alcance = alcance;
        this.usuario = usuario;
    }

    public Alerta() {
    }
}
