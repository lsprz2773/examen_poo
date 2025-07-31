package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.Sesion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionRepository {

    public List<Sesion> findSesiones() throws SQLException {
        List<Sesion> sesions = new ArrayList<>();
        String query = "SELECT * FROM sesion";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sesion p = new Sesion();
                p.setIdSesion(rs.getInt("idSesion"));
                p.setIdUsuario(rs.getInt("idUsuario"));
                p.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                p.setDuracionReal(rs.getInt("duracionReal"));
                p.setDescansoReal(rs.getInt("descansoReal"));
                p.setIntentos(rs.getInt("intentos"));
                p.setEstado(rs.getString("estado"));
                p.setPomodoros(rs.getInt("pomodoros"));
                sesions.add(p);
            }
        }
        return sesions;
    }

    public Sesion findByIdSesion(int idSesion) throws SQLException {
        Sesion sesion = null;
        String query = "SELECT * FROM sesion WHERE idSesion = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSesion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Sesion p = new Sesion();
                    p.setIdSesion(rs.getInt("idSesion"));
                    p.setIdUsuario(rs.getInt("idUsuario"));
                    p.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    p.setDuracionReal(rs.getInt("duracionReal"));
                    p.setDescansoReal(rs.getInt("descansoReal"));
                    p.setIntentos(rs.getInt("intentos"));
                    p.setEstado(rs.getString("estado"));
                    p.setPomodoros(rs.getInt("pomodoros"));
                    return p;
                }
            }
        }
        return sesion;
    }

    public int save(Sesion sesion) throws SQLException {
        String query = "INSERT INTO sesion (idUsuario, fechaCreacion, duracionReal, descansoReal, intentos, estado, pomodoros) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sesion.getIdUsuario());
            stmt.setTimestamp(2, Timestamp.valueOf(sesion.getFechaCreacion()));
            stmt.setInt(3, sesion.getDuracionReal());
            stmt.setInt(4, sesion.getDescansoReal());
            stmt.setInt(5, sesion.getIntentos());
            stmt.setString(6, sesion.getEstado());
            stmt.setInt(7, sesion.getPomodoros());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                else throw new SQLException("No se pudo obtener el ID de sesión insertada.");
            }
        }
    }

    public boolean update(Sesion sesion) throws SQLException {
        String query = "UPDATE sesion SET duracionReal = ?, descansoReal = ?, intentos = ?, estado = ?, pomodoros = ? WHERE idSesion = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, sesion.getDuracionReal());
            stmt.setInt(2, sesion.getDescansoReal());
            stmt.setInt(3, sesion.getIntentos());
            stmt.setString(4, sesion.getEstado());
            stmt.setInt(5, sesion.getPomodoros());
            stmt.setInt(6, sesion.getIdSesion());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int idSesion) throws SQLException {
        String query = "DELETE FROM sesion WHERE idSesion = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idSesion);
            return stmt.executeUpdate() > 0;
        }
    }

    public double getSumaDuracionRealByUsuario(int idUsuario) throws SQLException {
        String query = """
        SELECT COALESCE(SUM(s.duracionReal), 0) 
        FROM sesion s 
        JOIN sesion_objetivo so ON s.idSesion = so.idSesion 
        JOIN objetivo o ON so.idObjetivo = o.idObjetivo 
        WHERE o.idUsuario = ?
        """;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble(1) : 0.0;
        }
    }

    // Contar sesiones completadas por usuario (aproximación a pomodoros)
    public int countSesionesCompletadasByUsuario(int idUsuario) throws SQLException {
        String query = """
        SELECT COUNT(*) 
        FROM sesion s 
        JOIN sesion_objetivo so ON s.idSesion = so.idSesion 
        JOIN objetivo o ON so.idObjetivo = o.idObjetivo 
        WHERE o.idUsuario = ? AND s.estado = 'completado'
        """;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Obtener suma de intentos fallidos por usuario
    public int getSumaIntentosFallidosByUsuario(int idUsuario) throws SQLException {
        String query = """
        SELECT COALESCE(SUM(s.intentos), 0) 
        FROM sesion s 
        JOIN sesion_objetivo so ON s.idSesion = so.idSesion 
        JOIN objetivo o ON so.idObjetivo = o.idObjetivo 
        WHERE o.idUsuario = ? AND s.estado IN ('iniciado', 'no_iniciadoPomodoro')
        """;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
