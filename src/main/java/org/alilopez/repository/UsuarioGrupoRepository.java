package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.UsuarioGrupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioGrupoRepository {
    public List<UsuarioGrupo> findUsersByGrupo(int idGrupo) throws SQLException {
        List<UsuarioGrupo> usuarioGrupos = new ArrayList<>();
        String query = "SELECT * FROM usuario_grupo WHERE idGrupo = ?";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
                    stmt.setInt(1, idGrupo);
                    try(ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            UsuarioGrupo ug = new UsuarioGrupo();
                            ug.setIdUsuario(rs.getInt("idUsuario"));
                            ug.setIdGrupo(rs.getInt("idGrupo"));
                            ug.setRol(rs.getString("rol"));
                            ug.setFechaUnion(rs.getTimestamp("fechaUnion").toLocalDateTime());
                            usuarioGrupos.add(ug);
                        }
                    }
                }
        return usuarioGrupos;
    }

    public void save(UsuarioGrupo ug) throws SQLException {
        String query = "INSERT INTO usuario_grupo (idUsuario, idGrupo, rol) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ug.getIdUsuario());
            stmt.setInt(2, ug.getIdGrupo());
            stmt.setString(3, ug.getRol());
            stmt.executeUpdate();
        }
    }

    public boolean delete(int idUsuario, int idGrupo) throws SQLException {
        String query = "DELETE FROM usuario_grupo WHERE idGrupo = ? AND idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idGrupo);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean exists(int idUsuario, int idGrupo) throws SQLException {
        String query = "SELECT 1 FROM usuario_grupo WHERE idUsuario = ? AND idGrupo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idGrupo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public String findRolByUsuarioYGrupo(int idUsuario, int idGrupo) throws SQLException {
        String query = "SELECT rol FROM usuario_grupo WHERE idUsuario = ? AND idGrupo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idGrupo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                } else {
                    return null; // Usuario no pertenece al grupo
                }
            }
        }
    }

    public List<UsuarioGrupo> findAlumnosByTarea(int idTarea) throws SQLException {
        List<UsuarioGrupo> alumnos = new ArrayList<>();
        String query = "SELECT ug.idUsuario, ug.idGrupo, ug.rol FROM usuario_grupo ug " +
                "JOIN sesion_tarea st ON ug.idGrupo = st.idGrupo " +
                "WHERE ug.rol = 'alumno' AND st.idTarea = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTarea);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
                    usuarioGrupo.setIdUsuario(rs.getInt("idUsuario"));
                    usuarioGrupo.setIdGrupo(rs.getInt("idGrupo"));
                    usuarioGrupo.setRol(rs.getString("rol"));
                    alumnos.add(usuarioGrupo);
                }
            }
        }
        return alumnos;
    }
}
