package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.EvidenciaTareaController;

public class EvidenciaTareaRoutes {
    private final EvidenciaTareaController controller;

    public EvidenciaTareaRoutes(EvidenciaTareaController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        // Ruta para crear la evidencia
        app.post("/tarea/{idTarea}/evidencia", controller::create);

        // Ruta para obtener la evidencia de una tarea y un usuario específico
        app.get("/tarea/{idTarea}/usuario/{idUsuario}/evidencia", controller::getByTareaYUsuario);
        app.put("/evidencia/{idEvidencia}/estado", controller::updateEstado);
    }
}
