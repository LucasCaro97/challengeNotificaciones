package servicio;

import modelo.Tema;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ITemaServicio {

    Set<Tema> obtenerTodos();
    Tema obtenerPorNombre(String nombre);
    boolean crear(Tema tema);
    boolean eliminar(String nombre);
    boolean verificarExistenciaDeDatos();
    boolean verificarDuplicados(String nombre);

    void mostrarTemas();



}
