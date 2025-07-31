package org.alilopez.model;

import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AdjuntarArchivos {
    public static String saveFile(UploadedFile file) throws IOException {
        String uploadDir = "src/main/resources/static/uploads/";
        String fileName = System.currentTimeMillis() + "_" + file.filename(); // evitar colisiones

        File target = new File(uploadDir + fileName);
        try (FileOutputStream out = new FileOutputStream(target)) {
            out.write(file.content().readAllBytes());
        }

        return "/archivos/" + fileName; // esto será el recursoURL para tareas/evidencias
    }
}
