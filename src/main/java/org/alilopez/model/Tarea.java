package org.alilopez.model;

import java.time.LocalDateTime;

public class Tarea {
    private int idTarea;
    private String titulo;
    private String descripcion;
    private String recursoURL;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;
    private int idGrupo;

    private int totalPomodoros;
    private float duracionPomodoro;
    private float duracionDescanso;


    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRecursoURL() {
        return recursoURL;
    }

    public void setRecursoURL(String recursoURL) {
        this.recursoURL = recursoURL;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getTotalPomodoros() {
        return totalPomodoros;
    }

    public void setTotalPomodoros(int totalPomodoros) {
        this.totalPomodoros = totalPomodoros;
    }

    public float getDuracionPomodoro() {
        return duracionPomodoro;
    }

    public void setDuracionPomodoro(float duracionPomodoro) {
        this.duracionPomodoro = duracionPomodoro;
    }

    public float getDuracionDescanso() {
        return duracionDescanso;
    }

    public void setDuracionDescanso(float duracionDescanso) {
        this.duracionDescanso = duracionDescanso;
    }
}
