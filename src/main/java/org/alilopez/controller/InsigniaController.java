package org.alilopez.controller;

import io.javalin.http.Context;
import org.alilopez.service.InsigniaService;
import org.alilopez.service.UsuarioInsigniaService;

public class InsigniaController {
    private final InsigniaService insigniaService;
    private final UsuarioInsigniaService usuarioInsigniaService; // Agregar esta línea

    public InsigniaController(InsigniaService insigniaService, UsuarioInsigniaService usuarioInsigniaService) {
        this.insigniaService = insigniaService;
        this.usuarioInsigniaService = usuarioInsigniaService; // Agregar esta línea
    }

    public void getAll(Context ctx) {
        try {
            var insignias = insigniaService.getAllInsignias();
            ctx.json(insignias);
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener insignias");
        }
    }

    public void getInsigniasUsuario(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            var insignias = usuarioInsigniaService.getByUsuario(idUsuario);
            ctx.json(insignias);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener insignias del usuario");
        }
    }

    public void recalcularInsignias(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            insigniaService.recalcularInsigniasUsuario(idUsuario);
            ctx.status(200).result("Insignias recalculadas correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al recalcular insignias");
        }
    }
}