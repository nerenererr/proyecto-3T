import dao.AsistenteDAO;
import dao.EventoDAO;
import modelos.Asistente;
import modelos.Evento;

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

        System.out.println("\n--- EJECUCIÓN DE CONSULTAS DE EVENTO ---");
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
    }
}