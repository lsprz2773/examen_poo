package org.alilopez.service;

import org.alilopez.model.RachaUsuario;
import org.alilopez.repository.RachaUsuarioRepository;

import java.sql.SQLException;

public class RachaUsuarioService {
    private final RachaUsuarioRepository rachaUsuarioRepository;

    public RachaUsuarioService(RachaUsuarioRepository rachaUsuarioRepository) {
        this.rachaUsuarioRepository = rachaUsuarioRepository;
    }

    public RachaUsuario getByRachaByUserId(int idUsuario) throws SQLException {
        return rachaUsuarioRepository.findRachaByIdUser(idUsuario);
    }
}
