package org.alilopez.service;

import org.alilopez.repository.SesionRepository;
import org.alilopez.repository.EvidenciaObjetivoRepository;

import java.sql.SQLException;

public class InsigniaLogicaService {
    private final SesionRepository sesionRepository;
    private final EvidenciaObjetivoRepository evidenciaObjetivoRepository;
    private final UsuarioInsigniaService usuarioInsigniaService;

    public InsigniaLogicaService(SesionRepository sesionRepository,
                                 EvidenciaObjetivoRepository evidenciaObjetivoRepository,
                                 UsuarioInsigniaService usuarioInsigniaService) {
        this.sesionRepository = sesionRepository;
        this.evidenciaObjetivoRepository = evidenciaObjetivoRepository;
        this.usuarioInsigniaService = usuarioInsigniaService;
    }

    // MÉTODO PRINCIPAL: Verificar y otorgar todas las insignias
    public void verificarYOtorgarInsignias(int idUsuario) throws SQLException {
        verificarInsigniasTemporadas(idUsuario);
        verificarInsigniasPomodoros(idUsuario);
        verificarInsigniasObjetivos(idUsuario);
        verificarInsigniasFallos(idUsuario);
    }

    // A. INSIGNIAS POR TIEMPO EFECTIVO (IDs 1-3)
    private void verificarInsigniasTemporadas(int idUsuario) throws SQLException {
        double duracionSegundos = sesionRepository.getSumaDuracionRealByUsuario(idUsuario);
        double horasEfectivas = duracionSegundos / 3600.0;

        if (horasEfectivas >= 12.0) {
            otorgarInsigniaSimple(idUsuario, 3); // tiempo-oro
        } else if (horasEfectivas >= 6.0) {
            otorgarInsigniaSimple(idUsuario, 2); // tiempo-plata
        } else if (horasEfectivas >= 2.0) {
            otorgarInsigniaSimple(idUsuario, 1); // tiempo-bronce
        }
    }

    // B. INSIGNIAS POR POMODOROS TERMINADOS (IDs 4-6)
    private void verificarInsigniasPomodoros(int idUsuario) throws SQLException {
        int sesionesCompletadas = sesionRepository.countSesionesCompletadasByUsuario(idUsuario);

        if (sesionesCompletadas >= 100) {
            otorgarInsigniaSimple(idUsuario, 6); // pmdro-oro
        } else if (sesionesCompletadas >= 50) {
            otorgarInsigniaSimple(idUsuario, 5); // pmdro-plata
        } else if (sesionesCompletadas >= 10) {
            otorgarInsigniaSimple(idUsuario, 4); // pmdro-bronce
        }
    }

    // C. INSIGNIAS POR OBJETIVOS ALCANZADOS (IDs 7-9)
    private void verificarInsigniasObjetivos(int idUsuario) throws SQLException {
        int objetivosAlcanzados = evidenciaObjetivoRepository.countCompletadasByUsuario(idUsuario);

        if (objetivosAlcanzados >= 50) {
            otorgarInsigniaSimple(idUsuario, 9); // objetivo-oro
        } else if (objetivosAlcanzados >= 30) {
            otorgarInsigniaSimple(idUsuario, 8); // objetivo-plata
        } else if (objetivosAlcanzados >= 10) {
            otorgarInsigniaSimple(idUsuario, 7); // objetivo-bronce
        }
    }

    // D. INSIGNIAS POR INTENTOS FALLIDOS (IDs 10-12)
    private void verificarInsigniasFallos(int idUsuario) throws SQLException {
        int intentosFallidos = sesionRepository.getSumaIntentosFallidosByUsuario(idUsuario);

        if (intentosFallidos >= 50) {
            otorgarInsigniaSimple(idUsuario, 12); // intento-oro
        } else if (intentosFallidos >= 30) {
            otorgarInsigniaSimple(idUsuario, 11); // intento-plata
        } else if (intentosFallidos >= 10) {
            otorgarInsigniaSimple(idUsuario, 10); // intento-bronce
        }
    }

    // Método auxiliar para otorgar insignia si no la tiene
    private void otorgarInsigniaSimple(int idUsuario, int idInsignia) throws SQLException {
        if (!usuarioInsigniaService.exists(idUsuario, idInsignia)) {
            // Obtener el nivel de la insignia (1, 2, o 3)
            int nivel = obtenerNivelInsignia(idInsignia);
            usuarioInsigniaService.assign(idUsuario, idInsignia, nivel);
            System.out.println("🏆 Insignia otorgada: Usuario " + idUsuario + " -> Insignia " + idInsignia);
        }
    }

    // Obtener nivel basado en el ID de la insignia
    private int obtenerNivelInsignia(int idInsignia) {
        // Tiempo: 1-3, Pomodoros: 4-6, Objetivos: 7-9, Fallos: 10-12
        int posicionEnCategoria = ((idInsignia - 1) % 3) + 1;
        return posicionEnCategoria; // 1=bronce, 2=plata, 3=oro
    }

    // ===== MÉTODOS PÚBLICOS PARA INTEGRAR EN OTROS SERVICIOS =====

    public void verificarInsigniasAlCompletarObjetivo(int idUsuario) throws SQLException {
        verificarInsigniasObjetivos(idUsuario);
        verificarInsigniasTemporadas(idUsuario);
        verificarInsigniasPomodoros(idUsuario);
    }

    public void verificarInsigniasAlCompletarSesion(int idUsuario) throws SQLException {
        verificarInsigniasPomodoros(idUsuario);
        verificarInsigniasTemporadas(idUsuario);
    }

    public void verificarInsigniasAlFallarSesion(int idUsuario) throws SQLException {
        verificarInsigniasFallos(idUsuario);
    }

    public void recalcularTodasLasInsignias(int idUsuario) throws SQLException {
        verificarYOtorgarInsignias(idUsuario);
    }
}