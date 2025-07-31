package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.alilopez.model.User;
import org.alilopez.service.ObjetivoService;
import org.alilopez.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void getAll(Context ctx) {
        try {
            List<User> users = userService.getAllUsers();
            ctx.json(users);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener usuarios");
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User user = userService.getByIdUser(id);
            if (user != null) {
                ctx.json(user);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Usuario no encontrado");
            }
        } catch (Exception e) {
            ctx.status(404).result("Error al obtener usuarios");
        }
    }

    public void create(Context ctx) {
        try {
            User user = ctx.bodyAsClass(User.class);
            userService.createUser(user);
            ctx.status(201).result("Usuario creado");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al crear usuario");
        }
    }

    public void registerUser(Context ctx) {
        try{
            User user = ctx.bodyAsClass(User.class);

            if (userService.existsByEmail(user.getEmail())){
                ctx.status(409).result("Correo ya registrado");
                return;
            }

            userService.createUser(user);
            ctx.status(201).result("Usuario creado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error al crear usuario");

            //Ver en consola el error específico del código 400 :)
            //e.printStackTrace(); // Imprime en consola
            //ctx.status(400).result("Error al registrar usuario: " + e.getMessage());
        }
    }

    public void loginUser(Context ctx) {
        try{
            User loginRequest = ctx.bodyAsClass(User.class);
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            User user = userService.findByEmailAndPassword(email, password);

            if (user != null) {
                ctx.json(user);
            } else {
                ctx.status(404).result("Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Error");
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User user = ctx.bodyAsClass(User.class);
            user.setIdUsuario(id); // Asegúrate de que el ID en la URL y en el objeto coincidan
            boolean updated = userService.updateUser(user);
            if (updated) {
                ctx.status(HttpStatus.OK).result("Usuario actualizado");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Usuario no encontrado");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al actualizar usuario");
        }
    }

    public void updateAvatar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User user = ctx.bodyAsClass(User.class);
            user.setIdUsuario(id); // Asegúrate de que el ID en la URL y en el objeto coincidan
            boolean updated = userService.updateAvatar(user);
            if (updated) {
                ctx.status(HttpStatus.OK).result("Avatar actualizado");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al actualizar avatar");
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                ctx.status(HttpStatus.OK).result("Usuario eliminado");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Usuario no encontrado");
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al eliminar usuario");
        }
    }
}
