package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.ObjetivoController;

public class ObjetivoRoutes {
    private final ObjetivoController objetivoController;

    public ObjetivoRoutes(ObjetivoController objetivoController) {
        this.objetivoController = objetivoController;
    }

    public void register(Javalin app) {
        app.get("/objetivos", objetivoController::getAll);
        app.post("/objetivos", objetivoController::create);
        app.get("/objetivos/{id}", objetivoController::getById);
        app.put("/objetivos/{id}", objetivoController::update);
        app.delete("/objetivos/{id}", objetivoController::delete);
        app.get("/objetivos/usuario/{id}", objetivoController::getObjetivosByUser);
        app.get("/objetivos/entregados/{idUsuario}", objetivoController::getObjetivosEntregados);
        app.get("/objetivos/no-entregados/{idUsuario}", objetivoController::getObjetivosNoEntregados);
        app.get("/objetivos/ultimo/{idUsuario}", objetivoController::getUltimoObjetivoByUsuario);

        // Ejemplo de más rutas:
        //app.put("/sesion/:id", sesionController::update);
        //app.delete("/sesion/:id", sesionController::delete);
    }
}
