package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SesionObjetivoRepository {

    public void save(int idSesion, int idObjetivo) throws SQLException {
        String query = "INSERT INTO sesion_objetivo (idSesion, idObjetivo) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSesion);
            stmt.setInt(2, idObjetivo);
            stmt.executeUpdate();
        }
    }
}
