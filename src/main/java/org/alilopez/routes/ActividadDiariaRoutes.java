package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.ActividadDiariaController;

public class ActividadDiariaRoutes {
    private final ActividadDiariaController actividadDiariaController;
    public ActividadDiariaRoutes(ActividadDiariaController actividadDiariaController) {
        this.actividadDiariaController = actividadDiariaController;
    }
    public void register(Javalin app) {
        app.put("/actividad", actividadDiariaController::updateById);
    }
}
