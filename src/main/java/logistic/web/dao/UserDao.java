package logistic.web.dao;

import logistic.web.models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Stateless
public class UserDao extends Dao<User> implements iUserDao {

    @Override
    public User getById(int id) {
        return (User)em.createNativeQuery("SELECT `id`, `name`, `email`, `password`, `type`, `phone`, `maxweight`, `width`, `height`, `length` FROM `users` WHERE `id`= :id", User.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<User> getAll() {
        return (List<User>)em.createNativeQuery("SELECT `id`, `name`, `email`, `password`, `type`, `phone`, `maxweight`, `width`, `height`, `length` FROM `users`", User.class).getResultList();
    }
}
