package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.alilopez.model.EvidenciaTarea;
import org.alilopez.service.EvidenciaTareaService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public class EvidenciaTareaController {
    private final EvidenciaTareaService evidenciaTareaService;

    public EvidenciaTareaController(EvidenciaTareaService evidenciaTareaService) {
        this.evidenciaTareaService = evidenciaTareaService;
    }

    // Método para obtener la evidencia de una tarea y un usuario específico
    public void getByTareaYUsuario(Context ctx) {
        try {
            int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));

            // Obtener la evidencia de la tarea para ese usuario
            EvidenciaTarea evidenciaTarea = evidenciaTareaService.getEvidenciaByTarea(idTarea, idUsuario);

            if (evidenciaTarea != null) {
                ctx.json(evidenciaTarea);  // Si la evidencia existe, devolverla
            } else {
                ctx.status(404).result("Evidencia no encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener la evidencia.");
        }
    }

    // Método para subir una evidencia para una tarea
    public void create(Context ctx) {
        try {
            // Obtener idTarea y idUsuario de los parámetros
            int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
            int idUsuario = Integer.parseInt(ctx.formParam("idUsuario"));
            UploadedFile archivo = ctx.uploadedFile("file");

            if (archivo == null) {
                ctx.status(400).result("No se envió ningún archivo.");
                return;
            }

            // Validar que el archivo sea un PDF
            if (!archivo.contentType().equals("application/pdf")) {
                ctx.status(400).result("Solo se permiten archivos PDF.");
                return;
            }

            // Generar un nombre único para el archivo
            String nombreArchivo = "evidencia_tarea_" + idUsuario + "_" + System.currentTimeMillis() + ".pdf";
            Path destino = Paths.get("static/evidencias/tareas", nombreArchivo);

            // Guardar el archivo
            try (InputStream input = archivo.content()) {
                Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
            }

            // Crear la evidencia de la tarea
            EvidenciaTarea evidencia = new EvidenciaTarea();
            evidencia.setIdTarea(idTarea);
            evidencia.setIdUsuario(idUsuario);
            evidencia.setFileURL("evidencias/tareas/" + nombreArchivo); // URL correcta
            evidencia.setEstado("entregada"); // Estado inicial de la evidencia

            // Guardar la evidencia en la base de datos
            evidenciaTareaService.agregarEvidencia(evidencia);

            // Responder con un mensaje de éxito
            ctx.status(201).result("Evidencia agregada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al agregar la evidencia.");
        }
    }

    public void updateEstado(Context ctx) {
        try {
            int idEvidencia = Integer.parseInt(ctx.pathParam("idEvidencia"));
            String nuevoEstado = ctx.bodyAsClass(String.class);

            // Validar que el estado no esté vacío
            if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
                ctx.status(400).result("El estado es obligatorio");
                return;
            }

            boolean actualizado = evidenciaTareaService.actualizarEstadoEntrega(idEvidencia, nuevoEstado);

            if (actualizado) {
                ctx.status(200).result("Estado actualizado correctamente");
            } else {
                ctx.status(404).result("Evidencia no encontrada");
            }

        } catch (NumberFormatException e) {
            ctx.status(400).result("ID de evidencia inválido");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al actualizar el estado");
        }
    }
}
