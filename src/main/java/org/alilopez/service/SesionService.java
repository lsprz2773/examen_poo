package org.alilopez.service;

import org.alilopez.model.Sesion;
import org.alilopez.repository.SesionRepository;

import java.sql.SQLException;
import java.util.List;

public class SesionService {
    private final SesionRepository sesionRepository;
    private InsigniaLogicaService insigniaLogicaService; // Agregar esta dependencia

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    // Método para inyectar el servicio de insignias (opcional)
    public void setInsigniaLogicaService(InsigniaLogicaService insigniaLogicaService) {
        this.insigniaLogicaService = insigniaLogicaService;
    }

    public List<Sesion> getAll() throws SQLException {
        return sesionRepository.findSesiones();
    }

    public Sesion getByIdSesion(int id) throws SQLException {
        return sesionRepository.findByIdSesion(id);
    }

    public void createSesion(Sesion sesion) throws SQLException {
        sesionRepository.save(sesion);
    }

    public int createSesionWithId(Sesion sesion) throws SQLException {
        return sesionRepository.save(sesion);
    }

    public boolean updateSesion(Sesion sesion) throws SQLException {
        boolean updated = sesionRepository.update(sesion);

        // Verificar insignias según el estado de la sesión
        if (updated && insigniaLogicaService != null) {
            // Necesitarás obtener el idUsuario de la sesión mediante join
            // Por ahora, pasamos 0 como placeholder - deberás implementar esto
            int idUsuario = obtenerUsuarioDeSesion(sesion.getIdSesion());

            if ("completado".equals(sesion.getEstado())) {
                insigniaLogicaService.verificarInsigniasAlCompletarSesion(idUsuario);
            } else {
                insigniaLogicaService.verificarInsigniasAlFallarSesion(idUsuario);
            }
        }

        return updated;
    }

    public boolean deleteSesion(int id) throws SQLException {
        return sesionRepository.delete(id);
    }

    // Método auxiliar que necesitarás implementar
    private int obtenerUsuarioDeSesion(int idSesion) throws SQLException {
        // Implementar query para obtener idUsuario desde sesion -> sesion_objetivo -> objetivo
        return 0; // Placeholder
    }
}