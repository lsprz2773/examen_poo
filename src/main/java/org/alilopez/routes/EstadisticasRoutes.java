package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.EstadisticasController;

public class EstadisticasRoutes {

    private final EstadisticasController controller;

    public EstadisticasRoutes(EstadisticasController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        app.get("/estadisticas/{idUsuario}", controller::getEstadisticasGenerales);
        app.get("/estadisticas/{idUsuario}/tiempo", controller::getTiempoEfectivo);
        app.get("/estadisticas/{idUsuario}/tiempo-por-dia", controller::getTiempoPorDia);
        app.get("/estadisticas/{idUsuario}/pomodoros-por-dia", controller::getPomodorosPorDia);
        app.get("/estadisticas/{idUsuario}/objetivos-por-dia", controller::getObjetivosPorDia);
        app.get("/estadisticas/{idUsuario}/fallos-por-dia", controller::getFallosPorDia);
    }
}