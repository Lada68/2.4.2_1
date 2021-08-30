package org.example.test1.service;

import org.example.test1.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> showAllUsers();

    void deleteUser(int id);

    void saveUser(User user);

    void updateUser(int id, User user);

    User UserById(int id);

    User findByLogin(String username);

    @Override
    UserDetails loadUserByUsername(String s);
}
