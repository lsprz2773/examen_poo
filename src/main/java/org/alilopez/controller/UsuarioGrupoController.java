package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.alilopez.model.UsuarioGrupo;
import org.alilopez.service.UsuarioGrupoService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioGrupoController {
    private final UsuarioGrupoService usuarioGrupoService;

    public UsuarioGrupoController(UsuarioGrupoService usuarioGrupoService) {
        this.usuarioGrupoService = usuarioGrupoService;
    }

    public static class UnirseGrupoRequest {
        public String codigoUnico;
        public int idUsuario;
    }

    public void create(Context ctx) {
        try {
            UsuarioGrupo usuarioGrupo = ctx.bodyAsClass(UsuarioGrupo.class);
            usuarioGrupoService.addUserToGroup(usuarioGrupo);
            ctx.status(201).result("Usuario agregado al grupo");
        } catch (Exception e) {
            ctx.status(400).result("Error al agregar usuario al grupo");
        }
    }

    public void getByGrupo(Context ctx) {
        try {
            int idGrupo = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(usuarioGrupoService.getUsersByGroup(idGrupo));
        } catch (Exception e) {
            ctx.status(400).result("Error al obtener usuarios del grupo");
        }
    }

    public void delete(Context ctx) {
        try {
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            usuarioGrupoService.deleteUserOfGroup(idGrupo, idUsuario);
            ctx.status(200).result("Usuario eliminado del grupo");
        } catch (Exception e) {
            ctx.status(400).result("Error al eliminar usuario del grupo");
        }
    }

    public void join(Context ctx) {
        try {
            UnirseGrupoRequest data = ctx.bodyAsClass(UnirseGrupoRequest.class);
            usuarioGrupoService.unirAlumnoPorCodigo(data.codigoUnico, data.idUsuario);
            ctx.status(201).result("Usuario unido al grupo como alumno");
        } catch (Exception e) {
            ctx.status(400).result("Error al unirse al grupo: " + e.getMessage());
        }
    }
}
