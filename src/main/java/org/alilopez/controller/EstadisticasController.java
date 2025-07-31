package org.alilopez.controller;

import io.javalin.http.Context;
import org.alilopez.model.EstadisticasDTO;
import org.alilopez.service.EstadisticasService;
import java.util.Map;



public class EstadisticasController {
    private final EstadisticasService service;

    public EstadisticasController(EstadisticasService service) {
        this.service = service;
    }

    public void getEstadisticasGenerales(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        EstadisticasDTO dto = service.obtenerEstadisticasGenerales(idUsuario);
        ctx.json(dto);
    }

    public void getTiempoEfectivo(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        int totalMinutos = service.obtenerTiempoTotalMinutos(idUsuario);
        String amigable = totalMinutos >= 60 ?
                (totalMinutos / 60) + " horas" :
                totalMinutos + " minutos";
        ctx.json(new TiempoEfectivoResponse(totalMinutos, amigable));
    }

    private record TiempoEfectivoResponse(int tiempoTotalMinutos, String formatoAmigable) {}


    public void getTiempoPorDia(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        Map<String, Object> resultado = service.obtenerTiempoPorDiaChart(idUsuario);
        ctx.json(resultado);
    }

    public void getPomodorosPorDia(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        Map<String, Object> resultado = service.obtenerPomodorosPorDiaChart(idUsuario);
        ctx.json(resultado);
    }

    public void getObjetivosPorDia(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        Map<String, Object> resultado = service.obtenerObjetivosPorDiaChart(idUsuario);
        ctx.json(resultado);
    }

    public void getFallosPorDia(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        Map<String, Object> resultado = service.obtenerFallosPorDiaChart(idUsuario);
        ctx.json(resultado);
    }


}