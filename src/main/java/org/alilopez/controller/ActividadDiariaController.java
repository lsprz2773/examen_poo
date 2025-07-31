package org.alilopez.controller;

import io.javalin.http.Context;
import org.alilopez.model.ActividadDiaria;
import org.alilopez.service.ActividadDiariaService;

public class ActividadDiariaController {
    private final ActividadDiariaService actividadDiariaService;

    public ActividadDiariaController( ActividadDiariaService actividadDiariaService ) {
        this.actividadDiariaService = actividadDiariaService;
    }

    public void updateById(Context ctx){
        try {
            ActividadDiaria actividadDiaria = ctx.bodyAsClass(ActividadDiaria.class);
            actividadDiariaService.updateActividadAndRacha(actividadDiaria.getIdUsuario());
            ctx.status(200).result("Actividad y racha actualizada.");
        } catch (Exception e) {
            ctx.status(400).result("Error al actualizar actividad diaria.");
        }
    }
}
