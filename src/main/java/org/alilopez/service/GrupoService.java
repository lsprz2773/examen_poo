package org.alilopez.service;

import org.alilopez.dto.CrearGrupoRequest;
import org.alilopez.dto.GrupoConRolResponse;
import org.alilopez.model.Grupo;
import org.alilopez.model.UsuarioGrupo;
import org.alilopez.repository.GrupoRepository;
import org.alilopez.repository.UsuarioGrupoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final UsuarioGrupoService usuarioGrupoService;
    private final UsuarioGrupoRepository usuarioGrupoRepository;

    public GrupoService(GrupoRepository grupoRepository, UsuarioGrupoService usuarioGrupoService, UsuarioGrupoRepository usuarioGrupoRepository) {
        this.grupoRepository = grupoRepository;
        this.usuarioGrupoService = usuarioGrupoService;
        this.usuarioGrupoRepository = usuarioGrupoRepository;
    }

    public List<Grupo> getAll() throws SQLException {
        return grupoRepository.findGrupos();
    }

    public Grupo getByIdGrupo(int id) throws SQLException {
        return grupoRepository.findByIdGrupo(id);
    }

    public int createGrupoConDocente(CrearGrupoRequest data) throws SQLException {
        if (grupoRepository.existsByCodigoUnico(data.codigoUnico)) {
            throw new SQLException("Ya existe un grupo con ese codigo unico");
        }

        Grupo grupo = new Grupo();
        grupo.setNombre(data.nombre);
        grupo.setCodigoUnico(data.codigoUnico);
        int idGrupo = grupoRepository.save(grupo);

        UsuarioGrupo ug = new UsuarioGrupo();
        ug.setIdUsuario(data.idUsuario);
        ug.setIdGrupo(idGrupo);
        ug.setRol("docente");
        ug.setFechaUnion(LocalDateTime.now());
        usuarioGrupoService.addUserToGroup(ug);

        return idGrupo;
    }

    public boolean updateGrupo(Grupo grupo) throws SQLException {
        return grupoRepository.update(grupo);
    }

    public boolean deleteGrupo(int id) throws SQLException {
        return grupoRepository.delete(id);
    }

    public List<Grupo> getGruposComoDocente(int idUsuario) throws SQLException {
        return grupoRepository.findGruposComoDocente(idUsuario);
    }

    public List<Grupo> getGruposComoAlumno(int idUsuario) throws SQLException {
        return grupoRepository.findGruposComoAlumno(idUsuario);
    }

    public GrupoConRolResponse getGrupoYRol(int idGrupo, int idUsuario) throws SQLException {
        Grupo grupo = grupoRepository.findByIdGrupo(idGrupo);
        String rol = usuarioGrupoRepository.findRolByUsuarioYGrupo(idUsuario, idGrupo);

        if (grupo == null || rol == null) {
            throw new SQLException("No se encontró el grupo o el usuario no pertenece al grupo");
        }

        return new GrupoConRolResponse(grupo, rol);
    }

    public Grupo getByCodigoUnico(String codigoUnico) throws SQLException {
        return grupoRepository.findByCodigoUnico(codigoUnico);
    }
}
