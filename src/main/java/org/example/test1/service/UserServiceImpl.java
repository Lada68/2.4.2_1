package org.example.test1.service;

import org.example.test1.dao.UserDao;
import org.example.test1.model.Role;
import org.example.test1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<User> showAllUsers() {
        return userDao.showAllUsers();
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {

      user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
        userDao.updateUser(id, user);
    }

    @Override
    @Transactional
    public User UserById(int id) {
        return userDao.UserById(id);
    }

    @Override
    @Transactional
    public User findByLogin(String username) {
        return userDao.findByLogin(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       User user =  userDao.findByLogin(s);
       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
               ,mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());

    }

}
