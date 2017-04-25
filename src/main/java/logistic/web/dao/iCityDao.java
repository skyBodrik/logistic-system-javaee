package logistic.web.dao;

import logistic.web.models.City;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Local
public interface iCityDao {

    void add(City city);

    void update(City city);

    void delete(City city);

    City getById(int id);

    List<City> getAll();
}
