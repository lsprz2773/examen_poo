package org.alilopez.service;

import org.alilopez.model.RachaUsuario;
import org.alilopez.repository.ActividadDiariaRepository;
import org.alilopez.repository.RachaUsuarioRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class ActividadDiariaService {
    private final ActividadDiariaRepository actividadDiariaRepository;
    private final RachaUsuarioRepository rachaUsuarioRepository;

    public ActividadDiariaService(ActividadDiariaRepository actividadDiariaRepository, RachaUsuarioRepository rachaUsuarioRepository) {
        this.actividadDiariaRepository = actividadDiariaRepository;
        this.rachaUsuarioRepository = rachaUsuarioRepository;
    }

    public void updateActividadAndRacha(int idUsuario) throws SQLException {
        LocalDate today = LocalDate.now();
        System.out.println("Verificando actividad para usuario: " + idUsuario + " - Fecha: " + today);

        if (actividadDiariaRepository.existsActividad(idUsuario, today)){
            System.out.println("Ya existía actividad para hoy. No se actualiza racha.");
            return;
        }

        actividadDiariaRepository.registerActividad(idUsuario, today);
        System.out.println("Actividad registrada.");

        RachaUsuario ru = rachaUsuarioRepository.findRachaByIdUser(idUsuario);
        if (ru == null) {
            ru = new RachaUsuario();
            ru.setIdUsuario(idUsuario);
            ru.setRachaActual(1);
            ru.setRachaMaxima(1);
            System.out.println("Primera actividad registrada. Racha inicializada.");

        } else {
            LocalDate yesterday = today.minusDays(1);
            System.out.println("Última fecha guardada: " + ru.getFechaUltimoDia());

            if (yesterday.equals(ru.getFechaUltimoDia())){
                ru.setRachaActual(ru.getRachaActual() + 1);
                System.out.println("Día consecutivo. Racha actual: " + ru.getRachaActual());
            } else {
                ru.setRachaActual(1);
                System.out.println("No fue consecutivo. Racha reiniciada.");

            }
            if (ru.getRachaActual() > ru.getRachaMaxima()) {
                ru.setRachaMaxima(ru.getRachaActual());
                System.out.println("¡Nueva racha máxima alcanzada!: " + ru.getRachaMaxima());

            }
        }

        ru.setFechaUltimoDia(today);
        System.out.println("Guardando en base de datos: actual = " + ru.getRachaActual() + ", máxima = " + ru.getRachaMaxima() + ", fecha = " + ru.getFechaUltimoDia());

        rachaUsuarioRepository.saveOrUpdate(ru);
        System.out.println("Guardado con éxito.");

    }

}
