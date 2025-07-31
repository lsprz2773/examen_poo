package org.alilopez.service;

import org.alilopez.model.Insignia;
import org.alilopez.repository.InsigniaRepository;

import java.sql.SQLException;
import java.util.List;

public class InsigniaService {
    private final InsigniaRepository insigniaRepository;
    private final InsigniaLogicaService insigniaLogicaService;

    public InsigniaService(InsigniaRepository insigniaRepository, InsigniaLogicaService insigniaLogicaService) {
        this.insigniaRepository = insigniaRepository;
        this.insigniaLogicaService = insigniaLogicaService;
    }

    // Constructor alternativo para mantener compatibilidad
    public InsigniaService(InsigniaRepository insigniaRepository) {
        this.insigniaRepository = insigniaRepository;
        this.insigniaLogicaService = null;
    }

    public List<Insignia> getAllInsignias() throws SQLException {
        return insigniaRepository.findInsignias();
    }

    // Nuevo método para recalcular insignias
    public void recalcularInsigniasUsuario(int idUsuario) throws SQLException {
        if (insigniaLogicaService != null) {
            insigniaLogicaService.recalcularTodasLasInsignias(idUsuario);
        }
    }
}