package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM usuario";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("correo"));
                u.setPassword(rs.getString("contrasena"));
                u.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                users.add(u);
            }
        }
        return users;
    }

    public User findByIdUser(int idUser) throws SQLException {
        User user = null;
        String query = "SELECT * FROM usuario WHERE idUsuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setIdUsuario(rs.getInt("idUsuario"));
                    user.setNombre(rs.getString("nombre"));
                    user.setEmail(rs.getString("correo"));
                    user.setPassword(rs.getString("contrasena"));
                    user.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                    user.setAvatar(rs.getInt("avatar"));
                }
            }
        }
        return user;
    }

    public void save(User user) throws SQLException {
        String query = "INSERT INTO usuario (nombre, correo, contrasena) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNombre());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        }
    }

    public boolean update(User user) throws SQLException {
        String query = "UPDATE usuario SET nombre = ? WHERE idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getNombre());
            stmt.setInt(2, user.getIdUsuario());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateAvatar(User user) throws SQLException {
        String query = "UPDATE usuario SET avatar = ? WHERE idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user.getAvatar());
            stmt.setInt(2, user.getIdUsuario());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int idUser) throws SQLException {
        String query = "DELETE FROM usuario WHERE idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser);
            return stmt.executeUpdate() > 0;
        }
    }

    public User findByEmail(String correo) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUsuario(rs.getInt("idUsuario"));
                    user.setNombre(rs.getString("nombre"));
                    user.setEmail(rs.getString("correo"));
                    user.setPassword(rs.getString("contrasena"));
                    user.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                    user.setAvatar(rs.getInt("avatar"));
                    return user;
                }
            }
        }
        return null;
    }

    public User findByEmailAndPassword(String correo, String contrasena) throws SQLException {
        String query = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUsuario(rs.getInt("idUsuario"));
                    user.setNombre(rs.getString("nombre"));
                    user.setEmail(rs.getString("correo"));
                    user.setPassword(rs.getString("contrasena"));
                    user.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                    user.setAvatar(rs.getInt("avatar"));
                    return user;
                }
            }
        }
        return null;
    }

}
