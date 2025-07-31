package org.alilopez.service;

import org.alilopez.model.EvidenciaObjetivo;
import org.alilopez.repository.EvidenciaObjetivoRepository;

import java.sql.SQLException;

public class EvidenciaObjetivoService {
    private final EvidenciaObjetivoRepository evidenciaObjetivoRepository;
    private InsigniaLogicaService insigniaLogicaService; // Agregar esta dependencia

    public EvidenciaObjetivoService(EvidenciaObjetivoRepository evidenciaObjetivoRepository) {
        this.evidenciaObjetivoRepository = evidenciaObjetivoRepository;
    }

    // Método para inyectar el servicio de insignias (opcional)
    public void setInsigniaLogicaService(InsigniaLogicaService insigniaLogicaService) {
        this.insigniaLogicaService = insigniaLogicaService;
    }

    public void agregarEvidencia(EvidenciaObjetivo evidencia) throws SQLException {
        evidenciaObjetivoRepository.save(evidencia);

        // Verificar insignias al completar objetivo
        if (insigniaLogicaService != null && evidencia.getEstado()) {
            insigniaLogicaService.verificarInsigniasAlCompletarObjetivo(evidencia.getIdUsuario());
        }
    }

    public EvidenciaObjetivo getEvidenciaByObjetivo(int idObjetivo, int idUsuario) throws SQLException {
        return evidenciaObjetivoRepository.findByObjetivoAndUsuario(idObjetivo, idUsuario);
    }
}