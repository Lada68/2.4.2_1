package org.example.test1.service;

import org.example.test1.model.User;

import java.util.List;

public interface UserService {
    List<User> showAllUsers();

    void deleteUser(int id);

    void saveUser(User user);

    void updateUser(int id, User user);

    User UserById(int id);
}
