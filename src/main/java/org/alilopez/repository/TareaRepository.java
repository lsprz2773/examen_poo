package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.dto.AlumnoEvidenciaDTO;
import org.alilopez.dto.AlumnoNoEvidenciaDTO;
import org.alilopez.dto.TareaAlumnoDTO;
import org.alilopez.model.Tarea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaRepository {
    public List<Tarea> findTareas() throws SQLException {
        List<Tarea> tareas = new ArrayList<>();
        String query = "SELECT * FROM tarea";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tarea t = new Tarea();
                t.setIdTarea(rs.getInt("idTarea"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setRecursoURL(rs.getString("recursoURL"));
                t.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                t.setFechaEntrega(rs.getTimestamp("fechaEntrega").toLocalDateTime());
                t.setIdGrupo(rs.getInt("idGrupo"));
                t.setTotalPomodoros(rs.getInt("totalPomodoros"));
                t.setDuracionPomodoro(rs.getFloat("duracionPomodoro"));
                t.setDuracionDescanso(rs.getFloat("duracionDescanso"));

                tareas.add(t);
            }
        }
        return tareas;
    }

    public Tarea findByIdTarea(int idTarea) throws SQLException {
        Tarea tarea = null;
        String query = "SELECT * FROM tarea WHERE idTarea = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTarea);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tarea t = new Tarea();
                    t.setIdTarea(rs.getInt("idTarea"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setRecursoURL(rs.getString("recursoURL"));
                    t.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    t.setFechaEntrega(rs.getTimestamp("fechaEntrega").toLocalDateTime());
                    t.setIdGrupo(rs.getInt("idGrupo"));
                    t.setTotalPomodoros(rs.getInt("totalPomodoros"));
                    t.setDuracionPomodoro(rs.getFloat("duracionPomodoro"));
                    t.setDuracionDescanso(rs.getFloat("duracionDescanso"));
                    return t;
                }
            }
        }
        return tarea;
    }

    public int save(Tarea tarea) throws SQLException {
        String query = "INSERT INTO tarea (titulo, descripcion, recursoURL, fechaEntrega, idGrupo, totalPomodoros, duracionPomodoro, duracionDescanso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tarea.getTitulo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setString(3, tarea.getRecursoURL());
            stmt.setTimestamp(4, Timestamp.valueOf(tarea.getFechaEntrega()));
            stmt.setInt(5, tarea.getIdGrupo());
            stmt.setInt(6,tarea.getTotalPomodoros());
            stmt.setFloat(7, tarea.getDuracionPomodoro());
            stmt.setFloat(8, tarea.getDuracionDescanso());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la tarea insertada.");
                }
            }
        }
    }


    public boolean update(Tarea tarea) throws SQLException {
        String query = "UPDATE tarea SET titulo = ?, descripcion = ?, fechaEntrega = ? WHERE idTarea = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tarea.getTitulo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setTimestamp(3, Timestamp.valueOf(tarea.getFechaEntrega()));
            stmt.setInt(4, tarea.getIdTarea());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int idTarea) throws SQLException {
        String query = "DELETE FROM tarea WHERE idTarea = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTarea);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Tarea> findTareasByIdGrupo(int idGrupo) throws SQLException {
        List<Tarea> tareas = new ArrayList<>();
        String query = "SELECT * FROM tarea JOIN grupo ON tarea.idGrupo = grupo.idGrupo WHERE tarea.idGrupo = ?";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idGrupo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tarea t = new Tarea();
                    t.setIdTarea(rs.getInt("idTarea"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setRecursoURL(rs.getString("recursoURL"));
                    t.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    t.setFechaEntrega(rs.getTimestamp("fechaEntrega").toLocalDateTime());
                    t.setIdGrupo(rs.getInt("idGrupo"));
                    t.setTotalPomodoros(rs.getInt("totalPomodoros"));
                    t.setDuracionPomodoro(rs.getFloat("duracionPomodoro"));
                    t.setDuracionDescanso(rs.getFloat("duracionDescanso"));
                    tareas.add(t);
                }
            }
            return tareas;
        }
    }

    public List<AlumnoEvidenciaDTO> findAlumnosConTareaCompletada(int idTarea, int idGrupo) throws SQLException {
        List<AlumnoEvidenciaDTO> alumnos = new ArrayList<>();
        String query = """
        SELECT u.idUsuario, u.nombre,
        et.idEvidencia, et.fileURL, et.fechaEnvio, et.estadoEntrega
        FROM usuario u
        INNER JOIN usuario_grupo ug ON u.idUsuario = ug.idUsuario
        INNER JOIN evidencia_tarea et ON u.idUsuario = et.idUsuario
        WHERE ug.idGrupo = ? 
        AND ug.rol = 'alumno'
        AND et.idTarea = ? 
        AND et.estadoEntrega = 'entregada'
        """;



        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idTarea);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AlumnoEvidenciaDTO alumno = new AlumnoEvidenciaDTO();
                    // Datos del usuario
                    alumno.setIdUsuario(rs.getInt("idUsuario"));
                    alumno.setNombre(rs.getString("nombre"));

                    // Datos de la evidencia
                    alumno.setIdEvidencia(rs.getInt("idEvidencia"));
                    alumno.setFileURL(rs.getString("fileURL"));
                    alumno.setFechaEnvio(rs.getTimestamp("fechaEnvio").toLocalDateTime());
                    alumno.setEstadoEntrega(rs.getString("estadoEntrega"));

                    alumnos.add(alumno);
                }
            }
        }
        return alumnos;
    }

    public List<AlumnoNoEvidenciaDTO> findAlumnosQueNoEntregaron(int idTarea, int idGrupo) throws SQLException {
        List<AlumnoNoEvidenciaDTO> alumnos = new ArrayList<>();
        String query = """
        SELECT u.idUsuario, u.nombre, u.correo, u.avatar, u.fechaRegistro,
               et.idEvidencia, et.fileURL, et.fechaEnvio, et.estadoEntrega
        FROM usuario u
        INNER JOIN usuario_grupo ug ON u.idUsuario = ug.idUsuario
        LEFT JOIN evidencia_tarea et ON u.idUsuario = et.idUsuario AND et.idTarea = ?
        WHERE ug.idGrupo = ? 
        AND ug.rol = 'alumno'
        AND (et.idEvidencia IS NULL OR et.estadoEntrega = 'no_entregado')
        """;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idTarea);
            stmt.setInt(2, idGrupo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AlumnoNoEvidenciaDTO alumno = new AlumnoNoEvidenciaDTO();

                    // Datos del usuario
                    alumno.setIdUsuario(rs.getInt("idUsuario"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setCorreo(rs.getString("correo"));
                    alumno.setAvatar(rs.getString("avatar"));
                    alumno.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());

                    // Datos de evidencia (pueden ser null)
                    if (rs.getObject("idEvidencia") != null) {
                        // Caso: Agotó intentos
                        alumno.setIdEvidencia(rs.getInt("idEvidencia"));
                        alumno.setFileURL(rs.getString("fileURL"));
                        alumno.setFechaEnvio(rs.getTimestamp("fechaEnvio").toLocalDateTime());
                        alumno.setEstadoEntrega(rs.getString("estadoEntrega"));
                        alumno.setTipoNoEntrega("intentos_agotados");
                    } else {
                        // Caso: Nunca intentó la tarea
                        alumno.setIdEvidencia(null);
                        alumno.setFileURL(null);
                        alumno.setFechaEnvio(null);
                        alumno.setEstadoEntrega(null);
                        alumno.setTipoNoEntrega("sin_intentar");
                    }

                    alumnos.add(alumno);
                }
            }
        }
        return alumnos;
    }

    public List<TareaAlumnoDTO> findTareasCompletadasPorAlumno(int idUsuario, int idGrupo) throws SQLException {
        List<TareaAlumnoDTO> tareas = new ArrayList<>();
        String query = """
        SELECT t.idTarea, t.titulo, t.descripcion, t.recursoURL, 
               t.fechaCreacion, t.fechaEntrega, t.idGrupo,
               et.idEvidencia, et.fileURL, et.fechaEnvio, et.estadoEntrega
        FROM tarea t
        INNER JOIN evidencia_tarea et ON t.idTarea = et.idTarea
        WHERE t.idGrupo = ? 
        AND et.idUsuario = ?
        AND et.estadoEntrega = 'entregada'
        """;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TareaAlumnoDTO tarea = new TareaAlumnoDTO();
                    // Datos de la tarea
                    tarea.setIdTarea(rs.getInt("idTarea"));
                    tarea.setTitulo(rs.getString("titulo"));
                    tarea.setDescripcion(rs.getString("descripcion"));
                    tarea.setRecursoURL(rs.getString("recursoURL"));
                    tarea.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    tarea.setFechaEntrega(rs.getTimestamp("fechaEntrega").toLocalDateTime());
                    tarea.setIdGrupo(rs.getInt("idGrupo"));

                    // Datos de la evidencia
                    tarea.setIdEvidencia(rs.getInt("idEvidencia"));
                    tarea.setFileURL(rs.getString("fileURL"));
                    tarea.setFechaEnvio(rs.getTimestamp("fechaEnvio").toLocalDateTime());
                    tarea.setEstadoEntrega(rs.getString("estadoEntrega"));

                    tareas.add(tarea);
                }
            }
        }
        return tareas;
    }

    // 2. Tareas que el alumno NO ha hecho en un grupo
    public List<TareaAlumnoDTO> findTareasPendientesPorAlumno(int idUsuario, int idGrupo) throws SQLException {
        List<TareaAlumnoDTO> tareas = new ArrayList<>();
        String query = """
        SELECT t.idTarea, t.titulo, t.descripcion, t.recursoURL, 
               t.fechaCreacion, t.fechaEntrega, t.idGrupo
        FROM tarea t
        WHERE t.idGrupo = ?
        AND t.idTarea NOT IN (
            SELECT et.idTarea 
            FROM evidencia_tarea et 
            WHERE et.idUsuario = ?
        )
        """;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TareaAlumnoDTO tarea = new TareaAlumnoDTO();
                    // Solo datos de la tarea
                    tarea.setIdTarea(rs.getInt("idTarea"));
                    tarea.setTitulo(rs.getString("titulo"));
                    tarea.setDescripcion(rs.getString("descripcion"));
                    tarea.setRecursoURL(rs.getString("recursoURL"));
                    tarea.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    tarea.setFechaEntrega(rs.getTimestamp("fechaEntrega").toLocalDateTime());
                    tarea.setIdGrupo(rs.getInt("idGrupo"));

                    // Sin evidencia porque no la ha hecho
                    tarea.setIdEvidencia(null);
                    tarea.setFileURL(null);
                    tarea.setFechaEnvio(null);
                    tarea.setEstadoEntrega(null);

                    tareas.add(tarea);
                }
            }
        }
        return tareas;
    }
}
