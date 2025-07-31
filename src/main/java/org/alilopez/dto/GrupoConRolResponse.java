package org.alilopez.dto;

import org.alilopez.model.Grupo;

public class GrupoConRolResponse {
    public Grupo grupo;
    public String rol;

    public GrupoConRolResponse(Grupo grupo, String rol) {
        this.grupo = grupo;
        this.rol = rol;
    }
}
