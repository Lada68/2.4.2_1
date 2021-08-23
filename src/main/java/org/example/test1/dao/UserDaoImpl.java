package org.example.test1.dao;

import org.example.test1.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

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
}
