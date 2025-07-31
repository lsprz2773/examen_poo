package org.alilopez.dto;

import java.time.LocalDateTime;

public class TareaAlumnoDTO {
    private int idTarea;
    private String titulo;
    private String descripcion;
    private String recursoURL;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;
    private int idGrupo;

    // Datos de evidencia (si existe)
    private Integer idEvidencia;
    private String fileURL;
    private LocalDateTime fechaEnvio;
    private String estadoEntrega;

    // Getters y Setters
    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRecursoURL() { return recursoURL; }
    public void setRecursoURL(String recursoURL) { this.recursoURL = recursoURL; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }

    public Integer getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Integer idEvidencia) { this.idEvidencia = idEvidencia; }

    public String getFileURL() { return fileURL; }
    public void setFileURL(String fileURL) { this.fileURL = fileURL; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getEstadoEntrega() { return estadoEntrega; }
    public void setEstadoEntrega(String estadoEntrega) { this.estadoEntrega = estadoEntrega; }
}