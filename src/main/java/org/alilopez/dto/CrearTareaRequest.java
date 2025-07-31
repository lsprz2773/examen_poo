package org.alilopez.dto;

import java.time.LocalDateTime;

public class CrearTareaRequest {
    public String titulo;
    public String descripcion;
    public String recursoURL;
    public LocalDateTime fechaEntrega;
    public int idGrupo;
    public float duracionPomodoro;
    public float duracionDescanso;
    public int intentos;

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

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
