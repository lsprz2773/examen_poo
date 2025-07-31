package org.alilopez.dto;

import java.time.LocalDateTime;

public class AlumnoEvidenciaDTO {
    private int idUsuario;
    private String nombre;

    // Datos de la evidencia
    private int idEvidencia;
    private String fileURL;
    private LocalDateTime fechaEnvio;
    private String estadoEntrega;

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(int idEvidencia) { this.idEvidencia = idEvidencia; }

    public String getFileURL() { return fileURL; }
    public void setFileURL(String fileURL) { this.fileURL = fileURL; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getEstadoEntrega() { return estadoEntrega; }
    public void setEstadoEntrega(String estadoEntrega) { this.estadoEntrega = estadoEntrega; }
}
