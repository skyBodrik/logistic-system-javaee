package logistic.web.dao;

import logistic.web.models.Calendar;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Local
public interface iCalendarDao {

    void add(Calendar calendar);

    void update(Calendar calendar);

    void delete(Calendar calendar);

    Calendar getById(int id);

    List<Calendar> getAll();
}
