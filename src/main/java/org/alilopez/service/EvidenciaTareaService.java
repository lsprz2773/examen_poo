package org.alilopez.service;

import org.alilopez.model.EvidenciaTarea;
import org.alilopez.repository.EvidenciaTareaRepository;

import java.sql.SQLException;

public class EvidenciaTareaService {
    private final EvidenciaTareaRepository evidenciaTareaRepository;

    public EvidenciaTareaService(EvidenciaTareaRepository evidenciaTareaRepository) {
        this.evidenciaTareaRepository = evidenciaTareaRepository;
    }

    // Agregar una evidencia a la tarea
    public void agregarEvidencia(EvidenciaTarea evidencia) throws SQLException {
        evidenciaTareaRepository.save(evidencia);
    }

    // Obtener la evidencia de una tarea para un usuario específico
    public EvidenciaTarea getEvidenciaByTarea(int idTarea, int idUsuario) throws SQLException {
        return evidenciaTareaRepository.findByTareaAndUsuario(idTarea, idUsuario);
    }

    public boolean actualizarEstadoEntrega(int idEvidencia, String nuevoEstado) throws SQLException {
        return evidenciaTareaRepository.updateEstadoEntrega(idEvidencia, nuevoEstado);
    }
}
