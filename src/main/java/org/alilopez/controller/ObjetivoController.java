package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.alilopez.dto.CrearObjetivoRequest;
import org.alilopez.dto.CrearObjetivoResponse;
import org.alilopez.model.Objetivo;
import org.alilopez.service.ObjetivoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ObjetivoController {
    private final ObjetivoService objetivoService;

    public ObjetivoController(ObjetivoService objetivoService) {
        this.objetivoService = objetivoService;
    }

    public void getAll(Context ctx) {
        try {
            List<Objetivo> objetivos = objetivoService.getAll();
            ctx.json(objetivos);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener objetivos");
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Objetivo objetivo = objetivoService.getByIdObjetivo(id);
            if (objetivo != null) {
                ctx.json(objetivo);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Objetivo no encontrado");
            }
        } catch (Exception e) {
            ctx.status(404).result("Error al obtener objetivo");
        }
    }

    public void create(Context ctx) {
        CrearObjetivoRequest data = ctx.bodyAsClass(CrearObjetivoRequest.class);
        try {
            CrearObjetivoResponse resultado = objetivoService.crearObjetivoConSesion(data);
            ctx.status(201).json(Map.of(
                    "mensaje","Objetivo creado exitosamente",
                    "idObjetivo", resultado.getIdObjetivo(),
                    "idSesion", resultado.getIdSesion()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al crear objetivo: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Objetivo objetivo = ctx.bodyAsClass(Objetivo.class);
            objetivo.setIdObjetivo(id);
            boolean updated = objetivoService.updateObjetivo(objetivo);
            if (updated) {
                ctx.status(HttpStatus.OK).result("Objetivo actualizado");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Objetivo no encontrado");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al actualizar objetivo");
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = objetivoService.deleteObjetivo(id);
            if (deleted) {
                ctx.status(HttpStatus.OK).result("Objetivo eliminado");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Objetivo no encontrado");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al eliminar objetivo");
        }
    }

    public void getObjetivosByUser(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("id"));
            List<Objetivo> objetivos = objetivoService.getObjetivosByIdUser(idUsuario);
            ctx.json(objetivos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener objetivos");
        }
    }

    public void getObjetivosEntregados(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            List<Objetivo> objetivos = objetivoService.getObjetivosEntregados(idUsuario);
            ctx.json(objetivos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener objetivos entregados");
        }
    }

    public void getObjetivosNoEntregados(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            List<Objetivo> objetivos = objetivoService.getObjetivosNoEntregados(idUsuario);
            ctx.json(objetivos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener objetivos no entregados");
        }
    }

    public void getUltimoObjetivoByUsuario(Context ctx) {
        try{
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            Objetivo objetivo = objetivoService.getUltimoObjetivoByUsuario(idUsuario);

            if (objetivo != null) {
                ctx.status(200).json(objetivo);
            } else {
                ctx.status(404).result("Usuario sin objetivos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener objetivo");
        }
    }
}
