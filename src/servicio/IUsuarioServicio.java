package servicio;

import DTO.UsuarioDTO;
import modelo.Tema;
import modelo.Usuario;

import java.util.List;
import java.util.Map;

public interface IUsuarioServicio {

    Map<String, Usuario> obtenerTodos();
    Usuario obtenerPorUsuario(String email);
    boolean crear(UsuarioDTO usuario);
    boolean eliminar(String email);

    boolean verificarExistenciaDeDatos();
    boolean verificarDuplicados(String email);

    void mostrarUsuario(Usuario usuario);

    Usuario IniciarSesion(UsuarioDTO usuarioDTO);

    void vincularTema(Usuario usuario, Tema tema);
}
