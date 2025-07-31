package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.RachaUsuarioController;

public class RachaUsuarioRoutes {
    private final RachaUsuarioController rachaUsuarioController;
    public RachaUsuarioRoutes(RachaUsuarioController rachaUsuarioController) {
        this.rachaUsuarioController = rachaUsuarioController;
    }
    public void register(Javalin app) {
        app.get("/usuarios/{idUsuario}/racha", rachaUsuarioController::getById);
    }
}
