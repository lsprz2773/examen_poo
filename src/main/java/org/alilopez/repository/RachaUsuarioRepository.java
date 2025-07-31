package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.RachaUsuario;

import java.sql.*;

public class RachaUsuarioRepository {
    public RachaUsuario findRachaByIdUser(int idUsuario) throws SQLException {
        RachaUsuario rachaUsuario = null;
        String query = "SELECT * FROM racha_usuario WHERE idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RachaUsuario ru = new RachaUsuario();
                    ru.setIdUsuario(rs.getInt("idUsuario"));
                    ru.setRachaActual(rs.getInt("rachaActual"));
                    ru.setRachaMaxima(rs.getInt("rachaMaxima"));
                    ru.setFechaUltimoDia(rs.getDate("fechaUltimoDia").toLocalDate());
                    return ru;
                }
            }
        }
        return rachaUsuario;
    }

    public void saveOrUpdate(RachaUsuario rachaUsuario) throws SQLException {
        String query ="INSERT INTO racha_usuario (idUsuario, rachaActual, rachaMaxima, fechaUltimoDia) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE rachaActual = VALUES(rachaActual), rachaMaxima = VALUES(rachaMaxima), fechaUltimoDia = VALUES(fechaUltimoDia)";
        try (
            Connection conn = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setInt(1, rachaUsuario.getIdUsuario());
            stmt.setInt(2, rachaUsuario.getRachaActual());
            stmt.setInt(3, rachaUsuario.getRachaMaxima());
            stmt.setDate(4, Date.valueOf(rachaUsuario.getFechaUltimoDia()));
            stmt.executeUpdate();
        }
    }
}
