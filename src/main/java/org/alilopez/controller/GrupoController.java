package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.alilopez.dto.GrupoConRolResponse;
import org.alilopez.model.Grupo;
import org.alilopez.service.GrupoService;
import org.alilopez.dto.CrearGrupoRequest;
import org.alilopez.dto.UnirAGrupoRequest;
import org.alilopez.service.UsuarioGrupoService;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GrupoController {
    private final GrupoService grupoService;
    private final UsuarioGrupoService usuarioGrupoService;

    public GrupoController(GrupoService grupoService, UsuarioGrupoService usuarioGrupoService) {
        this.grupoService = grupoService;
        this.usuarioGrupoService = usuarioGrupoService;
    }

    public void getAll(Context ctx) {
        try {
            List<Grupo> grupos = grupoService.getAll();
            ctx.json(grupos);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener clases");
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Grupo grupo = grupoService.getByIdGrupo(id);
            if (grupo != null) {
                ctx.json(grupo);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Clase no encontrada");
            }
        } catch (Exception e) {
            ctx.status(404).result("Error al obtener clase");
        }
    }

    public void getByCodigoUnico(Context ctx) {
        try {
            String id = ctx.pathParam("codigoUnico");
            Grupo grupo = grupoService.getByCodigoUnico(id);
            if (grupo != null) {
                ctx.json(grupo);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Clase no encontrada");
            }
        } catch (Exception e) {
            ctx.status(404).result("Error al obtener clase");
        }
    }

    public void create(Context ctx) {
        CrearGrupoRequest data = ctx.bodyAsClass(CrearGrupoRequest.class);

        try {
            int idGrupo = grupoService.createGrupoConDocente(data);
            ctx.status(201).json(Map.of(
                    "mensaje", "Grupo creado correctamente",
                    "idGrupo", idGrupo
            ));

        } catch (SQLException e) {
            ctx.status(400).result("Error al crear grupo "+ e.getMessage());
        } catch (Exception e){
            ctx.status(500).result("Error al crear grupo "+ e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Grupo grupo = ctx.bodyAsClass(Grupo.class);
            grupo.setIdGrupo(id); // Asegúrate de que el ID en la URL y en el objeto coincidan
            boolean updated = grupoService.updateGrupo(grupo);
            if (updated) {
                ctx.status(HttpStatus.OK).result("Clase actualizada");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Clase no encontrada");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al actualizar clase");
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = grupoService.deleteGrupo(id);
            if (deleted) {
                ctx.status(HttpStatus.OK).result("Clase eliminada");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Clase no encontrada");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al eliminar clase");
        }
    }

    public void unirseAGrupo(Context ctx) {
        UnirAGrupoRequest data = ctx.bodyAsClass(UnirAGrupoRequest.class);

        try {
            int idGrupo = usuarioGrupoService.unirAlumnoPorCodigo(data.codigoUnico, data.idUsuario);
            ctx.status(201).json(Map.of(
                    "mensaje", "Te uniste al grupo correctamente",
                    "idGrupo", idGrupo
            ));
        } catch (SQLException e) {
            ctx.status(400).json(Map.of("error", "No se pudo unir al grupo: " + e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error inesperado: " + e.getMessage()));
        }
    }

    public void getGruposComoDocente(Context ctx) {
        try{
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            List<Grupo> grupos = grupoService.getGruposComoDocente(idUsuario);
            ctx.json(grupos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener grupos como docente");
        }
    }

    public void getGruposComoAlumno(Context ctx) {
        try{
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            List<Grupo> grupos = grupoService.getGruposComoAlumno(idUsuario);
            ctx.json(grupos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener grupos como alumno");
        }
    }

    public void getGrupoYRol(Context ctx) {
        try {
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            GrupoConRolResponse response = grupoService.getGrupoYRol(idGrupo, idUsuario);
            ctx.json(response);
        } catch (Exception e) {
            ctx.status(404).json(Map.of("error", "No se pudo obtener grupo o rol"));
        }
    }
}
