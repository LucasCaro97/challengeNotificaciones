package servicio;

import modelo.Alerta;
import modelo.Tema;
import modelo.Usuario;

import java.util.List;
import java.util.Map;

public interface IAlertaServicio {

    boolean crearAlertaGlobal(Alerta alerta, Map<String, Usuario> usuarioList);
    boolean crearAlertaIndividual(Alerta alerta);

    boolean validarCamposVacios(Alerta alertaDTO);

    List<Alerta> obtenerPorUsuario(Usuario usuario);

    List<Alerta> obtenerPorTema(String tema);

    boolean marcarComoLeida(Integer id);


}
