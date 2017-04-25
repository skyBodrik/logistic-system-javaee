package logistic.web.repositories;


import logistic.web.dao.CityDao;
import logistic.web.models.City;

import javax.ejb.EJB;
import java.util.List;

public class CitiesRepository {
    private static CitiesRepository instance;

    @EJB
    private CityDao dao = new CityDao();

    public static CitiesRepository getInstance() {
        if (instance == null) {
            instance = new CitiesRepository();
        }

        return instance;
    }

    public List<City> getAll() {
        return this.dao.getAll();
    }

    public City getById(int id) {
        return this.dao.getById(id);
    }
}
