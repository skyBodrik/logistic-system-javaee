package logistic.web.dao;

import logistic.web.models.City;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Stateless
public class CityDao extends Dao<City> implements iCityDao {

    @Override
    public City getById(int id) {
        return (City)em.createNativeQuery("SELECT `id`, `name` FROM `cities` WHERE `id` = :id", City.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<City> getAll() {
        return (List<City>)em.createNativeQuery("SELECT `id`, `name` FROM `cities`", City.class).getResultList();
    }
}
