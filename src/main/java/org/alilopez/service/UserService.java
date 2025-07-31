package org.alilopez.service;

import org.alilopez.model.User;
import org.alilopez.repository.UserRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private final UserRepository userRepo;
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    public List<User> getAllUsers() throws SQLException {
        return userRepo.findAll();
    }

    public User getByIdUser(int idUser) throws SQLException {
        return userRepo.findByIdUser(idUser);
    }

    public void createUser(User user) throws SQLException {
        userRepo.save(user);
    }

    public boolean existsByEmail(String email) throws SQLException {
        return userRepo.findByEmail(email) != null;
    }

    public User findByEmailAndPassword(String email, String password) throws SQLException {
        return userRepo.findByEmailAndPassword(email, password);
    }

    public boolean updateUser(User user) throws SQLException {
        return userRepo.update(user);
    }

    public boolean deleteUser(int id) throws SQLException {
        return userRepo.delete(id);
    }

    public boolean updateAvatar(User user) throws SQLException {
        return userRepo.updateAvatar(user);
    }
}
