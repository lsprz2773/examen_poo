package org.alilopez.dto;

import java.time.LocalDateTime;

public class AlumnoNoEvidenciaDTO {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String avatar;
    private LocalDateTime fechaRegistro;

    // Datos de evidencia (pueden ser null si nunca intentó)
    private Integer idEvidencia; // Puede ser null
    private String fileURL;      // Puede ser null
    private LocalDateTime fechaEnvio; // Puede ser null
    private String estadoEntrega; // Puede ser null o 'no_entregado'
    private String tipoNoEntrega; // 'sin_intentar' o 'intentos_agotados'

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Integer getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Integer idEvidencia) { this.idEvidencia = idEvidencia; }

    public String getFileURL() { return fileURL; }
    public void setFileURL(String fileURL) { this.fileURL = fileURL; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getEstadoEntrega() { return estadoEntrega; }
    public void setEstadoEntrega(String estadoEntrega) { this.estadoEntrega = estadoEntrega; }

    public String getTipoNoEntrega() { return tipoNoEntrega; }
    public void setTipoNoEntrega(String tipoNoEntrega) { this.tipoNoEntrega = tipoNoEntrega; }
}
