package dao;

import modelos.Asistente;
import modelos.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    private String url = "jdbc:mysql://127.0.0.1:3306/eventos_db";
    private String user = "root";
    private String password = "";

    public int insertarEvento(Evento evento) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO eventos (nombre, ubicacion, fecha, precio) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getUbicacion());
            pstmt.setDate(3, Date.valueOf(evento.getFecha()));
            pstmt.setDouble(4, evento.getPrecio());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public void actualizarEvento(Evento evento, int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE eventos SET nombre = ?, ubicacion = ?, fecha = ?, precio = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getUbicacion());
            pstmt.setDate(3, Date.valueOf(evento.getFecha()));
            pstmt.setDouble(4, evento.getPrecio());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarEvento(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM eventos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<String> obtenerEventosConTotalAsistentes() {
        List<String> resultado = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT e.nombre, COUNT(i.asistente_id) AS total " +
                    "FROM eventos e LEFT JOIN inscripciones i ON e.id = i.evento_id " +
                    "GROUP BY e.id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultado.add("Evento: " + rs.getString("nombre") + " | Asistentes: " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public List<Asistente> obtenerAsistentesPorEvento(int eventoId) {
        List<Asistente> asistentes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT a.id, a.nombre, a.email, a.edad FROM asistentes a " +
                    "JOIN inscripciones i ON a.id = i.asistente_id WHERE i.evento_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eventoId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                asistentes.add(new Asistente(
                        rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("email"), rs.getInt("edad")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return asistentes;
    }

    public List<Evento> obtenerEventosConMasDeDosAsistentes() {
        List<Evento> eventos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT e.id, e.nombre, e.ubicacion, e.fecha, e.precio FROM eventos e " +
                    "JOIN inscripciones i ON e.id = i.evento_id " +
                    "GROUP BY e.id HAVING COUNT(i.asistente_id) > 2";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                eventos.add(new Evento(
                        rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ubicacion"), rs.getDate("fecha").toString(),
                        rs.getDouble("precio")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return eventos;
    }

    public List<String> obtenerTop3EventosIngresos() {
        List<String> resultado = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT e.nombre, (COUNT(i.asistente_id) * e.precio) AS ingresos " +
                    "FROM eventos e LEFT JOIN inscripciones i ON e.id = i.evento_id " +
                    "GROUP BY e.id ORDER BY ingresos DESC LIMIT 3";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultado.add("Evento: " + rs.getString("nombre") + " | Ingresos: " + rs.getDouble("ingresos") + "€");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public Evento obtenerEventoMasCaroPorUbicacion(String ubicacion) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM eventos WHERE ubicacion = ? ORDER BY precio DESC LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ubicacion);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Evento(
                        rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ubicacion"), rs.getDate("fecha").toString(),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


}
