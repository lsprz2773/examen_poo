package org.alilopez.di;

import org.alilopez.controller.*;
import org.alilopez.repository.*;
import org.alilopez.routes.*;
import org.alilopez.service.*;

public class AppModule {

    public static UserRoutes initUser() {
        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        UserController userController = new UserController(userService);
        return new UserRoutes(userController);
    }

    public static GrupoRoutes initGrupo() {
        GrupoRepository grupoRepository = new GrupoRepository();
        UsuarioGrupoRepository usuarioGrupoRepository = new UsuarioGrupoRepository();
        UsuarioGrupoService usuarioGrupoService = buildUsuarioGrupoService(grupoRepository);
        GrupoService grupoService = new GrupoService(grupoRepository, usuarioGrupoService, usuarioGrupoRepository);
        GrupoController grupoController = new GrupoController(grupoService, usuarioGrupoService);
        return new GrupoRoutes(grupoController);
    }

    public static TareaRoutes initTarea() {
        TareaRepository tareaRepository = new TareaRepository();
        UsuarioGrupoRepository usuarioGrupoRepository = new UsuarioGrupoRepository();

        TareaService tareaService = new TareaService(
                tareaRepository,
                usuarioGrupoRepository
        );

        TareaController tareaController = new TareaController(tareaService);
        return new TareaRoutes(tareaController);
    }


    public static ObjetivoRoutes initObjetivo() {
        ObjetivoRepository objetivoRepository = new ObjetivoRepository();
        SesionRepository sesionRepository = new SesionRepository();
        SesionObjetivoRepository sesionObjetivoRepository = new SesionObjetivoRepository();

        ObjetivoService objetivoService = new ObjetivoService(
                objetivoRepository,
                sesionRepository,
                sesionObjetivoRepository
        );

        ObjetivoController objetivoController = new ObjetivoController(objetivoService);
        return new ObjetivoRoutes(objetivoController);
    }

    public static SesionRoutes initSesion() {
        SesionRepository sesionRepository = new SesionRepository();
        SesionTareaRepository sesionTareaRepository = new SesionTareaRepository();
        SesionService sesionService = new SesionService(sesionRepository);
        SesionController sesionController = new SesionController(sesionService,sesionTareaRepository);
        return new SesionRoutes(sesionController);
    }

    public static UsuarioGrupoRoutes initUsuarioGrupo() {
        GrupoRepository grupoRepository = new GrupoRepository();
        UsuarioGrupoService usuarioGrupoService = buildUsuarioGrupoService(grupoRepository);
        UsuarioGrupoController usuarioGrupoController = new UsuarioGrupoController(usuarioGrupoService);
        return new UsuarioGrupoRoutes(usuarioGrupoController);
    }

    private static UsuarioGrupoService buildUsuarioGrupoService(GrupoRepository grupoRepository) {
        UsuarioGrupoRepository usuarioGrupoRepository = new UsuarioGrupoRepository();
        return new UsuarioGrupoService(usuarioGrupoRepository, grupoRepository);
    }

    public static EvidenciaTareaRoutes initEvidenciaTarea() {
        EvidenciaTareaRepository evidenciaTareaRepository = new EvidenciaTareaRepository();
        EvidenciaTareaService evidenciaTareaService = new EvidenciaTareaService(evidenciaTareaRepository);
        EvidenciaTareaController evidenciaTareaController = new EvidenciaTareaController(evidenciaTareaService);
        return new EvidenciaTareaRoutes(evidenciaTareaController);
    }

    public static EvidenciaObjetivoRoutes initEvidenciaObjetivo() {
        EvidenciaObjetivoRepository evidenciaObjetivoRepository = new EvidenciaObjetivoRepository();
        EvidenciaObjetivoService evidenciaObjetivoService = new EvidenciaObjetivoService(evidenciaObjetivoRepository);
        EvidenciaObjetivoController evidenciaObjetivoController = new EvidenciaObjetivoController(evidenciaObjetivoService);
        return new EvidenciaObjetivoRoutes(evidenciaObjetivoController);
    }

    public static InsigniaRoutes initInsignia() {
        InsigniaRepository insigniaRepository = new InsigniaRepository();
        SesionRepository sesionRepository = new SesionRepository();
        EvidenciaObjetivoRepository evidenciaObjetivoRepository = new EvidenciaObjetivoRepository();
        UsuarioInsigniaRepository usuarioInsigniaRepository = new UsuarioInsigniaRepository();

        UsuarioInsigniaService usuarioInsigniaService = new UsuarioInsigniaService(usuarioInsigniaRepository);
        InsigniaLogicaService insigniaLogicaService = new InsigniaLogicaService(
                sesionRepository,
                evidenciaObjetivoRepository,
                usuarioInsigniaService
        );

        InsigniaService insigniaService = new InsigniaService(insigniaRepository, insigniaLogicaService);
        InsigniaController insigniaController = new InsigniaController(insigniaService, usuarioInsigniaService);
        return new InsigniaRoutes(insigniaController);
    }

    public static UsuarioInsigniaRoutes initUsuarioInsignia() {
        UsuarioInsigniaRepository usuarioInsigniaRepository = new UsuarioInsigniaRepository();
        UsuarioInsigniaService usuarioInsigniaService = new UsuarioInsigniaService(usuarioInsigniaRepository);
        UsuarioInsigniaController usuarioInsigniaController = new UsuarioInsigniaController(usuarioInsigniaService);
        return new UsuarioInsigniaRoutes(usuarioInsigniaController);
    }

    public static RachaUsuarioRoutes initRachaUsuario() {
        RachaUsuarioRepository rachaUsuarioRepository = new RachaUsuarioRepository();
        RachaUsuarioService rachaUsuarioService = new RachaUsuarioService(rachaUsuarioRepository);
        RachaUsuarioController rachaUsuarioController = new RachaUsuarioController(rachaUsuarioService);
        return new RachaUsuarioRoutes(rachaUsuarioController);
    }

    public static ActividadDiariaRoutes initActividadDiaria() {
        ActividadDiariaRepository actividadDiariaRepository = new ActividadDiariaRepository();
        RachaUsuarioRepository rachaUsuarioRepository = new RachaUsuarioRepository();
        ActividadDiariaService actividadDiariaService = new ActividadDiariaService(actividadDiariaRepository, rachaUsuarioRepository);
        ActividadDiariaController actividadDiariaController = new ActividadDiariaController(actividadDiariaService);
        return new ActividadDiariaRoutes(actividadDiariaController);
    }

    public static EstadisticasRoutes initEstadisticas() {
        EstadisticasRepository estadisticasRepo = new EstadisticasRepository();
        EstadisticasService estadisticasService = new EstadisticasService(estadisticasRepo);
        EstadisticasController estadisticasController = new EstadisticasController(estadisticasService);
        return new EstadisticasRoutes(estadisticasController);
    }
}
