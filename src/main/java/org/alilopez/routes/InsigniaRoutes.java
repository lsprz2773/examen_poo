package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.InsigniaController;

public class InsigniaRoutes {
    private final InsigniaController insigniaController;

    public InsigniaRoutes(InsigniaController insigniaController) {
        this.insigniaController = insigniaController;
    }

    public void register(Javalin app) {
        app.get("/insignias", insigniaController::getAll);
        app.get("/usuarios/{idUsuario}/insignias", insigniaController::getInsigniasUsuario);
        // Recalcular insignias de un usuario (para testing)
        app.post("/usuarios/{idUsuario}/insignias/recalcular", insigniaController::recalcularInsignias);
    }
}
