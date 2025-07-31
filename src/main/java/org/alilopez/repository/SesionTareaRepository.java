package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SesionTareaRepository {
    public void save(int idSesion, int idTarea) throws SQLException {
        String query = "INSERT INTO sesion_tarea (idSesion, idTarea) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSesion);
            stmt.setInt(2, idTarea);
            stmt.executeUpdate();
        }
    }

    public boolean deleteSesion(int idSesion, int idTarea) throws SQLException {
        String query = "DELETE FROM sesion_tarea WHERE idSesion = ? AND idTarea = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSesion);
            stmt.setInt(2, idTarea);
            return stmt.executeUpdate() > 0;
        }
    }
}
