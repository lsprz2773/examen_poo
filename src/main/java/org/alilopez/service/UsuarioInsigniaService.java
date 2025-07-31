package org.alilopez.service;

import org.alilopez.model.UsuarioInsignia;
import org.alilopez.repository.UsuarioInsigniaRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioInsigniaService {
    private final UsuarioInsigniaRepository usuarioInsigniaRepository;

    public UsuarioInsigniaService(UsuarioInsigniaRepository usuarioInsigniaRepository) {
        this.usuarioInsigniaRepository = usuarioInsigniaRepository;
    }

    public List<UsuarioInsignia> getByUsuario(int idUsuario) throws SQLException {
        return usuarioInsigniaRepository.findByUsuario(idUsuario);
    }

    public boolean exists(int idUsuario, int idInsignia) throws SQLException {
        return usuarioInsigniaRepository.exists(idUsuario, idInsignia);
    }

    public void assign(int idUsuario, int idInsignia, int nivel) throws SQLException {
        usuarioInsigniaRepository.insert(idUsuario, idInsignia, nivel);
    }

    public void updateNivel(int idUsuario, int idInsignia, int nivel) throws SQLException {
        usuarioInsigniaRepository.updateNivel(idUsuario, idInsignia, nivel);
    }

}
