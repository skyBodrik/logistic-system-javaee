package logistic.web.facade;

import logistic.web.models.City;
import logistic.web.repositories.CitiesRepository;

import java.util.List;

public class CityFacade {

    public static List<City> getCities() {
        return CitiesRepository.getInstance().getAll();
    }

    public static City getCityById(int id) {
        return CitiesRepository.getInstance().getById(id);
    }
}
