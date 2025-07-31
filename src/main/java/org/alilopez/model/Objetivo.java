package org.alilopez.model;

import java.time.LocalDateTime;

public class Objetivo {
    private int idObjetivo;
    private int idUsuario;
    private String nombre;
    private String descripcion;
    private int totalPomodoros;
    private LocalDateTime fechaCreacion;
    private float duracionPomodoro;
    private float duracionDescanso;
    private int idSesion;

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotalPomodoros() {
        return totalPomodoros;
    }

    public void setTotalPomodoros(int totalPomodoros) {
        this.totalPomodoros = totalPomodoros;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }
}
