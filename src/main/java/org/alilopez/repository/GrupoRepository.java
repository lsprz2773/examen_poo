package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoRepository {
    public List<Grupo> findGrupos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT * FROM grupo";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Grupo g = new Grupo();
                g.setIdGrupo(rs.getInt("idGrupo"));
                g.setNombre(rs.getString("nombre"));
                g.setCodigoUnico(rs.getString("codigoUnico"));
                g.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                grupos.add(g);
            }
        }
        return grupos;
    }

    public Grupo findByIdGrupo(int idGrupo) throws SQLException {
        Grupo grupo = null;
        String query = "SELECT * FROM grupo WHERE idGrupo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idGrupo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Grupo g = new Grupo();
                    g.setIdGrupo(rs.getInt("idGrupo"));
                    g.setNombre(rs.getString("nombre"));
                    g.setCodigoUnico(rs.getString("codigoUnico"));
                    g.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    return g;
                }
            }
        }
        return grupo;
    }

    public int save(Grupo grupo) throws SQLException {
        String query = "INSERT INTO grupo (nombre, codigoUnico) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {

            stmt.setString(1, grupo.getNombre());
            stmt.setString(2, grupo.getCodigoUnico());
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se encontro el id del grupo");
                }
            }
        }
    }

    public boolean update(Grupo grupo) throws SQLException {
        String query = "UPDATE grupo SET nombre = ? WHERE idGrupo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, grupo.getNombre());
            stmt.setInt(2, grupo.getIdGrupo());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int idClase) throws SQLException {
        String query = "DELETE FROM grupo WHERE idGrupo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idClase);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Grupo> findGruposComoDocente(int idUsuario) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT grupo.* FROM grupo JOIN usuario_grupo ON grupo.idGrupo = usuario_grupo.idGrupo WHERE usuario_grupo.idUsuario = ? AND usuario_grupo.rol = 'docente'";

        try(
                Connection conn = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Grupo g = new Grupo();
                    g.setIdGrupo(rs.getInt("idGrupo"));
                    g.setNombre(rs.getString("nombre"));
                    g.setCodigoUnico(rs.getString("codigoUnico"));
                    g.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    grupos.add(g);
                }
            }
        }
        return grupos;
    }

    public List<Grupo> findGruposComoAlumno(int idUsuario) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT grupo.* FROM grupo JOIN usuario_grupo ON grupo.idGrupo = usuario_grupo.idGrupo WHERE usuario_grupo.idUsuario = ? AND usuario_grupo.rol = 'alumno'";
        try(
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Grupo g = new Grupo();
                    g.setIdGrupo(rs.getInt("idGrupo"));
                    g.setNombre(rs.getString("nombre"));
                    g.setCodigoUnico(rs.getString("codigoUnico"));
                    g.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    grupos.add(g);
                }
            }
        }
        return grupos;
    }

    public int findIdGrupoByCodigoUnico(String codigoUnico) throws SQLException {
        String query = "SELECT idGrupo FROM grupo WHERE codigoUnico = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codigoUnico);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idGrupo");
                } else {
                    throw new SQLException("No se encontro el id del grupo");
                }
            }
        }
    }

    public boolean existsByCodigoUnico(String codigoUnico) throws SQLException {
        String query = "SELECT 1 FROM grupo WHERE codigoUnico = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codigoUnico);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Grupo findByCodigoUnico(String codigoUnico) throws SQLException {
        Grupo g = null;
        String query = "SELECT * FROM grupo WHERE codigoUnico = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codigoUnico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    g = new Grupo();
                    g.setIdGrupo(rs.getInt("idGrupo"));
                }
            }
        }
        return g;
    }
}
