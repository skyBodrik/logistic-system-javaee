package logistic.web.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by bodrik on 24.04.17.
 */

abstract public class Dao<T> implements iDao<T> {

    protected EntityManager em = Persistence.createEntityManagerFactory("logistic").createEntityManager();

    @Override
    public void add(T obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
    }

    @Override
    public void update(T obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
    }

    @Override
    public void delete(T obj) {
        em.remove(obj);
    }

    @Override
    public void refresh(T obj) {
        em.refresh(obj);
    }
}
