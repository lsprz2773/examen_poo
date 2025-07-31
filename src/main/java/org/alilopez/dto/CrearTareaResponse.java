package org.alilopez.dto;

public class CrearTareaResponse {
    private int idTarea;
    private int idSesion;
    private int idGrupo;

    public CrearTareaResponse(int idTarea, int idSesion, int idGrupo) {
        this.idTarea = idTarea;
        this.idSesion = idSesion;
        this.idGrupo = idGrupo;
    }

    // Getters y setters
    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    public int getIdSesion() { return idSesion; }
    public void setIdSesion(int idSesion) { this.idSesion = idSesion; }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
}
