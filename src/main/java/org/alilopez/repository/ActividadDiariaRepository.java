package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActividadDiariaRepository {
    public boolean existsActividad(int idUsuario, LocalDate fecha)throws SQLException{
        String query = "SELECT 1 FROM actividad_diaria WHERE idUsuario = ? AND fechaActividad = ?";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, idUsuario);
            stmt.setDate(2, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void registerActividad(int idUsuario, LocalDate fecha)throws SQLException{
        String query = "INSERT INTO actividad_diaria (idUsuario, fechaActividad) VALUES (?, ?)";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, idUsuario);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.executeUpdate();
        }
    }
}
