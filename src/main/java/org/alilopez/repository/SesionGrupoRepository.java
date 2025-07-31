package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SesionGrupoRepository {
    public void save(int idGrupo, int idTarea) throws SQLException {
        String query = "INSERT INTO sesion_grupo (idGrupo, idTarea) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idTarea);
            stmt.executeUpdate();
        }
    }
}
