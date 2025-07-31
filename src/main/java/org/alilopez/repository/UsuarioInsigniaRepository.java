package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.UsuarioInsignia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UsuarioInsigniaRepository {
    public List<UsuarioInsignia> findByUsuario(int idUsuario) throws SQLException {
        List<UsuarioInsignia> usuarios = new ArrayList<>();
        String query = "SELECT usuario_insignia.*, insignia.nombre FROM usuario_insignia JOIN insignia ON usuario_insignia.idInsignia = insignia.idInsignia WHERE usuario_insignia.idUsuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UsuarioInsignia usuario = new UsuarioInsignia();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setIdInsignia(rs.getInt("idInsignia"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setNivel(rs.getInt("nivel"));
                usuario.setFechaObtencion(rs.getTimestamp("fechaObtencion").toLocalDateTime());
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public boolean exists(int idUsuario, int idInsignia) throws SQLException {
        String query = "SELECT 1 FROM usuario_insignia WHERE idUsuario = ? AND idInsignia = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idInsignia);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void insert(int idUsuario, int idInsignia, int nivel) throws SQLException {
        String query = "INSERT INTO usuario_insignia (idUsuario, idInsignia, nivel) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idInsignia);
            stmt.setInt(3, nivel);
            stmt.executeUpdate();
        }
    }

    public void updateNivel(int idUsuario, int idInsignia, int nivel) throws SQLException {
        String query = "UPDATE usuario_insignia SET nivel = ? WHERE idUsuario = ? AND idInsignia = ?";

        try(Connection conn = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nivel);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idInsignia);
            stmt.executeUpdate();
        }
    }
}
