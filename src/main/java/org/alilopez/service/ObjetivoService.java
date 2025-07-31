package org.alilopez.service;

import org.alilopez.dto.CrearObjetivoRequest;
import org.alilopez.dto.CrearObjetivoResponse;
import org.alilopez.dto.UltimoObjetivoDTO;
import org.alilopez.model.Objetivo;
import org.alilopez.model.Sesion;
import org.alilopez.repository.ObjetivoRepository;
import org.alilopez.repository.SesionRepository;
import org.alilopez.repository.SesionObjetivoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ObjetivoService {
    private final ObjetivoRepository objetivoRepository;
    private final SesionRepository sesionRepository;
    private final SesionObjetivoRepository sesionObjetivoRepository;

    public ObjetivoService(ObjetivoRepository objetivoRepository, SesionRepository sesionRepository, SesionObjetivoRepository sesionObjetivoRepository) {
        this.objetivoRepository = objetivoRepository;
        this.sesionRepository = sesionRepository;
        this.sesionObjetivoRepository = sesionObjetivoRepository;
    }

    public List<Objetivo> getAll() throws SQLException {
        return objetivoRepository.findObjetivos();
    }

    public Objetivo getByIdObjetivo(int id) throws SQLException {
        return objetivoRepository.findByIdObjetivo(id);
    }

    public void createObjetivo(Objetivo objetivo) throws SQLException {
        objetivoRepository.save(objetivo);
    }

    public boolean updateObjetivo(Objetivo objetivo) throws SQLException {
        return objetivoRepository.update(objetivo);
    }

    public boolean deleteObjetivo(int id) throws SQLException {
        return objetivoRepository.delete(id);
    }

    public List<Objetivo> getObjetivosByIdUser(int idUsuario) throws SQLException {
        return objetivoRepository.findObjetivosByIdUser(idUsuario);
    }

    public List<Objetivo> getObjetivosEntregados(int idUsuario) throws SQLException {
        return objetivoRepository.findObjetivosEntregados(idUsuario);
    }

    public List<Objetivo> getObjetivosNoEntregados(int idUsuario) throws SQLException {
        return objetivoRepository.findObjetivosNoEntregados(idUsuario);
    }

    public CrearObjetivoResponse crearObjetivoConSesion(CrearObjetivoRequest data) throws SQLException {
        // Crear objetivo
        Objetivo obj = new Objetivo();
        obj.setIdUsuario(data.idUsuario);
        obj.setNombre(data.nombre);
        obj.setDescripcion(data.descripcion);
        obj.setTotalPomodoros(data.totalPomodoros);
        obj.setDuracionPomodoro(data.duracionPomodoro);
        obj.setDuracionDescanso(data.duracionDescanso);
        int idObjetivo = objetivoRepository.save(obj);

        // Crear sesión
        Sesion sesion = new Sesion();
        sesion.setFechaCreacion(LocalDateTime.now());
        sesion.setIdUsuario(data.idUsuario);
        sesion.setDuracionReal(0);
        sesion.setDescansoReal(0);
        sesion.setIntentos(0);
        sesion.setEstado("no_iniciadoPomodoro");
        int idSesion = sesionRepository.save(sesion);

        // Vincular en tabla intermedia
        sesionObjetivoRepository.save(idSesion, idObjetivo);

        return new CrearObjetivoResponse(idObjetivo, idSesion);
    }

    public Objetivo getUltimoObjetivoByUsuario(int idUsuario) throws SQLException {
        return objetivoRepository.findUltimoObjetivoByUsuario(idUsuario);
    }
}
