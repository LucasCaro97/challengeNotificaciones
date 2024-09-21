package servicio;

import DTO.UsuarioDTO;
import Enums.Alcance;
import Enums.TipoAlerta;
import modelo.Alerta;
import modelo.Tema;
import modelo.Usuario;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MenuPrincipal {


    UsuarioServicio usuarioServicio = new UsuarioServicio();
    TemaServicio temaServicio = new TemaServicio();
    AlertaServicio alertaServicio = new AlertaServicio();


    public void mostrarMenuInicioSesion(){

        Usuario usuarioLogueado = null;
        int opcionSeleccionada = 0;
        Scanner scanner = new Scanner(System.in);

        do{

            try{
                mostrarMenuSesion();
                opcionSeleccionada = scanner.nextInt();

                if(opcionSeleccionada == 1){
                    usuarioLogueado = usuarioServicio.IniciarSesion(formularioInicioSesion());
                }
                else if(opcionSeleccionada == 2){
                    if(usuarioServicio.crear(formularioRegistro())) System.out.println("Exito al crear el usuario");
                    else System.out.println("Error al crear el usuario");
                }

                if(usuarioLogueado!= null){
                    mostrarMenuDeUsuario(usuarioLogueado);
                }
            }catch (Exception e){
                System.out.println("Opcion ingresada incorrecta, ingrese solo numeros");
                scanner.next();
            }

        }while (usuarioLogueado == null && opcionSeleccionada!= 3 || opcionSeleccionada == 3);




    }


    /*
    * Muestra el menu para inicio de sesion o registro
    * */
    void mostrarMenuSesion(){
        System.out.println("");
        System.out.println("------BIENVENIDO AL SISTEMA DE NOTIFICIONES------");
        System.out.println("1- Iniciar Sesion");
        System.out.println("2- Registrar Usuario");
        System.out.println("3- Salir de la aplicacion");
        System.out.print("Ingrese una opcion del menu: ");
    }


    /*
     * Muestra el menu para para el usuario logueado
     * */
    void mostrarMenuDeUsuario(Usuario usuario){
        int opcionSeleccionada = 0;
        do{
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.println("");
                System.out.println("------BIENVENIDO " + usuario.getNombreUsuario() +" AL SISTEMA DE NOTIFICIONES------");
                System.out.println("1- Ver temas disponibles");
                System.out.println("2- Registrar un nuevo tema");
                System.out.println("3- Mis temas");
                System.out.println("4- Agregar temas a mi lista");
                System.out.println("5- Enviar nueva alerta");
                System.out.println("6- Ver alertas pendientes");
                System.out.println("7- Ver alertas pendientes filtradas por tema"); //PENDIENTE -> Preg si quiere ver todas o si quiere filtrar por tema. --> Informar si la alerta es global o individial
                System.out.println("8- Marcar alerta como leida");
                System.out.println("9- Salir de la aplicacion");
                System.out.print("Seleccione una opcion: ");
                opcionSeleccionada = scanner.nextInt();

                switch (opcionSeleccionada){
                    case 1:
                        /*
                         * Para el caso 1, le pido tema servicio que liste los temas en memoria
                         * */
                        temaServicio.mostrarTemas();
                        break;
                    case 2:
                        /*
                         * Para el caso 2, solicito el nombre del tema al usuario a traves del metodo formularioTema y se lo paso
                         * al metodo crear de temaServicio
                         * */
                        if(temaServicio.crear(formularioTema())) System.out.println("Exito al crear el tema");
                        else System.out.println("Error al crear el tema");
                        break;
                    case 3:
                        /*
                         * Obtengo los temas que tengo vinculados a mi usuario, en caso de estar vacio informo, que no cuenta con temas vinculados
                         * */
                        List<Tema> temaList = usuarioServicio.obtenerPorUsuario(usuario.getNombreUsuario()).getTema();
                        if(temaList.size() > 0){
                            System.out.println("Lista de temas de su interes:");
                            for (Tema item: temaList) {
                                System.out.println(item);
                            }
                        }else{
                            System.out.println("Usted no tiene temas asignados");
                        }
                        break;
                    case 4:
                        temaServicio.mostrarTemas();
                        System.out.print("Escriba el nombre del tema que quiere agregar a su lista: ");
                        Scanner scanner1 = new Scanner(System.in);
                        String temaAVincular = scanner1.nextLine();
                        Tema temaEncontrado = temaServicio.obtenerPorNombre(temaAVincular);
                        if(temaEncontrado != null) usuarioServicio.vincularTema(usuario, temaEncontrado);
                        else System.out.println("El tema proporcionado no fue encontrado en memoria");
                        break;
                    case 5:
                        Alerta alerta = formularioAlerta();
                        if(alerta.getAlcance() == Alcance.GLOBAL) alertaServicio.crearAlertaGlobal(alerta, usuarioServicio.obtenerTodos()) ;
                        else alertaServicio.crearAlertaIndividual(alerta);
                        break;
                    case 6:
                        List<Alerta> alertasPendientesUrgentes = alertaServicio.obtenerPorUsuario(usuario).stream().filter(item -> item.getTipoAlerta().equals(TipoAlerta.URGENTE) && item.getFechaYHoraExpiracion().isAfter(LocalDateTime.now()) && !item.isLeida()).sorted(Comparator.comparing(Alerta::getId).reversed()).collect(Collectors.toList());
                        List<Alerta> alertasPendientesInformativas = alertaServicio.obtenerPorUsuario(usuario).stream().filter(item -> item.getTipoAlerta().equals(TipoAlerta.INFORMATIVA) && item.getFechaYHoraExpiracion().isAfter(LocalDateTime.now()) && !item.isLeida()).sorted(Comparator.comparing(Alerta::getId)).collect(Collectors.toList());

                        System.out.println("-----LISTA DE ALERTAS-----");
                        alertasPendientesUrgentes.forEach(System.out::println);
                        alertasPendientesInformativas.forEach(System.out::println);
                        break;
                    case 7:
                        Tema temaElegido = null;
                        String temaEntrada = "";
                        do{
                                    List<Tema> temaList2 = usuarioServicio.obtenerPorUsuario(usuario.getNombreUsuario()).getTema();
                                    if(temaList2.size() > 0){
                                        System.out.println("Lista de temas de su interes:");
                                        for (Tema item: temaList2) {
                                            System.out.println(item);
                                        }
                                        System.out.println("Por favor digite el nombre del tema que quiere filtrar");
                                        temaEntrada = scanner.nextLine();
                                        temaElegido = temaServicio.obtenerPorNombre(temaEntrada);
                                        System.out.println("Tema elegido: " + temaElegido);
                                        if(temaElegido == null) System.out.println("El tema ingresado no existe, intente nuevamente");
                                    }else{
                                        System.out.println("Usted no tiene temas asignados");
                                        break;
                                    }
                                }while (temaElegido == null);
                        List<Alerta> alertasFiltradas = alertaServicio.obtenerPorTema(temaEntrada);
                        alertasFiltradas.forEach(System.out::println);

                        break;
                    case 8:
                        List<Alerta> alertasPendientes2 = alertaServicio.obtenerPorUsuario(usuario).stream().filter(item -> item.getFechaYHoraExpiracion().isAfter(LocalDateTime.now()) && !item.isLeida()).collect(Collectors.toList());
                        System.out.println("-----LISTA DE ALERTAS-----");
                        for (Alerta item: alertasPendientes2 ) {
                            System.out.println(item);
                        }

                        System.out.println("Ingrese el ID de la alerta que quiere marcar como leida");
                        Integer id = scanner.nextInt();
                        try{
                            alertaServicio.marcarComoLeida(id);
                            System.out.println("Alerta marcada como leida");
                        }catch (Exception e){
                            System.out.println("Error al marcar la alerta como leida");
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        break;

                }
            }catch (Exception e){
                System.out.println("La opcion ingresada es incorrecta, ingrese solo numeros");
            }

        }while (opcionSeleccionada != 9);
 }

    /*
     * Pido al usuario el email y contraseña para iniciar sesion
     * */
    UsuarioDTO formularioInicioSesion(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese su nombre de usuario: ");
        String email = scanner.nextLine();
        System.out.println("Por favor ingrese su contraseña: ");
        String password = scanner.nextLine();
        return new UsuarioDTO(email, password);
    }

    UsuarioDTO formularioRegistro(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese su nombre de usuario: ");
        String email = scanner.nextLine();
        System.out.println("Por favor ingrese su contraseña: ");
        String password = scanner.nextLine();
        return new UsuarioDTO(email, password);
    }

    Tema formularioTema() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        return new Tema(nombre);
    }

    //Solicito los datos de la alerta a generar
    Alerta formularioAlerta(){
        Scanner scanner = new Scanner(System.in);
        Tema tema = null;
        TipoAlerta tipoAlerta = null;
        Alcance alcance = null;
        Usuario usuario = null;
        LocalDateTime fechaExpiracion = null;

        //Pido al usuario que ingrese un tema, en caso de no existir vuelvo a pedir hasta elegir uno existente
        do{
            System.out.println("Ingrese el nombre del tema para la alerta: ");
            temaServicio.mostrarTemas();
            tema = temaServicio.obtenerPorNombre(scanner.nextLine());
            if(tema == null) System.out.println("El tema ingreso no existe");
        }while (tema == null);

        System.out.print("Ingrese la descripcion de la alerta: ");
        String descripcion = scanner.nextLine();

        //Pido al usuario que ingrese el tipo de alerta, en caso de no existir, vuelve a pedir hasta que se elija uno existente
        do{
            System.out.println("Ingrese el tipo de alerta: ");
            System.out.println(TipoAlerta.INFORMATIVA);
            System.out.println(TipoAlerta.URGENTE);
            String tipoAlertaDTO = scanner.nextLine().toUpperCase();
            try{
                tipoAlerta = TipoAlerta.valueOf(tipoAlertaDTO);
            }catch (IllegalArgumentException e){
                System.out.println("Tipo de alerta invalido, por favor intente nuevamente");
            }
        }while (tipoAlerta == null);

        //Pido al usuario que ingrese el alcance, en caso de no existir, vuelve a pedir hasta que se elija uno existente
        do{
            System.out.println("Ingrese el alcance de la alerta: ");
            System.out.println(Alcance.GLOBAL);
            System.out.println(Alcance.INDIVIDUAL);
            String alcanceDTO = scanner.nextLine().toUpperCase();
            try{
                alcance = Alcance.valueOf(alcanceDTO);
            }catch (IllegalArgumentException e){
                System.out.println("Alcance invalido, por favor intente nuevamente");
            }
        }while (alcance == null);
        //Solicito el nombre de usuario, en caso de ser una alerta individual
        if(alcance == Alcance.INDIVIDUAL){
            do{
                System.out.println("Ingrese el nombre de usuario, al que desea enviar la notificacion");
                String nombreUsuario = scanner.nextLine();
                usuario = usuarioServicio.obtenerPorUsuario(nombreUsuario);
                if(usuario == null) System.out.println("El usuario ingresado no existe, intente nuevamente");
            }while (usuario == null);

        }
        fechaExpiracion = solicitarFechaDeExpiracion();

        return new Alerta(tema, descripcion, fechaExpiracion, tipoAlerta, alcance, usuario);


    }

    LocalDateTime solicitarFechaDeExpiracion(){
        Scanner scanner = new Scanner(System.in);
        LocalDate fecha = null;
        LocalTime hora = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        //Solicito la fecha mientras que el formato no sea el adecuado
        while(fecha == null){
            System.out.println("Ingrese la fecha de expiracion de la alerta (dd/MM/yyyy):");
            String fechaIngresada = scanner.nextLine();
            try{
                fecha = LocalDate.parse(fechaIngresada, dateFormatter);
            }catch (DateTimeParseException e){
                System.out.println("Formato de fecha invalido. Por favor intente nuevamente");
            }

        }

        //Solicito la hora mientras que el formato no sea el adecuado
        while(hora == null){
            System.out.println("Ingrese una hora (HH:mm) : ");
            String horaIngresada = scanner.nextLine();
            try{
                hora = LocalTime.parse(horaIngresada, timeFormatter);
            }catch (DateTimeParseException e){
                System.out.println("Formato de hora invalido. Por favor intente nuevamente");
            }
        }

        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        return fechaHora;
    }


}
