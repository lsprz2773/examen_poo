package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.UserController;

public class UserRoutes {
    private final UserController userController;
    public UserRoutes(UserController userController) {
        this.userController = userController;
    }
    public void register(Javalin app) {
        app.get("/usuarios", userController::getAll);
        app.post("/usuarios", userController::create);
        app.get("/usuarios/{id}", userController::getById);
        app.post("/usuarios/register", userController::registerUser);
        app.post("/usuarios/login", userController::loginUser);
        // Ejemplo de más rutas:
        app.put("/usuarios/{id}", userController::update);
        app.put("/usuarios/{id}/avatar", userController::updateAvatar);
        app.delete("/usuarios/{id}", userController::delete);
    }
}
