package org.alilopez.model;

import java.time.LocalDateTime;

public class EvidenciaTarea {
    private int idEvidencia;
    private int idTarea;
    private int idUsuario;
    private String fileURL;
    private LocalDateTime fechaEnvio;
    private String estado; // Usamos un String para almacenar el valor del ENUM

    // Getters y Setters
    public int getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(int idEvidencia) { this.idEvidencia = idEvidencia; }

    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getFileURL() { return fileURL; }
    public void setFileURL(String fileURL) { this.fileURL = fileURL; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
