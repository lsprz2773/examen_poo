package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.SesionController;

public class SesionRoutes {
    private final SesionController sesionController;

    public SesionRoutes(SesionController sesionController) {
        this.sesionController = sesionController;
    }

    public void register(Javalin app) {
        app.get("/sesiones", sesionController::getAll);
        app.post("/sesiones", sesionController::create);
        app.get("/sesiones/{id}", sesionController::getById);
        app.put("/sesiones/{id}", sesionController::update);
        app.delete("/sesiones/{id}", sesionController::delete);
        app.post("/sesiones/tarea", sesionController::createSesionTarea);

        // Ejemplo de más rutas:
        //app.put("/sesion/:id", sesionController::update);
        //app.delete("/sesion/:id", sesionController::delete);
    }
}
