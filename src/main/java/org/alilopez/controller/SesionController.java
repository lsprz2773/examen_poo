package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.alilopez.config.DatabaseConfig;
import org.alilopez.dto.CrearSesionTareaDTO;
import org.alilopez.model.Sesion;
import org.alilopez.repository.SesionTareaRepository;
import org.alilopez.service.SesionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SesionController {
    private final SesionService sesionService;
    private final SesionTareaRepository sesionTareaRepository;

    public SesionController(SesionService sesionService, SesionTareaRepository sesionTareaRepository) {
        this.sesionService = sesionService;
        this.sesionTareaRepository = sesionTareaRepository;
    }

    public void getAll(Context ctx) {
        try {
            List<Sesion> sesions = sesionService.getAll();
            ctx.json(sesions);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener sesiones");
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Sesion sesion = sesionService.getByIdSesion(id);
            if (sesion != null) {
                ctx.json(sesion);
            } else {

                ctx.status(HttpStatus.NOT_FOUND).result("Sesion no encontrada");
            }
        } catch (Exception e) {
            ctx.status(404).result("Error al obtener sesion");
        }
    }

    public void create(Context ctx) {
        try {
            Sesion sesion = ctx.bodyAsClass(Sesion.class);
            if (sesion.getFechaCreacion() == null) {
                sesion.setFechaCreacion(LocalDateTime.now());
            }
            sesionService.createSesion(sesion);
            ctx.status(201).result("Sesion creada");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al crear sesion");
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Sesion sesion = ctx.bodyAsClass(Sesion.class);
            if (sesion.getFechaCreacion() == null) {
                sesion.setFechaCreacion(LocalDateTime.now());
            }
            sesion.setIdSesion(id); // Asegúrate de que el ID en la URL y en el objeto coincidan
            boolean updated = sesionService.updateSesion(sesion);
            if (updated) {
                ctx.status(HttpStatus.OK).result("Sesion actualizada");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Sesion no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al actualizar sesion");
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = sesionService.deleteSesion(id);
            if (deleted) {
                ctx.status(HttpStatus.OK).result("Sesion eliminado");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Sesion no encontrada");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al eliminar sesion");
        }
    }

    public void createSesionTarea(Context ctx) {
        Connection conn = null;
        try {
            // Recibir datos del frontend
            CrearSesionTareaDTO dto = ctx.bodyAsClass(CrearSesionTareaDTO.class);

            // Convertir strings a enteros
            Sesion sesion = new Sesion();
            sesion.setIdUsuario(dto.getIdUsuario());
            sesion.setDuracionReal(Integer.parseInt(dto.getDuracionReal()));
            sesion.setDescansoReal(Integer.parseInt(dto.getDescansoReal()));
            sesion.setIntentos(Integer.parseInt(dto.getIntentos()));
            sesion.setEstado(dto.getEstado());
            sesion.setPomodoros(Integer.parseInt(dto.getPomodoros()));
            sesion.setFechaCreacion(LocalDateTime.now());

            // Obtener conexión para transacción
            conn = DatabaseConfig.getDataSource().getConnection();
            conn.setAutoCommit(false);

            // 1. Crear la sesión
            int idSesion = sesionService.createSesionWithId(sesion);

            // 2. Crear la relación en sesion_tarea
            sesionTareaRepository.save(idSesion, dto.getIdTarea());

            // Confirmar transacción
            conn.commit();

            ctx.json(Map.of(
                    "idSesion", idSesion,
                    "mensaje", "Sesión creada y relacionada exitosamente"
            ));

        } catch (Exception e) {
            // Revertir en caso de error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            ctx.status(500).result("Error al crear sesión: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
