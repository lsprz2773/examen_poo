package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.EvidenciaTarea;

import java.sql.*;

public class EvidenciaTareaRepository {

    // Guardar la evidencia de la tarea
    public void save(EvidenciaTarea evidencia) throws SQLException {
        String query = "INSERT INTO evidencia_tarea (idTarea, idUsuario, fileURL, estadoEntrega) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, evidencia.getIdTarea());
            stmt.setInt(2, evidencia.getIdUsuario());
            stmt.setString(3, evidencia.getFileURL());
            stmt.setString(4, evidencia.getEstado()); // Usando ENUM (String)
            stmt.executeUpdate();
        }
    }

    // Obtener la evidencia de la tarea para un usuario específico
    public EvidenciaTarea findByTareaAndUsuario(int idTarea, int idUsuario) throws SQLException {
        String query = "SELECT * FROM evidencia_tarea WHERE idTarea = ? AND idUsuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idTarea);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EvidenciaTarea evidenciaTarea = new EvidenciaTarea();
                evidenciaTarea.setIdEvidencia(rs.getInt("idEvidencia"));
                evidenciaTarea.setIdTarea(rs.getInt("idTarea"));
                evidenciaTarea.setIdUsuario(rs.getInt("idUsuario"));
                evidenciaTarea.setFileURL(rs.getString("fileURL"));
                evidenciaTarea.setFechaEnvio(rs.getTimestamp("fechaEnvio").toLocalDateTime());
                evidenciaTarea.setEstado(rs.getString("estadoEntrega"));
                return evidenciaTarea;
            }
        }
        return null;  // Si no se encuentra la evidencia
    }

    public boolean updateEstadoEntrega(int idEvidencia, String nuevoEstado) throws SQLException {
        String query = "UPDATE evidencia_tarea SET estadoEntrega = ? WHERE idEvidencia = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idEvidencia);

            return stmt.executeUpdate() > 0; // Retorna true si se actualizó al menos 1 fila
        }
    }
}
