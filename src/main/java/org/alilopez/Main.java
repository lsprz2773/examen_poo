package org.alilopez;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.alilopez.di.AppModule;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            // CORS
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });

            // Archivos estáticos (para evidencias, recursos, etc)
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "static"; // Carpeta raíz del proyecto
                staticFiles.location = Location.EXTERNAL; // Que busque fuera de /resources
                staticFiles.hostedPath = "/archivos"; // Ej: http://localhost:7000/archivos/miarchivo.pdf
                staticFiles.precompress = false;
            });
        }).start(7000);

        // Ruta raíz
        app.get("/", ctx -> ctx.result("API Javalin 2"));

        // Rutas de tu app
        AppModule.initUser().register(app);
        AppModule.initGrupo().register(app);
        AppModule.initTarea().register(app);
        AppModule.initObjetivo().register(app);
        AppModule.initSesion().register(app);
        AppModule.initUsuarioGrupo().register(app);
        AppModule.initEvidenciaTarea().register(app);
        AppModule.initEvidenciaObjetivo().register(app);
        AppModule.initInsignia().register(app);
        AppModule.initUsuarioInsignia().register(app);
        AppModule.initRachaUsuario().register(app);
        AppModule.initActividadDiaria().register(app);
        AppModule.initEstadisticas().register(app);
    }
}
