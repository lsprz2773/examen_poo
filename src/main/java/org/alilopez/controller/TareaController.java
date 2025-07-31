package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import org.alilopez.dto.AlumnoEvidenciaDTO;
import org.alilopez.dto.AlumnoNoEvidenciaDTO;
import org.alilopez.dto.CrearTareaRequest;
import org.alilopez.dto.TareaAlumnoDTO;
import org.alilopez.model.Tarea;
import org.alilopez.model.UsuarioGrupo;
import org.alilopez.service.TareaService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.List;

public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    public void getAll(Context ctx) {
        try {
            List<Tarea> tareas = tareaService.getAll();
            ctx.json(tareas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener tareas");
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Tarea tarea = tareaService.getByIdTarea(id);
            if (tarea != null) {
                ctx.json(tarea);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Tarea no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error de servidor");
        }
    }

    public void create(Context ctx) {
        try {
            // Obtener datos del formulario
            String titulo = ctx.formParam("titulo");
            String descripcion = ctx.formParam("descripcion");
            String fechaEntregaStr = ctx.formParam("fechaEntrega");
            int idGrupo = Integer.parseInt(ctx.formParam("idGrupo"));
            int duracionPomodoro = Integer.parseInt(ctx.formParam("duracionPomodoro"));
            int duracionDescanso = Integer.parseInt(ctx.formParam("duracionDescanso"));
            int totalPomodoros = Integer.parseInt(ctx.formParam("intentos"));
            UploadedFile archivo = ctx.uploadedFile("recurso");

            // Parsear fecha
            LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaStr);

            // Manejar archivo opcional
            String recursoURL = null;
            if (archivo != null) {
                // Validar que el archivo sea un PDF
                if (!archivo.contentType().equals("application/pdf")) {
                    ctx.status(400).result("Solo se permiten archivos PDF.");
                    return;
                }

                // Generar un nombre único para el archivo
                String nombreArchivo = "recurso_tarea_" + idGrupo + "_" + System.currentTimeMillis() + ".pdf";
                Path destino = Paths.get("static/recursos/tareas", nombreArchivo);

                // Guardar el archivo
                try (InputStream input = archivo.content()) {
                    Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                }

                // URL relativa para la base de datos
                recursoURL = "recursos/tareas/" + nombreArchivo;
            }

            // Crear el objeto tarea

            Tarea tarea = new Tarea();
            tarea.setTitulo(titulo);
            tarea.setDescripcion(descripcion);
            tarea.setRecursoURL(recursoURL);
            tarea.setFechaEntrega(fechaEntrega);
            tarea.setIdGrupo(idGrupo);
            tarea.setTotalPomodoros(totalPomodoros);
            tarea.setDuracionPomodoro(duracionPomodoro);
            tarea.setDuracionDescanso(duracionDescanso);


            // Crear la tarea
            tareaService.createTarea(tarea);

            // Responder con un mensaje de éxito
            ctx.status(201).result("Tarea creada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al crear tarea: " + e.getMessage());
        }
    }


    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Tarea tarea = ctx.bodyAsClass(Tarea.class);
            tarea.setIdTarea(id); // Asegúrate de que el ID en la URL y en el objeto coincidan
            boolean updated = tareaService.updateTarea(tarea);
            if (updated) {
                ctx.status(HttpStatus.OK).result("Tarea actualizada");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Tarea no encontrada");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al actualizar tarea");
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = tareaService.deleteTarea(id);
            if (deleted) {
                ctx.status(HttpStatus.OK).result("Tarea eliminada");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Tarea no encontrada");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al eliminar tarea");
        }
    }

    public void getTareasByGrupo(Context ctx) {
        try {
            int idGrupo = Integer.parseInt(ctx.pathParam("id"));
            List<Tarea> tareas = tareaService.getTareasByIdGrupo(idGrupo);
            ctx.json(tareas);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener tareas");
        }
    }

    public void getAlumnosByTarea(Context ctx) {
        try {
            int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
            List<UsuarioGrupo> alumnos = tareaService.getAlumnosPorTarea(idTarea);

            if (!alumnos.isEmpty()) {
                ctx.json(alumnos);  // Devolvemos la lista de alumnos
            } else {
                ctx.status(404).result("No se encontraron alumnos para esta tarea.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener la lista de alumnos.");
        }
    }

    public void getAlumnosConTareaCompletada(Context ctx) {
        try {
            int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));

            List<AlumnoEvidenciaDTO> alumnos = tareaService.getAlumnosConTareaCompletada(idTarea, idGrupo);
            ctx.json(alumnos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener alumnos con tarea completada");
        }
    }

    public void getAlumnosQueNoEntregaron(Context ctx) {
        try {
            int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));

            List<AlumnoNoEvidenciaDTO> alumnos = tareaService.getAlumnosQueNoEntregaron(idTarea, idGrupo);
            ctx.json(alumnos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener alumnos que no entregaron");
        }
    }

    public void getTareasCompletadasPorAlumno(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));

            List<TareaAlumnoDTO> tareas = tareaService.getTareasCompletadasPorAlumno(idUsuario, idGrupo);
            ctx.json(tareas);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener tareas completadas");
        }
    }

    public void getTareasPendientesPorAlumno(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));

            List<TareaAlumnoDTO> tareas = tareaService.getTareasPendientesPorAlumno(idUsuario, idGrupo);
            ctx.json(tareas);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al obtener tareas pendientes");
        }
    }

}
