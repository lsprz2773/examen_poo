package org.alilopez.service;

import org.alilopez.model.UsuarioGrupo;
import org.alilopez.repository.GrupoRepository;
import org.alilopez.repository.UsuarioGrupoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioGrupoService {
    private final UsuarioGrupoRepository usuarioGrupoRepository;
    private final GrupoRepository grupoRepository;

    public UsuarioGrupoService(UsuarioGrupoRepository usuarioGrupoRepository, GrupoRepository grupoRepository) {
        this.usuarioGrupoRepository = usuarioGrupoRepository;
        this.grupoRepository = grupoRepository;
    }

    public void addUserToGroup(UsuarioGrupo ug) throws SQLException {
        usuarioGrupoRepository.save(ug);
    }

    public int unirAlumnoPorCodigo(String codigoUnico, int idUsuario) throws SQLException {
        int idGrupo = grupoRepository.findIdGrupoByCodigoUnico(codigoUnico);

        if (usuarioGrupoRepository.exists(idUsuario, idGrupo)) {
            throw new SQLException("Alumno ya dentro del grupo");
        }

        UsuarioGrupo ug = new UsuarioGrupo();
        ug.setIdUsuario(idUsuario);
        ug.setIdGrupo(idGrupo);
        ug.setRol("alumno");
        ug.setFechaUnion(LocalDateTime.now());

        usuarioGrupoRepository.save(ug);
        return idGrupo;
    }

    public List<UsuarioGrupo> getUsersByGroup(int idGrupo) throws SQLException {
        return usuarioGrupoRepository.findUsersByGrupo(idGrupo);
    }

    public void deleteUserOfGroup(int idGrupo, int idUsuario) throws SQLException {
        usuarioGrupoRepository.delete(idGrupo, idUsuario);
    }
}
