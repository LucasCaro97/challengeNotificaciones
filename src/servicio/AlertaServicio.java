package servicio;

import javafx.scene.control.Alert;
import modelo.Alerta;
import modelo.Tema;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlertaServicio implements IAlertaServicio{

    List<Alerta> alertaList = new ArrayList<>();

    @Override
    public List<Alerta> obtenerPorUsuario(Usuario usuario) {
        List<Alerta> alertasDelUsuario = new ArrayList<>();
        for (Alerta item: alertaList ) {
            if(item.getUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())) alertasDelUsuario.add(item);
        }
        if(alertasDelUsuario.size() > 0) return alertasDelUsuario;
        else return null;
    }

    /**
    * Devuelve las alertas que tienen asignadas el tema buscado por el usuario
    * */
    @Override
    public List<Alerta> obtenerPorTema(String tema) {
        List<Alerta> alertasFiltradas = alertaList.stream().filter( item -> item.getTema().getNombre().equals(tema)).collect(Collectors.toList());
        return alertasFiltradas;
    }

    @Override
    public boolean marcarComoLeida(Integer id) {
        boolean bandera = false;
        for (int i = 0; alertaList.size() > i ; i++) {
            if(alertaList.get(i).getId() == id.longValue()){
                alertaList.get(i).setLeida(true);
                bandera = true;
            }
        }
        return bandera;
    }

    /**
    * Valido que el campo descripcion no este vacio, caso verdadero procedo a generar 1 alerta para cada usuario,
    * caso contrario retorno false
    * */
    @Override
    public boolean crearAlertaGlobal(Alerta alerta, Map<String, Usuario> usuarioList) {
        if(validarCamposVacios(alerta)){
            for (Usuario item: usuarioList.values()) {
                Alerta nuevaAlerta = new Alerta();
                Integer id = alertaList.size() + 1;
                nuevaAlerta.setId(id.longValue());
                nuevaAlerta.setTema(alerta.getTema());
                nuevaAlerta.setDescripcion(alerta.getDescripcion());
                nuevaAlerta.setFechaYHoraExpiracion(alerta.getFechaYHoraExpiracion());
                nuevaAlerta.setTipoAlerta(alerta.getTipoAlerta());
                nuevaAlerta.setAlcance(alerta.getAlcance());
                nuevaAlerta.setLeida(false);
                nuevaAlerta.setUsuario(item);
                alertaList.add(nuevaAlerta);
            }
        return true;
        }
        return false;
    }

    /**
     * Valido que el campo descripcion no este vacio, caso verdadero procedo a generar la alerta para el usuario establecido,
     * caso contrario retorno false
     * */
    @Override
    public boolean crearAlertaIndividual(Alerta alerta) {
        if(validarCamposVacios(alerta)){
            Alerta nuevaAlerta = new Alerta();
            Integer id = alertaList.size() + 1;
            nuevaAlerta.setId(id.longValue());
            nuevaAlerta.setTema(alerta.getTema());
            nuevaAlerta.setDescripcion(alerta.getDescripcion());
            nuevaAlerta.setFechaYHoraExpiracion(alerta.getFechaYHoraExpiracion());
            nuevaAlerta.setTipoAlerta(alerta.getTipoAlerta());
            nuevaAlerta.setAlcance(alerta.getAlcance());
            nuevaAlerta.setLeida(false);
            nuevaAlerta.setUsuario(alerta.getUsuario());
            alertaList.add(nuevaAlerta);
            return true;
        }

        return false;
    }

    @Override
    public boolean validarCamposVacios(Alerta alertaDTO) {
        if (alertaDTO.getDescripcion().equals("")) {
            System.out.println("No se puede enviar una alerta con la descripcion vacia");
            return false;
        }
        return true;
    }

}
