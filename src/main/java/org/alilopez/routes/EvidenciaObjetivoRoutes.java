package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.EvidenciaObjetivoController;

public class EvidenciaObjetivoRoutes {
    private final EvidenciaObjetivoController evidenciaObjetivoController;
    public EvidenciaObjetivoRoutes(EvidenciaObjetivoController evidenciaObjetivoController) {
        this.evidenciaObjetivoController = evidenciaObjetivoController;
    }
    public void register(Javalin app) {
        app.post("/objetivo/{idObjetivo}/evidencia", evidenciaObjetivoController::create);  // Para subir la evidencia
        app.get("/objetivo/{idObjetivo}/evidencia/{idUsuario}", evidenciaObjetivoController::getByObjetivo);  // Para obtener la evidencia
    }
}
