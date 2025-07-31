package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.Insignia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsigniaRepository {
    public List<Insignia> findInsignias() throws SQLException {
        List<Insignia> insignias = new ArrayList<>();
        String query = "SELECT * FROM insignia";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Insignia i = new Insignia();
                i.setIdInsignia(rs.getInt("idInsignia"));
                i.setNombre(rs.getString("nombre"));
                i.setDescripcion(rs.getString("descripcion"));
                i.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                i.setNivel(rs.getInt("nivel"));
                insignias.add(i);
            }
        }
        return insignias;
    }
}
