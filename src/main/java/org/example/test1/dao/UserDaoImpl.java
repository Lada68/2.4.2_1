package org.example.test1.dao;

import org.example.test1.model.Role;
import org.example.test1.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Component
public class UserDaoImpl implements UserDao {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> showAllUsers() {

        return entityManagerFactory.createEntityManager()
                .createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void deleteUser(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);
        session.remove(session.get(User.class, id));
        em.flush();
        em.close();
    }

    @Override
    public void saveUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Set<Role> list = user.getRoles();
        for (Role role : list) {
            if (role.getName().equals("ROLE_ADMIN")) {
                role.setId(1);
            }
            if (role.getName().equals("ROLE_USER")) {
                role.setId(2);
            }
            user.setRoles(list);
        }
        em.persist(user);
        em.flush();
        em.close();

    }

    @Override
    public void updateUser(int id, User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);
        User user1 = session.get(User.class, id);
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAge(user.getAge());
        em.flush();
        em.close();
    }

    @Override
    public User UserById(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(User.class, id);
    }


    public User findByLogin(String username) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("from User u where u.username=:username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();

    }
}
