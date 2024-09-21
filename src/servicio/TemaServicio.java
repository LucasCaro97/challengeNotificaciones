package servicio;

import modelo.Tema;

import java.util.*;

public class TemaServicio implements ITemaServicio{

    /*
    * Mapa de temas para almacenar en memoria todos los temas creados,
    * sin que existan duplicados
    * */
    private Set<Tema> temaList = new HashSet<>();

    public TemaServicio() {
    }

    /**
    * Metodo para listar todos los temas en memoria
    * */
    @Override
    public Set<Tema> obtenerTodos() {
        if(verificarExistenciaDeDatos()) return temaList;
        else return null;
    }

    /**
    * Metodo para obtener por nombre un tema en especifico
    * */
    @Override
    public Tema obtenerPorNombre(String nombre) {
        for (Tema item: temaList) {
            if(item.getNombre().equals(nombre)){
                return item;
            }
        }
        return null;
    }

    /**
    * Metodo para crear un nuevo tema. Primero verifica que el nombre no este vacio,
    * luego verifica que no exista un nombre con el mismo valor, caso contrario devuelvo false.
    * */
    @Override
    public boolean crear(Tema tema) {
        if(tema.getNombre().equals("")){
            System.out.println("El nombre no puede estar vacio");
            return false;
        }
        if(verificarDuplicados(tema.getNombre())){
            System.out.println("Ya existe un tema con el mismo nombre");
            return false;
        }
        temaList.add(tema);
        return true;
    }

    /*
    * Metodo para iterar el set de temas y eliminar en caso de que se encuentre el elemento buscado,
    * caso contrario retorno false
    * */
    @Override
    public boolean eliminar(String nombre) {
        Iterator<Tema> iterator = temaList.iterator();
        while(iterator.hasNext()){
            Tema tema = iterator.next();
            if(tema.getNombre().equals(nombre)){
                iterator.remove();
                return true;
            }
        }
        System.out.println("No existe el registro que desea eliminar");
        return false;

    }

    /**
     *Verificamos que la lista de temas este vacia para contuniar el flujo, caso contrario devolvemos false
     */
    @Override
    public boolean verificarExistenciaDeDatos() {
        if(!temaList.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
    * Verificamos que no exista un tema con la misma clave unica "nombre",
    * caso contrario devolvemos false
    * */
    @Override
    public boolean verificarDuplicados(String nombre) {
        for (Tema item: temaList) {
            if(item.getNombre().equals(nombre)) return true;
        }
        return false;
    }

    /**
    * Verifico que mi lista de temas no este vacia para mostrarlas en consola,
    * caso contrario informo que la lista esta vacia
    * */
    @Override
    public void mostrarTemas() {
        Set<Tema> temaList = this.obtenerTodos();
        if(temaList == null){
            System.out.println("Su lista de temas esta vacia");
        }else{
            System.out.println("---LISTA DE TEMAS---");
            for (Tema item: temaList) {
                System.out.println(item);
            }
        }
    }
}
