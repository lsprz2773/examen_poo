package org.alilopez.dto;

public class CrearSesionTareaDTO {
    private int idUsuario;
    private int idTarea;
    private String duracionReal;
    private String descansoReal;
    private String intentos;
    private String estado;
    private String pomodoros;

    // Getters y setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    public String getDuracionReal() { return duracionReal; }
    public void setDuracionReal(String duracionReal) { this.duracionReal = duracionReal; }

    public String getDescansoReal() { return descansoReal; }
    public void setDescansoReal(String descansoReal) { this.descansoReal = descansoReal; }

    public String getIntentos() { return intentos; }
    public void setIntentos(String intentos) { this.intentos = intentos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPomodoros() { return pomodoros; }
    public void setPomodoros(String pomodoros) { this.pomodoros = pomodoros; }
}