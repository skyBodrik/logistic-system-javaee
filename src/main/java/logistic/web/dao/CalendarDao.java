package logistic.web.dao;

import logistic.web.models.Calendar;
import logistic.web.models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Stateless
public class CalendarDao extends Dao<Calendar> implements iCalendarDao {

    @Override
    public Calendar getById(int id) {
        return (Calendar)em.createNativeQuery("SELECT `id`, `date`, `user_id`, `city_id` FROM `calendar` WHERE `id` = :id", Calendar.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Calendar> getAll() {
        return (List<Calendar>)em.createNativeQuery("SELECT `id`, `date`, `user_id`, `city_id` FROM `calendar`", Calendar.class).getResultList();
    }
}
