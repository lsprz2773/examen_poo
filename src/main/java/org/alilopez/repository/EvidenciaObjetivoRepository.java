package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.EvidenciaObjetivo;

import java.sql.*;

public class EvidenciaObjetivoRepository {

    // Método para guardar la evidencia en la base de datos
    public void save(EvidenciaObjetivo evidencia) throws SQLException {
        String query = "INSERT INTO evidencia_objetivo (idObjetivo, idUsuario, fileURL) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, evidencia.getIdObjetivo());
            stmt.setInt(2, evidencia.getIdUsuario());
            stmt.setString(3, evidencia.getFileURL());
            stmt.executeUpdate();
        }
    }

    // Método para obtener la evidencia de un objetivo y usuario
    public EvidenciaObjetivo findByObjetivoAndUsuario(int idObjetivo, int idUsuario) throws SQLException {
        String query = "SELECT * FROM evidencia_objetivo WHERE idObjetivo = ? AND idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idObjetivo);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EvidenciaObjetivo evidencia = new EvidenciaObjetivo();
                evidencia.setIdEvidencia(rs.getInt("idEvidencia"));
                evidencia.setIdObjetivo(rs.getInt("idObjetivo"));
                evidencia.setIdUsuario(rs.getInt("idUsuario"));
                evidencia.setFileURL(rs.getString("fileURL"));
                evidencia.setFechaEnvio(rs.getTimestamp("fechaEnvio").toLocalDateTime());
                evidencia.setEstado(rs.getBoolean("estado"));
                return evidencia;
            }
        }
        return null;  // Si no se encuentra la evidencia
    }

    public int countCompletadasByUsuario(int idUsuario) throws SQLException {
        String query = "SELECT COUNT(*) FROM evidencia_objetivo WHERE idUsuario = ? AND estado = 1";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
