import dao.AsistenteDAO;
import dao.DesarrolladorDAO;
import dao.EventoDAO;
import dao.ProyectoDAO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelos.Asistente;
import modelos.Desarrollador;
import modelos.Evento;
import modelos.Proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventoDAO eventoDAO = new EventoDAO();
        AsistenteDAO asistenteDAO = new AsistenteDAO();

        Evento nuevoEvento = new Evento("Hackathon Java", "Bilbao", "2026-08-15", 15.00);
        //int idEventoGenerado = eventoDAO.insertarEvento(nuevoEvento);
        //System.out.println("Evento insertado con ID: " + idEventoGenerado);

        Asistente nuevoAsistente = new Asistente("Test User", "test@example.com", 24);
        //int idAsistenteGenerado = asistenteDAO.insertarAsistente(nuevoAsistente);
        //System.out.println("Asistente insertado con ID: " + idAsistenteGenerado);

        //nuevoEvento.setPrecio(20.50);
        //eventoDAO.actualizarEvento(nuevoEvento, idEventoGenerado);
        //System.out.println("Evento actualizado.");

        //nuevoAsistente.setEdad(25);
        //asistenteDAO.actualizarAsistente(nuevoAsistente, idAsistenteGenerado);
        //System.out.println("Asistente actualizado.");

        //asistenteDAO.inscribirAsistente(idAsistenteGenerado, idEventoGenerado, "2026-05-18");
        //System.out.println("Asistente temporal inscrito en evento temporal.");

        //asistenteDAO.eliminarInscripcion(idAsistenteGenerado, idEventoGenerado);
        //System.out.println("Inscripción de prueba eliminada.");

        //asistenteDAO.eliminarAsistente(idAsistenteGenerado);
        //System.out.println("Asistente de prueba eliminado.");

        //eventoDAO.eliminarEvento(idEventoGenerado);
        //System.out.println("Evento de prueba eliminado.");

    /*    System.out.println("\n--- EJECUCIÓN DE CONSULTAS DE EVENTO ---");
        System.out.println("-> Todos los eventos con su número total de asistentes:");
        eventoDAO.obtenerEventosConTotalAsistentes().forEach(System.out::println);

        System.out.println("\n-> Asistentes del Evento 1 (Concierto Rock):");
        eventoDAO.obtenerAsistentesPorEvento(1).forEach(System.out::println);

        System.out.println("\n-> Eventos con más de 2 asistentes:");
        eventoDAO.obtenerEventosConMasDeDosAsistentes().forEach(System.out::println);

        System.out.println("\n-> Los 3 eventos con más ingresos:");
        eventoDAO.obtenerTop3EventosIngresos().forEach(System.out::println);

        System.out.println("\n-> Evento más caro de Madrid:");
        Evento caroMadrid = eventoDAO.obtenerEventoMasCaroPorUbicacion("Madrid");
        System.out.println(caroMadrid != null ? caroMadrid : "No hay eventos en esa ubicación");

        System.out.println("\n--- EJECUCIÓN DE CONSULTAS DE ASISTENTE ---");
        System.out.println("-> Asistentes con su gasto total:");
        asistenteDAO.obtenerAsistentesConGastoTotal().forEach(System.out::println);

        System.out.println("\n-> Edad media de los asistentes:");
        System.out.printf("%.2f años\n", asistenteDAO.obtenerEdadMedia());

        System.out.println("\n-> Asistentes que no se han inscrito a ningún evento:");
        List<Asistente> sinInscripcion = asistenteDAO.obtenerAsistentesSinEventos();
        if (sinInscripcion.isEmpty()) {
            System.out.println("Todos los asistentes tienen al menos una inscripción.");
        } else {
            sinInscripcion.forEach(System.out::println);
        }

     */


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto.odb");
        ProyectoDAO proyectoDAO = new ProyectoDAO(emf);
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO(emf);

        Proyecto proyectoPrueba = new Proyecto("Proyecto Prueba", 10000.0, "Go");
        //proyectoDAO.insertarProyecto(proyectoPrueba);
        //System.out.println("Proyecto insertado: " + proyectoPrueba);

        Desarrollador desarrolladorPrueba = new Desarrollador("Dev Prueba", 1, 30000.0);
        //desarrolladorDAO.insertarDesarrollador(desarrolladorPrueba);
        //System.out.println("Desarrollador insertado: " + desarrolladorPrueba);

        //proyectoPrueba.setNombre("Proyecto Actualizado");
        //proyectoPrueba.setPresupuesto(15000.0);
        //proyectoDAO.actualizarProyecto(proyectoPrueba.getId());
        //System.out.println("Proyecto actualizado.");

        //desarrolladorPrueba.setNombre("Dev Actualizado");
        //desarrolladorPrueba.setAnyosExperiencia(2);
        //desarrolladorDAO.actualizarDesarrollador(desarrolladorPrueba.getId());
        //System.out.println("Desarrollador actualizado.");

        //desarrolladorDAO.asignarDesarrollador(desarrolladorPrueba.getId(), proyectoPrueba.getId());
        //System.out.println("Desarrollador asignado al proyecto.");

        //desarrolladorDAO.eliminarAsignacion(desarrolladorPrueba.getId(), proyectoPrueba.getId());
        //System.out.println("Asignación eliminada.");

        //desarrolladorDAO.eliminarDesarrollador(desarrolladorPrueba.getId());
        //System.out.println("Desarrollador de prueba eliminado.");

        //proyectoDAO.eliminarProyecto(proyectoPrueba.getId());
        //System.out.println("Proyecto de prueba eliminado.");

        System.out.println("\n--- CONSULTAS DE PROYECTO ---");

        System.out.println("\n-> Número de desarrolladores por proyecto:");
        proyectoDAO.getNumDesarrolladoresPorProyecto().forEach((nombre, count) ->
                System.out.println("   " + nombre + ": " + count));

        System.out.println("\n-> Desarrolladores del proyecto con ID 4:");
        proyectoDAO.getDesarrolladoresPorProyecto(4).forEach(System.out::println);

        System.out.println("\n-> Proyectos con más de 5 desarrolladores:");
        proyectoDAO.getProyectosMasDe5Desarrolladores().forEach(System.out::println);

        System.out.println("\n-> Top 3 proyectos con mayor presupuesto:");
        proyectoDAO.getTop3ProyectosPresupuestoAlto().forEach(System.out::println);

        System.out.println("\n-> Proyecto con menor presupuesto en lenguaje 'Java':");
        Proyecto menorPresupuesto = proyectoDAO.getProyectoPresupuestoBajoPorLenguaje("Java");
        System.out.println(menorPresupuesto != null ? menorPresupuesto : "No hay proyectos con ese lenguaje.");

        System.out.println("\n--- CONSULTAS DE DESARROLLADOR ---");

        System.out.println("\n-> Proyectos del desarrollador con ID 13:");
        desarrolladorDAO.getProyectosPorDesarrollador(13).forEach(System.out::println);

        System.out.println("\n-> Media de años de experiencia:");
        System.out.printf("   %.2f años%n", desarrolladorDAO.getMediaAnyosExperiencia());

        System.out.println("\n-> Desarrolladores sin ningún proyecto asignado:");
        List<Desarrollador> sinProyectos = desarrolladorDAO.getDesarrolladoresSinProyectos();
        if (sinProyectos.isEmpty()) {
            System.out.println("   Todos los desarrolladores tienen al menos un proyecto.");
        } else {
            sinProyectos.forEach(System.out::println);
        }

        emf.close();
    }
}