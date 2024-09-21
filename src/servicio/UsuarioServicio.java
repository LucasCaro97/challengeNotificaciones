package servicio;

import DTO.UsuarioDTO;
import modelo.Tema;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioServicio implements IUsuarioServicio {

    /*
    * Mapa de usuarios para poder almacenar en memoria todos los usuarios creados,
    * sin que existan duplicados
    * */
    private Map<String, Usuario> usuarioList = new HashMap<>();

    public UsuarioServicio() {
    }

    /*
    * Metodo para listar todos los usuarios
    * */
    @Override
    public Map<String, Usuario> obtenerTodos() {
        if (verificarExistenciaDeDatos()) return usuarioList;
        else return null;
    }

    /*
    * Metodo para buscar usuarios por email
    * */
    @Override
    public Usuario obtenerPorUsuario(String nombreUsuario) {
        if (verificarExistenciaDeDatos()) {
            for (Usuario usuario : usuarioList.values()) {
                if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                    return usuario;
                }
            }
        }
        return null;
    }

    /*
    * Metodo para crear un nuevo usuario. En caso de tener email o contraseña vacias no se crea el usuario, o
    * en caso de ya existir un usuario con la clave unica, se informa del problema y devuelve false
    * */
    @Override
    public boolean crear(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getNombreUsuario().isEmpty() || usuarioDTO.getContraseña().isEmpty()){
            System.out.println("El correo y contraseña no pueden ser nulos");
            return false;
        }else if(verificarDuplicados(usuarioDTO.getNombreUsuario())){
            System.out.println("Ya existe un usuario con el email proporcionado");
            return false;
        }else{
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
            usuario.setContraseña(usuarioDTO.getContraseña());
            usuarioList.put(usuario.getNombreUsuario(), usuario);
            return true;
        }
    }

    /*
    * Metodo para eliminar un usuario en caso de encontrar el id del usuario proporcionado
    * */
    @Override
    public boolean eliminar(String nombreUsuario) {
        for (int i = 0; i <= usuarioList.size(); i++){
            if(usuarioList.get(i).getNombreUsuario() == nombreUsuario){
                usuarioList.remove(i);
                return true;
            }
        }
        System.out.println("No existe el registro que desea eliminar");
        return false;
    }

    /*
    * Verifico si existen datos en la tabla de usuarios para continuar el flujo, caso contrario informa que la tabla esta vacia
    * */
    @Override
    public boolean verificarExistenciaDeDatos() {
        if(!this.usuarioList.isEmpty()){
            return true;
        }else{
            System.out.println("La lista de usuarios esta vacia");
            return false;
        }
    }

    /*
    * Verificamos que no exista un usuario con la misma clave unica, caso contrario devolvemos false
    * */
    @Override
    public boolean verificarDuplicados(String email) {
        for (Usuario item: usuarioList.values()) {
            if (item.getNombreUsuario().equals(email)) return true;
        }
        return false;
    }

    /*
    * Metodo para mostrar la informacion del usuario en caso de ser encontrado
    * */
    @Override
    public void mostrarUsuario(Usuario usuarioEncontrado) {
            if(usuarioEncontrado != null) System.out.println("Usuario encontrado: " + usuarioEncontrado);
            else System.out.println("No se ha encontrado el usuario con la informacion proporcionada");
    }

    /*
    * Metodo para verificar al iniciar sesion la contraseña sea correcta con la que se seteo en el registro
    * */
    @Override
    public Usuario IniciarSesion(UsuarioDTO usuarioDTO) {
        //Valido que el usuario no ingrese campos vacios
        if(usuarioDTO.getNombreUsuario().equals("") || usuarioDTO.getContraseña().equals("")){
            System.out.println("El usuario y/o contraseña no pueden ser nulos");
            return null;
        }
        //Busco el usuario en la BD
        Usuario usuario = this.obtenerPorUsuario(usuarioDTO.getNombreUsuario());
        //Si existe el usuario, comparo si la contraseña brindada por el usuario coincide con la contraseña seteada en la BD
        if(usuario != null){
            if (usuario.getContraseña().equals(usuarioDTO.getContraseña())){
                System.out.println("Exito al iniciar sesion");
                return usuario;
            }
        }
        System.out.println("Usuario y/o contraseña Incorrecta");
        return null;
    }

    /**
    * Metodo para vincular un tema a la lista de temas de interes del usuario
    * */
    @Override
    public void vincularTema(Usuario usuario, Tema tema) {
        Usuario user = this.obtenerPorUsuario(usuario.getNombreUsuario());
        List<Tema> temaList = user.getTema();
        temaList.add(tema);
    }


}
