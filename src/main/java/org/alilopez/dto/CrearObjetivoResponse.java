package org.alilopez.dto;

public class CrearObjetivoResponse {
    private int idObjetivo;
    private int idSesion;

    public CrearObjetivoResponse(int idObjetivo, int idSesion) {
        this.idObjetivo = idObjetivo;
        this.idSesion = idSesion;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }
}
