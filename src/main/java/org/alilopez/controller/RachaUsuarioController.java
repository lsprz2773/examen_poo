package org.alilopez.controller;

import io.javalin.http.Context;
import org.alilopez.model.RachaUsuario;
import org.alilopez.service.RachaUsuarioService;

public class RachaUsuarioController {
    private final RachaUsuarioService rachaUsuarioService;

    public RachaUsuarioController(RachaUsuarioService rachaUsuarioService) {
        this.rachaUsuarioService = rachaUsuarioService;
    }

    public void getById(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            RachaUsuario rachaUsuario = rachaUsuarioService.getByRachaByUserId(idUsuario);
            if (rachaUsuario != null) {
                ctx.json(rachaUsuario);
            } else {

                ctx.status(404).result("Racha no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener racha");
        }
    }
}
