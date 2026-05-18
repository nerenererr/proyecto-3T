package dao;

import modelos.Asistente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenteDAO {
    private String url = "jdbc:mysql://127.0.0.1:3306/gestion_eventos";
    private String user = "root";
    private String password = "1234";

    public int insertarAsistente(Asistente asistente) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO asistentes (nombre, email, edad) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, asistente.getNombre());
            pstmt.setString(2, asistente.getEmail());
            pstmt.setInt(3, asistente.getEdad());
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

    public void actualizarAsistente(Asistente asistente, int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE asistentes SET nombre = ?, email = ?, edad = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, asistente.getNombre());
            pstmt.setString(2, asistente.getEmail());
            pstmt.setInt(3, asistente.getEdad());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarAsistente(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM asistentes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void inscribirAsistente(int asistenteId, int eventoId, String fecha) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO inscripciones (asistente_id, evento_id, fecha_inscripcion) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, asistenteId);
            pstmt.setInt(2, eventoId);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void eliminarInscripcion(int asistenteId, int eventoId) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM inscripciones WHERE asistente_id = ? AND evento_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, asistenteId);
            pstmt.setInt(2, eventoId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<String> obtenerAsistentesConGastoTotal() {
        List<String> resultado = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT a.nombre, COALESCE(SUM(e.precio), 0) AS gasto " +
                    "FROM asistentes a LEFT JOIN inscripciones i ON a.id = i.asistente_id " +
                    "LEFT JOIN eventos e ON i.evento_id = e.id GROUP BY a.id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultado.add("Asistente: " + rs.getString("nombre") + " | Gasto Total: " + rs.getDouble("gasto") + "€");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public double obtenerEdadMedia() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT AVG(edad) AS edad_media FROM asistentes";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("edad_media");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public List<Asistente> obtenerAsistentesSinEventos() {
        List<Asistente> asistentes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT a.id, a.nombre, a.email, a.edad FROM asistentes a " +
                    "LEFT JOIN inscripciones i ON a.id = i.asistente_id WHERE i.evento_id IS NULL";
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
}
