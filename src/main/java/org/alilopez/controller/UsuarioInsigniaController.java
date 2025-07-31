package org.alilopez.controller;

import io.javalin.http.Context;
import org.alilopez.model.UsuarioInsignia;
import org.alilopez.service.UsuarioInsigniaService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioInsigniaController {
    private final UsuarioInsigniaService usuarioInsigniaService;

    public UsuarioInsigniaController(UsuarioInsigniaService usuarioInsigniaService) {
        this.usuarioInsigniaService = usuarioInsigniaService;
    }

    public void getInsigniasByUsuario(Context ctx) {
        try{
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            List<UsuarioInsignia> usuarioInsignias = usuarioInsigniaService.getByUsuario(idUsuario);
            ctx.json(usuarioInsignias);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener insignias del usuario");
        }
    }

    public void assignInsigniasToUsuario(Context ctx) {
        try{
            UsuarioInsignia usuarioInsignia = ctx.bodyAsClass(UsuarioInsignia.class);
            if (!usuarioInsigniaService.exists(usuarioInsignia.getIdUsuario(), usuarioInsignia.getIdInsignia())){
                usuarioInsigniaService.assign(usuarioInsignia.getIdUsuario(), usuarioInsignia.getIdInsignia(), usuarioInsignia.getNivel());
                ctx.status(201).result("Insignia asignada.");
            } else {
                ctx.status(409).result("El usuario ya tiene esta insignia.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al asignar insignia.");
        }
    }

    public void updateInsigniasFromUsuario(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            UsuarioInsignia usuarioInsignia = ctx.bodyAsClass(UsuarioInsignia.class);
            usuarioInsigniaService.updateNivel(idUsuario, usuarioInsignia.getIdInsignia(), usuarioInsignia.getNivel());
            ctx.status(200).result("Nivel actualizado");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al actualizar insignias del usuario");
        }
    }
}
