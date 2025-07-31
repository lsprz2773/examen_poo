package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.alilopez.model.EvidenciaObjetivo;
import org.alilopez.service.EvidenciaObjetivoService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public class EvidenciaObjetivoController {
    private final EvidenciaObjetivoService evidenciaObjetivoService;

    public EvidenciaObjetivoController(EvidenciaObjetivoService evidenciaObjetivoService) {
        this.evidenciaObjetivoService = evidenciaObjetivoService;
    }

    // Método para crear la evidencia
    public void create(Context ctx) {
        try {
            // Obtener el ID del objetivo y el ID del usuario
            int idObjetivo = Integer.parseInt(ctx.pathParam("idObjetivo"));
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

            // Crear un nombre único para el archivo
            String nombreArchivo = "evidencia_objetivo_" + idUsuario + "_" + System.currentTimeMillis() + ".pdf";
            Path destino = Paths.get("static/evidencias/objetivos", nombreArchivo);

            // Guardar el archivo en el sistema
            try (InputStream input = archivo.content()) {
                Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
            }

            // Crear la evidencia y guardarla en la base de datos
            EvidenciaObjetivo evidencia = new EvidenciaObjetivo();
            evidencia.setIdObjetivo(idObjetivo);
            evidencia.setIdUsuario(idUsuario);
            evidencia.setFileURL("evidencias/objetivos/" + nombreArchivo); // URL pública
            evidencia.setFechaEnvio(LocalDateTime.now());
            evidencia.setEstado(true); // Asumimos que la evidencia fue entregada

            evidenciaObjetivoService.agregarEvidencia(evidencia);

            ctx.status(201).result("Evidencia agregada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al agregar la evidencia.");
        }
    }

    public void getByObjetivo(Context ctx) {
        try {
            // Obtener el idObjetivo y idUsuario de los parámetros
            int idObjetivo = Integer.parseInt(ctx.pathParam("idObjetivo"));
            int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));

            // Obtener la evidencia desde el servicio
            EvidenciaObjetivo evidencia = evidenciaObjetivoService.getEvidenciaByObjetivo(idObjetivo, idUsuario);

            if (evidencia != null) {
                // Si la evidencia existe, devolvemos el objeto
                ctx.json(evidencia);
            } else {
                ctx.status(404).result("Evidencia no encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener la evidencia.");
        }
    }

}
